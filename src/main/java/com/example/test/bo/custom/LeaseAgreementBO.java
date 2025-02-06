package com.example.test.bo.custom;

import com.example.test.bo.SuperBO;
import com.example.test.dto.HouseInspectDTO;
import com.example.test.dto.LeaseAgreementDTO;
import com.example.test.dto.TenantDTO;
import com.example.test.entity.Tenant;
import com.example.test.view.tdm.LeaseAgreementTM;
import javafx.collections.ObservableList;

import java.sql.SQLException;

public interface LeaseAgreementBO extends SuperBO {

    public String add(LeaseAgreementDTO leaseAgreementDTO) throws SQLException, ClassNotFoundException;

    public String generateNewId() throws SQLException, ClassNotFoundException;

    public ObservableList<LeaseAgreementDTO> getAll() throws SQLException, ClassNotFoundException;

    public LeaseAgreementDTO search(String leaseId) throws SQLException, ClassNotFoundException;

    public HouseInspectDTO getLastHouseInspectCheckDetails(LeaseAgreementTM selectedLeaseAgreement) throws SQLException, ClassNotFoundException;

    public String update(LeaseAgreementDTO selectedLeaseAgreement) throws SQLException, ClassNotFoundException;

    public TenantDTO getTenantEmail(String tenantId) throws SQLException, ClassNotFoundException;

    public boolean updateLeaseAgreementStatus(LeaseAgreementDTO leaseAgreement) throws SQLException, ClassNotFoundException;

    public String getLeaseAgreementByTenantId(String tenantId) throws SQLException, ClassNotFoundException;

    public ObservableList<LeaseAgreementDTO> getOnlyActiveAgreements() throws SQLException, ClassNotFoundException;

    public ObservableList<String> getDistinctHouseIds() throws SQLException, ClassNotFoundException;
}
