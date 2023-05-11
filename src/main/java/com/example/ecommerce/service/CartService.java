package com.example.ecommerce.service;

import com.example.ecommerce.dto.RequestDto.CheckoutCartRequestDto;
import com.example.ecommerce.dto.ResponseDto.CartResponseDto;
import com.example.ecommerce.dto.ResponseDto.ItemResponseDto;
import com.example.ecommerce.dto.ResponseDto.OrderResponseDto;
import com.example.ecommerce.entity.*;
import com.example.ecommerce.exception.InvalidCardException;
import com.example.ecommerce.repository.CardRepository;
import com.example.ecommerce.repository.CartRepository;
import com.example.ecommerce.repository.CustomerRepository;
import com.example.ecommerce.repository.OrderedRepository;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@Service
public class CartService {
    @Autowired
    CustomerRepository customerRepository;
    @Autowired
    CardRepository cardRepository;
    @Autowired
    OrderedRepository orderedRepository;
    @Autowired
    ProductService productService;
    @Autowired
    OrderService orderService;
    @Autowired
    CartRepository cartRepository;
    public CartResponseDto saveCart(Integer customerId, Item item){
        Customer customer = customerRepository.findById(customerId).get();
        Cart cart = customer.getCart();
        int newTotal = cart.getCartTotal() + item.getRequiredQuantity()*item.getProduct().getPrice();
        cart.setCartTotal(newTotal);
        System.out.println(cart.getItems().size());
        cart.getItems().add(item);
        System.out.println(cart.getItems().size());
        cart.setNumberOfItems(cart.getItems().size());
        Cart saveCart = cartRepository.save(cart);

        CartResponseDto cartResponseDto = CartResponseDto.builder()
                .cartTotal(saveCart.getCartTotal())
                .customerName(customer.getName())
                .numberOfItems(saveCart.getNumberOfItems())
                .build();

        List<ItemResponseDto> itemResponseDtoList = new ArrayList<>();
        for(Item itemEntity : saveCart.getItems()){
            ItemResponseDto itemResponseDto = new ItemResponseDto();
            itemResponseDto.setPriceOfOneItem(itemEntity.getProduct().getPrice());
            itemResponseDto.setTotalPrice(itemEntity.getRequiredQuantity()*itemEntity.getProduct().getPrice());
            itemResponseDto.setProductName(itemEntity.getProduct().getName());
            itemResponseDto.setQuantity(itemEntity.getRequiredQuantity());
            //add itemResponseDto in list
            itemResponseDtoList.add(itemResponseDto);
        }
        cartResponseDto.setItems(itemResponseDtoList);
        return cartResponseDto;
    }
    public OrderResponseDto checkOutCart(CheckoutCartRequestDto checkoutCartRequestDto)throws Exception{
        Customer customer;
        try{
            customer = customerRepository.findById(checkoutCartRequestDto.getCustomerId()).get();
        }catch (Exception e){
            throw new Exception("Customer Id does not valid !!!");
        }
        Card card = cardRepository.findByCardNo(checkoutCartRequestDto.getCardNo());
        if(card == null || card.getCvv() != checkoutCartRequestDto.getCvv() || card.getCustomer() != customer){
            throw new InvalidCardException("Your card is not Valid");
        }

        Cart cart = customer.getCart();
        if(cart.getNumberOfItems() == 0){
            throw new Exception("Cart is Empty !!!");
        }

        try{
            Ordered order = orderService.placeOrder(customer,card);
            customer.getOrderedList().add(order);
            Ordered saveOrder = orderedRepository.save(order);
            resetCart(cart);

            //lets make responseDto
            OrderResponseDto orderResponseDto = new OrderResponseDto();
            orderResponseDto.setOrderDate((Date) saveOrder.getOrderDate());
            orderResponseDto.setCardUsed(saveOrder.getCardUsed());
            orderResponseDto.setCustomerName(customer.getName());
            orderResponseDto.setOrderNo(saveOrder.getOrderNo());
            orderResponseDto.setTotalValue(saveOrder.getTotalValue());

            List<ItemResponseDto> items = new ArrayList<>();
            for(Item itemEntity : saveOrder.getItems()){
                ItemResponseDto itemResponseDto = new ItemResponseDto();
                itemResponseDto.setPriceOfOneItem(itemEntity.getProduct().getPrice());
                itemResponseDto.setTotalPrice(itemEntity.getRequiredQuantity()*itemEntity.getProduct().getPrice());
                itemResponseDto.setProductName(itemEntity.getProduct().getName());
                itemResponseDto.setQuantity(itemEntity.getRequiredQuantity());

                items.add(itemResponseDto);
            }
            orderResponseDto.setItems(items);
            return orderResponseDto;
        }
        catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }
    public void resetCart(Cart cart){
        cart.setCartTotal(0);
        cart.setNumberOfItems(0);
        cart.setItems(new ArrayList<>());
    }
}
