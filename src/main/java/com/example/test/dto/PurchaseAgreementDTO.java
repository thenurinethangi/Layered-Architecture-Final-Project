package com.example.test.dto;

import com.example.test.entity.PurchaseAgreement;
import com.example.test.view.tdm.PurchaseAgreementTM;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
public class PurchaseAgreementDTO {

    private String purchaseAgreementId;
    private String homeOwnerId;
    private String houseId;
    private double purchasePrice;
    private String signedDate;
    private String status;

    public PurchaseAgreementDTO toDTO(PurchaseAgreement purchaseAgreement){
        this.purchaseAgreementId = purchaseAgreement.getPurchaseAgreementId();
        this.homeOwnerId = purchaseAgreement.getHomeOwnerId();
        this.houseId = purchaseAgreement.getHouseId();
        this.purchasePrice = purchaseAgreement.getPurchasePrice();
        this.signedDate = purchaseAgreement.getSignedDate();
        this.status = purchaseAgreement.getStatus();
        return this;
    }

    public PurchaseAgreementDTO toDTO(PurchaseAgreementTM purchaseAgreementTM){
        this.purchaseAgreementId = purchaseAgreementTM.getPurchaseAgreementId();
        this.homeOwnerId = purchaseAgreementTM.getHomeOwnerId();
        this.houseId = purchaseAgreementTM.getHouseId();
        this.purchasePrice = purchaseAgreementTM.getPurchasePrice();
        this.signedDate = purchaseAgreementTM.getSignedDate();
        this.status = purchaseAgreementTM.getStatus();
        return this;
    }
}
