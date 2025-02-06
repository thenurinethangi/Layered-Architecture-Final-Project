package com.example.test.dao.custom;

import com.example.test.dao.CrudDAO;
import com.example.test.dao.SQLUtil;
import com.example.test.entity.User;
import com.example.test.entity.UserValidation;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface SignUpDAO extends CrudDAO<User> {

     public boolean addUserValidationDetails(UserValidation userValidation) throws SQLException, ClassNotFoundException;
}
