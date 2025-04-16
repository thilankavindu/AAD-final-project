package com.example.finding_spare_part.service;

import com.example.finding_spare_part.dto.CartItemDTO;

import java.util.List;

public interface CartItemService {
    List<CartItemDTO> getCartItemsByCartId(Long cartId);
    void removeCartItem(Long id);
}