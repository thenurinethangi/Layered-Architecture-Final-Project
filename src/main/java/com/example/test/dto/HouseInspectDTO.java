package com.example.test.dto;

import com.example.test.entity.HouseInspect;
import com.example.test.view.tdm.HouseInspectTM;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class HouseInspectDTO {

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
    private String date;

    public HouseInspectDTO toDTO(HouseInspect houseInspect){
        this.checkNumber = houseInspect.getCheckNumber();
        this.livingRoomStatus = houseInspect.getLivingRoomStatus();
        this.kitchenStatus = houseInspect.getKitchenStatus();
        this.bedRoomsStatus = houseInspect.getBedRoomsStatus();
        this.bathRoomsStatus = houseInspect.getBathRoomsStatus();
        this.totalHouseStatus = houseInspect.getTotalHouseStatus();
        this.tenantId = houseInspect.getTenantId();
        this.houseId = houseInspect.getHouseId();
        this.estimatedCostForRepair = houseInspect.getEstimatedCostForRepair();
        this.isPaymentDone = houseInspect.getIsPaymentDone();
        this.date = houseInspect.getDate();
        return this;
    }


    public HouseInspectDTO toDTO(HouseInspectTM houseInspectTM){
        this.checkNumber = houseInspectTM.getCheckNumber();
        this.livingRoomStatus = houseInspectTM.getLivingRoomStatus();
        this.kitchenStatus = houseInspectTM.getKitchenStatus();
        this.bedRoomsStatus = houseInspectTM.getBedRoomsStatus();
        this.bathRoomsStatus = houseInspectTM.getBathRoomsStatus();
        this.totalHouseStatus = houseInspectTM.getTotalHouseStatus();
        this.tenantId = houseInspectTM.getTenantId();
        this.houseId = houseInspectTM.getHouseId();
        this.estimatedCostForRepair = houseInspectTM.getEstimatedCostForRepair();
        this.isPaymentDone = houseInspectTM.getIsPaymentDone();
        return this;
    }
}
