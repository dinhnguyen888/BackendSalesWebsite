package com.example.backendsaleswebsite.service;

import com.example.backendsaleswebsite.model.Inventory;
import com.example.backendsaleswebsite.model.Product;
import com.example.backendsaleswebsite.repository.InventoryRepository;
import com.example.backendsaleswebsite.repository.ProductRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.backendsaleswebsite.dto.*;

import java.util.List;

@Service
public class InventoryService {
    
    @Autowired
    private InventoryRepository inventoryRepository;
    @Autowired
    private ProductRepository productRepository;

    // Tạo mới Inventory
    public Inventory createInventory(InventoryResponse request) {
        // Tìm kiếm sản phẩm theo productId
        Product product = productRepository.findById(request.getProductId())
                .orElseThrow(() -> new RuntimeException("Product not found"));

        // Tạo đối tượng Inventory mới với product và productQuantity
        Inventory inventory = Inventory.builder()
                .product(product) // Chỉ gán đối tượng Product từ productId
                .productQuantity(request.getProductQuantity())
                .build();

        return inventoryRepository.save(inventory);
    }


    // Lấy tất cả Inventory
    public List<Inventory> getAllInventories() {
        return inventoryRepository.findAll();
    }

    // Lấy Inventory theo ID
    public Inventory getInventoryById(Long id) {
        return inventoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Inventory not found"));
    }

    // Cập nhật Inventory
    public Inventory updateInventory(Long id, Inventory inventoryDetails) {
        Inventory inventory = getInventoryById(id);
        inventory.setProduct(inventoryDetails.getProduct());
        inventory.setProductQuantity(inventoryDetails.getProductQuantity());
        return inventoryRepository.save(inventory);
    }

    // Xóa Inventory
    public void deleteInventory(Long id) {
        Inventory inventory = getInventoryById(id);
        inventoryRepository.delete(inventory);
    }
}
