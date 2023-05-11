package com.example.ecommerce.service;

import com.example.ecommerce.dto.RequestDto.CustomerRequestDto;
import com.example.ecommerce.dto.ResponseDto.CustomerResponseDto;
import com.example.ecommerce.entity.Cart;
import com.example.ecommerce.entity.Customer;
import com.example.ecommerce.exception.MobileNoAlreadyPresentException;
import com.example.ecommerce.repository.CustomerRepository;
import com.example.ecommerce.transformer.CustomerTransformer;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CustomerService {
    @Autowired
    CustomerRepository customerRepository;
    public CustomerResponseDto addCustomer(CustomerRequestDto customerRequestDto)throws MobileNoAlreadyPresentException{
        if(customerRepository.findByMobNo(customerRequestDto.getMobNo()) != null)
            throw new MobileNoAlreadyPresentException("Mobile Number is Already Exists");

        //request dto to customer
        Customer customer = CustomerTransformer.CustomerRequestDtoToCustomer(customerRequestDto);

        Cart cart = Cart.builder()
                .cartTotal(0)
                .numberOfItems(0)
                .customer(customer)
                .build();
        customer.setCart(cart);

        Customer savedCustomer = customerRepository.save(customer);
        return CustomerTransformer.CustomerToCustomerResponseDto(savedCustomer);
    }
}
