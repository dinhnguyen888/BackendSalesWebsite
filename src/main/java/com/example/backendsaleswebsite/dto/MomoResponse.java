package com.example.backendsaleswebsite.dto;

import lombok.Data;

@Data
public class MomoResponse {
	private int resultCode;
    private String message;
    private String payUrl;
    private String deeplink;
    private String qrCodeUrl;
    private String requestId;
    private String orderId;
}
