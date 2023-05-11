package com.example.ecommerce.service;

import com.example.ecommerce.Enums.CardType;
import com.example.ecommerce.dto.RequestDto.CardRequestDto;
import com.example.ecommerce.dto.ResponseDto.CardResponseDto;
import com.example.ecommerce.entity.Card;
import com.example.ecommerce.entity.Customer;
import com.example.ecommerce.exception.InvalidCardException;
import com.example.ecommerce.exception.InvalidCustomerException;
import com.example.ecommerce.repository.CardRepository;
import com.example.ecommerce.repository.CustomerRepository;
import com.example.ecommerce.transformer.CardTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CardService {
    @Autowired
    CustomerRepository customerRepository;
    @Autowired
    CardRepository cardRepository;
    public CardResponseDto addCard(CardRequestDto cardRequestDto) throws InvalidCustomerException{
        Customer customer = customerRepository.findByMobNo(cardRequestDto.getCustomerMobNo());
        if(customer == null){
            throw new InvalidCustomerException("Customer dose not exists");
        }
        Card card = CardTransformer.CardRequestDtoToCard(cardRequestDto);
        card.setCustomer(customer);
        customer.getCards().add(card);
        customerRepository.save(customer);

        return CardResponseDto.builder()
                .customerName(customer.getName())
                .cardNo(card.getCardNo())
                .build();
    }
    public List<CardResponseDto> getCardByType(CardType cardType)throws InvalidCardException {
        List<Card> cards = cardRepository.findByCardType(cardType);
        List<CardResponseDto> cardResponseDtos = new ArrayList<>();
        for(Card card : cards){
            cardResponseDtos.add(CardTransformer.CardToCardResponseDto(card));
        }
        return cardResponseDtos;
    }
}
