package com.example.test.dto;

import com.example.test.entity.Floor;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
public class FloorDTO {

    private int floorNo;
    private int noOfHouses;


    public FloorDTO toDTO(Floor floor){
        this.floorNo = floor.getFloorNo();
        this.noOfHouses = floor.getNoOfHouses();
        return this;
    }
}
