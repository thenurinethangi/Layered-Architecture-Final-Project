package com.example.test.dao.custom;

import com.example.test.dao.CrudDAO;
import com.example.test.entity.HouseReturn;
import javafx.collections.ObservableList;

import java.sql.SQLException;

public interface HouseReturnDAO extends CrudDAO<HouseReturn> {

    public boolean addNewHouseReturnWithoutRefund(HouseReturn houseReturn) throws SQLException, ClassNotFoundException;

    public boolean addNewHouseReturnWithRefund(HouseReturn houseReturn) throws SQLException, ClassNotFoundException;

    public ObservableList<String> getAllDistinctTenantIds() throws SQLException, ClassNotFoundException;

    public ObservableList<String> getAllDistinctHouseIds() throws SQLException, ClassNotFoundException;

}
