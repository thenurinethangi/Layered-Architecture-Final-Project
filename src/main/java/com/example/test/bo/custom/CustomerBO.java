package com.example.test.bo.custom;

import com.example.test.bo.SuperBO;
import com.example.test.dto.CustomerDTO;
import javafx.collections.ObservableList;

import java.sql.SQLException;

public interface CustomerBO extends SuperBO {

    public ObservableList<CustomerDTO> getAll() throws SQLException, ClassNotFoundException;

    public ObservableList<String> getAllIds() throws SQLException, ClassNotFoundException;

    public String delete(CustomerDTO customerDto) throws SQLException, ClassNotFoundException;

    public boolean isExist(CustomerDTO customerDto) throws SQLException, ClassNotFoundException;

    public ObservableList<String> getPhoneNoSuggestions(String input) throws SQLException, ClassNotFoundException;

    public ObservableList<String> getNicSuggestions(String input) throws SQLException, ClassNotFoundException;

    public ObservableList<String> getNameSuggestions(String input) throws SQLException, ClassNotFoundException;

    public ObservableList<String> getJobTitlesSuggestions(String input) throws SQLException, ClassNotFoundException;

    public CustomerDTO search(String customerId) throws SQLException, ClassNotFoundException;

    public String isExistByNic(String nic) throws SQLException, ClassNotFoundException;

    public CustomerDTO isExistByPhoneNo(String phoneNo) throws SQLException, ClassNotFoundException;

    public String generateNewId() throws SQLException, ClassNotFoundException;

    public String update(CustomerDTO customerDto) throws SQLException, ClassNotFoundException;

    public String add(CustomerDTO customerDto) throws SQLException, ClassNotFoundException;

}
