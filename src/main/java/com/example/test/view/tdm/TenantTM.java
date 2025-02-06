package com.example.test.view.tdm;

import com.example.test.dto.TenantDTO;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
public class TenantTM {

    private String tenantId;
    private String name;
    private String phoneNo;
    private int membersCount;
    private String rentStartDate;
    private double monthlyRent;
    private String lastPaidMonth;
    private String houseId;

    public TenantTM toTM(TenantDTO tenantDTO){
        this.tenantId = tenantDTO.getTenantId();
        this.name = tenantDTO.getName();
        this.phoneNo = tenantDTO.getPhoneNo();
        this.membersCount = tenantDTO.getMembersCount();
        this.rentStartDate = tenantDTO.getRentStartDate();
        this.monthlyRent = tenantDTO.getMonthlyRent();
        this.lastPaidMonth = tenantDTO.getLastPaidMonth();
        this.houseId = tenantDTO.getHouseId();
        return this;
    }
}
