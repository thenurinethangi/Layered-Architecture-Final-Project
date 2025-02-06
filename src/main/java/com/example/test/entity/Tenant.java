package com.example.test.entity;

import com.example.test.dto.TenantDTO;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
public class Tenant {

    private String tenantId;
    private String headOfHouseholdName;
    private String phoneNo;
    private int membersCount;
    private String rentStartDate;
    private double monthlyRent;
    private String lastPaidMonth;
    private String houseId;
    private String email;
    private double securityPaymentRemain;
    private int isActiveTenant;

    public Tenant toEntity(TenantDTO tenantDto){
        this.tenantId = tenantDto.getTenantId();
        this.headOfHouseholdName = tenantDto.getName();
        this.phoneNo = tenantDto.getPhoneNo();
        this.membersCount = tenantDto.getMembersCount();
        this.rentStartDate = tenantDto.getRentStartDate();
        this.monthlyRent = tenantDto.getMonthlyRent();
        this.lastPaidMonth = tenantDto.getLastPaidMonth();
        this.houseId = tenantDto.getHouseId();
        this.email = tenantDto.getEmail();
        this.securityPaymentRemain = tenantDto.getSecurityPaymentRemain();
        return this;
    }
}






