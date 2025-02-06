package com.example.test.bo.custom;

import com.example.test.bo.SuperBO;
import com.example.test.dto.UserDTO;
import com.example.test.dto.UserValidationDTO;
import com.example.test.entity.User;
import com.example.test.entity.UserValidation;

import java.sql.SQLException;

public interface SignUpBO extends SuperBO {

    boolean isExist(UserDTO userDto) throws SQLException, ClassNotFoundException;

    public String add(UserDTO userDTO) throws SQLException, ClassNotFoundException;

    public boolean addUserValidationDetails(UserValidationDTO userValidationDTO) throws SQLException, ClassNotFoundException;
}
