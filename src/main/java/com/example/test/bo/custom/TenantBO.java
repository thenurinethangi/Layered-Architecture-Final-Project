package com.example.test.bo.custom;

import com.example.test.bo.SuperBO;
import com.example.test.dto.TenantDTO;
import com.example.test.dto.UnitDTO;
import javafx.collections.ObservableList;

import java.sql.SQLException;

public interface TenantBO extends SuperBO {

    public String generateNewId() throws SQLException, ClassNotFoundException;

    public String add(TenantDTO tenantDTO) throws SQLException, ClassNotFoundException;

    public ObservableList<TenantDTO> getAll() throws SQLException, ClassNotFoundException;

    public ObservableList<String> getHouseIds() throws SQLException, ClassNotFoundException;

    public ObservableList<String> getNameSuggestions(String input) throws SQLException, ClassNotFoundException;

    public TenantDTO search(String tenantId) throws SQLException, ClassNotFoundException;

    public String update(TenantDTO tenantDTO) throws SQLException, ClassNotFoundException;

    public boolean updateLastPaymentMonth(TenantDTO tenant) throws SQLException, ClassNotFoundException;

    public String delete(TenantDTO tenant) throws SQLException, ClassNotFoundException;

    public boolean checkRemainingSecurityFundEnoughOrNot(String tenantId, String costOfRepair) throws SQLException, ClassNotFoundException;

    public String reduceRepairCostFromSecurityCharge(String tenantId, String costOfRepair) throws SQLException, ClassNotFoundException;

    public TenantDTO searchByPhoneNo(String phoneNo) throws SQLException, ClassNotFoundException;

    public boolean setNewLastPaidMonth(TenantDTO tenant) throws SQLException, ClassNotFoundException;

    public UnitDTO getUnitDetails(String unitId)throws SQLException, ClassNotFoundException;
}
