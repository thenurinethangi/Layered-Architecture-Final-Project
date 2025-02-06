package com.example.test.bo.custom;

import com.example.test.bo.SuperBO;
import com.example.test.dto.*;
import javafx.collections.ObservableList;

import java.sql.SQLException;

public interface HouseReturnBO extends SuperBO {

    public ObservableList<HouseReturnDTO> getAll() throws SQLException, ClassNotFoundException;

    public String reclaimHouse(HouseReturnDTO houseReturnDTO,String agreementId) throws SQLException, ClassNotFoundException;

    public String generateNewId() throws SQLException, ClassNotFoundException;

    public String reclaimHouseWithRefundSecurityDeposit(HouseReturnDTO houseReturnDTO, String agreementId) throws SQLException, ClassNotFoundException;

    public ObservableList<String> getAllDistinctTenantIds() throws SQLException, ClassNotFoundException;

    public ObservableList<String> getAllDistinctHouseIds() throws SQLException, ClassNotFoundException;

    public String delete(HouseReturnDTO selectedRow) throws SQLException, ClassNotFoundException;

    public TenantDTO getTenantDetails(String tenantId)throws SQLException, ClassNotFoundException;

    public UnitDTO getUnitDetails(String houseId)throws SQLException, ClassNotFoundException;

    public String getLeaseAgreementByTenantId(String tenantId) throws SQLException, ClassNotFoundException;

    public HouseInspectDTO getLatestHouseInspectOfTenant(String tenantId) throws SQLException, ClassNotFoundException;
}
