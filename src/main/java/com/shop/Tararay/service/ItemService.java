package com.shop.Tararay.service;

import com.shop.Tararay.dto.ItemDTO;
import com.shop.Tararay.entity.Item;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

public interface ItemService {

    List<Item> getAll();

    List<Item> getAllById(List<Integer> allId);

    void create(Item item);

    ItemDTO mapItemDTO(Item item);

    Item mapItem(@RequestBody ItemDTO item);

    Integer addAmount(Integer itemCode, Integer amount);

    void makeOrder(List<Item> items);
}
