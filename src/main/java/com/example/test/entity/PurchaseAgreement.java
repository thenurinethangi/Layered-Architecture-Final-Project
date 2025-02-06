package com.example.test.entity;

import com.example.test.dto.PurchaseAgreementDTO;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
public class PurchaseAgreement {

    private String purchaseAgreementId;
    private String homeOwnerId;
    private String houseId;
    private double purchasePrice;
    private String signedDate;
    private String status;

    public PurchaseAgreement toEntity(PurchaseAgreementDTO purchaseAgreementDTO){
        this.purchaseAgreementId = purchaseAgreementDTO.getPurchaseAgreementId();
        this.homeOwnerId = purchaseAgreementDTO.getHomeOwnerId();
        this.houseId = purchaseAgreementDTO.getHouseId();
        this.purchasePrice = purchaseAgreementDTO.getPurchasePrice();
        this.signedDate = purchaseAgreementDTO.getSignedDate();
        this.status = purchaseAgreementDTO.getStatus();
        return this;
    }
}
