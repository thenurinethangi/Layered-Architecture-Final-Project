package com.example.test.bo.custom;

import com.example.test.bo.SuperBO;
import com.example.test.dto.MaintenanceRequestDTO;
import com.example.test.dto.TenantDTO;
import com.example.test.dto.UnitDTO;
import javafx.collections.ObservableList;

import java.sql.SQLException;

public interface MaintenanceRequestBO extends SuperBO {

    public ObservableList<MaintenanceRequestDTO> getAll() throws SQLException, ClassNotFoundException;

    public ObservableList<String> getDistinctTenantIds() throws SQLException, ClassNotFoundException;

    public String updateRequestStatus(MaintenanceRequestDTO selectedRequest) throws SQLException, ClassNotFoundException;

    public String generateNewId() throws SQLException, ClassNotFoundException;

    public String add(MaintenanceRequestDTO maintenanceRequestDTO) throws SQLException, ClassNotFoundException;

    public String delete(MaintenanceRequestDTO selectedRequest) throws SQLException, ClassNotFoundException;

    public String update(MaintenanceRequestDTO maintenanceRequestDTO) throws SQLException, ClassNotFoundException;

    public boolean setRequestNotComplete(MaintenanceRequestDTO maintenanceRequestDTO) throws SQLException, ClassNotFoundException;

    public ObservableList<String> getRequestIdSuggestions(String input) throws SQLException, ClassNotFoundException;

    public boolean checkEnteredRequestIdValid(String maintenanceRequestNo) throws SQLException, ClassNotFoundException;

    public boolean setActualCost(MaintenanceRequestDTO maintenanceRequestDTO) throws SQLException, ClassNotFoundException;

    public ObservableList<MaintenanceRequestDTO> getAllInProgressRequests() throws SQLException, ClassNotFoundException;

    public TenantDTO getTenantDetails(String tenantId)throws SQLException, ClassNotFoundException;

    public UnitDTO getUnitDetails(String houseId)throws SQLException, ClassNotFoundException;

    public ObservableList<String> getAllTechnicians()throws SQLException, ClassNotFoundException;
}
