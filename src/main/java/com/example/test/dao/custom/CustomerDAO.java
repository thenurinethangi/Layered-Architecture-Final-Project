package com.example.test.dao.custom;

import com.example.test.dao.CrudDAO;
import com.example.test.entity.Customer;
import javafx.collections.ObservableList;

import java.sql.SQLException;

public interface CustomerDAO extends CrudDAO<Customer> {

    public ObservableList<String> getAllIds() throws SQLException, ClassNotFoundException;

    public ObservableList<String> getPhoneNoSuggestions(String input) throws SQLException, ClassNotFoundException;

    public ObservableList<String> getNicSuggestions(String input) throws SQLException, ClassNotFoundException;

    public ObservableList<String> getNameSuggestions(String input) throws SQLException, ClassNotFoundException;

    public ObservableList<String> getJobTitlesSuggestions(String input) throws SQLException, ClassNotFoundException;

    public String isExistByNic(String nic) throws SQLException, ClassNotFoundException;

    public Customer isExistByPhoneNo(String phoneNo) throws SQLException, ClassNotFoundException;
}
