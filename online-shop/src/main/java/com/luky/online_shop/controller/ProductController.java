package com.luky.online_shop.controller;

import com.luky.online_shop.constant.APIUrl;
import com.luky.online_shop.dto.request.SearchProductRequest;
import com.luky.online_shop.entity.Product;
import com.luky.online_shop.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = APIUrl.PRODUCT_API)
public class ProductController {
    private final ProductService productService;

    @PostMapping
    public Product createNewProduct(@RequestBody Product product){
        return productService.create(product);
    }

    @GetMapping(path = APIUrl.PATH_VAR_ID)
    public Product getById(@PathVariable String id){
        return productService.getById(id);
    }

    @GetMapping
    public Page<Product> getAllProduct(
            @RequestParam(name = "page", defaultValue = "1") Integer page,
            @RequestParam(name = "size", defaultValue = "10") Integer size,
            @RequestParam(name = "sortBy", defaultValue = "name") String sortBy,
            @RequestParam(name = "direction", defaultValue = "asc") String direction,
            @RequestParam(name = "name", required = false) String name
    ){

        SearchProductRequest searchProductRequest = SearchProductRequest.builder()
                .page(page)
                .size(size)
                .sortBy(sortBy)
                .direction(direction)
                .name(name)
                .build();

        return productService.getAll(searchProductRequest);
    }

    @PutMapping
    public Product updateProduct(@RequestBody Product product){
        return productService.update(product);
    }

    @DeleteMapping(path = APIUrl.PATH_VAR_ID)
    public String deleteById(@PathVariable String id){
        productService.deleteById(id);
        return "OK, Success delete product with id " + id;
    }
}
