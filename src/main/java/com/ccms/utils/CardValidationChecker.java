package com.ccms.utils;

import com.ccms.dto.Response;
import com.ccms.exception.EmptyFieldException;
import com.ccms.model.Card;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class CardValidationChecker {

    Logger logger = LoggerFactory.getLogger(UsersValidationChecker.class);

    public boolean isCardContainsAllMandatoryData(Card card, Response response) {
        if (card.getCardName() == null || card.getCardName().isEmpty()) {
            logger.error("Card Name not provided!");
            throw new EmptyFieldException("Error: Card Name not provided!");
        } else if (card.getAmountUsed() == 0) {
            logger.error("Amount Used Not provided or not equal to 0.");
            throw new EmptyFieldException("Error: Amount Used Not provided or not equal to 0.");
        } else if (card.getCreditLimit() <= 0) {
            logger.error("Credit Limit is Wrong!");
            throw new EmptyFieldException("Error: Credit Limit is Wrong!");
        }
        return true;
    }
}
