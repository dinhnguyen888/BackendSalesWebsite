package com.example.backendsaleswebsite.dto;

import lombok.*;

@Data
@Getter
@Setter
public class MomoRequest {
	private String partnerCode;
    private String partnerName;
    private String storeId;
    private String requestId;
    private String amount;
    private String orderId;
    private String orderInfo;
    private String redirectUrl;
    private String ipnUrl;
    private String lang;
    private String extraData;
    private String requestType;
    private String signature;
}
