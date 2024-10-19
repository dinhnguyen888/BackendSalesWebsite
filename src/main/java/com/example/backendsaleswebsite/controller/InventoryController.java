package com.example.backendsaleswebsite.controller;

import com.example.backendsaleswebsite.dto.InventoryDTO;
import com.example.backendsaleswebsite.model.Inventory;
import com.example.backendsaleswebsite.service.InventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/inventories")
public class InventoryController {

    @Autowired
    private InventoryService inventoryService;

    // Tạo mới Inventory
    @PostMapping
    public ResponseEntity<InventoryDTO> createInventory(@RequestBody InventoryDTO request) {
        InventoryDTO createdInventory = inventoryService.createInventory(request);
        return ResponseEntity.ok(createdInventory);
    }

    // Lấy tất cả Inventory
    @GetMapping
    public ResponseEntity<List<InventoryDTO>> getAllInventories() {
        List<InventoryDTO> inventories = inventoryService.getAllInventories();
        return ResponseEntity.ok(inventories);
    }

    // Lấy Inventory theo ID
    @GetMapping("/{id}")
    public ResponseEntity<InventoryDTO> getInventoryById(@PathVariable Long id) {
        InventoryDTO inventory = inventoryService.getInventoryById(id);
        return ResponseEntity.ok(inventory);
    }

    // Cập nhật Inventory
    @PutMapping("/{id}")
    public ResponseEntity<InventoryDTO> updateInventory(@PathVariable Long id, @RequestBody InventoryDTO inventoryDetails) {
        InventoryDTO updatedInventory = inventoryService.updateInventory(id, inventoryDetails);
        return ResponseEntity.ok(updatedInventory);
    }

    // Xóa Inventory
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteInventory(@PathVariable Long id) {
        inventoryService.deleteInventory(id);
        return ResponseEntity.noContent().build();
    }
}
