package com.example.test.bo.custom;

import com.example.test.bo.SuperBO;
import com.example.test.dto.HouseInspectDTO;
import com.example.test.dto.TenantDTO;
import com.example.test.dto.UnitDTO;
import com.example.test.entity.HouseInspect;
import javafx.collections.ObservableList;

import java.sql.SQLException;

public interface HouseInspectBO extends SuperBO {

    public ObservableList<HouseInspectDTO> getAll() throws SQLException, ClassNotFoundException;

    public HouseInspectDTO getLatestHouseInspectOfTenant(String tenantId) throws SQLException, ClassNotFoundException;

    public String add(HouseInspectDTO houseInspectDTO) throws SQLException, ClassNotFoundException;

    public String generateNewId() throws SQLException, ClassNotFoundException;

    public ObservableList<String> getHouseCheckNumbersSuggestions(String input) throws SQLException, ClassNotFoundException;

    public HouseInspectDTO search(String houseInspectionNumber) throws SQLException, ClassNotFoundException;

    public String update(HouseInspectDTO houseInspectDTO) throws SQLException, ClassNotFoundException;

    public boolean checkIfThisCheckLastCheck(HouseInspectDTO selectedHouseCheck) throws SQLException, ClassNotFoundException;

    public String delete(HouseInspectDTO selectedHouseCheck) throws SQLException, ClassNotFoundException;

    public TenantDTO getTenantDetails(String tenantId)throws SQLException, ClassNotFoundException;

    public UnitDTO getUnitDetails(String houseId)throws SQLException, ClassNotFoundException;

    public ObservableList<TenantDTO> getAllTenants() throws SQLException, ClassNotFoundException;

    public ObservableList<UnitDTO> getAllUnits() throws SQLException, ClassNotFoundException;

    public boolean checkRemainingSecurityFundEnoughOrNot(String tenantId,String costOfRepair) throws SQLException, ClassNotFoundException;

    public String reduceRepairCostFromSecurityCharge(String tenantId,String costOfRepair) throws SQLException, ClassNotFoundException;
}
