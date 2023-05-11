package com.example.ecommerce.dto.RequestDto;

import com.example.ecommerce.Enums.ProductCategory;
import jakarta.persistence.Access;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Repository;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class ProductRequestDto {
    int sellerId;

    String productName;

    int price;

    int quantity;

    ProductCategory productCategory;
}
