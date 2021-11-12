package com.example.billingservice.feign;

import com.example.billingservice.model.Product;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.hateoas.PagedModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name="PRODUCT-SERVICE")
public interface ProductItemRestClient {
    //pagination
    @GetMapping(path="/products")
    PagedModel<Product> pageProducts(@RequestParam(value="page") int page,@RequestParam(name="size") int size);

    @GetMapping(path = "/products/{id}")
    public Product findByProductId(@PathVariable(name = "id") Long id);

    @GetMapping(path = "/products/{id}")
    public Product getProductById(@PathVariable  Long id);
}
