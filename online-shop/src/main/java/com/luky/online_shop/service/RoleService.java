package com.luky.online_shop.service;

import com.luky.online_shop.constant.UserRole;
import com.luky.online_shop.entity.Role;

public interface RoleService {
    Role getOrSave(UserRole role);
}
