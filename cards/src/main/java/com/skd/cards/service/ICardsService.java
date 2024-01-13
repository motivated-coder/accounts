package com.skd.cards.service;

import com.skd.cards.dto.CardsDto;

public interface ICardsService {

    CardsDto issueNewCard(String mobileNumber);

    CardsDto fetchCardDetails(String mobileNumber);

    boolean updateCardDetails(CardsDto cardsDto);

    boolean deleteCardDetails(String mobileNumber);
}
