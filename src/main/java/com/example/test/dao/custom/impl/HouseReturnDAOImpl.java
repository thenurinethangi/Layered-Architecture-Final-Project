package com.example.test.dao.custom.impl;

import com.example.test.dao.SQLUtil;
import com.example.test.dao.custom.HouseReturnDAO;
import com.example.test.db.DBConnection;
import com.example.test.dto.HouseReturnDTO;
import com.example.test.entity.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public class HouseReturnDAOImpl implements HouseReturnDAO {

    private UnitDAOImpl unitDAOImpl = new UnitDAOImpl();
    private final TenantDAOImpl tenantDAOImpl = new TenantDAOImpl();
    private final LeaseAgreementDAOImpl leaseAgreementDAOImpl = new LeaseAgreementDAOImpl();
    private final ExpenseDAOImpl expenseDAOImpl = new ExpenseDAOImpl();


    public ObservableList<HouseReturn> getAll() throws SQLException, ClassNotFoundException {

        String sql = "select * from returnhouse where isActive!=0";
        ResultSet result = SQLUtil.execute(sql);

        ObservableList<HouseReturn> returnHouses = FXCollections.observableArrayList();

        while (result.next()){

            String returnNo = result.getString(1);
            String reason = result.getString(2);
            String date = result.getString(3);
            String tenantId = result.getString(4);
            String houseId = result.getString(5);
            String refundedAmount = result.getString(6);
            if(refundedAmount==null){
                refundedAmount = "N/A";
            }
            String expenseNo = result.getString(7);
            if(expenseNo==null){
                expenseNo = "N/A";
            }
            int isActive = result.getInt(8);

            HouseReturn returnHouse = new HouseReturn(returnNo,reason,date,tenantId,houseId,refundedAmount,expenseNo,isActive);
            returnHouses.add(returnHouse);
        }

        return  returnHouses;
    }


    public boolean addNewHouseReturnWithoutRefund(HouseReturn houseReturn) throws SQLException, ClassNotFoundException {

        String newReturnId =  generateNewId();
        String today = String.valueOf(LocalDate.now());

        String sql = "INSERT INTO returnhouse (returnNo, reason, date, tenantId, houseId, isActive)\n" +
                "VALUES (?,?,?,?,?,?)";
        boolean result = SQLUtil.execute(sql,newReturnId,houseReturn.getReason(),today,houseReturn.getTenantId(),houseReturn.getHouseId(),1);

        return result;
    }


    public String generateNewId() throws SQLException, ClassNotFoundException {

        String sql = "select returnNo from returnhouse order by returnNo desc limit 1";
        ResultSet result = SQLUtil.execute(sql);

        if(result.next()){

            String lastId = result.getString("returnNo");
            String subStr = lastId.substring(3);
            System.out.println("sub string: "+subStr);
            int id = Integer.parseInt(subStr);
            id+=1;
            return String.format("RT-%04d", id);

        }
        else{
            return "RT-0001";
        }

    }

    @Override
    public String update(HouseReturn entity) throws SQLException, ClassNotFoundException {
        return "";
    }

    @Override
    public String add(HouseReturn entity) throws SQLException, ClassNotFoundException {
        return "";
    }


    public boolean addNewHouseReturnWithRefund(HouseReturn houseReturn) throws SQLException, ClassNotFoundException {

        String newReturnId =  generateNewId();
        String today = String.valueOf(LocalDate.now());

        Expense expenseDto = expenseDAOImpl.getLastExpenseDetails();

        String sql = "INSERT INTO returnhouse (returnNo, reason, date, tenantId, houseId, refundedAmount, expenseNo, isActive)\n" +
                "VALUES (?,?,?,?,?,?,?,?)";
        boolean result = SQLUtil.execute(sql,newReturnId,houseReturn.getReason(),today,houseReturn.getTenantId(),houseReturn.getHouseId(),expenseDto.getAmount(),expenseDto.getExpenceNo(),1);

        return result;
    }


    public ObservableList<String> getAllDistinctTenantIds() throws SQLException, ClassNotFoundException {

        String sql = "SELECT DISTINCT tenantId FROM returnhouse order by tenantId asc";
        ResultSet result = SQLUtil.execute(sql);

        ObservableList<String> distinctTenantIds = FXCollections.observableArrayList();
        distinctTenantIds.add("Select");

        while (result.next()){

            distinctTenantIds.add(result.getString("tenantId"));
        }

        return distinctTenantIds;
    }


    public ObservableList<String> getAllDistinctHouseIds() throws SQLException, ClassNotFoundException {

        String sql = "SELECT DISTINCT houseId FROM returnhouse order by houseId asc";
        ResultSet result = SQLUtil.execute(sql);

        ObservableList<String> distinctHouseIds = FXCollections.observableArrayList();
        distinctHouseIds.add("Select");

        while (result.next()){

            distinctHouseIds.add(result.getString("houseId"));
        }

        return distinctHouseIds;
    }


    public String delete(HouseReturn selectedRow) throws SQLException, ClassNotFoundException {

        String sql = "UPDATE returnhouse SET isActive = ? WHERE returnNo = ?";
        boolean result = SQLUtil.execute(sql,0,selectedRow.getReturnNo());

        return result ? "Successfully Deleted The House Return Details" : "Failed To Delete The House Return Details,Try Again Later";
    }


    @Override
    public boolean isExist(HouseReturn entity) throws SQLException, ClassNotFoundException {

        String sql = "select * from returnhouse where returnNo = ?";
        ResultSet rst = SQLUtil.execute(sql,entity.getReturnNo());
        return rst.next();
    }


    @Override
    public HouseReturn search(String id) throws SQLException, ClassNotFoundException {

        String sql = "select * from returnhouse where returnNo = ?";
        ResultSet rst = SQLUtil.execute(sql,id);

        HouseReturn houseReturn = new HouseReturn();

        if(rst.next()){
            houseReturn.setReturnNo(rst.getString(1));
            houseReturn.setReason(rst.getString(2));
            houseReturn.setDate(rst.getString(3));
            houseReturn.setTenantId(rst.getString(4));
            houseReturn.setHouseId(rst.getString(5));
            houseReturn.setRefundedAmount(rst.getString(6));
            houseReturn.setExpenseNo(rst.getString(7));
            houseReturn.setIsActive(rst.getInt(8));
        }
        return houseReturn;
    }
}






