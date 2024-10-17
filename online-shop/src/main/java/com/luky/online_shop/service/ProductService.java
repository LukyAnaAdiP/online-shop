package com.luky.online_shop.service;

import com.luky.online_shop.dto.request.SearchProductRequest;
import com.luky.online_shop.entity.Product;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ProductService {
    Product create(Product product);
    Product getById(String id);
    Page<Product> getAll(SearchProductRequest searchProductRequest);
    Product update(Product product);
    void deleteById(String id);
}
