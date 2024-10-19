package com.example.backendsaleswebsite.service;

import com.example.backendsaleswebsite.dto.VoucherDTO;
import com.example.backendsaleswebsite.model.Voucher;
import com.example.backendsaleswebsite.repository.VoucherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class VoucherService {

    @Autowired
    private VoucherRepository voucherRepository;

    // Tạo mới Voucher
    public VoucherDTO createVoucher(VoucherDTO voucherDTO) {
        Voucher voucher = mapToEntity(voucherDTO);
        voucher = voucherRepository.save(voucher);
        return mapToDTO(voucher);
    }

    // Lấy tất cả Voucher
    public List<VoucherDTO> getAllVouchers() {
        return voucherRepository.findAll().stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    // Lấy Voucher theo ID
    public VoucherDTO getVoucherById(Long id) {
        Voucher voucher = voucherRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Voucher not found"));
        return mapToDTO(voucher);
    }

    // Cập nhật Voucher
    public VoucherDTO updateVoucher(Long id, VoucherDTO voucherDTO) {
        Voucher voucher = mapToEntity(getVoucherById(id));
        voucher.setVoucherName(voucherDTO.getVoucherName());
        voucher.setDiscount(voucherDTO.getDiscount());
        // Cập nhật Account nếu cần
        return mapToDTO(voucherRepository.save(voucher));
    }

    // Xóa Voucher
    public void deleteVoucher(Long id) {
        Voucher voucher = mapToEntity(getVoucherById(id));
        voucherRepository.delete(voucher);
    }

    // Phương thức chuyển đổi từ DTO sang Entity
    private Voucher mapToEntity(VoucherDTO voucherDTO) {
        Voucher voucher = new Voucher();
        voucher.setVoucherId(voucherDTO.getVoucherId());
        voucher.setVoucherName(voucherDTO.getVoucherName());
        voucher.setDiscount(voucherDTO.getDiscount());
        // Thiết lập Account nếu có thông tin
        return voucher;
    }

    // Phương thức chuyển đổi từ Entity sang DTO
    private VoucherDTO mapToDTO(Voucher voucher) {
        return new VoucherDTO(
                voucher.getVoucherId(),
                voucher.getVoucherName(),
                voucher.getDiscount(),
                voucher.getAccount().getUserId() // Lấy userId từ account
        );
    }
}
