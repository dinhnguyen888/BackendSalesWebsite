package com.example.backendsaleswebsite.service;

import com.example.backendsaleswebsite.dto.InventoryDTO;
import com.example.backendsaleswebsite.model.Inventory;
import com.example.backendsaleswebsite.model.Product;
import com.example.backendsaleswebsite.repository.InventoryRepository;
import com.example.backendsaleswebsite.repository.ProductRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class InventoryService {

    @Autowired
    private InventoryRepository inventoryRepository;
    
    @Autowired
    private ProductRepository productRepository;

    // Tạo mới Inventory
    public InventoryDTO createInventory(InventoryDTO request) {
        // Tìm kiếm sản phẩm theo productId
        Product product = productRepository.findById(request.getProductId())
                .orElseThrow(() -> new RuntimeException("Product not found"));

        // Chuyển đổi InventoryDTO sang Inventory
        Inventory inventory = mapToEntity(request);
        inventory.setProduct(product); // Gán đối tượng Product từ productId

        inventory = inventoryRepository.save(inventory);
        return mapToDTO(inventory);
    }

    // Lấy tất cả Inventory
    public List<InventoryDTO> getAllInventories() {
        return inventoryRepository.findAll().stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    // Lấy Inventory theo ID
    public InventoryDTO getInventoryById(Long id) {
        Inventory inventory = inventoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Inventory not found"));
        return mapToDTO(inventory);
    }

    // Cập nhật Inventory
    public InventoryDTO updateInventory(Long id, InventoryDTO inventoryDetails) {
        Inventory inventory = mapToEntity(getInventoryById(id));
        inventory.setProductQuantity(inventoryDetails.getProductQuantity());
        inventory = inventoryRepository.save(inventory);
        return mapToDTO(inventory);
    }

    // Xóa Inventory
    public void deleteInventory(Long id) {
        Inventory inventory = mapToEntity(getInventoryById(id));
        inventoryRepository.delete(inventory);
    }

    // Phương thức chuyển đổi từ Entity sang DTO
    private InventoryDTO mapToDTO(Inventory inventory) {
        return new InventoryDTO(
                inventory.getInventoryId(),
                inventory.getProduct().getProductId(), // Lấy productId từ product
                inventory.getProductQuantity()
        );
    }

    // Phương thức chuyển đổi từ DTO sang Entity
    private Inventory mapToEntity(InventoryDTO inventoryDTO) {
        Inventory inventory = new Inventory();
        inventory.setInventoryId(inventoryDTO.getInventoryId()); // Gán ID nếu có
        inventory.setProductQuantity(inventoryDTO.getProductQuantity());
        return inventory;
    }
}
