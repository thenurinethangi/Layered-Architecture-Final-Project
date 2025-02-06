package com.example.test.dao.custom.impl;

import com.example.test.dao.SQLUtil;
import com.example.test.dao.custom.HouseTypeDAO;
import com.example.test.db.DBConnection;
import com.example.test.entity.HouseType;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public class HouseTypeDAOImpl implements HouseTypeDAO {


    public ObservableList<HouseType> getAll() throws SQLException, ClassNotFoundException {

        String sql = "select * from housetype";
        ResultSet result = SQLUtil.execute(sql);

        ObservableList<HouseType> tableData = FXCollections.observableArrayList();

        while(result.next()){

            String houseType = result.getString("houseType");
            String desc = result.getString("description");

            HouseType houseTypeEntity = new HouseType(houseType,desc);

            tableData.add(houseTypeEntity);
        }

        return tableData;
    }


    public String add(HouseType houseType) throws SQLException, ClassNotFoundException {

        String sql = "insert into housetype values(?,?)";
        boolean result =  SQLUtil.execute(sql,houseType.getHouseType(),houseType.getDescription());

        return result ? "successfully added new house type to the system" : "something went wrong, failed to add new house type to the system, try again later";

    }


    public String delete(HouseType houseType) throws SQLException, ClassNotFoundException {

        String sql = "delete from housetype where houseType = ?";
        boolean result = SQLUtil.execute(sql,houseType.getHouseType());

        return result ? "successfully delete the house type" : "failed to delete the house type,try again later";

    }

    @Override
    public boolean isExist(HouseType entity) throws SQLException, ClassNotFoundException {

        String sql = "select * from housetype where houseType = ?";
        ResultSet rst = SQLUtil.execute(sql,entity.getHouseType());
        return rst.next();
    }

    @Override
    public HouseType search(String id) throws SQLException, ClassNotFoundException {

        String sql = "select * from housetype where houseType = ?";
        ResultSet rst = SQLUtil.execute(sql,id);

        HouseType houseType = new HouseType();

        if(rst.next()){
            houseType.setHouseType(rst.getString(1));
            houseType.setDescription(rst.getString(2));
        }

        return houseType;
    }


    @Override
    public String generateNewId() throws SQLException, ClassNotFoundException {
        return "";
    }

    @Override
    public String update(HouseType entity) throws SQLException, ClassNotFoundException {
        return "";
    }


    public String Update(HouseType houseType, HouseType oldHouseTypeDetails) throws SQLException, ClassNotFoundException{

        if(houseType.getHouseType().isEmpty()){
            String sqlTwo = "UPDATE housetype SET description = ? WHERE houseType = ?";
            boolean res = SQLUtil.execute(sqlTwo,houseType.getDescription(), oldHouseTypeDetails.getHouseType());

            return res ? "Successfully update the House Type" : "Something went wrong, failed to update the House Type, try again";
        }

        if(houseType.getDescription().isEmpty()){
            String sqlTwo = "UPDATE housetype SET houseType = ? WHERE houseType = ?";
            boolean res = SQLUtil.execute(sqlTwo,houseType.getHouseType(), oldHouseTypeDetails.getHouseType());

            return res ? "Successfully update the House Type" : "Something went wrong, failed to update the House Type, try again";
        }

        String sqlOne = "select * from housetype where houseType = ?";
        ResultSet result = SQLUtil.execute(sqlOne,houseType.getHouseType());

        if(result.next()){
            return "This House Type is  already exist";
        }

        String sqlTwo = "UPDATE housetype SET houseType = ?, description = ? WHERE houseType = ?";
        boolean res = SQLUtil.execute(sqlTwo,houseType.getHouseType(),houseType.getDescription(), oldHouseTypeDetails.getHouseType());

        return res ? "Successfully update the House Type" : "Something went wrong, failed to update the House Type, try again";
    }
}






