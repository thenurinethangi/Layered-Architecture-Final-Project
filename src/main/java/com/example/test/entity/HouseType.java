package com.example.test.entity;

import com.example.test.dto.HouseTypeDTO;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@ToString
@NoArgsConstructor
public class HouseType {

    private String houseType;
    private String description;

    public HouseType toEntity(HouseTypeDTO houseTypeDto){
        this.houseType = houseTypeDto.getHouseType();
        this.description = houseTypeDto.getDescription();
        return this;
    }
}
