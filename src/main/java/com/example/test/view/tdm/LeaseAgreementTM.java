package com.example.test.view.tdm;

import com.example.test.dto.LeaseAgreementDTO;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
public class LeaseAgreementTM {

    private String leaseId;
    private String tenantId;
    private String houseId;
    private String leaseTurn;
    private String startDate;
    private String endDate;
    private String status;

    public LeaseAgreementTM toTM(LeaseAgreementDTO leaseAgreementDTO){
        this.leaseId = leaseAgreementDTO.getLeaseId();
        this.tenantId = leaseAgreementDTO.getTenantId();
        this.houseId = leaseAgreementDTO.getHouseId();
        this.leaseTurn = leaseAgreementDTO.getLeaseTurn();
        this.startDate = leaseAgreementDTO.getStartDate();
        this.endDate = leaseAgreementDTO.getEndDate();
        this.status = leaseAgreementDTO.getStatus();
        return this;

    }
}
