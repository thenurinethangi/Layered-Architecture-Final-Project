package com.example.test.dao.custom.impl;

import com.example.test.LoginDetails;
import com.example.test.dao.SQLUtil;
import com.example.test.dao.custom.UserProfileDAO;
import com.example.test.db.DBConnection;
import com.example.test.dto.UserDTO;
import com.example.test.entity.User;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class UserProfileDAOImpl implements UserProfileDAO {

    private final String userName = LoginDetails.getUserName();

    public User getUserDetails() throws SQLException, ClassNotFoundException {

        String sql = "select * from user where userName = ?";
        ResultSet result = SQLUtil.execute(sql,userName);

        if(result.next()){

            User user = new User();

            user.setUserName(result.getString(1));
            user.setName(result.getString(2));
            user.setEmail(result.getString(3));
            user.setPassword(result.getString(4));

            return user;
        }
        else{
            return null;
        }
    }


    public String changeUserDetailsOne(String[] data, String[] column) throws SQLException, ClassNotFoundException {

        String sql = "UPDATE user SET "+column[0] +" = ? WHERE userName = ?";
        boolean result = SQLUtil.execute(sql,data[0],userName);

        if(result){
            return "Successfully updated your profile";
        }
        else{
            return "something went wrong, failed to update your profile, try again later";
        }

    }


    public String changeUserDetailsTwo(String[] data, String[] column) throws SQLException, ClassNotFoundException {

        String sql = "UPDATE user SET "+column[0] +" = ?, "+ column[1] +" = ? WHERE userName = ?";
        boolean result = SQLUtil.execute(sql,data[0],data[1],userName);

        if(result){
            return "Successfully updated your profile";
        }
        else{
            return "something went wrong, failed to update your profile, try again later";
        }
    }


    public String changeUserDetailsThree(String[] data, String[] column) throws SQLException, ClassNotFoundException {

        String sql = "UPDATE user SET "+column[0] +" = ?, "+ column[1] +" = ?, "+ column[2]+" = ? WHERE userName = ?";
        boolean result = SQLUtil.execute(sql,data[0],data[1],data[2],userName);

        if(result){
            return "Successfully updated your profile";
        }
        else{
            return "something went wrong, failed to update your profile, try again later";
        }
    }


    public String changeUserDetailsFour(String[] data, String[] column) throws SQLException, ClassNotFoundException {

        String sql = "UPDATE user SET "+column[0] +" = ?, "+ column[1] +" = ?, "+ column[2]+" = ?, "+column[3]+" =? WHERE userName = ?";
        boolean result = SQLUtil.execute(sql,data[0],data[1],data[2],data[3],userName);

        if(result){
            return "Successfully updated your profile";
        }
        else{
            return "something went wrong, failed to update your profile, try again later";
        }
    }

    @Override
    public ObservableList<User> getAll() throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public String delete(User entity) throws SQLException, ClassNotFoundException {
        return "";
    }

    @Override
    public boolean isExist(User entity) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public User search(String id) throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public String generateNewId() throws SQLException, ClassNotFoundException {
        return "";
    }

    @Override
    public String update(User entity) throws SQLException, ClassNotFoundException {
        return "";
    }

    @Override
    public String add(User entity) throws SQLException, ClassNotFoundException {
        return "";
    }
}






