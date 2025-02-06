package com.example.test.dao.custom;

import com.example.test.dao.CrudDAO;
import com.example.test.entity.*;
import javafx.collections.ObservableList;

import java.sql.SQLException;


public interface RequestDAO extends CrudDAO<Request> {

    public boolean updateRequestStatus(Request request) throws SQLException, ClassNotFoundException;

    public ObservableList<Request> getOnlyClosedRequests() throws SQLException, ClassNotFoundException;

    public ObservableList<String> getDistinctCustomers() throws SQLException, ClassNotFoundException;

    public boolean updateAssignedHouse(Request request) throws SQLException, ClassNotFoundException;
}
