package com.example.test.entity;

import com.example.test.dto.FloorDTO;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Floor {

    private int floorNo;
    private int noOfHouses;

    public Floor toEntity(FloorDTO floorDto){
        this.floorNo = floorDto.getFloorNo();
        this.noOfHouses = floorDto.getNoOfHouses();
        return this;
    }
}
