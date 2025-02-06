package com.example.test.dao.custom;

import com.example.test.dao.CrudDAO;
import com.example.test.entity.HouseType;

import java.sql.SQLException;

public interface HouseTypeDAO extends CrudDAO<HouseType> {

    public String Update(HouseType houseType, HouseType oldHouseTypeDetails) throws SQLException, ClassNotFoundException;
}
