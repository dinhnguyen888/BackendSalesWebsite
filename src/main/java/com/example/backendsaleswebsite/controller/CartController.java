package com.example.backendsaleswebsite.controller;

import com.example.backendsaleswebsite.dto.CartResponse;
import com.example.backendsaleswebsite.model.Cart;
import com.example.backendsaleswebsite.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cart")
public class CartController {

    @Autowired
    private CartService cartService;

    @GetMapping("/{userId}")
    public Cart getCartById(@PathVariable Long userId) {
        return cartService.getCartByUserId(userId);
    }

    @PostMapping("/add")
    public CartResponse addToCart(@RequestParam Long userId, @RequestParam Long productId, @RequestParam int quantity) {
        return cartService.addToCart(userId, productId, quantity);
    }

    @DeleteMapping("/{cartId}")
    public void removeFromCart(@PathVariable Long cartId) {
        cartService.removeFromCart(cartId);
    }

    @PutMapping("/change-quantity")
    public Cart changeQuantity(@RequestParam Long cartId, @RequestParam int quantity) {
        return cartService.changeQuantity(cartId, quantity);
    }
}
