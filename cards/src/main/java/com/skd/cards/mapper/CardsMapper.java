package com.skd.cards.mapper;

import com.skd.cards.dto.CardsDto;
import com.skd.cards.entity.Cards;

public class CardsMapper {

    private CardsMapper(){}
    public static CardsDto toCardsDto(Cards cards){
        return CardsDto.builder()
                .cardType(cards.getCardType())
                .cardNumber(cards.getCardNumber())
                .mobileNumber(cards.getMobileNumber())
                .totalLimit(cards.getTotalLimit())
                .amountUsed(cards.getAmountUsed())
                .availableAmount(cards.getAvailableAmount())
                .build();
    }

    public static Cards toCards(CardsDto cardsDto){
        return Cards.builder()
                .cardType(cardsDto.getCardType())
                .cardNumber(cardsDto.getCardNumber())
                .mobileNumber(cardsDto.getMobileNumber())
                .totalLimit(cardsDto.getTotalLimit())
                .amountUsed(cardsDto.getAmountUsed())
                .availableAmount(cardsDto.getAvailableAmount())
                .build();
    }

    public static void mapToCards(Cards cards, CardsDto cardsDto) {
        cards.setCardNumber(cardsDto.getCardNumber());
        cards.setCardType(cardsDto.getCardType());
        cards.setAmountUsed(cardsDto.getAmountUsed());
        cards.setAvailableAmount(cardsDto.getAvailableAmount());
        cards.setTotalLimit(cardsDto.getTotalLimit());
    }
}
