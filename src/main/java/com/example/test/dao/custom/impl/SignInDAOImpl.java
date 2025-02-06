package com.example.test.dao.custom.impl;

import com.example.test.dao.SQLUtil;
import com.example.test.dao.custom.SignInDAO;
import com.example.test.db.DBConnection;
import com.example.test.entity.User;
import com.example.test.entity.UserValidation;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SignInDAOImpl implements SignInDAO {

    public String signInAuthentication(User user) throws SQLException, ClassNotFoundException {

        String sql = "select userName,password from user where userName = ?";
        ResultSet result = SQLUtil.execute(sql,user.getUserName());

        if(!result.next()){

            return "This User Name does not exit";

        }
        else{
            String pWord = result.getString("password");

            if(pWord.equals(user.getPassword())){
                return "All Correct";
            }
            else{
                return "Your Password is incorrect, try again";
            }
        }

    }


    public String getPassword(UserValidation userValidation) throws SQLException, ClassNotFoundException {

        String sql = "select questionOne,questionTwo,questionThree from userValidation where userName = ?";
        ResultSet result = SQLUtil.execute(sql,userValidation.getUserName());

        if(!result.next()){
            return "This User Name does not exit";
        }
        else{

            String fQ = result.getString(1);
            String sQ = result.getString(2);
            String tQ = result.getString(3);

            if(fQ.equals(userValidation.getQOne()) && sQ.equals(userValidation.getQTwo()) && tQ.equals(userValidation.getQThree())){

                String sqlTwo = "select password from user where userName = ?";
                ResultSet res = SQLUtil.execute(sqlTwo,userValidation.getUserName());

                if(!res.next()){
                    return "Something wrong with getting password back, try again later";
                }
                else{
                    return res.getString(1);
                }
            }
            else{
                return "Can't get password because your answers are incorrect";

            }

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
