package com.example.test.dao.custom;

import com.example.test.dao.CrudDAO;
import com.example.test.entity.Unit;
import javafx.collections.ObservableList;

import java.sql.SQLException;

public interface UnitDAO extends CrudDAO<Unit> {

    public ObservableList<String> getAvailableRentHouses(Unit unit) throws SQLException, ClassNotFoundException;

    public ObservableList<String> getRecommendedRentHouses(Unit unit) throws SQLException, ClassNotFoundException;

    public ObservableList<String> getAvailableSellHouses(Unit unit) throws SQLException, ClassNotFoundException;

    public ObservableList<Unit> getAvailableRentHousesAsUnitObject(Unit unit) throws SQLException, ClassNotFoundException;

    public ObservableList<Unit> getRecommendedRentHousesAsUnitObject(Unit unit) throws SQLException, ClassNotFoundException;

    public ObservableList<Unit> getAvailableSellHousesAsUnitObject(Unit unit) throws SQLException, ClassNotFoundException;

    public boolean updateHouseStatus(Unit unit) throws SQLException, ClassNotFoundException;

    public ObservableList<Unit> getOnlyAvailableUnits() throws SQLException, ClassNotFoundException;

    public ObservableList<String> getRecommendedSellHouses(Unit unit) throws SQLException, ClassNotFoundException;

    public ObservableList<Unit> getRecommendedSellHousesAsUnitObject(Unit unit) throws SQLException, ClassNotFoundException;

    public boolean checkFloorIsUsed(Unit unit) throws SQLException, ClassNotFoundException;

    public boolean isHouseTypeUsing(String houseType) throws SQLException, ClassNotFoundException;
}
