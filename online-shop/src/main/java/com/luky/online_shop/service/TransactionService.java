package com.luky.online_shop.service;

import com.luky.online_shop.dto.request.TransactionRequest;
import com.luky.online_shop.dto.response.TransactionResponse;

import java.util.List;

public interface TransactionService {
    TransactionResponse create(TransactionRequest transactionRequest);

    List<TransactionResponse> getAll();
}
