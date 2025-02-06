package com.example.test.entity;

import com.example.test.dto.PaymentDTO;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Payment {

    private String invoiceNo;
    private double amount;
    private String date;
    private String paymentType;
    private String tenantId;
    private int isFirstPayment;
    private int ownerPayment;

    public Payment(String invoiceNo, double amount, String date, String paymentType, String tenantId) {
        this.invoiceNo = invoiceNo;
        this.amount = amount;
        this.date = date;
        this.paymentType = paymentType;
        this.tenantId = tenantId;
    }

    public Payment toEntity(PaymentDTO paymentDto){
        this.invoiceNo = paymentDto.getInvoiceNo();
        this.amount = paymentDto.getAmount();
        this.date = paymentDto.getDate();
        this.paymentType = paymentDto.getPaymentType();
        this.tenantId = paymentDto.getTenantId();
        return this;

    }
}
