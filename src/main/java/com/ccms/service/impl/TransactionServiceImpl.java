package com.ccms.service.impl;

import com.ccms.dto.Response;
import com.ccms.exception.CardNotActivatedException;
import com.ccms.exception.EmptyFieldException;
import com.ccms.exception.LimitExceedingException;
import com.ccms.exception.ResourceNotFoundException;
import com.ccms.model.Card;
import com.ccms.model.Transaction;
import com.ccms.repository.CardRepository;
import com.ccms.repository.TransactionRepository;
import com.ccms.service.TransactionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class TransactionServiceImpl implements TransactionService {

    public static final String TRANSACTION_AMOUNT_REQUESTED = "Transaction Amount Requested";
    public static final String CARD_NOT_FOUND = "Card Not Found, check card Id";
    public static final String TRANSACTION_PERFORMED = "Transaction Performed";
    public static final String CARD_NOT_ACTIVATED = "Card Not Activated";
    public static final String CARD_LIMIT_EXCEEDS = "Card Limit Exceeds";
    private final static Logger logger = LoggerFactory.getLogger(TransactionServiceImpl.class);
    private final TransactionRepository transactionRepository;
    private final CardRepository cardRepository;

    @Autowired
    public TransactionServiceImpl(TransactionRepository transactionRepository, CardRepository cardRepository) {
        this.transactionRepository = transactionRepository;
        this.cardRepository = cardRepository;
    }

    @Override
    public Response makeTransaction(long cardId, int transactionAmountRequested) {
        Response response = new Response();
        if (transactionAmountRequested >= 0) {
            Optional<Card> optionalCard = cardRepository.findById(cardId);
            if (optionalCard.isPresent()) {
                Card card = optionalCard.get();
                int totalAmount = card.getAmountUsed() + transactionAmountRequested;
                if (card.isActivated()) {
                    if (card.getCreditLimit() >= totalAmount) {
                        Transaction transaction = performTransaction(card, transactionAmountRequested);
                        response.setMessage(TRANSACTION_PERFORMED);
                        response.setData(transaction);
                    } else {
                        throw new LimitExceedingException(CARD_LIMIT_EXCEEDS);
                    }
                } else {
                    throw new CardNotActivatedException(CARD_NOT_ACTIVATED);
                }
            } else {
                throw new ResourceNotFoundException(CARD_NOT_FOUND);
            }
        } else {
            throw new EmptyFieldException(TRANSACTION_AMOUNT_REQUESTED);
        }
        return response;
    }

    @Transactional
    private Transaction performTransaction(Card card, int amountRequested) {
        updateCardAmount(card, amountRequested);
        return createTransaction(amountRequested);
    }

    private void updateCardAmount(Card card, int amountRequested) {
        card.setAmountUsed(card.getAmountUsed() + amountRequested);
        cardRepository.save(card);
    }

    private Transaction createTransaction(int amountRequested) {
        Transaction transaction = new Transaction();
        transaction.setAmount(amountRequested);
        transaction.setDateOfTransaction(LocalDateTime.now());
        transaction = transactionRepository.save(transaction);
        return transaction;
    }
}
