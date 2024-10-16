package com.luky.online_shop.dto.request;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SearchCustomerRequest {
    private String name;
    private String phone;
    private String birthDate;
    private Boolean status;
}
