package com.example.test.dao.custom;

import com.example.test.dao.CrudDAO;
import com.example.test.entity.User;

import java.sql.SQLException;

public interface UserProfileDAO extends CrudDAO<User> {

    public User getUserDetails() throws SQLException, ClassNotFoundException;

    public String changeUserDetailsOne(String[] data, String[] column) throws SQLException, ClassNotFoundException;

    public String changeUserDetailsTwo(String[] data, String[] column) throws SQLException, ClassNotFoundException;

    public String changeUserDetailsThree(String[] data, String[] column) throws SQLException, ClassNotFoundException;

    public String changeUserDetailsFour(String[] data, String[] column) throws SQLException, ClassNotFoundException;
}
