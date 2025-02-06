package com.example.test.entity;

import com.example.test.dto.HouseInspectDTO;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class HouseInspect {

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

    public HouseInspect toEntity(HouseInspectDTO houseInspectDto){
        this.checkNumber = houseInspectDto.getCheckNumber();
        this.livingRoomStatus = houseInspectDto.getLivingRoomStatus();
        this.kitchenStatus = houseInspectDto.getKitchenStatus();
        this.bedRoomsStatus = houseInspectDto.getBedRoomsStatus();
        this.bathRoomsStatus = houseInspectDto.getBathRoomsStatus();
        this.totalHouseStatus = houseInspectDto.getTotalHouseStatus();
        this.tenantId = houseInspectDto.getTenantId();
        this.houseId = houseInspectDto.getHouseId();
        this.estimatedCostForRepair = houseInspectDto.getEstimatedCostForRepair();
        this.isPaymentDone = houseInspectDto.getIsPaymentDone();
        this.date = houseInspectDto.getDate();
        return this;
    }
}





