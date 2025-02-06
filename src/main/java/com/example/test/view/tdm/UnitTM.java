package com.example.test.view.tdm;

import com.example.test.dto.UnitDTO;
import com.example.test.entity.Unit;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@Getter
@Setter
@ToString
public class UnitTM {

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

    public UnitTM() {

    }

    public UnitTM toTM(UnitDTO unitDTO){
        this.houseId = unitDTO.getHouseId();
        this.bedroom = unitDTO.getBedroom();
        this.bathroom = unitDTO.getBathroom();
        this.rentOrBuy = unitDTO.getRentOrBuy();
        this.totalValue = unitDTO.getTotalValue();
        this.securityCharge = unitDTO.getSecurityCharge();
        this.monthlyRent = unitDTO.getMonthlyRent();
        this.status = unitDTO.getStatus();
        this.houseType = unitDTO.getHouseType();
        this.floorNo = unitDTO.getFloorNo();
        return this;
    }
}


