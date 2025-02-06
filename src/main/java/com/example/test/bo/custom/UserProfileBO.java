package com.example.test.bo.custom;

import com.example.test.bo.SuperBO;
import com.example.test.dto.UserDTO;
import com.example.test.entity.User;

import java.sql.SQLException;

public interface UserProfileBO extends SuperBO {

    public UserDTO getUserDetails() throws SQLException, ClassNotFoundException;

    public String changeUserDetailsOne(String[] data, String[] column) throws SQLException, ClassNotFoundException;

    public String changeUserDetailsTwo(String[] data, String[] column) throws SQLException, ClassNotFoundException;

    public String changeUserDetailsThree(String[] data, String[] column) throws SQLException, ClassNotFoundException;

    public String changeUserDetailsFour(String[] data, String[] column) throws SQLException, ClassNotFoundException;
}
