package com.skd.apigateway;

import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import io.github.resilience4j.timelimiter.TimeLimiterConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.circuitbreaker.resilience4j.ReactiveResilience4JCircuitBreakerFactory;
import org.springframework.cloud.circuitbreaker.resilience4j.Resilience4JConfigBuilder;
import org.springframework.cloud.client.circuitbreaker.Customizer;
import org.springframework.cloud.gateway.filter.ratelimit.KeyResolver;
import org.springframework.cloud.gateway.filter.ratelimit.RedisRateLimiter;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.time.LocalDateTime;

import static org.springframework.cloud.gateway.support.RouteMetadataUtils.CONNECT_TIMEOUT_ATTR;
import static org.springframework.cloud.gateway.support.RouteMetadataUtils.RESPONSE_TIMEOUT_ATTR;

@SpringBootApplication
public class ApigatewayApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApigatewayApplication.class, args);
	}

	@Bean
	public RouteLocator routeLocator(RouteLocatorBuilder routeLocatorBuilder){
		return routeLocatorBuilder.routes()
				.route(p -> p.path("/accounts/**")
						.filters(f -> f.rewritePath("/accounts/(?<segment>.*)","/${segment}")
								.addResponseHeader("X-Response-Time", LocalDateTime.now().toString())
								.circuitBreaker(config -> config.setName("accountCircuitBreakerConfig")
										.setFallbackUri("forward:/fallback/accounts")))
						.uri("lb://ACCOUNTS"))
				.route(p -> p.path("/loans/**")
						.filters(f -> f.rewritePath("/loans/(?<segment>.*)","/${segment}")
								.addResponseHeader("X-Response-Time", LocalDateTime.now().toString())
								.circuitBreaker(config -> config.setName("loansCircuitBreakerConfig")
										.setFallbackUri("forward:/fallback/loans")))
						.uri("lb://LOANS"))
				.route(p -> p.path("/cards/**")
						.filters(f -> f.rewritePath("/cards/(?<segment>.*)","/${segment}")
								.addResponseHeader("X-Response-Time", LocalDateTime.now().toString()))
						.uri("lb://CARDS"))
				.route(p -> p.path("/skdbank/accounts/**")
						.filters(f -> f.rewritePath("/skdbank/accounts/(?<segment>.*)","/${segment}")
								.addResponseHeader("X-Response-Time", LocalDateTime.now().toString()))
						.uri("lb://ACCOUNTS"))
				.route(p -> p.path("/skdbank/loans/**")
						.filters(f -> f.rewritePath("/skdbank/loans/(?<segment>.*)","/${segment}")
								.addResponseHeader("X-Response-Time", LocalDateTime.now().toString()))
						.uri("lb://LOANS"))
				.route(p -> p.path("/skdbank/cards/**")
						.filters(f -> f.rewritePath("/skdbank/cards/(?<segment>.*)","/${segment}")
								.addResponseHeader("X-Response-Time", LocalDateTime.now().toString()))
						.uri("lb://CARDS"))
				.route(p -> p.path("/api/v1/accounts/**")
						.filters(f -> f.rewritePath("/api/v1/accounts/(?<segment>.*)","/api/v1/accounts/${segment}")
								.addResponseHeader("X-Response-Time", LocalDateTime.now().toString())
								.metadata(RESPONSE_TIMEOUT_ATTR,2000)
								.metadata(CONNECT_TIMEOUT_ATTR,2000)
						)
						.uri("lb://ACCOUNTS"))

				.route(p -> p.path("/api/v1/loans//**")
						.filters(f -> f.rewritePath("/api/v1/loans/(?<segment>.*)","/api/v1/loans/${segment}")
								.addResponseHeader("X-Response-Time", LocalDateTime.now().toString())
								.metadata(RESPONSE_TIMEOUT_ATTR,2000)
								.metadata(CONNECT_TIMEOUT_ATTR,2000)
						)
						.uri("lb://LOANS"))
				.route(p -> p.path("/api/v1/cards/**")
						.filters(f -> f.rewritePath("/api/v1/cards/(?<segment>.*)","/api/v1/cards/${segment}")
										.addResponseHeader("X-Response-Time", LocalDateTime.now().toString())
								.retry(retryConfig -> retryConfig.setRetries(3)
										.setMethods(HttpMethod.GET)
										.setBackoff(Duration.ofMillis(100),Duration.ofMillis(1000),2,true))
								.requestRateLimiter(config -> config.setRateLimiter(redisRateLimiter()).setKeyResolver(userKeyResolver())))
						.uri("lb://CARDS"))
				.build();
	}

	@Bean
	public Customizer<ReactiveResilience4JCircuitBreakerFactory> defaultCustomizer() {
		return factory -> factory.configureDefault(id -> new Resilience4JConfigBuilder(id)
				.circuitBreakerConfig(CircuitBreakerConfig.ofDefaults())
				.timeLimiterConfig(TimeLimiterConfig.custom().timeoutDuration(Duration.ofSeconds(4)).build()).build());
	}

	@Bean
	public RedisRateLimiter redisRateLimiter() {
		return new RedisRateLimiter(1, 1, 1);
	}

	@Bean
	KeyResolver userKeyResolver() {
		return exchange -> Mono.justOrEmpty(exchange.getRequest().getHeaders().getFirst("user"))
				.defaultIfEmpty("anonymous");
	}

}
