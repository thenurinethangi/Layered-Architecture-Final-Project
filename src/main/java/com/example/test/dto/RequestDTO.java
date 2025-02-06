package com.example.test.dto;

import com.example.test.entity.Request;
import com.example.test.view.tdm.RequestTM;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
public class RequestDTO {

    private String requestId;
    private String customerId;
    private String date;
    private String rentOrBuy;
    private String houseType;
    private int noOfFamilyMembers;
    private double monthlyIncome;
    private double annualIncome;
    private String bankAccountDetails;
    private String reasonForLeaving;
    private String estimatedMonthlyBudgetForRent;
    private String leaseTurnDesire;
    private String previousLandlordNumber;
    private String isSmoking;
    private String hasCriminalBackground;
    private String hasPets;
    private String allDocumentsProvided;
    private String qualifiedCustomerOrNot;
    private String agreesToAllTermsAndConditions;
    private String isPaymentsCompleted;
    private String customerRequestStatus;
    private String requestStatus;
    private String houseId;

    public RequestDTO(String requestId, String customerId, String date, String rentOrBuy, String houseType, int noOfFamilyMembers, double monthlyIncome, double annualIncome, String bankAccountDetails, String reasonForLeaving, String estimatedMonthlyBudgetForRent, String leaseTurnDesire, String previousLandlordNumber, String isSmoking, String hasCriminalBackground, String hasPets) {
        this.requestId = requestId;
        this.customerId = customerId;
        this.date = date;
        this.rentOrBuy = rentOrBuy;
        this.houseType = houseType;
        this.noOfFamilyMembers = noOfFamilyMembers;
        this.monthlyIncome = monthlyIncome;
        this.annualIncome = annualIncome;
        this.bankAccountDetails = bankAccountDetails;
        this.reasonForLeaving = reasonForLeaving;
        this.estimatedMonthlyBudgetForRent = estimatedMonthlyBudgetForRent;
        this.leaseTurnDesire = leaseTurnDesire;
        this.previousLandlordNumber = previousLandlordNumber;
        this.isSmoking = isSmoking;
        this.hasCriminalBackground = hasCriminalBackground;
        this.hasPets = hasPets;
    }

    public RequestDTO toDTO(Request request){
        this.requestId = request.getRequestId();
        this.customerId = request.getCustomerId();
        this.date = request.getDate();
        this.rentOrBuy = request.getRentOrBuy();
        this.houseType = request.getHouseType();
        this.noOfFamilyMembers = request.getNoOfFamilyMembers();
        this.monthlyIncome = request.getMonthlyIncome();
        this.annualIncome = request.getAnnualIncome();;
        this.bankAccountDetails = request.getBankAccountDetails();
        this.reasonForLeaving = request.getReasonForLeaving();
        this.estimatedMonthlyBudgetForRent = request.getEstimatedMonthlyBudgetForRent();
        this.leaseTurnDesire = request.getLeaseTurnDesire();
        this.previousLandlordNumber = request.getPreviousLandlordNumber();
        this.isSmoking = request.getIsSmoking();
        this.hasCriminalBackground = request.getHasCriminalBackground();
        this.hasPets = request.getHasPets();
        this.allDocumentsProvided = request.getAllDocumentsProvided();
        this.qualifiedCustomerOrNot = request.getQualifiedCustomerOrNot();
        this.agreesToAllTermsAndConditions = request.getAgreesToAllTermsAndConditions();
        this.isPaymentsCompleted = request.getIsPaymentsCompleted();
        this.requestStatus = request.getRequestStatus();
        this.customerRequestStatus = request.getCustomerRequestStatus();
        this.houseId = request.getHouseId();
        return this;
    }

    public RequestDTO toDTO(RequestTM requestTM) {
        this.requestId = requestTM.getRequestId();
        this.customerId = requestTM.getCustomerId();
        this.rentOrBuy = requestTM.getRentOrBuy();
        this.houseType = requestTM.getHouseType();
        this.leaseTurnDesire = requestTM.getLeaseTurnDesire();
        this.allDocumentsProvided = requestTM.getAllDocumentsProvided();
        this.qualifiedCustomerOrNot = requestTM.getQualifiedCustomerOrNot();
        this.agreesToAllTermsAndConditions = requestTM.getAgreesToAllTermsAndConditions();
        this.isPaymentsCompleted = requestTM.getIsPaymentsCompleted();
        this.requestStatus = requestTM.getRequestStatus();
        this.customerRequestStatus = requestTM.getCustomerRequestStatus();
        this.houseId = requestTM.getHouseId();
        return this;
    }
}





