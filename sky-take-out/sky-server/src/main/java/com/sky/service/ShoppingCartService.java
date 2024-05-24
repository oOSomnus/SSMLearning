package com.sky.service;

import com.sky.dto.ShoppingCartDTO;
import com.sky.entity.ShoppingCart;

import java.util.List;


public interface ShoppingCartService {
    /**
     * Add Shopping Cart
     *
     * @param shoppingCartDTO
     */
    void addShoppingCart(ShoppingCartDTO shoppingCartDTO);

    /**
     * Show carts
     *
     * @return
     */
    List<ShoppingCart> showShoppingCart();

    /**
     * Clean cart
     */
    void cleanShoppingCart();
}
