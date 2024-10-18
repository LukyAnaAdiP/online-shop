package com.luky.online_shop.service;

import com.luky.online_shop.dto.response.JwtClaims;
import com.luky.online_shop.entity.UserAccount;

public interface JwtService {
    String generateToken(UserAccount userAccount);
    boolean verifyJwtToken(String token);
    JwtClaims getClaimsByToken(String token);
}
