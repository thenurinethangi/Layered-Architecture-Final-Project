package com.example.test.dto;

import com.example.test.entity.HouseType;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class HouseTypeDTO {

    private String houseType;
    private String description;

    public HouseTypeDTO toDTO(HouseType houseType){
        this.houseType = houseType.getHouseType();
        this.description = houseType.getDescription();
        return this;
    }

}
