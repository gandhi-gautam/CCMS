package com.ccms.service;

import com.ccms.dto.Response;
import com.ccms.model.Transaction;

public interface TransactionService {
    Response makeTransaction(long cardId, int transactionAmountRequested);
}
