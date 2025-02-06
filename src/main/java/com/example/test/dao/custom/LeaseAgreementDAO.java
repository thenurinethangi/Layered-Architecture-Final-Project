package com.example.test.dao.custom;

import com.example.test.dao.CrudDAO;
import com.example.test.dao.SQLUtil;
import com.example.test.dto.HouseInspectDTO;
import com.example.test.entity.LeaseAgreement;
import com.example.test.entity.Tenant;
import com.example.test.view.tdm.LeaseAgreementTM;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public interface LeaseAgreementDAO extends CrudDAO<LeaseAgreement> {

    public boolean updateLeaseAgreementStatus(LeaseAgreement leaseAgreement) throws SQLException, ClassNotFoundException;

    public String getLeaseAgreementByTenantId(String tenantId) throws SQLException, ClassNotFoundException;

    public ObservableList<LeaseAgreement> getOnlyActiveAgreements() throws SQLException, ClassNotFoundException;

    public ObservableList<String> getDistinctHouseIds() throws SQLException, ClassNotFoundException;

    public ObservableList<String> getExpiredAgreements() throws SQLException, ClassNotFoundException;

    public ObservableList<String> getSoonExpiredAgreements() throws SQLException, ClassNotFoundException;
}
