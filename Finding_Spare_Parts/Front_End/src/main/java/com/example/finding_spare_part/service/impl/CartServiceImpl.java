package com.example.finding_spare_part.service.impl;

import com.example.finding_spare_part.dto.CartDTO;
import com.example.finding_spare_part.dto.CartItemDTO;
import com.example.finding_spare_part.entity.Cart;
import com.example.finding_spare_part.entity.CartItem;
import com.example.finding_spare_part.repo.CartRepository;
import com.example.finding_spare_part.repo.CartItemRepository;
import com.example.finding_spare_part.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CartServiceImpl implements CartService {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private CartItemRepository cartItemRepository;

    @Override
    public CartDTO getCartByUserId(Long userId) {
        try {
            Cart cart = cartRepository.findByUserId(userId);
            if (cart == null) {
                cart = new Cart();
                cart.setUserId(userId);
                cartRepository.save(cart);
            }
            return convertToDTO(cart);
        } catch (Exception e) {
            throw new RuntimeException("Error getting cart: " + e.getMessage());
        }
    }

    @Override
    public CartDTO addItemToCart(Long userId, CartItemDTO cartItemDTO) {
        try {
            Cart cart = cartRepository.findByUserId(userId);
            if (cart == null) {
                cart = new Cart();
                cart.setUserId(userId);
                cartRepository.save(cart);
            }

            CartItem cartItem = new CartItem();
            cartItem.setCartId(cart.getId());
            cartItem.setProductId(cartItemDTO.getProductId());
            cartItem.setQuantity(cartItemDTO.getQuantity());
            cartItemRepository.save(cartItem);

            return convertToDTO(cart);
        } catch (Exception e) {
            throw new RuntimeException("Error adding item to cart: " + e.getMessage());
        }
    }

    @Override
    @Transactional
    public void clearCart(Long userId) {
        try {
            Cart cart = cartRepository.findByUserId(userId);
            if (cart != null) {
                cartItemRepository.deleteByCartId(cart.getId());
            }
        } catch (Exception e) {
            throw new RuntimeException("Error clearing cart: " + e.getMessage());
        }
    }

    private CartDTO convertToDTO(Cart cart) {
        CartDTO cartDTO = new CartDTO();
        cartDTO.setId(cart.getId());
        cartDTO.setUserId(cart.getUserId());
        List<CartItemDTO> cartItems = cartItemRepository.findByCartId(cart.getId()).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
        cartDTO.setCartItems(cartItems);
        return cartDTO;
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