package com.luky.online_shop.service.Impl;

import com.luky.online_shop.constant.UserRole;
import com.luky.online_shop.entity.Role;
import com.luky.online_shop.repository.RoleRepository;
import com.luky.online_shop.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {
    private final RoleRepository roleRepository;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Role getOrSave(UserRole role) {
        return roleRepository.findByRole(role)
                .orElseGet(() -> roleRepository.saveAndFlush(
                        Role.builder()
                                .role(role)
                                .build()
                ));
    }
}
