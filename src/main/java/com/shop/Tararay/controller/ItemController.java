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

    /**
     * @return response with list of all items from DB
     */
    @GetMapping
    public ResponseEntity getAllItems() {
        log.info("GET /items");
        List<Item> allItems = itemService.getAll();
        List<ItemDTO> itemDTOS = allItems.stream()
                .map(itemService::mapItemDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(itemDTOS);
    }

    /**
     * @param allId id of items from cart
     * @return response with list of items from DB that is in stock
     */
    @GetMapping("/cart")
    public ResponseEntity getCartItems(@RequestParam("allId[]") final Integer[] allId) {
        log.info("GET /items/cart All cart items by ids");
        List<Item> cartItems = itemService.getAllById(Arrays.asList(allId)).stream()
                .filter(item -> item.getAmount() > 0)
                .collect(Collectors.toList());
        List<ItemDTO> itemDTOS = cartItems.stream()
                .map(itemService::mapItemDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(itemDTOS);
    }

    /**
     * @param allId id of items from cart
     * @return ok response
     * <p>
     * Make order of items from cart that is in stock
     */
    @PostMapping("/cart")
    public ResponseEntity makeOrder(@RequestParam("allId[]") final Integer[] allId) {
        log.info("POST /items/cart Make order all items from cart");
        List<Item> itemsInStock = itemService.getAllById(Arrays.asList(allId)).stream()
                .filter(item -> item.getAmount() > 0)
                .collect(Collectors.toList());
        itemService.makeOrder(itemsInStock);
        return ResponseEntity.ok().build();
    }

    /**
     * @param item DTO with info for item creation
     *             <p>
     *             Create new item and save it to DB
     */
    @PostMapping
    public void createItem(@RequestBody final ItemDTO item) {
        log.info("POST /items Create new item with code={}", item.getCode());
        Item newItem = itemService.mapItem(item);
        itemService.create(newItem);
    }

    /**
     * @param itemCode code of item that will be updated
     * @param amount   amount to add
     * @return new item' amount
     *
     * Update of item amount
     */
    @PutMapping("/{itemCode}")
    public ResponseEntity updateItemAmount(@PathVariable final Integer itemCode, @RequestParam final Integer amount) {
        log.info("PUT /items Change item(code={}) amount on {} itm.", itemCode, amount);
        Integer newAmount = itemService.addAmount(itemCode, amount);
        return ResponseEntity.ok(newAmount);
    }
}
