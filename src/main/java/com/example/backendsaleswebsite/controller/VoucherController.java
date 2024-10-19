package com.example.backendsaleswebsite.controller;

import com.example.backendsaleswebsite.dto.VoucherDTO;
import com.example.backendsaleswebsite.service.VoucherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/vouchers")
public class VoucherController {

    @Autowired
    private VoucherService voucherService;

    // Tạo mới Voucher
    @PostMapping
    public ResponseEntity<VoucherDTO> createVoucher(@RequestBody VoucherDTO voucherDTO) {
        VoucherDTO createdVoucher = voucherService.createVoucher(voucherDTO);
        return ResponseEntity.ok(createdVoucher);
    }

    // Lấy tất cả Voucher
    @GetMapping
    public ResponseEntity<List<VoucherDTO>> getAllVouchers() {
        List<VoucherDTO> vouchers = voucherService.getAllVouchers();
        return ResponseEntity.ok(vouchers);
    }

    // Lấy Voucher theo ID
    @GetMapping("/{id}")
    public ResponseEntity<VoucherDTO> getVoucherById(@PathVariable Long id) {
        VoucherDTO voucher = voucherService.getVoucherById(id);
        return ResponseEntity.ok(voucher);
    }

    // Cập nhật Voucher
    @PutMapping("/{id}")
    public ResponseEntity<VoucherDTO> updateVoucher(@PathVariable Long id, @RequestBody VoucherDTO voucherDetails) {
        VoucherDTO updatedVoucher = voucherService.updateVoucher(id, voucherDetails);
        return ResponseEntity.ok(updatedVoucher);
    }

    // Xóa Voucher
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVoucher(@PathVariable Long id) {
        voucherService.deleteVoucher(id);
        return ResponseEntity.noContent().build();
    }
}
