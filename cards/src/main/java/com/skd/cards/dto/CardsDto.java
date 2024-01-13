package com.skd.cards.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(name = "Cards", description = "Schema to hold Cards information")
public class CardsDto {
    @NotEmpty
    @Pattern(regexp = "(^$|[0-9]{10})", message = "MobileNumber must be of 10 digits")
    @Schema(description = "Mobile Number of a customer", example = "7042364107")
    private String mobileNumber;

    @NotEmpty(message = "CardNumber can't be null or empty")
    @Pattern(regexp = "(^$|[0-9]{12})", message = "CardNumber must be 12 digits")
    @Schema(description = "Card Number of card", example = "127042364107")
    private String cardNumber;

    @Schema(description = "CardType of a card", example = "CREDIT CARD")
    @NotEmpty(message = "CardType can't be null or empty")
    private String cardType;

    @Schema(description = "Total Limit of a card")
    @Positive(message = "TotalLimit can't be 0 or less")
    private int totalLimit;

    @Schema(description = "Total Amount used from total Limit of a card")
    @PositiveOrZero(message = "AmountUsed can't be negative")
    private int amountUsed;

    @Schema(description = "Total Amount available (to be used)")
    @PositiveOrZero(message = "AvailableAmount can't be negative")
    private int availableAmount;
}
