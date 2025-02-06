package com.example.test.dao.custom;

import com.example.test.dao.CrudDAO;
import com.example.test.entity.Owner;
import javafx.collections.ObservableList;

import java.sql.SQLException;

public interface OwnerDAO extends CrudDAO<Owner> {

    public ObservableList<String> getAllDistinctInvoiceNos() throws SQLException, ClassNotFoundException;

    public ObservableList<String> getAllHouseIds() throws SQLException, ClassNotFoundException;

    public ObservableList<String> getNameSuggestions(String input) throws SQLException, ClassNotFoundException;

    public ObservableList<String> getPhoneNosSuggestions(String input) throws SQLException, ClassNotFoundException;

    public String getLastAddedOwnerId() throws SQLException, ClassNotFoundException;

}
