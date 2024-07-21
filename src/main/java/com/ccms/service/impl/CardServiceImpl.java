package com.ccms.service.impl;

import com.ccms.dto.Response;
import com.ccms.exception.EmptyFieldException;
import com.ccms.exception.ResourceNotFoundException;
import com.ccms.model.Card;
import com.ccms.model.Users;
import com.ccms.repository.CardRepository;
import com.ccms.repository.UsersRepository;
import com.ccms.service.CardService;
import com.ccms.utils.CardValidationChecker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CardServiceImpl implements CardService {

    public static final String CARD_ADDED = "Card Added";
    public static final String USER_NOT_FOUND = "User Not Found";
    public static final String CARD_NOT_FOUND = "Card Not Found!";
    public static final String CARD_FOUND = "Card Found";
    public static final String CARD_ACTIVATED = "Card Activated";
    public static final String LIMIT_IS_WRONG = "Limit is wrong";
    public static final String CARD_UPDATED = "Card Updated";
    private final CardRepository cardRepository;
    private final UsersRepository usersRepository;
    private final CardValidationChecker cardValidationChecker;
    private final Logger logger = LoggerFactory.getLogger(CardServiceImpl.class);

    @Autowired
    public CardServiceImpl(CardRepository cardRepository, UsersRepository usersRepository,
                           CardValidationChecker cardValidationChecker) {
        this.cardRepository = cardRepository;
        this.usersRepository = usersRepository;
        this.cardValidationChecker = cardValidationChecker;
    }

    @Override
    public Response addNewCardToUser(long userId, Card card) {
        Response response = new Response();
        if (usersRepository.existsById(userId)) {
            Optional<Users> optionalUser = usersRepository.findById(userId);
            Users users = optionalUser.get();

            if (cardValidationChecker.isCardContainsAllMandatoryData(card, response)) {
                users.getCards().add(card);
                card.setUser(users);
                card = cardRepository.save(card);
                response.setData(card);
                response.setMessage(CARD_ADDED);
            }
        } else {
            throw new ResourceNotFoundException(USER_NOT_FOUND);
        }
        return response;
    }

    @Override
    public Response getCardInfo(long cardId) {
        Response response = new Response();
        Optional<Card> optionalCard = cardRepository.findById(cardId);
        if (optionalCard.isEmpty()) {
            throw new ResourceNotFoundException(CARD_NOT_FOUND);
        } else {
            response.setMessage(CARD_FOUND);
            response.setData(optionalCard.get());
        }
        return response;
    }

    @Override
    public Response updateCardInfo(long cardId, Card card) {
        Response response = new Response();
        Optional<Card> cardOptional = cardRepository.findById(cardId);
        if (cardOptional.isPresent()) {
            Card dbCard = cardOptional.get();
            updateCardDetails(dbCard, card);
            card = cardRepository.save(dbCard);
            response.setMessage(CARD_ADDED);
            response.setData(card);
        } else {
            throw new ResourceNotFoundException(CARD_NOT_FOUND);
        }
        return response;
    }

    @Override
    public Response activateCard(long cardId) {
        Response response = new Response();
        Optional<Card> optionalCard = cardRepository.findById(cardId);
        if (optionalCard.isPresent()) {
            Card card = optionalCard.get();
            card.setActivated(true);
            card = cardRepository.save(card);
            response.setData(card);
            response.setMessage(CARD_ACTIVATED);
        } else {
            throw new ResourceNotFoundException(CARD_NOT_FOUND);
        }
        return response;
    }

    @Override
    public Response adjustLimitOfTheCard(long cardId, int limit) {
        Response response = new Response();
        Optional<Card> optionalCard = cardRepository.findById(cardId);
        if (optionalCard.isPresent()) {
            Card card = optionalCard.get();
            if (limit > 0) {
                card.setCreditLimit(limit);
                card = cardRepository.save(card);
                response.setMessage(CARD_UPDATED);
                response.setData(card);
            } else {
                throw new EmptyFieldException(LIMIT_IS_WRONG);
            }
        } else {
            throw new ResourceNotFoundException(CARD_NOT_FOUND);
        }
        return response;
    }

    @Override
    public Response deActivateCard(long cardId) {
        Response response = new Response();
        Optional<Card> optionalCard = cardRepository.findById(cardId);
        if (optionalCard.isPresent()) {
            Card card = optionalCard.get();
            card.setActivated(false);
            card = cardRepository.save(card);
            response.setData(card);
            response.setMessage(CARD_ACTIVATED);
        } else {
            throw new ResourceNotFoundException(CARD_NOT_FOUND);
        }
        return response;
    }

    private void updateCardDetails(Card dbCard, Card card) {
        if (card != null) {
            if (card.getCardName() != null && !card.getCardName().isEmpty()) {
                dbCard.setCardName(card.getCardName());
            }

            if (card.getAmountUsed() > 0) {
                dbCard.setAmountUsed(card.getAmountUsed());
            }
        }
    }
}
