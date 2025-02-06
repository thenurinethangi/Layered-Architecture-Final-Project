package com.example.test.dao.custom;

import com.example.test.dao.CrudDAO;
import com.example.test.entity.Tenant;
import javafx.collections.ObservableList;

import java.sql.SQLException;

public interface TenantDAO extends CrudDAO<Tenant> {

    public ObservableList<String> getHouseIds() throws SQLException, ClassNotFoundException;

    public ObservableList<String> getNameSuggestions(String input) throws SQLException, ClassNotFoundException;

    public boolean updateLastPaymentMonth(Tenant tenant) throws SQLException, ClassNotFoundException;

    public boolean checkRemainingSecurityFundEnoughOrNot(String tenantId, String costOfRepair) throws SQLException, ClassNotFoundException;

    public String reduceRepairCostFromSecurityCharge(String tenantId, String costOfRepair) throws SQLException, ClassNotFoundException;

    public Tenant searchByPhoneNo(String phoneNo) throws SQLException, ClassNotFoundException;

    public boolean setNewLastPaidMonth(Tenant tenant) throws SQLException, ClassNotFoundException;

    public ObservableList<Tenant> getTenantWhoNotDonePayment() throws SQLException, ClassNotFoundException;

    public ObservableList<Tenant> checkTenantsWhoHaveNotPaidForEarlierMonths() throws SQLException, ClassNotFoundException;
}
