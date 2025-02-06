package com.example.test.dto;

import com.example.test.entity.LeaseAgreement;
import com.example.test.view.tdm.LeaseAgreementTM;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
public class LeaseAgreementDTO {

    private String leaseId;
    private String tenantId;
    private String houseId;
    private String leaseTurn;
    private String startDate;
    private String endDate;
    private String status;
    private double monthlyRent;
    private double securityPayment;

    public LeaseAgreementDTO toDTO(LeaseAgreement leaseAgreement){
        this.leaseId = leaseAgreement.getLeaseId();
        this.tenantId = leaseAgreement.getTenantId();
        this.houseId = leaseAgreement.getHouseId();
        this.leaseTurn = leaseAgreement.getLeaseTurn();
        this.startDate = leaseAgreement.getStartDate();
        this.endDate = leaseAgreement.getEndDate();
        this.monthlyRent = leaseAgreement.getMonthlyRent();
        this.securityPayment = leaseAgreement.getSecurityPayment();
        this.status = leaseAgreement.getStatus();
        return this;

    }

    public LeaseAgreementDTO toDTO(LeaseAgreementTM leaseAgreementTM){
        this.leaseId = leaseAgreementTM.getLeaseId();
        this.tenantId = leaseAgreementTM.getTenantId();
        this.houseId = leaseAgreementTM.getHouseId();
        this.leaseTurn = leaseAgreementTM.getLeaseTurn();
        this.startDate = leaseAgreementTM.getStartDate();
        this.endDate = leaseAgreementTM.getEndDate();
        this.status = leaseAgreementTM.getStatus();
        return this;

    }
}
