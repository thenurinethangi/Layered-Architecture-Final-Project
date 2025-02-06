package com.example.test.bo.custom.impl;

import com.example.test.bo.custom.SignInBO;
import com.example.test.dao.DAOFactory;
import com.example.test.dao.custom.SignInDAO;
import com.example.test.dto.UserDTO;
import com.example.test.dto.UserValidationDTO;
import com.example.test.entity.User;
import com.example.test.entity.UserValidation;

import java.sql.SQLException;

public class SignInBOImpl implements SignInBO {

    SignInDAO signInDAO = (SignInDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.SIGNIN);


    public String signInAuthentication(UserDTO signInDto) throws SQLException, ClassNotFoundException {

        return signInDAO.signInAuthentication(new User().toEntity(signInDto));

    }


    public String getPassword(UserValidationDTO userValidationDTO) throws SQLException, ClassNotFoundException{

        return signInDAO.getPassword(new UserValidation().toEntity(userValidationDTO));
    }
}
