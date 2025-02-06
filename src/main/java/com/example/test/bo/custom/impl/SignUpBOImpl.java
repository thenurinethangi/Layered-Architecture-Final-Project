package com.example.test.bo.custom.impl;

import com.example.test.bo.custom.SignUpBO;
import com.example.test.dao.DAOFactory;
import com.example.test.dao.custom.SignUpDAO;
import com.example.test.dto.UserDTO;
import com.example.test.dto.UserValidationDTO;
import com.example.test.entity.User;
import com.example.test.entity.UserValidation;

import java.sql.SQLException;

public class SignUpBOImpl implements SignUpBO {

    private SignUpDAO signUpDAO = (SignUpDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.SIGNUP);


    public boolean isExist(UserDTO userDto) throws SQLException, ClassNotFoundException {

        return signUpDAO.isExist(new User().toEntity(userDto));
    }


    public String add(UserDTO userDTO) throws SQLException, ClassNotFoundException{

        return signUpDAO.add(new User().toEntity(userDTO));
    }


    public boolean addUserValidationDetails(UserValidationDTO userValidationDTO) throws SQLException, ClassNotFoundException{

        return signUpDAO.addUserValidationDetails(new UserValidation().toEntity(userValidationDTO));
    }
}
