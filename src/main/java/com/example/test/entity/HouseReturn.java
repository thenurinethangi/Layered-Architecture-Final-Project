package com.example.test.entity;

import com.example.test.dto.HouseReturnDTO;
import com.example.test.view.tdm.HouseReturnTM;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
public class HouseReturn {

    private String returnNo;
    private String reason;
    private String date;
    private String tenantId;
    private String houseId;
    private String refundedAmount;
    private String expenseNo;
    private int isActive;

    public HouseReturn toEntity(HouseReturnDTO houseReturnDTO){
        this.returnNo = houseReturnDTO.getReturnNo();
        this.reason = houseReturnDTO.getReason();
        this.date = houseReturnDTO.getDate();
        this.tenantId = houseReturnDTO.getTenantId();
        this.houseId = houseReturnDTO.getHouseId();
        this.refundedAmount = houseReturnDTO.getRefundedAmount();
        this.expenseNo = houseReturnDTO.getExpenseNo();
        this.isActive = houseReturnDTO.getIsActive();
        return this;
    }
}
