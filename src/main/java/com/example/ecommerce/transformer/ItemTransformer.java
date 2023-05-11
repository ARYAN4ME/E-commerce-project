package com.example.ecommerce.transformer;

import com.example.ecommerce.dto.RequestDto.ItemRequestDto;
import com.example.ecommerce.entity.Item;

public class ItemTransformer {
    public static Item ItemRequestDtoToItem(ItemRequestDto itemRequestDto){
        return Item.builder()
                .requiredQuantity(itemRequestDto.getRequiredQuantity())
                .build();
    }
}
