package com.example.test.dao.custom.impl;

import com.example.test.dao.SQLUtil;
import com.example.test.dao.custom.PurchaseAgreementDAO;
import com.example.test.entity.PurchaseAgreement;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public class PurchaseAgreementDAOImpl implements PurchaseAgreementDAO {

    public ObservableList<PurchaseAgreement> getAll() throws SQLException, ClassNotFoundException {

        String sql = "select * from purchaseagreement where status = 'Active'";
        ResultSet result = SQLUtil.execute(sql);

        ObservableList<PurchaseAgreement> allAgreements = FXCollections.observableArrayList();

        while(result.next()){

           PurchaseAgreement purchaseAgreement = new PurchaseAgreement();
           purchaseAgreement.setPurchaseAgreementId(result.getString(1));
           purchaseAgreement.setHomeOwnerId(result.getString(2));
           purchaseAgreement.setHouseId(result.getString(3));
           purchaseAgreement.setPurchasePrice(result.getDouble(4));
           purchaseAgreement.setSignedDate(result.getString(5));
           purchaseAgreement.setStatus(result.getString(6));

           allAgreements.add(purchaseAgreement);
        }

        return allAgreements;
    }


    @Override
    public String delete(PurchaseAgreement entity) throws SQLException, ClassNotFoundException {
        return "";
    }


    @Override
    public boolean isExist(PurchaseAgreement entity) throws SQLException, ClassNotFoundException {

        String sql = "select * from purchaseagreement where purchaseAgreementId = ?";
        ResultSet rst = SQLUtil.execute(sql,entity.getPurchaseAgreementId());
        return rst.next();
    }


    public ObservableList<String> getAllHouseIds() throws SQLException, ClassNotFoundException {

        String sql = "select houseId from purchaseagreement order by houseId asc";
        ResultSet result = SQLUtil.execute(sql);

        ObservableList<String> houseIds = FXCollections.observableArrayList();
        houseIds.add("Select");

        while(result.next()){

            houseIds.add(result.getString("houseId"));
        }

        return houseIds;
    }


    public ObservableList<String> getAllOwnerIds() throws SQLException, ClassNotFoundException {

        String sql = "select homeOwnerId from purchaseagreement order by homeOwnerId asc";
        ResultSet result = SQLUtil.execute(sql);

        ObservableList<String> ownerIds = FXCollections.observableArrayList();
        ownerIds.add("Select");

        while(result.next()){

            ownerIds.add(result.getString("homeOwnerId"));
        }

        return ownerIds;
    }


    public String add(PurchaseAgreement purchaseAgreement) throws SQLException, ClassNotFoundException {

        String id = generateNewId();

        String sql = "insert into purchaseagreement values(?,?,?,?,?,?)";
        boolean result = SQLUtil.execute(sql,id,purchaseAgreement.getHomeOwnerId(), purchaseAgreement.getHouseId(),purchaseAgreement.getPurchasePrice(),String.valueOf(LocalDate.now()),"Active");

        return result ? "Success" : "Failed";
    }


    public String generateNewId() throws SQLException, ClassNotFoundException {

        String sql = "select purchaseAgreementId from purchaseagreement order by purchaseAgreementId desc limit 1";
        ResultSet result = SQLUtil.execute(sql);

        if(result.next()){

            String lastId = result.getString("purchaseAgreementId");
            String subStr = lastId.substring(3);
            int id = Integer.parseInt(subStr);
            id+=1;
            String newId = String.format("PA-%04d", id);
            return newId;

        }

        return "PA-0001";
    }

    @Override
    public String update(PurchaseAgreement entity) throws SQLException, ClassNotFoundException {
        return "";
    }


    public PurchaseAgreement search(String id) throws SQLException, ClassNotFoundException{

        String sql = "select * from purchaseagreement where purchaseAgreementId = ?";
        ResultSet result = SQLUtil.execute(sql,id);

        PurchaseAgreement purchaseAgreement = new PurchaseAgreement();

        if(result.next()){
            purchaseAgreement.setPurchaseAgreementId(result.getString(1));
            purchaseAgreement.setHomeOwnerId(result.getString(2));
            purchaseAgreement.setHouseId(result.getString(3));
            purchaseAgreement.setPurchasePrice(result.getDouble(4));
            purchaseAgreement.setSignedDate(result.getString(5));
            purchaseAgreement.setStatus(result.getString(6));
        }

        return purchaseAgreement;
    }
}




