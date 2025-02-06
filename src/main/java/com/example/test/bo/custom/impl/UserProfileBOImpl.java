package com.example.test.bo.custom.impl;

import com.example.test.bo.custom.UserProfileBO;
import com.example.test.dao.DAOFactory;
import com.example.test.dao.custom.UserProfileDAO;
import com.example.test.dto.UnitDTO;
import com.example.test.dto.UserDTO;
import com.example.test.entity.User;

import java.sql.SQLException;

public class UserProfileBOImpl implements UserProfileBO {


    UserProfileDAO userProfileDAO = (UserProfileDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.USERPROFILE);


    public UserDTO getUserDetails() throws SQLException, ClassNotFoundException{

        return new UserDTO().toDTO(userProfileDAO.getUserDetails());
    }


    public String changeUserDetailsOne(String[] data, String[] column) throws SQLException, ClassNotFoundException{

        return userProfileDAO.changeUserDetailsOne(data,column);
    }


    public String changeUserDetailsTwo(String[] data, String[] column) throws SQLException, ClassNotFoundException{

        return userProfileDAO.changeUserDetailsTwo(data,column);
    }


    public String changeUserDetailsThree(String[] data, String[] column) throws SQLException, ClassNotFoundException{

        return userProfileDAO.changeUserDetailsThree(data,column);
    }


    public String changeUserDetailsFour(String[] data, String[] column) throws SQLException, ClassNotFoundException{

        return userProfileDAO.changeUserDetailsFour(data,column);
    }
}




