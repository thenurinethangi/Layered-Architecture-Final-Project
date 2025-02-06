package com.example.test.dto;

import com.example.test.entity.HouseReturn;
import com.example.test.view.tdm.HouseReturnTM;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
public class HouseReturnDTO {

    private String returnNo;
    private String reason;
    private String date;
    private String tenantId;
    private String houseId;
    private String refundedAmount;
    private String expenseNo;
    private int isActive;
    private String agreementId;

    public HouseReturnDTO toDTO(HouseReturn houseReturn){
        this.returnNo = houseReturn.getReturnNo();
        this.reason = houseReturn.getReason();
        this.date = houseReturn.getDate();
        this.tenantId = houseReturn.getTenantId();
        this.houseId = houseReturn.getHouseId();
        this.refundedAmount = houseReturn.getRefundedAmount();
        this.expenseNo = houseReturn.getExpenseNo();
        this.isActive = houseReturn.getIsActive();
        return this;
    }

    public HouseReturnDTO toDTO(HouseReturnTM houseReturnTM){
        this.returnNo = houseReturnTM.getReturnNo();
        this.reason = houseReturnTM.getReason();
        this.date = houseReturnTM.getDate();
        this.tenantId = houseReturnTM.getTenantId();
        this.houseId = houseReturnTM.getHouseId();
        this.refundedAmount = houseReturnTM.getRefundedAmount();
        this.expenseNo = houseReturnTM.getExpenseNo();
        return this;
    }

}
