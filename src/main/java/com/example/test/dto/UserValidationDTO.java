package com.example.test.dto;

import com.example.test.entity.UserValidation;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
public class UserValidationDTO {

    private String qOne;
    private String qTwo;
    private String qThree;
    private String userName;

    public UserValidationDTO(String qOne, String qTwo, String qThree) {
        this.qOne = qOne;
        this.qTwo = qTwo;
        this.qThree = qThree;
    }

    public UserValidationDTO toDTO(UserValidation userValidation){
        this.qOne= userValidation.getQOne();
        this.qTwo = userValidation.getQTwo();
        this.qThree = userValidation.getQThree();
        this.userName = userValidation.getUserName();
        return this;
    }

}


