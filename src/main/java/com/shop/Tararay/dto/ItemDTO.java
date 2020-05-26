package com.shop.Tararay.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

/**
 * Data transfer object of item
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ItemDTO {

    @NotNull
    private Integer code;

    @NotNull
    private String name;

    @NotNull
    private String model;

    @NotNull
    private Integer price;

    @NotNull
    private String image;

    @NotNull
    private String description;

    @NotNull
    private Integer amount;
}