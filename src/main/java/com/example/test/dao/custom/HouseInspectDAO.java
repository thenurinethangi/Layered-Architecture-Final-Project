package com.example.test.dao.custom;

import com.example.test.dao.CrudDAO;

import com.example.test.entity.HouseInspect;
import javafx.collections.ObservableList;

import java.sql.SQLException;

public interface HouseInspectDAO extends CrudDAO<HouseInspect> {

    public HouseInspect getLatestHouseInspectOfTenant(String tenantId) throws SQLException, ClassNotFoundException;

    public ObservableList<String> getHouseCheckNumbersSuggestions(String input) throws SQLException, ClassNotFoundException;

    public boolean checkIfThisCheckLastCheck(HouseInspect selectedHouseCheck) throws SQLException, ClassNotFoundException;

    public ObservableList<String> getWhoNotPaidDamageCost() throws SQLException, ClassNotFoundException;
}
