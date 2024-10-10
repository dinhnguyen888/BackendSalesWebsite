package com.example.backendsaleswebsite.controller;

import com.example.backendsaleswebsite.dto.InventoryResponse;
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
    public ResponseEntity<Inventory> createInventory(@RequestBody InventoryResponse request) {
        Inventory createdInventory = inventoryService.createInventory(request);
        return ResponseEntity.ok(createdInventory);
    }


    // Lấy tất cả Inventory
    @GetMapping
    public ResponseEntity<List<Inventory>> getAllInventories() {
        try {
            List<Inventory> inventories = inventoryService.getAllInventories();
            return ResponseEntity.ok(inventories);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(null); // Hoặc bạn có thể trả về một thông điệp cụ thể
        }
    }

    // Lấy Inventory theo ID
    @GetMapping("/{id}")
    public ResponseEntity<Inventory> getInventoryById(@PathVariable Long id) {
        try {
            Inventory inventory = inventoryService.getInventoryById(id);
            return ResponseEntity.ok(inventory);
        } catch (RuntimeException e) {
            return ResponseEntity.status(404).body(null); // Không tìm thấy
        } catch (Exception e) {
            return ResponseEntity.status(500).body(null); // Lỗi máy chủ
        }
    }

    // Cập nhật Inventory
    @PutMapping("/{id}")
    public ResponseEntity<Inventory> updateInventory(@PathVariable Long id, @RequestBody Inventory inventoryDetails) {
        try {
            Inventory updatedInventory = inventoryService.updateInventory(id, inventoryDetails);
            return ResponseEntity.ok(updatedInventory);
        } catch (RuntimeException e) {
            return ResponseEntity.status(404).body(null); // Không tìm thấy
        } catch (Exception e) {
            return ResponseEntity.status(500).body(null); // Lỗi máy chủ
        }
    }

    // Xóa Inventory
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteInventory(@PathVariable Long id) {
        try {
            inventoryService.deleteInventory(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.status(404).build(); // Không tìm thấy
        } catch (Exception e) {
            return ResponseEntity.status(500).build(); // Lỗi máy chủ
        }
    }
}
