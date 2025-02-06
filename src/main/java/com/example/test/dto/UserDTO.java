package com.example.test.dto;

import com.example.test.entity.User;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
public class UserDTO {

    private String userName;
    private String name;
    private String email;
    private String password;

    public UserDTO toDTO(User user){
        this.userName = user.getUserName();
        this.name = user.getName();
        this.email = user.getEmail();
        this.password = user.getPassword();
        return this;
    }

}
