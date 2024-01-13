package com.skd.cards.service.impl;

import com.skd.cards.dto.CardsDto;
import com.skd.cards.entity.Cards;
import com.skd.cards.exception.CardAlreadyExitsException;
import com.skd.cards.exception.ResourceNotFoundException;
import com.skd.cards.mapper.CardsMapper;
import com.skd.cards.repository.CardsRepository;
import com.skd.cards.service.ICardsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;
import java.util.Random;

import static com.skd.cards.constants.CardsConstants.*;

@Service
@RequiredArgsConstructor
public class CardsServiceImpl implements ICardsService {

    private final CardsRepository cardsRepository;
    @Override
    public CardsDto issueNewCard(String mobileNumber) {
        Optional<Cards> fetchedCard = cardsRepository.findByMobileNumber(mobileNumber);
        if(fetchedCard.isPresent()){
            throw new CardAlreadyExitsException("A Card has already been issued to this customer with mobile number  "+mobileNumber);
        }
        Cards newCard = generateNewCard(mobileNumber);
        Cards savedCard = cardsRepository.save(newCard);
        return CardsMapper.toCardsDto(savedCard);
    }

    @Override
    public CardsDto fetchCardDetails(String mobileNumber) {
        Cards fetchedCard = cardsRepository.findByMobileNumber(mobileNumber).orElseThrow(
                () -> new ResourceNotFoundException("Cards","mobileNumber",mobileNumber));
        return CardsMapper.toCardsDto(fetchedCard);
    }

    @Override
    public boolean updateCardDetails(CardsDto cardsDto) {
        boolean isUpdated = false;
        if(Objects.nonNull(cardsDto.getCardNumber())){
        Cards fetchedCard = cardsRepository.findByCardNumber(cardsDto.getCardNumber()).orElseThrow(
                () -> new ResourceNotFoundException("Cards","CardNumber",cardsDto.getCardNumber())
        );
        CardsMapper.mapToCards(fetchedCard, cardsDto);
        CardsMapper.toCardsDto(cardsRepository.save(fetchedCard));
        isUpdated = true;
        }
        return isUpdated;
    }

    @Override
    public boolean deleteCardDetails(String mobileNumber) {
        Cards fetchedCard = cardsRepository.findByMobileNumber(mobileNumber).orElseThrow(
                () -> new ResourceNotFoundException("Cards","mobileNumber",mobileNumber));
        cardsRepository.delete(fetchedCard);
        return true;
    }

    private Cards generateNewCard(String mobileNumber) {
        long randomCardNumber = 100000000000L + new Random().nextInt(900000000);
        return Cards.builder()
                .cardType(CREDIT_CARD)
                .cardNumber(Long.toString(randomCardNumber))
                .mobileNumber(mobileNumber)
                .totalLimit(TOTAL_LIMIT)
                .availableAmount(AVAILABLE_LIMIT)
                .amountUsed(0)
                .build();

    }
}
