package com.example.backendsaleswebsite.service;

import com.example.backendsaleswebsite.dto.CartResponse;
import com.example.backendsaleswebsite.model.Account;
import com.example.backendsaleswebsite.model.Cart;
import com.example.backendsaleswebsite.model.Product;
import com.example.backendsaleswebsite.repository.AccountRepository;
import com.example.backendsaleswebsite.repository.CartRepository;
import com.example.backendsaleswebsite.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CartService {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private ProductRepository productRepository;
    
    @Autowired
    private AccountRepository accountRepository;
    
    
    public Cart getCartByUserId(Long userId) {
        return cartRepository.findByAccount_userId(userId)
                .orElseThrow(() -> new RuntimeException("Cart not found for userId: " + userId));
    }


    public CartResponse addToCart(Long userId, Long productId, int quantity) {
        Account account = accountRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Account not found"));
       
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));
        
        long totalCost = product.getCost() * quantity;
        Cart cart = Cart.builder()
                .account(account)
                .product(product)
                .quantity(quantity)
                .build();
        
        cartRepository.save(cart);
        return new CartResponse(cart, totalCost);
    }


    public void removeFromCart(Long cartId) {
        Optional<Cart> optionalCart = cartRepository.findById(cartId);
        
        if (optionalCart.isPresent()) {
            Cart cart = optionalCart.get();
            cart.setProduct(null); 
            cart.setAccount(null); 
            cart.setQuantity(0); 
            cartRepository.save(cart); 
            cartRepository.deleteById(cartId);
        }
    }


    public Cart changeQuantity(Long cartId, int quantity) {
        Cart cart = cartRepository.findById(cartId)
                .orElseThrow(() -> new RuntimeException("Cart not found"));
        cart.setQuantity(quantity);
        return cartRepository.save(cart);
    }
}
