package com.example.test.view.tdm;

import com.example.test.dto.PaymentDTO;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class PaymentTM {

    private String invoiceNo;
    private double amount;
    private String date;
    private String paymentType;
    private String tenantId;

    public PaymentTM toTM(PaymentDTO paymentDTO){
        this.invoiceNo = paymentDTO.getInvoiceNo();
        this.amount = paymentDTO.getAmount();
        this.date = paymentDTO.getDate();
        this.paymentType = paymentDTO.getPaymentType();
        this.tenantId = paymentDTO.getTenantId();
        return this;

    }
}
