package com.example.test.dao.custom.impl;

import com.example.test.dao.SQLUtil;
import com.example.test.dao.custom.UnitDAO;
import com.example.test.entity.Unit;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UnitDAOImpl implements UnitDAO {


    public ObservableList<Unit> getAll() throws SQLException, ClassNotFoundException {

        String sql = "select * from house";
        ResultSet result = SQLUtil.execute(sql);

        ObservableList<Unit> tableData = FXCollections.observableArrayList();

        while (result.next()){

            String houseId = result.getString("houseId");
            int bedroom = result.getInt("bedroomAmount");
            int bathroom = result.getInt("bathroomAmount");
            String rentOrBuy = result.getString("RentOrBuy");
            String totalValue = result.getString("totalValue");
            String securityCharge = result.getString("security_charge");
            String rent = result.getString("monthlyRent");
            String status = result.getString("status");
            String houseType = result.getString("houseType");
            int floorNo = result.getInt("floorNo");

            Unit unit = new Unit(houseId,bedroom,bathroom,rentOrBuy,totalValue,securityCharge,rent,status,houseType,floorNo);
            tableData.add(unit);
        }
        return  tableData;
    }


    public String delete(Unit selectedRow) throws SQLException, ClassNotFoundException {

        String sql = "delete from house where houseId = ?";
        boolean result = SQLUtil.execute(sql,selectedRow.getHouseId());

        return result ? "Successfully deleted the unit" : "Something went wrong, Failed to delete the unit id: "+selectedRow.getHouseId()+", Try again later";
    }

    @Override
    public boolean isExist(Unit entity) throws SQLException, ClassNotFoundException {

        String sql = "select * from house where houseId = ?";
        ResultSet rst = SQLUtil.execute(sql,entity.getHouseId());
        return rst.next();
    }


    public ObservableList<String> getAvailableRentHouses(Unit unit1) throws SQLException, ClassNotFoundException {

        String sql = "select houseId,bedroomAmount,bathroomAmount,security_charge,monthlyRent,houseType,floorNo from house where RentOrBuy = ? and status = ? and houseType = ? and monthlyRent<=?";
        ResultSet result = SQLUtil.execute(sql,unit1.getRentOrBuy(),"Available",unit1.getHouseType(),unit1.getMonthlyRent());

        ObservableList<String> units = FXCollections.observableArrayList();

        while(result.next()) {

            String houseId = result.getString(1);
            int bedRooms = result.getInt(2);
            int bathRooms = result.getInt(3);
            String securityCharge = result.getString(4);
            String monthlyRent = result.getString(5);
            String houseType = result.getString(6);
            int floorNo = result.getInt(7);

            String unit = "House ID: " + houseId + "\nBedRooms: " + bedRooms + "\nBathRooms: " + bathRooms + "\nSecurity Payment: " + securityCharge + "\nMonthly Rent: " + monthlyRent + "\nHouseType: " + houseType + "\nFloor No: " + floorNo;
            System.out.println(unit);
            units.add(unit);
        }

        return units;
    }


    public ObservableList<String> getRecommendedRentHouses(Unit unit1) throws SQLException, ClassNotFoundException {

        String sql = "select houseId,bedroomAmount,bathroomAmount,security_charge,monthlyRent,houseType,floorNo from house where RentOrBuy = ? and status = ? and houseType = ?";

        ResultSet result = SQLUtil.execute(sql,unit1.getRentOrBuy(),"Available",unit1.getHouseType());

        ObservableList<String> units = FXCollections.observableArrayList();

        while(result.next()) {

            String houseId = result.getString(1);
            int bedRooms = result.getInt(2);
            int bathRooms = result.getInt(3);
            String securityCharge = result.getString(4);
            String monthlyRent = result.getString(5);
            String houseType = result.getString(6);
            int floorNo = result.getInt(7);

            String unit = "House ID: " + houseId + "\nBedRooms: " + bedRooms + "\nBathRooms: " + bathRooms + "\nSecurity Payment: " + securityCharge + "\nMonthly Rent: " + monthlyRent + "\nHouseType: " + houseType + "\nFloor No: " + floorNo;
            System.out.println(unit);
            units.add(unit);
        }

        return units;
    }


    public ObservableList<String> getAvailableSellHouses(Unit unit1) throws SQLException, ClassNotFoundException {

        String sql = "select houseId,bedroomAmount,bathroomAmount,totalValue,houseType,floorNo from house where RentOrBuy = ? and status = ? and houseType = ?";

        ResultSet result = SQLUtil.execute(sql,"Sell","Available",unit1.getHouseType());

        ObservableList<String> units = FXCollections.observableArrayList();

        while(result.next()) {

            String houseId = result.getString(1);
            int bedRooms = result.getInt(2);
            int bathRooms = result.getInt(3);
            String totalValue = result.getString(4);
            String houseType = result.getString(5);
            int floorNo = result.getInt(6);

            String unit = "House ID: " + houseId + "\nBedRooms: " + bedRooms + "\nBathRooms: " + bathRooms + "\nTotal Value Of The House: " + totalValue + "\nHouseType: " + houseType + "\nFloor No: " + floorNo;
            units.add(unit);
        }

        return units;
    }


    public ObservableList<Unit> getAvailableRentHousesAsUnitObject(Unit unit) throws SQLException, ClassNotFoundException {

        String sql = "select houseId,bedroomAmount,bathroomAmount,security_charge,monthlyRent,houseType,floorNo from house where RentOrBuy = ? and status = ? and houseType = ? and monthlyRent<=?";
        ResultSet result = SQLUtil.execute(sql,unit.getRentOrBuy(),"Available",unit.getHouseType(),unit.getMonthlyRent());

        ObservableList<Unit> units = FXCollections.observableArrayList();

        while(result.next()) {

            String houseId = result.getString(1);
            int bedRooms = result.getInt(2);
            int bathRooms = result.getInt(3);
            String securityCharge = result.getString(4);
            String monthlyRent = result.getString(5);
            String houseType = result.getString(6);
            int floorNo = result.getInt(7);

            Unit unit1 = new Unit();
            unit1.setHouseId(houseId);
            unit1.setBedroom(bedRooms);
            unit1.setBathroom(bathRooms);
            unit1.setSecurityCharge(securityCharge);
            unit1.setMonthlyRent(monthlyRent);
            unit1.setHouseType(houseType);
            unit1.setFloorNo(floorNo);

            units.add(unit1);
        }

        return units;
    }


    public ObservableList<Unit> getRecommendedRentHousesAsUnitObject(Unit unit) throws SQLException, ClassNotFoundException {

        String sql = "select houseId,bedroomAmount,bathroomAmount,security_charge,monthlyRent,houseType,floorNo from house where RentOrBuy = ? and status = ? and houseType = ?";

        ResultSet result = SQLUtil.execute(sql,unit.getRentOrBuy(),"Available",unit.getHouseType());

        ObservableList<Unit> unitDTODTOS = FXCollections.observableArrayList();

        while(result.next()) {

            String houseId = result.getString(1);
            int bedRooms = result.getInt(2);
            int bathRooms = result.getInt(3);
            String securityCharge = result.getString(4);
            String monthlyRent = result.getString(5);
            String houseType = result.getString(6);
            int floorNo = result.getInt(7);

            Unit unit1 = new Unit();
            unit1.setHouseId(houseId);
            unit1.setBedroom(bedRooms);
            unit1.setBathroom(bathRooms);
            unit1.setSecurityCharge(securityCharge);
            unit1.setMonthlyRent(monthlyRent);
            unit1.setHouseType(houseType);
            unit1.setFloorNo(floorNo);

            unitDTODTOS.add(unit1);
        }

        return unitDTODTOS;
    }


    public ObservableList<Unit> getAvailableSellHousesAsUnitObject(Unit unit) throws SQLException, ClassNotFoundException {

        System.out.println(unit.getRentOrBuy());
        System.out.println(unit.getHouseType());
        String sql = "select houseId,bedroomAmount,bathroomAmount,totalValue,houseType,floorNo from house where RentOrBuy = ? and status = ? and houseType = ?";

        ResultSet result = SQLUtil.execute(sql,"Sell","Available",unit.getHouseType());

        ObservableList<Unit> units = FXCollections.observableArrayList();

        while(result.next()) {

            String houseId = result.getString(1);
            int bedRooms = result.getInt(2);
            int bathRooms = result.getInt(3);
            String totalValue = result.getString(4);
            String houseType = result.getString(5);
            int floorNo = result.getInt(6);

            Unit unit1 = new Unit();
            unit1.setHouseId(houseId);
            unit1.setBedroom(bedRooms);
            unit1.setBathroom(bathRooms);
            unit1.setTotalValue(totalValue);
            unit1.setHouseType(houseType);
            unit1.setFloorNo(floorNo);

            units.add(unit1);
        }

        return units;
    }


    public boolean updateHouseStatus(Unit unit) throws SQLException, ClassNotFoundException {

        String sql = "UPDATE house SET status = ? WHERE houseId = ?";
        boolean result = SQLUtil.execute(sql,unit.getStatus(),unit.getHouseId());

        return result;
    }


    public Unit search(String houseId) throws SQLException, ClassNotFoundException {

        String sql = "select * from house where houseId = ?";
        ResultSet result = SQLUtil.execute(sql,houseId);

        Unit unit = new Unit();

        if(result.next()){

            String id = result.getString(1);
            unit.setHouseId(id);
            int bedRooms = result.getInt(2);
            unit.setBedroom(bedRooms);
            int bathRoom = result.getInt(3);
            unit.setBathroom(bathRoom);
            String rentOrBuy = result.getString(4);
            unit.setRentOrBuy(rentOrBuy);
            String totalValue = result.getString(5);
            unit.setTotalValue(totalValue);
            String securityCharge = result.getString(6);
            unit.setSecurityCharge(securityCharge);
            String monthlyRent = result.getString(7);
            unit.setMonthlyRent(monthlyRent);
            String status = result.getString(8);
            unit.setStatus(status);
            String houseType = result.getString(9);
            unit.setHouseType(houseType);
            int floor = result.getInt(10);
            unit.setFloorNo(floor);

        }

        return unit;
    }


    public ObservableList<Unit> getOnlyAvailableUnits() throws SQLException, ClassNotFoundException {

        String sql = "select * from house where status = 'Available'";
        ResultSet result = SQLUtil.execute(sql);

        ObservableList<Unit> tableData = FXCollections.observableArrayList();

        while (result.next()){

            String houseId = result.getString("houseId");
            int bedroom = result.getInt("bedroomAmount");
            int bathroom = result.getInt("bathroomAmount");
            String rentOrBuy = result.getString("RentOrBuy");
            String totalValue = result.getString("totalValue");
            String securityCharge = result.getString("security_charge");
            String rent = result.getString("monthlyRent");
            String status = result.getString("status");
            String houseType = result.getString("houseType");
            int floorNo = result.getInt("floorNo");

            Unit unit = new Unit(houseId,bedroom,bathroom,rentOrBuy,totalValue,securityCharge,rent,status,houseType,floorNo);
            tableData.add(unit);

        }
        return  tableData;
    }


    public ObservableList<String> getRecommendedSellHouses(Unit unit1) throws SQLException, ClassNotFoundException {

        String houseType = unit1.getHouseType();

        switch (houseType) {
            case "Small Family House":
            case "Couple House":
                houseType = "Triplex";
            case "Triplex":
                houseType = "Small Family House";
            case "Single Person House":
                houseType = "Couple House";
            case "Big Family House":
                houseType = "Medium-Sized Family House";
            case "Medium-Sized Family House":
                houseType = "Big Family House";
            default:
                houseType = houseType;
        }

        String sql = "select houseId,bedroomAmount,bathroomAmount,totalValue,houseType,floorNo from house where RentOrBuy = ? and status = ? and houseType = ?";

        ResultSet result = SQLUtil.execute(sql,"Sell","Available",houseType);

        ObservableList<String> units = FXCollections.observableArrayList();

        while(result.next()) {

            String houseId = result.getString(1);
            int bedRooms = result.getInt(2);
            int bathRooms = result.getInt(3);
            String totalValue = result.getString(4);
            String type = result.getString(5);
            int floorNo = result.getInt(6);

            String unit = "House ID: " + houseId + "\nBedRooms: " + bedRooms + "\nBathRooms: " + bathRooms + "\nTotal Value Of The House: " + totalValue + "\nHouseType: " + houseType + "\nFloor No: " + floorNo;
            System.out.println(unit);
            units.add(unit);
        }

        return units;
    }


    public ObservableList<Unit> getRecommendedSellHousesAsUnitObject(Unit unit) throws SQLException, ClassNotFoundException {

        String houseType = unit.getHouseType();

        switch (houseType) {
            case "Small Family House":
            case "Couple House":
                houseType = "Triplex";
            case "Triplex":
                houseType = "Small Family House";
            case "Single Person House":
                houseType = "Couple House";
            case "Big Family House":
                houseType = "Medium-Sized Family House";
            case "Medium-Sized Family House":
                houseType = "Big Family House";
            default:
                houseType = houseType;
        }

        String sql = "select houseId,bedroomAmount,bathroomAmount,totalValue,houseType,floorNo from house where RentOrBuy = ? and status = ? and houseType = ?";

        ResultSet result = SQLUtil.execute(sql,"Sell","Available",houseType);

        ObservableList<Unit> units = FXCollections.observableArrayList();

        while(result.next()) {

            String houseId = result.getString(1);
            int bedRooms = result.getInt(2);
            int bathRooms = result.getInt(3);
            String totalValue = result.getString(4);
            String type = result.getString(5);
            int floorNo = result.getInt(6);

            Unit unit1 = new Unit();
            unit1.setHouseId(houseId);
            unit1.setBedroom(bedRooms);
            unit1.setBathroom(bathRooms);
            unit1.setTotalValue(totalValue);
            unit1.setHouseType(type);
            unit1.setFloorNo(floorNo);

            System.out.println(unit1.toString());

            units.add(unit1);
        }

        return units;
    }


    public String generateNewId() throws SQLException, ClassNotFoundException {

        String sql = "select houseId from house order by houseId desc limit 1;";
        ResultSet result = SQLUtil.execute(sql);

        if(!result.next()){
            return "H001";
        }
        else{

            String lastId = result.getString("houseId");
            String subStr = lastId.substring(1);
            int id = Integer.parseInt(subStr);
            id+=1;
            return String.format("H%03d", id);

        }
    }


    public String add(Unit unit) throws SQLException, ClassNotFoundException {

        String sql = "insert into house values(?,?,?,?,?,?,?,?,?,?)";
        boolean result = SQLUtil.execute(sql,unit.getHouseId(),unit.getBedroom(),unit.getBathroom(),unit.getRentOrBuy(),unit.getTotalValue(),unit.getSecurityCharge(),unit.getMonthlyRent(),unit.getStatus(),unit.getHouseType(),unit.getFloorNo());

        return result ? "successfully add new unit to the system" : "something went wrong, failed to add new unit to the system, please try again later";
    }


    public String update(Unit unit) throws SQLException, ClassNotFoundException {

        String sql = "UPDATE house SET bedroomAmount = ?, bathroomAmount = ?, RentOrBuy = ?, totalValue = ?, security_charge = ?, monthlyRent = ?, status = ?, houseType = ?, floorNo = ? WHERE houseId = ?";
        boolean result = SQLUtil.execute(sql,unit.getBedroom(),unit.getBathroom(),unit.getRentOrBuy(),unit.getTotalValue(),unit.getSecurityCharge(),unit.getMonthlyRent(),unit.getStatus(),unit.getHouseType(),unit.getFloorNo(),unit.getHouseId());

        return result ? "successfully update the unit" : "something went wrong, failed to update the unit, please try again later";

    }


    public boolean checkFloorIsUsed(Unit unit) throws SQLException, ClassNotFoundException {

        String sql = "select * from house where floorNo = ?";
        ResultSet result = SQLUtil.execute(sql,unit.getFloorNo());

        return result.next();
    }


    public boolean isHouseTypeUsing(String houseType) throws SQLException, ClassNotFoundException {

        String sql = "select * from house where houseType = ?";
        ResultSet result = SQLUtil.execute(sql,houseType);

        return result.next();
    }
}








