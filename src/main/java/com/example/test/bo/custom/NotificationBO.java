package com.example.test.bo.custom;

import com.example.test.bo.SuperBO;
import com.example.test.dto.TenantDTO;
import javafx.collections.ObservableList;

import java.sql.SQLException;

public interface NotificationBO extends SuperBO {

    public ObservableList<TenantDTO> getTenantWhoNotDonePayment() throws SQLException, ClassNotFoundException;

    public ObservableList<TenantDTO> checkTenantsWhoHaveNotPaidForEarlierMonths() throws SQLException, ClassNotFoundException;

    public ObservableList<String> getExpiredAgreements() throws SQLException, ClassNotFoundException;

    public ObservableList<String> getSoonExpiredAgreements() throws SQLException, ClassNotFoundException;

    public ObservableList<String> getInProcessMaintenanceRequest() throws SQLException, ClassNotFoundException;

    public ObservableList<String> getWhoNotPaidDamageCost() throws SQLException, ClassNotFoundException;
}
