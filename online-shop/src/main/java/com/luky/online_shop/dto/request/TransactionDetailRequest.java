package com.luky.online_shop.dto.request;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TransactionDetailRequest {
    private String productId;
    private Integer qty;
}
