package com.example.test.dto;

import com.example.test.entity.Unit;
import com.example.test.view.tdm.UnitTM;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UnitDTO {

    private String houseId;
    private int bedroom;
    private int bathroom;
    private String rentOrBuy;
    private String totalValue;
    private String securityCharge;
    private String monthlyRent;
    private String status;
    private String houseType;
    private int floorNo;

    public String toString(){
        return "House ID: " + houseId + "\nBedRooms: " + bedroom + "\nBathRooms: " + bathroom + "\nSecurity Payment: " + securityCharge + "\nMonthly Rent: " + monthlyRent + "\nHouseType: " + houseType + "\nFloor No: " + floorNo;
    }

    public UnitDTO toDTO(Unit unit){

        this.houseId = unit.getHouseId();
        this.bedroom = unit.getBedroom();
        this.bathroom = unit.getBathroom();
        this.rentOrBuy = unit.getRentOrBuy();
        this.totalValue = unit.getTotalValue();
        this.securityCharge = unit.getSecurityCharge();
        this.monthlyRent = unit.getMonthlyRent();
        this.status = unit.getStatus();
        this.houseType = unit.getHouseType();
        this.floorNo = unit.getFloorNo();
        return this;
    }

    public UnitDTO toDTO(UnitTM unitTM){

        this.houseId = unitTM.getHouseId();
        this.bedroom = unitTM.getBedroom();
        this.bathroom = unitTM.getBathroom();
        this.rentOrBuy = unitTM.getRentOrBuy();
        this.totalValue = unitTM.getTotalValue();
        this.securityCharge = unitTM.getSecurityCharge();
        this.monthlyRent = unitTM.getMonthlyRent();
        this.status = unitTM.getStatus();
        this.houseType = unitTM.getHouseType();
        this.floorNo = unitTM.getFloorNo();
        return this;
    }
}



