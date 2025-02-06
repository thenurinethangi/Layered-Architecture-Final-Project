package com.example.test.dto;

import com.example.test.entity.Tenant;
import com.example.test.view.tdm.TenantTM;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
public class TenantDTO {

    private String tenantId;
    private String name;
    private String phoneNo;
    private int membersCount;
    private String rentStartDate;
    private double monthlyRent;
    private String lastPaidMonth;
    private String houseId;
    private String email;
    private double securityPaymentRemain;


    public TenantDTO toDTO(Tenant tenant){
        this.tenantId = tenant.getTenantId();
        this.name = tenant.getHeadOfHouseholdName();
        this.phoneNo = tenant.getPhoneNo();
        this.membersCount = tenant.getMembersCount();
        this.rentStartDate = tenant.getRentStartDate();
        this.monthlyRent = tenant.getMonthlyRent();
        this.lastPaidMonth = tenant.getLastPaidMonth();
        this.houseId = tenant.getHouseId();
        this.email = tenant.getEmail();
        this.securityPaymentRemain = tenant.getSecurityPaymentRemain();
        return this;
    }

    public TenantDTO toDTO(TenantTM tenantTM){
        this.tenantId = tenantTM.getTenantId();
        this.name = tenantTM.getName();
        this.phoneNo = tenantTM.getPhoneNo();
        this.membersCount = tenantTM.getMembersCount();
        this.rentStartDate = tenantTM.getRentStartDate();
        this.monthlyRent = tenantTM.getMonthlyRent();
        this.lastPaidMonth = tenantTM.getLastPaidMonth();
        this.houseId = tenantTM.getHouseId();
        return this;
    }
}
