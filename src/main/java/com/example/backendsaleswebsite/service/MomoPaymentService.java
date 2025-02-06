package com.example.backendsaleswebsite.service;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.example.backendsaleswebsite.dto.MomoRequest;
import com.example.backendsaleswebsite.dto.MomoResponse;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.util.*;

@Service
public class MomoPaymentService {
	private final String endpoint = "https://test-payment.momo.vn/v2/gateway/api/create";
    private final String partnerCode = "MOMO4MUD20240115_TEST";
    private final String accessKey = "Ekj9og2VnRfOuIys";
    private final String secretKey = "PseUbm2s8QVJEbexsh8H3Jz2qa9tDqoa";
    private final String redirectUrl = "https://forhershop.vn";
    private final String ipnUrl = "https://forhershop.vn/shop";

    public MomoResponse createPayment(String amount) throws Exception {
        String orderId = String.valueOf(System.currentTimeMillis());
        String requestId = String.valueOf(System.currentTimeMillis());
        String orderInfo = "Thanh toán đơn hàng qua MoMo";
        String requestType = "payWithATM";
        String extraData = "";

        // Tạo chữ ký HMAC SHA256
        String rawHash = "accessKey=" + accessKey + "&amount=" + amount + "&extraData=" + extraData +
                "&ipnUrl=" + ipnUrl + "&orderId=" + orderId + "&orderInfo=" + orderInfo + "&partnerCode=" + partnerCode +
                "&redirectUrl=" + redirectUrl + "&requestId=" + requestId + "&requestType=" + requestType;
        String signature = hmacSHA256(rawHash, secretKey);

        MomoRequest requestBody = new MomoRequest();
        requestBody.setPartnerCode(partnerCode);
        requestBody.setPartnerName("Test");
        requestBody.setStoreId("MomoTestStore");
        requestBody.setRequestId(requestId);
        requestBody.setAmount(amount);
        requestBody.setOrderId(orderId);
        requestBody.setOrderInfo(orderInfo);
        requestBody.setRedirectUrl(redirectUrl);
        requestBody.setIpnUrl(ipnUrl);
        requestBody.setLang("vi");
        requestBody.setExtraData(extraData);
        requestBody.setRequestType(requestType);
        requestBody.setSignature(signature);

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<MomoRequest> requestEntity = new HttpEntity<>(requestBody, headers);

        ResponseEntity<MomoResponse> response = restTemplate.exchange(
                endpoint,
                HttpMethod.POST,
                requestEntity,
                new ParameterizedTypeReference<MomoResponse>() {}
            );
        return response.getBody();
    }

    private String hmacSHA256(String data, String key) throws Exception {
        Mac mac = Mac.getInstance("HmacSHA256");
        SecretKeySpec secretKeySpec = new SecretKeySpec(key.getBytes(), "HmacSHA256");
        mac.init(secretKeySpec);
        byte[] hash = mac.doFinal(data.getBytes());
        StringBuilder hexString = new StringBuilder();
        for (byte b : hash) {
            String hex = Integer.toHexString(0xff & b);
            if (hex.length() == 1) {
                hexString.append('0');
            }
            hexString.append(hex);
        }
        return hexString.toString();
    }
}
