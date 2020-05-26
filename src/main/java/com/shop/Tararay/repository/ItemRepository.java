package com.shop.Tararay.repository;

import com.shop.Tararay.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository for item processing with DB
 */
@Repository
public interface ItemRepository extends JpaRepository<Item, Integer> {
}
