package com.example.test.entity;

import com.example.test.dto.LeaseAgreementDTO;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
public class LeaseAgreement {

    private String leaseId;
    private String tenantId;
    private String houseId;
    private String leaseTurn;
    private String startDate;
    private String endDate;
    private double monthlyRent;
    private double securityPayment;
    private String status;

    public LeaseAgreement(String leaseId, String tenantId, String houseId, String leaseTurn, String startDate, String endDate, String status) {
        this.leaseId = leaseId;
        this.tenantId = tenantId;
        this.houseId = houseId;
        this.leaseTurn = leaseTurn;
        this.startDate = startDate;
        this.endDate = endDate;
        this.status = status;
    }

    public LeaseAgreement toEntity(LeaseAgreementDTO leaseAgreementDto){
        this.leaseId = leaseAgreementDto.getLeaseId();
        this.tenantId = leaseAgreementDto.getTenantId();
        this.houseId = leaseAgreementDto.getHouseId();
        this.leaseTurn = leaseAgreementDto.getLeaseTurn();
        this.startDate = leaseAgreementDto.getStartDate();
        this.endDate = leaseAgreementDto.getEndDate();
        this.monthlyRent = leaseAgreementDto.getMonthlyRent();
        this.securityPayment = leaseAgreementDto.getSecurityPayment();
        this.status = leaseAgreementDto.getStatus();
        return this;

    }
}
