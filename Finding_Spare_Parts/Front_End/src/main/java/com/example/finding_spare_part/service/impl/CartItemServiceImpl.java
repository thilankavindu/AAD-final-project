package com.example.finding_spare_part.service.impl;

import com.example.finding_spare_part.dto.CartItemDTO;
import com.example.finding_spare_part.entity.CartItem;
import com.example.finding_spare_part.repo.CartItemRepository;
import com.example.finding_spare_part.service.CartItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CartItemServiceImpl implements CartItemService {

    @Autowired
    private CartItemRepository cartItemRepository;

    @Override
    public List<CartItemDTO> getCartItemsByCartId(Long cartId) {
        try {
            return cartItemRepository.findByCartId(cartId).stream()
                    .map(this::convertToDTO)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new RuntimeException("Error getting cart items: " + e.getMessage());
        }
    }

    @Override
    public void removeCartItem(Long id) {
        try {
            cartItemRepository.deleteById(id);
        } catch (Exception e) {
            throw new RuntimeException("Error removing cart item: " + e.getMessage());
        }
    }

    private CartItemDTO convertToDTO(CartItem cartItem) {
        CartItemDTO cartItemDTO = new CartItemDTO();
        cartItemDTO.setId(cartItem.getId());
        cartItemDTO.setCartId(cartItem.getCartId());
        cartItemDTO.setProductId(cartItem.getProductId());
        cartItemDTO.setQuantity(cartItem.getQuantity());
        return cartItemDTO;
    }
}