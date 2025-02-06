package com.example.test.dao.custom.impl;

import com.example.test.dao.SQLUtil;
import com.example.test.dao.custom.HouseInspectDAO;
import com.example.test.dto.HouseInspectDTO;
import com.example.test.entity.HouseInspect;
import com.example.test.view.tdm.HouseInspectTM;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public class HouseInspectDAOImpl implements HouseInspectDAO {

    public ObservableList<HouseInspect> getAll() throws SQLException, ClassNotFoundException {

        String sql = "select * from houseStatusCheck";
        ResultSet result = SQLUtil.execute(sql);

        ObservableList<HouseInspect> allHouseInspectChecks = FXCollections.observableArrayList();

        while(result.next()){
            String checkNo = result.getString(1);
            String livingRoomStatus = result.getString(2);
            String kitchenStatus = result.getString(3);
            String bedRoomsStatus = result.getString(4);
            String bathRoomsStatus = result.getString(5);
            String totalHouseStatus = result.getString(6);
            String tenantId = result.getString(7);
            String houseId = result.getString(8);
            String estimatedCostForRepair = result.getString(9);
            String isPaymentDone = result.getString(10);
            String date = result.getString(11);

            HouseInspect houseStatusCheck = new HouseInspect(checkNo,livingRoomStatus,kitchenStatus,bedRoomsStatus,bathRoomsStatus,totalHouseStatus,tenantId,houseId,estimatedCostForRepair,isPaymentDone,date);
            allHouseInspectChecks.add(houseStatusCheck);
        }

        return allHouseInspectChecks;
    }


    public HouseInspect getLatestHouseInspectOfTenant(String tenantId) throws SQLException, ClassNotFoundException {

        String sql = "select totalHouseStatus,isPaymentDone from housestatuscheck where tenantId = ? order by checkNumber desc limit 1";
        ResultSet result = SQLUtil.execute(sql,tenantId);

        HouseInspect houseInspect = new HouseInspect();

        if(result.next()){

            houseInspect.setTotalHouseStatus(result.getString("totalHouseStatus"));
            houseInspect.setIsPaymentDone(result.getString("isPaymentDone"));
        }

        return houseInspect;
    }


    public String add(HouseInspect houseInspect) throws SQLException, ClassNotFoundException {

        String id = generateNewId();

        String sql = "INSERT INTO housestatuscheck VALUES(?,?,?,?,?,?,?,?,?,?,?)";
        boolean result = SQLUtil.execute(sql,id, houseInspect.getLivingRoomStatus(), houseInspect.getKitchenStatus(), houseInspect.getBedRoomsStatus(), houseInspect.getBathRoomsStatus(), houseInspect.getTotalHouseStatus(),
                houseInspect.getTenantId(), houseInspect.getHouseId(), houseInspect.getEstimatedCostForRepair(), houseInspect.getIsPaymentDone(), String.valueOf(LocalDate.now()));

        return result ? "Successfully Added New House Inspection" : "Failed To Add New House Inspection,Try Again Later";
    }


    public String generateNewId() throws SQLException, ClassNotFoundException {

        String sql = "select checkNumber from housestatuscheck order by checkNumber desc limit 1";
        ResultSet result = SQLUtil.execute(sql);

        if(result.next()){

            String lastId = result.getString("checkNumber");
            String subStr = lastId.substring(6);
            System.out.println(subStr);
            int id = Integer.parseInt(subStr);
            id+=1;
            return String.format("CHECK-%05d", id);

        }
        else{
            return "CHECK-00001";
        }

    }


    public ObservableList<String> getHouseCheckNumbersSuggestions(String input) throws SQLException, ClassNotFoundException {

        String sql = "SELECT checkNumber FROM housestatuscheck WHERE checkNumber LIKE ? and isPaymentDone = ?";
        ResultSet result = SQLUtil.execute(sql, input + "%","Not Yet");

        ObservableList<String> checkNumbers = FXCollections.observableArrayList();

        while (result.next()) {
            checkNumbers.add(result.getString("checkNumber"));
        }

        return checkNumbers;
    }


    public HouseInspect search(String houseInspectionNumber) throws SQLException, ClassNotFoundException {

        String sql = "select * from housestatuscheck where checkNumber = ? and isPaymentDone = ?";
        ResultSet result = SQLUtil.execute(sql,houseInspectionNumber,"Not Yet");

        HouseInspect houseInspect = new HouseInspect();

        if(result.next()){

            houseInspect.setCheckNumber(result.getString(1));
            houseInspect.setTenantId(result.getString(7));
            houseInspect.setHouseId(result.getString(8));
            houseInspect.setEstimatedCostForRepair(result.getString(9));
            houseInspect.setDate(result.getString(11));

        }
        return houseInspect;
    }


    public String update(HouseInspect houseInspect) throws SQLException, ClassNotFoundException {

        String sql = "UPDATE housestatuscheck SET isPaymentDone = ? WHERE checkNumber = ?";
        boolean result =  SQLUtil.execute(sql,houseInspect.getIsPaymentDone(),houseInspect.getCheckNumber());

        return result ? "Successfully" : "Failed";
    }


    public boolean checkIfThisCheckLastCheck(HouseInspect selectedHouseCheck) throws SQLException, ClassNotFoundException {

        String sql = "select * from housestatuscheck where tenantId = ? order by checkNumber desc limit 1";
        ResultSet result = SQLUtil.execute(sql,selectedHouseCheck.getTenantId());

        if(result.next()){

            String id = result.getString("checkNumber");
            System.out.println("check number: "+id+"house check no: "+selectedHouseCheck.getCheckNumber());


            if(id.equals(selectedHouseCheck.getCheckNumber())){
                return true;
            }
        }
        return false;
    }


    public String delete(HouseInspect selectedHouseCheck) throws SQLException, ClassNotFoundException {

        String sql = "delete from housestatuscheck where checkNumber = ?";
        boolean result = SQLUtil.execute(sql,selectedHouseCheck.getCheckNumber());

        return result ? "Successfully Deleted The House Inspection Number: "+selectedHouseCheck.getCheckNumber() : "Failed To Delete The House Inspection Number :" +selectedHouseCheck.getCheckNumber();
    }


    @Override
    public boolean isExist(HouseInspect entity) throws SQLException, ClassNotFoundException {

        String sql = "select * from housestatuscheck where checkNumber = ?";
        ResultSet rst = SQLUtil.execute(sql,entity.getHouseId());
        return rst.next();
    }


    public ObservableList<String> getWhoNotPaidDamageCost() throws SQLException, ClassNotFoundException {

        String sql = "select * from housestatuscheck where isPaymentDone = 'Not Yet'";
        ResultSet result = SQLUtil.execute(sql);

        ObservableList<String> notDamageCostPaidTenants = FXCollections.observableArrayList();

        while(result.next()){

            String id = result.getString("checkNumber");
            String tenantId = result.getString("tenantId");
            String houseId = result.getString("houseId");
            String cost = result.getString("estimatedCostForRepair");

            notDamageCostPaidTenants.add("Inspection NO: "+id+",  Tenant ID: "+tenantId+",  House ID: "+houseId+",  Estimated Cost For Repair: "+cost+"\n->The tenant has not paid the house damage costs identified in the latest inspection. Please take necessary action.");
        }

        return notDamageCostPaidTenants;
    }
}







