package com.luky.online_shop.service;

import com.luky.online_shop.entity.TransactionDetail;

import java.util.List;

public interface TransactionDetailService {
    List<TransactionDetail> createBulk(List<TransactionDetail> transactionDetails);
}
