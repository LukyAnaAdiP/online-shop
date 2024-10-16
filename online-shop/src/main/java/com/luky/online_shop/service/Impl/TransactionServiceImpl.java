package com.luky.online_shop.service.Impl;

import com.luky.online_shop.dto.request.TransactionRequest;
import com.luky.online_shop.dto.response.TransactionDetailResponse;
import com.luky.online_shop.dto.response.TransactionResponse;
import com.luky.online_shop.entity.Customer;
import com.luky.online_shop.entity.Product;
import com.luky.online_shop.entity.Transaction;
import com.luky.online_shop.entity.TransactionDetail;
import com.luky.online_shop.repository.TransactionRepository;
import com.luky.online_shop.service.CustomerService;
import com.luky.online_shop.service.ProductService;
import com.luky.online_shop.service.TransactionDetailService;
import com.luky.online_shop.service.TransactionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository transactionRepository;
    private final TransactionDetailService transactionDetailService;
    private final CustomerService customerService;
    private final ProductService productService;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public TransactionResponse create(TransactionRequest transactionRequest) {
        Customer customer = customerService.getById(transactionRequest.getCustomerId());

        Transaction trx = Transaction.builder()
                .customer(customer)
                .transDate(new Date())
                .build();
        transactionRepository.saveAndFlush(trx);
        log.info("Check detail dari trxDetail: {}", trx.getCustomer());

        List<TransactionDetail> trxDetail = transactionRequest.getTransactionDetails().stream()
                .map(detailRequest -> {
                    log.info("Quantity dari detail request: {}", detailRequest.getQty());
                    Product product = productService.getById(detailRequest.getProductId());

                    if (product.getStock() - detailRequest.getQty() < 0){
                        throw new RuntimeException("Out of stock");
                    }

                    product.setStock(product.getStock() - detailRequest.getQty());

                    return TransactionDetail.builder()
                            .product(product)
                            .transaction(trx)
                            .qty(detailRequest.getQty())
                            .productPrice(product.getPrice())
                            .build();
                }).toList();
        transactionDetailService.createBulk(trxDetail);
        trx.setTransactionDetails(trxDetail);

        List<TransactionDetailResponse> trxDetailResponse = trxDetail.stream()
                .map(detail -> TransactionDetailResponse.builder()
                        .id(detail.getId())
                        .productId(detail.getProduct().getId())
                        .productPrice(detail.getProductPrice())
                        .quantity(detail.getQty())
                        .build()).toList();

        return TransactionResponse.builder()
                .id(trx.getId())
                .customerId(trx.getCustomer().getId())
                .transDate(trx.getTransDate())
                .transactionDetails(trxDetailResponse)
                .build();
    }

    @Override
    public List<TransactionResponse> getAll() {
        List<Transaction> transactions = transactionRepository.findAll();

        return transactions.stream()
                .map(trx -> {
                    List<TransactionDetailResponse> trxDetailResponse = trx.getTransactionDetails().stream()
                            .map(detail -> TransactionDetailResponse.builder()
                                    .id(detail.getId())
                                    .productId(detail.getProduct().getId())
                                    .productPrice(detail.getProductPrice())
                                    .quantity(detail.getQty())
                                    .build()).toList();

                    return TransactionResponse.builder()
                            .id(trx.getId())
                            .customerId(trx.getCustomer().getId())
                            .transDate(trx.getTransDate())
                            .transactionDetails(trxDetailResponse)
                            .build();
                }).toList();
    }
}
