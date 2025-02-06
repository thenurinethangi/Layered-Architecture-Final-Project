package com.example.test.dto;

import com.example.test.entity.Payment;
import com.example.test.view.tdm.PaymentTM;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class PaymentDTO {

    private String invoiceNo;
    private double amount;
    private String date;
    private String paymentType;
    private String tenantId;
    private int isFirstPayment;
    private int isOwnerPayment;

    public PaymentDTO toDTO(Payment payment){
        this.invoiceNo = payment.getInvoiceNo();
        this.amount = payment.getAmount();
        this.date = payment.getDate();
        this.paymentType = payment.getPaymentType();
        this.tenantId = payment.getTenantId();
        this.isFirstPayment = payment.getIsFirstPayment();
        this.isOwnerPayment = payment.getOwnerPayment();
        return this;

    }

    public PaymentDTO toDTO(PaymentTM paymentTM){
        this.invoiceNo = paymentTM.getInvoiceNo();
        this.amount = paymentTM.getAmount();
        this.date = paymentTM.getDate();
        this.paymentType = paymentTM.getPaymentType();
        this.tenantId = paymentTM.getTenantId();
        return this;

    }
}
