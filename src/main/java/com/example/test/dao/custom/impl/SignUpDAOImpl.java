package com.example.test.dao.custom.impl;

import com.example.test.dao.SQLUtil;
import com.example.test.dao.custom.SignUpDAO;
import com.example.test.entity.User;
import com.example.test.entity.UserValidation;
import javafx.collections.ObservableList;

import java.sql.ResultSet;
import java.sql.SQLException;

public class SignUpDAOImpl implements SignUpDAO {


    @Override
    public ObservableList<User> getAll() throws SQLException, ClassNotFoundException {
        return null;
    }


    @Override
    public String delete(User entity) throws SQLException, ClassNotFoundException {
        return "";
    }


    public boolean isExist(User user) throws SQLException, ClassNotFoundException {

        String sql = "select userName from user where userName = ?";
        ResultSet rst = SQLUtil.execute(sql,user.getUserName());
        return rst.next();
    }


    @Override
    public User search(String customerId) throws SQLException, ClassNotFoundException {
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


    public String add(User user) throws SQLException, ClassNotFoundException {

        String sqlOne = "insert into user values(?,?,?,?)";
        boolean result = SQLUtil.execute(sqlOne,user.getUserName(),user.getName(),user.getEmail(),user.getPassword());

        return result ? "Success" : "Failed";

    }


    public boolean addUserValidationDetails(UserValidation userValidation) throws SQLException, ClassNotFoundException{

        String sqlTwo = "select userValidationNo from userValidation order by userValidationNo desc limit 1";
        ResultSet resultTwo = SQLUtil.execute(sqlTwo);

        int validationNO = 0;

        if (resultTwo.next()) {

            validationNO = resultTwo.getInt(1);
            validationNO += 1;
        } else {
            validationNO = 1;
        }

        String sqlThree = "insert into userValidation values(?,?,?,?,?)";
        return SQLUtil.execute(sqlThree,validationNO,userValidation.getQOne(),userValidation.getQTwo(),userValidation.getQThree(),userValidation.getUserName());

    }
}








