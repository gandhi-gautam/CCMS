package com.ccms.service;

import com.ccms.dto.Response;
import com.ccms.model.Card;

public interface CardService {

    Response addNewCardToUser(long userId, Card card);

    Response getCardInfo(long cardId);

    Response updateCardInfo(long cardId, Card card);

    Response activateCard(long cardId);

    Response adjustLimitOfTheCard(long cardId, int limit);

    Response deActivateCard(long cardId);

}
