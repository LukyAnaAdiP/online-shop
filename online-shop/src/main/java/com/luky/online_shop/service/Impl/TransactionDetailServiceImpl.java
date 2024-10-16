package com.luky.online_shop.service.Impl;

import com.luky.online_shop.entity.TransactionDetail;
import com.luky.online_shop.repository.TransactionDetailRepository;
import com.luky.online_shop.service.TransactionDetailService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TransactionDetailServiceImpl implements TransactionDetailService {
    private final TransactionDetailRepository transactionDetailRepository;
    @Override
    public List<TransactionDetail> createBulk(List<TransactionDetail> transactionDetails) {
        return transactionDetailRepository.saveAllAndFlush(transactionDetails);
    }
}
