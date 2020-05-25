package com.shop.Tararay.controller;

import com.shop.Tararay.dto.ItemDTO;
import com.shop.Tararay.entity.Item;
import com.shop.Tararay.service.ItemService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/items")
public class ItemController {

    private final ItemService itemService;

    @GetMapping
    public ResponseEntity getAllItems() {
        log.info("GET /items");
        List<Item> allItems = itemService.getAll();
        List<ItemDTO> itemDTOS = allItems.stream()
                .map(itemService::mapItemDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(itemDTOS);
    }

    @GetMapping("/cart")
    public ResponseEntity getCartItems(@RequestParam("allId[]") final Integer[] allId) {
        log.info("GET /items");
        List<Item> cartItems = itemService.getAllById(Arrays.asList(allId));
        List<ItemDTO> itemDTOS = cartItems.stream()
                .map(itemService::mapItemDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(itemDTOS);
    }

    @PostMapping("/cart")
    public ResponseEntity makeOrder(@RequestParam("allId[]") final Integer[] allId) {
        log.info("GET /items");
        List<Item> cartItems = itemService.getAllById(Arrays.asList(allId));
        itemService.makeOrder(cartItems);
        return ResponseEntity.ok().build();
    }

    @PostMapping
    public void createItem(@RequestBody final ItemDTO item) {
        Item newItem = itemService.mapItem(item);
        itemService.create(newItem);
    }

    @PutMapping("/{itemCode}")
    public ResponseEntity createItem(@PathVariable final Integer itemCode, @RequestParam final Integer amount) {
        Integer newAmount = itemService.addAmount(itemCode, amount);
        return ResponseEntity.ok(newAmount);
    }
}
