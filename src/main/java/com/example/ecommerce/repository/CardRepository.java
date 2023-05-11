package com.example.ecommerce.repository;

import com.example.ecommerce.Enums.CardType;
import com.example.ecommerce.entity.Card;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CardRepository extends JpaRepository<Card,Integer> {
    Card findByCardNo(String cardNo);
    List<Card> findByCardType(CardType cardType);
}
