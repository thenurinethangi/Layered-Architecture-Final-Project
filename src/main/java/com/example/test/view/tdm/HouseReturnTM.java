package com.example.test.view.tdm;

import com.example.test.dto.HouseReturnDTO;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
public class HouseReturnTM {

    private String returnNo;
    private String reason;
    private String date;
    private String tenantId;
    private String houseId;
    private String refundedAmount;
    private String expenseNo;

    public HouseReturnTM toTM(HouseReturnDTO houseReturnDTO){
        this.returnNo = houseReturnDTO.getReturnNo();
        this.reason = houseReturnDTO.getReason();
        this.date = houseReturnDTO.getDate();
        this.tenantId = houseReturnDTO.getTenantId();
        this.houseId = houseReturnDTO.getHouseId();
        this.refundedAmount = houseReturnDTO.getRefundedAmount();
        this.expenseNo = houseReturnDTO.getExpenseNo();
        return this;
    }

}
