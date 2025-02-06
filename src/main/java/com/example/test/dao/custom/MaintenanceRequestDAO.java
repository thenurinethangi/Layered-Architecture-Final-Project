package com.example.test.dao.custom;

import com.example.test.dao.CrudDAO;
import com.example.test.entity.MaintainRequest;
import javafx.collections.ObservableList;

import java.sql.SQLException;

public interface MaintenanceRequestDAO extends CrudDAO<MaintainRequest> {

    public ObservableList<String> getDistinctTenantIds() throws SQLException, ClassNotFoundException;

    public String updateRequestStatus(MaintainRequest selectedRequest) throws SQLException, ClassNotFoundException;

    public boolean setRequestNotComplete(MaintainRequest maintainRequest) throws SQLException, ClassNotFoundException;

    public ObservableList<String> getRequestIdSuggestions(String input) throws SQLException, ClassNotFoundException;

    public boolean checkEnteredRequestIdValid(String maintenanceRequestNo) throws SQLException, ClassNotFoundException;

    public boolean setActualCost(MaintainRequest maintainRequest) throws SQLException, ClassNotFoundException;

    public ObservableList<MaintainRequest> getAllInProgressRequests() throws SQLException, ClassNotFoundException;

    public ObservableList<String> getInProcessMaintenanceRequest() throws SQLException, ClassNotFoundException;

}
