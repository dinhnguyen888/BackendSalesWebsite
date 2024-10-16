package com.example.backendsaleswebsite.service;

import com.example.backendsaleswebsite.model.Voucher;
import com.example.backendsaleswebsite.repository.VoucherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VoucherService {

    @Autowired
    private VoucherRepository voucherRepository;

    // Tạo mới Voucher
    public Voucher createVoucher(Voucher voucher) {
        return voucherRepository.save(voucher);
    }

    // Lấy tất cả Voucher
    public List<Voucher> getAllVouchers() {
        return voucherRepository.findAll();
    }

    // Lấy Voucher theo ID
    public Voucher getVoucherById(Long id) {
        return voucherRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Voucher not found"));
    }

    // Cập nhật Voucher
    public Voucher updateVoucher(Long id, Voucher voucherDetails) {
        Voucher voucher = getVoucherById(id);
        voucher.setVoucherName(voucherDetails.getVoucherName());
        voucher.setDiscount(voucherDetails.getDiscount());
        voucher.setAccount(voucherDetails.getAccount());
        return voucherRepository.save(voucher);
    }

    // Xóa Voucher
    public void deleteVoucher(Long id) {
        Voucher voucher = getVoucherById(id);
        voucherRepository.delete(voucher);
    }
}
