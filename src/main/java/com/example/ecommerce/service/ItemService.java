package com.example.ecommerce.service;

import com.example.ecommerce.Enums.ProductStatus;
import com.example.ecommerce.dto.RequestDto.ItemRequestDto;
import com.example.ecommerce.entity.Customer;
import com.example.ecommerce.entity.Item;
import com.example.ecommerce.entity.Product;
import com.example.ecommerce.exception.InvalidCustomerException;
import com.example.ecommerce.repository.CustomerRepository;
import com.example.ecommerce.repository.ItemRepository;
import com.example.ecommerce.repository.ProductRepository;
import com.example.ecommerce.transformer.ItemTransformer;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ItemService {
    @Autowired
    CustomerRepository customerRepository;
    @Autowired
    ItemRepository itemRepository;
    @Autowired
    ProductRepository productRepository;
    public Item addItem(ItemRequestDto itemRequestDto)throws Exception{
        Customer customer;
        try{
            customer = customerRepository.findById(itemRequestDto.getCustomerId()).get();
        }catch (Exception e){
            throw new InvalidCustomerException("Customer Id is Invalid !!!");
        }
        Product product;
        try{
            product = productRepository.findById(itemRequestDto.getProductId()).get();
        }catch (Exception e){
            throw new Exception("Product does not present");
        }
        if(itemRequestDto.getRequiredQuantity()>product.getQuantity()||product.getProductStatus() != ProductStatus.AVAILABLE )
            throw new Exception("Product Out of Stock");

        Item item = ItemTransformer.ItemRequestDtoToItem(itemRequestDto);
        System.out.println(customer.getCart().getItems().size());
        item.setCart(customer.getCart());
        item.setProduct(product);
         product.getItemList().add(item);
         return itemRepository.save(item);
    }
}
