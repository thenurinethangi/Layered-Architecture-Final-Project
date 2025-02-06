package com.example.test.entity;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
public class Unit {

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

    public Unit toEntity(com.example.test.dto.UnitDTO unitDTO){
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
