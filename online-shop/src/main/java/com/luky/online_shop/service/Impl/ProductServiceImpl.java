package com.luky.online_shop.service.Impl;

import com.luky.online_shop.dto.request.SearchProductRequest;
import com.luky.online_shop.entity.Product;
import com.luky.online_shop.repository.ProductRepository;
import com.luky.online_shop.service.ProductService;
import com.luky.online_shop.specification.ProductSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

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
    public Page<Product> getAll(SearchProductRequest searchProductRequest) {
        if (searchProductRequest.getPage() <= 0){
            searchProductRequest.setPage(1);
        }

        String validSortBy;
        if ("name".equalsIgnoreCase(searchProductRequest.getSortBy()) || "price".equalsIgnoreCase(searchProductRequest.getSortBy()) || "stock".equalsIgnoreCase(searchProductRequest.getSortBy())){
            validSortBy = searchProductRequest.getSortBy();
        }else {
            validSortBy = "name";
        }

        Sort sort = Sort.by(Sort.Direction.fromString(searchProductRequest.getDirection()), validSortBy);

        Pageable pageable = PageRequest.of((searchProductRequest.getPage() - 1), searchProductRequest.getSize(), sort);

        Specification<Product> specification = ProductSpecification.getSpecification(searchProductRequest);

        return productRepository.findAll(specification, pageable);
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
