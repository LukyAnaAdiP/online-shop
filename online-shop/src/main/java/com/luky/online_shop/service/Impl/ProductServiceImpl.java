package com.luky.online_shop.service.Impl;

import com.luky.online_shop.entity.Product;
import com.luky.online_shop.repository.ProductRepository;
import com.luky.online_shop.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    @Override
    public Product create(Product product) {
        return productRepository.saveAndFlush(product);
    }

    @Override
    public Product getById(String id) {
        Optional<Product> optionalProduct = productRepository.findById(id);
        if (optionalProduct.isEmpty()){
            throw new RuntimeException("product not found");
        }
        return optionalProduct.get();
    }

    @Override
    public List<Product> getAll(String name) {
        if (name != null){
            return productRepository.findAllByNameLike("%" + name + "%");
        }
        return productRepository.findAll();
    }

    @Override
    public Product update(Product product) {
        getById(product.getId());
        return productRepository.saveAndFlush(product);
    }

    @Override
    public void deleteById(String id) {
        Product currentProduct =getById(id);
        productRepository.delete(currentProduct);
    }
}
