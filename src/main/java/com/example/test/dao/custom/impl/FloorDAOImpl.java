package com.example.test.dao.custom.impl;

import com.example.test.dao.SQLUtil;
import com.example.test.dao.custom.FloorDAO;
import com.example.test.entity.Floor;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.ResultSet;
import java.sql.SQLException;

public class FloorDAOImpl implements FloorDAO {


    public ObservableList<Floor> getAll() throws SQLException, ClassNotFoundException {

        String sql = "select * from floor";
        ResultSet result = SQLUtil.execute(sql);

        ObservableList<Floor> observableList = FXCollections.observableArrayList();

        while (result.next()){

            int floor = result.getInt("floorNo");
            int houseCount = result.getInt("noOfHouses");

            Floor f1 = new Floor(floor,houseCount);

            observableList.add(f1);

        }

        return observableList;

    }


    public String delete(Floor floor) throws SQLException, ClassNotFoundException {

        String sql = "delete from floor where floorNo = ?";
        boolean result = SQLUtil.execute(sql,floor.getFloorNo());

        return result ? "Successfully deleted the floor" : "Failed to delete the floor, Try again later";
    }

    @Override
    public boolean isExist(Floor entity) throws SQLException, ClassNotFoundException {

        String sql = "select * from floor where floorNo = ?";
        ResultSet rst = SQLUtil.execute(sql,entity.getFloorNo());
        return rst.next();

    }


    @Override
    public Floor search(String id) throws SQLException, ClassNotFoundException {

        String sql = "select * from floor where floorNo = ?";
        ResultSet rst = SQLUtil.execute(sql,id);

        Floor floor = new Floor();

        if(rst.next()){
            floor.setFloorNo(rst.getInt(1));
            floor.setNoOfHouses(rst.getInt(2));
        }

        return floor;
    }


    @Override
    public String generateNewId() throws SQLException, ClassNotFoundException {
        return "";
    }


    public String add(Floor floor) throws SQLException, ClassNotFoundException {

        String checkSql = "select * from floor where floorNo = ?";
        ResultSet res = SQLUtil.execute(checkSql,floor.getFloorNo());

        if(res.next()){

            return "This Floor No Already Exits, Try new Floor No";
        }

        String sql = "insert into floor values(?,?)";
        boolean result = SQLUtil.execute(sql,floor.getFloorNo(),floor.getNoOfHouses());

        return result ? "successfully add the new floor to the system" : "something went wrong, failed to add new floor, please try again later";

    }


    public ObservableList<String> getAllIds() throws SQLException, ClassNotFoundException {

        String sql = "select floorNo from floor order by floorNo asc";
        ResultSet result = SQLUtil.execute(sql);

        ObservableList<String> observableList = FXCollections.observableArrayList();
        observableList.add("Select");

        while (result.next()){

           int floor =  result.getInt("floorNo");
           observableList.add(String.valueOf(floor));

        }

        return observableList;
    }


    public String update(Floor floor) throws SQLException, ClassNotFoundException {

        String sql = "UPDATE floor SET noOfHouses = ? WHERE floorNo = ?";
        boolean result = SQLUtil.execute(sql,floor.getNoOfHouses(),floor.getFloorNo());

        return result ? "Successfully updated the floor" : "Something went wrong, failed to update the floor";
    }
}






