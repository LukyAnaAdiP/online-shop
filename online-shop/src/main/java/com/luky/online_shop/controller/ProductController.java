package com.luky.online_shop.controller;

import com.luky.online_shop.constant.APIUrl;
import com.luky.online_shop.entity.Product;
import com.luky.online_shop.service.ProductService;
import lombok.RequiredArgsConstructor;
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
    public List<Product> getAllProduct(
            @RequestParam(name = "name", required = false) String name
    ){
        return productService.getAll(name);
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
