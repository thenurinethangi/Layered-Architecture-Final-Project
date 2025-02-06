package com.example.test.bo.custom;

import com.example.test.bo.SuperBO;
import com.example.test.dto.UserDTO;
import com.example.test.dto.UserValidationDTO;
import com.example.test.entity.UserValidation;

import java.sql.SQLException;

public interface SignInBO extends SuperBO {

    public String signInAuthentication(UserDTO signInDto) throws SQLException, ClassNotFoundException;

    public String getPassword(UserValidationDTO userValidationDTO) throws SQLException, ClassNotFoundException;
}
