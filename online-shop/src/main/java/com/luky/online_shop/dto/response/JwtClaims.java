package com.luky.online_shop.dto.response;

import lombok.*;

import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class JwtClaims {
    // data <- payload : data jwt
    private String userAccountId;
    private List<String> roles;
}
