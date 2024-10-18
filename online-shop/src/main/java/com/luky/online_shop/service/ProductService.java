package com.luky.online_shop.service;

import com.luky.online_shop.dto.request.NewProductRequest;
import com.luky.online_shop.dto.request.SearchProductRequest;
import com.luky.online_shop.entity.Product;
import org.springframework.data.domain.Page;

public interface ProductService {
    Product create(NewProductRequest newProductRequest);
    Product getById(String id);
    Page<Product> getAll(SearchProductRequest searchProductRequest);
    Product update(Product product);
    void deleteById(String id);
}
