package com.example.test.entity;

import com.example.test.dto.RequestDTO;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Request {

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
    private String requestStatus;
    private String customerRequestStatus;
    private String houseId;

    public Request(String requestId, String customerId, String date, String rentOrBuy, String houseType, int noOfFamilyMembers, double monthlyIncome, double annualIncome, String bankAccountDetails, String reasonForLeaving, String estimatedMonthlyBudgetForRent, String leaseTurnDesire, String previousLandlordNumber, String isSmoking, String hasCriminalBackground, String hasPets) {
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

    public Request(String requestId, String customerId, String rentOrBuy, String houseType, String leaseTurnDesire, String allDocumentsProvided, String qualifiedCustomerOrNot, String agreesToAllTermsAndConditions, String isPaymentsCompleted, String customerRequestStatus, String requestStatus, String houseId) {
        this.requestId = requestId;
        this.customerId = customerId;
        this.rentOrBuy = rentOrBuy;
        this.houseType = houseType;
        this.leaseTurnDesire = leaseTurnDesire;
        this.allDocumentsProvided = allDocumentsProvided;
        this.qualifiedCustomerOrNot = qualifiedCustomerOrNot;
        this.agreesToAllTermsAndConditions = agreesToAllTermsAndConditions;
        this.isPaymentsCompleted = isPaymentsCompleted;
        this.customerRequestStatus = customerRequestStatus;
        this.requestStatus = requestStatus;
        this.houseId = houseId;
    }

    public Request toEntity(RequestDTO requestDto){
        this.requestId = requestDto.getRequestId();
        this.customerId = requestDto.getCustomerId();
        this.date = requestDto.getDate();
        this.rentOrBuy = requestDto.getRentOrBuy();
        this.houseType = requestDto.getHouseType();
        this.noOfFamilyMembers = requestDto.getNoOfFamilyMembers();
        this.monthlyIncome = requestDto.getMonthlyIncome();
        this.annualIncome = requestDto.getAnnualIncome();;
        this.bankAccountDetails = requestDto.getBankAccountDetails();
        this.reasonForLeaving = requestDto.getReasonForLeaving();
        this.estimatedMonthlyBudgetForRent = requestDto.getEstimatedMonthlyBudgetForRent();
        this.leaseTurnDesire = requestDto.getLeaseTurnDesire();
        this.previousLandlordNumber = requestDto.getPreviousLandlordNumber();
        this.isSmoking = requestDto.getIsSmoking();
        this.hasCriminalBackground = requestDto.getHasCriminalBackground();
        this.hasPets = requestDto.getHasPets();
        this.allDocumentsProvided = requestDto.getAllDocumentsProvided();
        this.qualifiedCustomerOrNot = requestDto.getQualifiedCustomerOrNot();
        this.agreesToAllTermsAndConditions = requestDto.getAgreesToAllTermsAndConditions();
        this.isPaymentsCompleted = requestDto.getIsPaymentsCompleted();
        this.requestStatus = requestDto.getRequestStatus();
        this.customerRequestStatus = requestDto.getCustomerRequestStatus();
        this.houseId = requestDto.getHouseId();
        return this;
    }
}
