package com.example.ecommerce.controller;

import com.example.ecommerce.Enums.ProductCategory;
import com.example.ecommerce.dto.RequestDto.ProductRequestDto;
import com.example.ecommerce.dto.ResponseDto.ProductResponseDto;
import com.example.ecommerce.exception.InvalidSellerException;
import com.example.ecommerce.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("product")
public class ProductController {
    @Autowired
    ProductService productService;
    // add product
    @PostMapping("/add")
    public ProductResponseDto addProduct(@RequestBody ProductRequestDto productRequestDto) throws InvalidSellerException{
        return productService.addProduct(productRequestDto);
    }
    // get all products of a particular category
    @GetMapping("/get/{category}")
    public List<ProductResponseDto> getAllProductByCategory(@PathVariable ProductCategory category){
        return productService.getAllProductsByCategory(category);
    }
    @GetMapping("/get/{price}/{category}")
    public List<ProductResponseDto> getAllProductsByPriceAndCategory(@PathVariable("price")int price,@PathVariable("category")String ProductCategory){
        return productService.getAllProductsByPriceAndCategory(price,ProductCategory);
    }
    // Get all product by seller email id

    // delete a product by seller id and product id

    // return top 5 cheapest products

    // return top 5 costliest products

    // return all out of stock products

    // return all available products

    // return all products that have quantity less than 10

    // return the cheapest product in a particular category

    // return the costliest product in a particular category
}
