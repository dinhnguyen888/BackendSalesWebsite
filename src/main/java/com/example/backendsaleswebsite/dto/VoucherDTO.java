package com.example.backendsaleswebsite.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class VoucherDTO {
    private long voucherId;
    private String voucherName;
    private long discount;
    private long userId; // Tham chiếu đến Account (userId)
}
