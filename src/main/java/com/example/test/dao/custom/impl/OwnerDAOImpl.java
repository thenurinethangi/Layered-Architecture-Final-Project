package com.example.test.dao.custom.impl;

import com.example.test.dao.SQLUtil;
import com.example.test.dao.custom.OwnerDAO;
import com.example.test.entity.Owner;
import com.example.test.view.tdm.PurchaseAgreementTM;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public class OwnerDAOImpl implements OwnerDAO {

    public ObservableList<Owner> getAll() throws SQLException, ClassNotFoundException {

        String sql = "select * from homeowner";
        ResultSet result = SQLUtil.execute(sql);

        ObservableList<Owner> owners = FXCollections.observableArrayList();

        while(result.next()){

            Owner owner = new Owner();
            owner.setOwnerId(result.getString(1));
            owner.setName(result.getString(2));
            owner.setPhoneNo(result.getString(3));
            owner.setMembersCount(result.getInt(4));
            owner.setPurchaseDate(result.getString(5));
            owner.setHouseId(result.getString(6));
            owner.setInvoiceNo(result.getString(7));

            owners.add(owner);
        }

        return owners;
    }


    @Override
    public String delete(Owner entity) throws SQLException, ClassNotFoundException {
        return "";
    }


    @Override
    public boolean isExist(Owner entity) throws SQLException, ClassNotFoundException {

        String sql = "select * from homeowner where homeOwnerId = ?";
        ResultSet rst = SQLUtil.execute(sql,entity.getOwnerId());
        return rst.next();
    }


    public ObservableList<String> getAllDistinctInvoiceNos() throws SQLException, ClassNotFoundException {

        String sql = "select distinct invoiceNo from homeowner order by invoiceNo asc";
        ResultSet result = SQLUtil.execute(sql);

        ObservableList<String> distinctInvoices = FXCollections.observableArrayList();
        distinctInvoices.add("Select");

        while(result.next()){

            distinctInvoices.add(result.getString("invoiceNo"));
        }

        return distinctInvoices;
    }


    public ObservableList<String> getAllHouseIds() throws SQLException, ClassNotFoundException {

        String sql = "select houseId from homeowner order by houseId asc";
        ResultSet result = SQLUtil.execute(sql);

        ObservableList<String> houseIds = FXCollections.observableArrayList();
        houseIds.add("Select");

        while(result.next()){

            houseIds.add(result.getString("houseId"));
        }

        return houseIds;
    }


    public ObservableList<String> getNameSuggestions(String input) throws SQLException, ClassNotFoundException {

        String sql = "SELECT distinct ownerName FROM homeowner WHERE ownerName LIKE ?";
        ResultSet result = SQLUtil.execute(sql, input + "%");

        ObservableList<String> names = FXCollections.observableArrayList();

        while (result.next()) {
            names.add(result.getString("ownerName"));
        }

        return names;
    }


    public ObservableList<String> getPhoneNosSuggestions(String input) throws SQLException, ClassNotFoundException {

        String sql = "SELECT phoneNo FROM homeowner WHERE phoneNo LIKE ?";
        ResultSet result = SQLUtil.execute(sql, input + "%");

        ObservableList<String> phoneNos = FXCollections.observableArrayList();

        while (result.next()) {
            phoneNos.add(result.getString("phoneNo"));
        }

        return phoneNos;
    }


    public String add(Owner owner) throws SQLException, ClassNotFoundException {

        String newOwnerId = generateNewId();

        String sql = "insert into homeowner values(?,?,?,?,?,?,?,?)";
        boolean result =  SQLUtil.execute(sql,newOwnerId,owner.getName(),owner.getPhoneNo(),owner.getMembersCount(), String.valueOf(LocalDate.now()),owner.getHouseId(),owner.getInvoiceNo(),owner.getEmail());

        return result ? "Success" : "Failed";
    }


    public String generateNewId() throws SQLException, ClassNotFoundException {

        String sql = "select homeOwnerId from homeowner order by homeOwnerId desc limit 1";
        ResultSet result = SQLUtil.execute(sql);

        if(result.next()){

            String lastId = result.getString("homeOwnerId");
            String subStr = lastId.substring(3);
            int id = Integer.parseInt(subStr);
            id+=1;
            String newId = String.format("HO-%04d", id);
            return newId;

        }

        return "HO-0001";

    }


    public String getLastAddedOwnerId() throws SQLException, ClassNotFoundException {

        String sql = "select homeOwnerId from homeowner order by homeOwnerId desc limit 1";
        ResultSet result = SQLUtil.execute(sql);

        if(result.next()){

            return result.getString("homeOwnerId");
        }
        return "0";
    }


    public Owner search(String ownerId) throws SQLException, ClassNotFoundException {

        String sql = "select homeOwnerId,ownerName,phoneNo,membersCount,purchaseDate,houseId,invoiceNo,email from homeowner where homeOwnerId = ?";
        ResultSet result = SQLUtil.execute(sql,ownerId);

        Owner owner = new Owner();

        if(result.next()){
            owner.setOwnerId(result.getString(1));
            owner.setName(result.getString(2));
            owner.setPhoneNo(result.getString(3));
            owner.setMembersCount(result.getInt(4));
            owner.setPurchaseDate(result.getString(5));
            owner.setHouseId(result.getString(6));
            owner.setInvoiceNo(result.getString(7));
            owner.setEmail(result.getString(8));
        }
        return owner;
    }


    public String update(Owner owner) throws SQLException, ClassNotFoundException {

        String sql = "UPDATE homeowner SET ownerName = ?, phoneNo = ?, membersCount = ?, email = ? WHERE homeOwnerId = ?";
        boolean result = SQLUtil.execute(sql,owner.getName(),owner.getPhoneNo(),owner.getMembersCount(),owner.getEmail(),owner.getOwnerId());

        return result ? "Successfully Updated The Owner ID: "+owner.getOwnerId() : "Failed To Update Owner ID: "+owner.getOwnerId()+", Please Try Again Later.";
    }

}



