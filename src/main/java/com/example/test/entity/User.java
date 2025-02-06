package com.example.test.entity;

import com.example.test.dto.UserDTO;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
public class User {

    private String userName;
    private String name;
    private String email;
    private String password;

    public User toEntity(UserDTO userDto){
        this.userName = userDto.getUserName();
        this.name = userDto.getName();
        this.email = userDto.getEmail();
        this.password = userDto.getPassword();
        return this;
    }

//    public User toEntity(SignInDTO signInDto){
//        this.userName = signInDto.getUserName();
//        this.password = signInDto.getPassword();
//        return this;
//    }
}
