package com.sky.service;

import com.sky.dto.ShoppingCartDTO;


public interface ShoppingCartService {
    /**
     * Add Shopping Cart
     *
     * @param shoppingCartDTO
     */
    void addShoppingCart(ShoppingCartDTO shoppingCartDTO);
}
