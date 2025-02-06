package com.example.test.entity;

import com.example.test.dto.UserValidationDTO;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
public class UserValidation {

    private int userValidationNo;
    private String qOne;
    private String qTwo;
    private String qThree;
    private String userName;

    public UserValidation toEntity(UserValidationDTO userValidationDto){
        this.qOne= userValidationDto.getQOne();
        this.qTwo = userValidationDto.getQTwo();
        this.qThree = userValidationDto.getQThree();
        this.userName = userValidationDto.getUserName();
        return this;
    }
}
