package com.example.test.view.tdm;

import com.example.test.dto.RequestDTO;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
public class RequestTM {

    private String requestId;
    private String customerId;
    private String rentOrBuy;
    private String houseType;
    private String leaseTurnDesire;
    private String allDocumentsProvided;
    private String qualifiedCustomerOrNot;
    private String agreesToAllTermsAndConditions;
    private String isPaymentsCompleted;
    private String customerRequestStatus;
    private String requestStatus;
    private String houseId;


    public RequestTM toTM(RequestDTO requestDTO){
        this.requestId = requestDTO.getRequestId();
        this.customerId = requestDTO.getCustomerId();
        this.rentOrBuy = requestDTO.getRentOrBuy();
        this.houseType = requestDTO.getHouseType();
        this.leaseTurnDesire = requestDTO.getLeaseTurnDesire();
        this.allDocumentsProvided = requestDTO.getAllDocumentsProvided();
        this.qualifiedCustomerOrNot = requestDTO.getQualifiedCustomerOrNot();
        this.agreesToAllTermsAndConditions = requestDTO.getAgreesToAllTermsAndConditions();
        this.isPaymentsCompleted = requestDTO.getIsPaymentsCompleted();
        this.requestStatus = requestDTO.getRequestStatus();
        this.customerRequestStatus = requestDTO.getCustomerRequestStatus();
        this.houseId = requestDTO.getHouseId();
        return this;
    }
}
