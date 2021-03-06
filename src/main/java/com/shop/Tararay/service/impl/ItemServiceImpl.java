package com.shop.Tararay.service.impl;

import com.shop.Tararay.dto.ItemDTO;
import com.shop.Tararay.entity.Item;
import com.shop.Tararay.repository.ItemRepository;
import com.shop.Tararay.service.ItemService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import javax.persistence.EntityNotFoundException;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class ItemServiceImpl implements ItemService {

    private final ItemRepository itemRepository;
    private final ModelMapper modelMapper;

    /**
     * @return list of all Items from DB
     */
    @Override
    public List<Item> getAll() {
        return itemRepository.findAll();
    }

    /**
     * @param allId id of items that should be returned from DB
     * @return list of all Items by id from DB
     */
    @Override
    public List<Item> getAllById(List<Integer> allId) {
        return itemRepository.findAllById(allId);
    }

    /**
     * @param item new Item
     *
     * Save new Item to DB
     */
    @Override
    public void create(Item item) {
        itemRepository.save(item);
    }

    /**
     * @param item entity
     * @return DTO of entity
     */
    @Override
    public ItemDTO mapItemDTO(Item item) {
        ItemDTO itemDTO = modelMapper.map(item, ItemDTO.class);
        itemDTO.setImage(StringUtils.toEncodedString(item.getImage(), StandardCharsets.UTF_8));
        return itemDTO;
    }

    /**
     * @param item DTO of entity
     * @return entity by DTO
     */
    @Override
    public Item mapItem(@RequestBody ItemDTO item) {
        Item newItem = modelMapper.map(item, Item.class);
        newItem.setImage(item.getImage().getBytes(StandardCharsets.UTF_8));
        return newItem;
    }

    /**
     * @param itemCode code of item that will be updated
     * @param amount amount to add
     * @return new amount of item
     */
    @Override
    public Integer addAmount(Integer itemCode, Integer amount) {
        Item item = itemRepository.findById(itemCode)
                .orElseThrow(EntityNotFoundException::new);
        item.setAmount(item.getAmount() + amount);
        itemRepository.save(item);
        return item.getAmount();
    }

    /**
     * @param items items that will be ordered
     *
     * Changed items(from cart) amount
     */
    @Override
    public void makeOrder(List<Item> items) {
        items.forEach(item -> item.setAmount(item.getAmount() - 1));
        itemRepository.saveAll(items);
    }
}
