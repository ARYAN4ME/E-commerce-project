package com.example.ecommerce.repository;

import com.example.ecommerce.Enums.ProductCategory;
import com.example.ecommerce.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product,Integer> {
    List<Product> findByProductCategory(ProductCategory productCategory);
    @Query(value = "select * from Product p where p.price >= :price and p.product_Category=:category",nativeQuery = true)
    List<Product> getAllProductsByPriceAndCategory(int price, String category);
}
