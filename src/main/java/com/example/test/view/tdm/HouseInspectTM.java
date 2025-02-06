package com.example.test.view.tdm;

import com.example.test.dto.HouseInspectDTO;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class HouseInspectTM {

    private String checkNumber;
    private String livingRoomStatus;
    private String kitchenStatus;
    private String bedRoomsStatus;
    private String bathRoomsStatus;
    private String totalHouseStatus;
    private String tenantId;
    private String houseId;
    private String estimatedCostForRepair;
    private String isPaymentDone;

    public HouseInspectTM toTM(HouseInspectDTO houseInspectDTO){
        this.checkNumber = houseInspectDTO.getCheckNumber();
        this.livingRoomStatus = houseInspectDTO.getLivingRoomStatus();
        this.kitchenStatus = houseInspectDTO.getKitchenStatus();
        this.bedRoomsStatus = houseInspectDTO.getBedRoomsStatus();
        this.bathRoomsStatus = houseInspectDTO.getBathRoomsStatus();
        this.totalHouseStatus = houseInspectDTO.getTotalHouseStatus();
        this.tenantId = houseInspectDTO.getTenantId();
        this.houseId = houseInspectDTO.getHouseId();
        this.estimatedCostForRepair = houseInspectDTO.getEstimatedCostForRepair();
        this.isPaymentDone = houseInspectDTO.getIsPaymentDone();
        return this;
    }
}
