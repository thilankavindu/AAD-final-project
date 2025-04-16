package com.example.finding_spare_part.service;

import com.example.finding_spare_part.dto.CartDTO;
import com.example.finding_spare_part.dto.CartItemDTO;

public interface CartService {
    CartDTO getCartByUserId(Long userId);
    CartDTO addItemToCart(Long userId, CartItemDTO cartItemDTO);
    void clearCart(Long userId);
}