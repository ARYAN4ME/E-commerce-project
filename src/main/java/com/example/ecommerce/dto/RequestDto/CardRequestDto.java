package com.example.ecommerce.dto.RequestDto;

import com.example.ecommerce.Enums.CardType;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.sql.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level= AccessLevel.PRIVATE)
@Builder
public class CardRequestDto {
    String CustomerMobNo;

    String cardNo;

    int cvv;

    Date expiryDate;

    CardType cardType;
}
