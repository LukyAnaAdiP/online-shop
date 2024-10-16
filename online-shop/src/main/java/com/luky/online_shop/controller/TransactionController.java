package com.luky.online_shop.controller;

import com.luky.online_shop.constant.APIUrl;
import com.luky.online_shop.dto.request.TransactionRequest;
import com.luky.online_shop.dto.response.TransactionResponse;
import com.luky.online_shop.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = APIUrl.TRANSACTION_API)
public class TransactionController {
    private final TransactionService transactionService;

    @PostMapping
    public TransactionResponse createNewTransaction(
            @RequestBody TransactionRequest transactionRequest
            ){
        return transactionService.create(transactionRequest);
    }

    @GetMapping
    public List<TransactionResponse> getAllTransaction(){return transactionService.getAll();}
}
