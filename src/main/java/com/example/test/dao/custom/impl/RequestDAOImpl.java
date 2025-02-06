package com.example.test.dao.custom.impl;

import com.example.test.dao.SQLUtil;
import com.example.test.dao.custom.RequestDAO;
import com.example.test.db.DBConnection;
import com.example.test.entity.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public class RequestDAOImpl implements RequestDAO {


    public ObservableList<Request> getAll() throws SQLException, ClassNotFoundException {

        String sql = "select requestId,customerId,rentOrBuy,houseType,lengthOfLeaseTurnDesire,allDocumentsProvided," +
                "qualifiedCustomerOrNot,agreesToAllTermsAndConditions,isPaymentsCompleted,customerRequestStatus,requestStatus," +
                "houseId from buyAndRentRequest";

        ResultSet result = SQLUtil.execute(sql);

        ObservableList<Request> requests = FXCollections.observableArrayList();

        while (result.next()){
            String requestId = result.getString(1);
            System.out.println(requestId);
            String customerId = result.getString(2);
            System.out.println(customerId);
            String rentOrBuy = result.getString(3);
            System.out.println(rentOrBuy);
            String houseType = result.getString(4);
            System.out.println(houseType);
            String lengthOfLeaseTurnDesire = result.getString(5);
            System.out.println(lengthOfLeaseTurnDesire);
            String allDocumentsProvided = result.getString(6);
            System.out.println(allDocumentsProvided);
            if(allDocumentsProvided==null){
                allDocumentsProvided = "-";
                System.out.println(allDocumentsProvided);
            }
            String qualifiedCustomerOrNot = result.getString(7);
            System.out.println(qualifiedCustomerOrNot);
            if(qualifiedCustomerOrNot==null){
                qualifiedCustomerOrNot = "-";
                System.out.println(qualifiedCustomerOrNot);
            }
            String agreesToAllTermsAndConditions = result.getString(8);
            System.out.println(agreesToAllTermsAndConditions);
            if(agreesToAllTermsAndConditions==null){
                agreesToAllTermsAndConditions = "-";
                System.out.println(agreesToAllTermsAndConditions);
            }
            String isPaymentsCompleted = result.getString(9);
            System.out.println(isPaymentsCompleted);
            if(isPaymentsCompleted==null){
                isPaymentsCompleted = "-";
                System.out.println(isPaymentsCompleted);
            }
            String customerRequestStatus = result.getString(10);
            System.out.println(customerRequestStatus);
            String requestStatus = result.getString(11);
            System.out.println(requestStatus);
            String houseId = result.getString(12);
            System.out.println(houseId);
            if(houseId==null){
                houseId = "-";
                System.out.println(houseId);
            }

            Request request = new Request(requestId,customerId,rentOrBuy,houseType,lengthOfLeaseTurnDesire,allDocumentsProvided,qualifiedCustomerOrNot,
                    agreesToAllTermsAndConditions,isPaymentsCompleted,customerRequestStatus,requestStatus,houseId);


            requests.add(request);
        }

        return requests;
    }


    public String delete(Request selectedRequest) throws SQLException, ClassNotFoundException {

        String sql = "delete from buyandrentrequest where requestId = ? ";
        boolean result = SQLUtil.execute(sql, selectedRequest.getRequestId());

        return result ? "Success" : "Failed";

    }

    @Override
    public boolean isExist(Request entity) throws SQLException, ClassNotFoundException {

        String sql = "select * from buyandrentrequest where requestId  = ?";
        ResultSet rst = SQLUtil.execute(sql,entity.getRequestId());
        return rst.next();
    }


    public boolean updateRequestStatus(Request request) throws SQLException, ClassNotFoundException {

        String sql = "update buyandrentrequest set requestStatus = ? where requestId = ?";
        boolean result = SQLUtil.execute(sql,request.getRequestStatus(),request.getRequestId());

        return result;

    }


    public ObservableList<Request> getOnlyClosedRequests() throws SQLException, ClassNotFoundException {

        String sql = "select requestId,customerId,rentOrBuy,houseType,lengthOfLeaseTurnDesire,allDocumentsProvided," +
                "qualifiedCustomerOrNot,agreesToAllTermsAndConditions,isPaymentsCompleted,customerRequestStatus,requestStatus," +
                "houseId from buyAndRentRequest where requestStatus = 'Closed'";

        ResultSet result = SQLUtil.execute(sql);

        ObservableList<Request> requests = FXCollections.observableArrayList();

        while (result.next()){
            String requestId = result.getString(1);
            System.out.println(requestId);
            String customerId = result.getString(2);
            System.out.println(customerId);
            String rentOrBuy = result.getString(3);
            System.out.println(rentOrBuy);
            String houseType = result.getString(4);
            System.out.println(houseType);
            String lengthOfLeaseTurnDesire = result.getString(5);
            System.out.println(lengthOfLeaseTurnDesire);
            String allDocumentsProvided = result.getString(6);
            System.out.println(allDocumentsProvided);
            if(allDocumentsProvided==null){
                allDocumentsProvided = "-";
                System.out.println(allDocumentsProvided);
            }
            String qualifiedCustomerOrNot = result.getString(7);
            System.out.println(qualifiedCustomerOrNot);
            if(qualifiedCustomerOrNot==null){
                qualifiedCustomerOrNot = "-";
                System.out.println(qualifiedCustomerOrNot);
            }
            String agreesToAllTermsAndConditions = result.getString(8);
            System.out.println(agreesToAllTermsAndConditions);
            if(agreesToAllTermsAndConditions==null){
                agreesToAllTermsAndConditions = "-";
                System.out.println(agreesToAllTermsAndConditions);
            }
            String isPaymentsCompleted = result.getString(9);
            System.out.println(isPaymentsCompleted);
            if(isPaymentsCompleted==null){
                isPaymentsCompleted = "-";
                System.out.println(isPaymentsCompleted);
            }
            String customerRequestStatus = result.getString(10);
            System.out.println(customerRequestStatus);
            String requestStatus = result.getString(11);
            System.out.println(requestStatus);
            String houseId = result.getString(12);
            System.out.println(houseId);
            if(houseId==null){
                houseId = "-";
                System.out.println(houseId);
            }

            Request request = new Request(requestId,customerId,rentOrBuy,houseType,lengthOfLeaseTurnDesire,allDocumentsProvided,qualifiedCustomerOrNot,
                    agreesToAllTermsAndConditions,isPaymentsCompleted,customerRequestStatus,requestStatus,houseId);


            requests.add(request);
        }

        return requests;
    }


    public ObservableList<String> getDistinctCustomers() throws SQLException, ClassNotFoundException {

        String sql = "select  distinct customerId from buyandrentrequest order by customerId asc";
        ResultSet result = SQLUtil.execute(sql);

        ObservableList<String> ids = FXCollections.observableArrayList();
        ids.add("Select");

        while(result.next()){

            ids.add(result.getString("customerId"));
        }

        return ids;
    }


    public String generateNewId() throws SQLException, ClassNotFoundException {

        String sql = "select requestId from buyAndRentRequest order by requestId desc limit 1";
        ResultSet result = SQLUtil.execute(sql);

        if(result.next()){

            String lastId = result.getString("requestId");
            String subStr = lastId.substring(1);
            System.out.println(subStr);
            int id = Integer.parseInt(subStr);
            id+=1;
            return String.format("R%05d", id);

        }
        else{
            return "R00001";
        }

    }


    public String add(Request request) throws SQLException, ClassNotFoundException {

        String sql = "INSERT INTO buyAndRentRequest(\n" +
                "    requestId, customerId, date, rentOrBuy, houseType, noOfFamilyMembers,\n" +
                "    monthlyIncome, annualIncome, bankAccountDetails, reasonForLeaving, \n" +
                "    estimatedMonthlyBudgetForRent, lengthOfLeaseTurnDesire, previousLandlordNumber, \n" +
                "    isSmorking, hasCriminalBackground, hasPets, allDocumentsProvided, \n" +
                "    qualifiedCustomerOrNot, agreesToAllTermsAndConditions, isPaymentsCompleted, \n" +
                "    requestStatus, customerRequestStatus, houseId\n" +
                ") VALUES (\n" +
                "   ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?\n" +
                ")";

        boolean result = SQLUtil.execute(sql,
                request.getRequestId(), request.getCustomerId(), request.getDate(),
                request.getRentOrBuy(), request.getHouseType(), request.getNoOfFamilyMembers(),
                request.getMonthlyIncome(), request.getAnnualIncome(), request.getBankAccountDetails(),
                request.getReasonForLeaving(), request.getEstimatedMonthlyBudgetForRent(),
                request.getLeaseTurnDesire(), request.getPreviousLandlordNumber(),
                request.getIsSmoking(), request.getHasCriminalBackground(), request.getHasPets(),
                "Not Yet", "-", "Not Yet", "Not Yet", "In Process", "Awaiting Confirmation", null);

        return result ? "Successfully Added New Rent Request" : "Failed To Add New Request, Try Again Later";

    }


    public Request search(String requestId) throws SQLException, ClassNotFoundException {

        String sql = "select * from buyandrentrequest where requestId = ?";
        ResultSet result = SQLUtil.execute(sql,requestId);

        Request request = new Request();

        while(result.next()){

            String id = result.getString(1);
            request.setRequestId(id);
            String customerId = result.getString(2);
            request.setCustomerId(customerId);
            String date = result.getString(3);
            request.setDate(date);
            String rentOrBuy = result.getString(4);
            request.setRentOrBuy(rentOrBuy);
            String houseType = result.getString(5);
            request.setHouseType(houseType);
            int familyMembersCount = result.getInt(6);
            request.setNoOfFamilyMembers(familyMembersCount);
            double monthlyIncome = result.getDouble(7);
            request.setMonthlyIncome(monthlyIncome);
            double annualIncome = result.getDouble(8);
            request.setAnnualIncome(annualIncome);
            String bankDetails= result.getString(9);
            request.setBankAccountDetails(bankDetails);
            String reasonToMove = result.getString(10);
            request.setReasonForLeaving(reasonToMove);
            String estimatedBudget = result.getString(11);
            request.setEstimatedMonthlyBudgetForRent(estimatedBudget);
            String leaseTurn = result.getString(12);
            request.setLeaseTurnDesire(leaseTurn);
            String landLordNumber = result.getString(13);
            request.setPreviousLandlordNumber(landLordNumber);
            String isSmorking = result.getString(14);
            request.setIsSmoking(isSmorking);
            String hasCriminalBackground = result.getString(15);
            request.setHasCriminalBackground(hasCriminalBackground);
            String hasPets = result.getString(16);
            request.setHasPets(hasPets);
            String docProvided = result.getString(17);
            request.setAllDocumentsProvided(docProvided);
            String isQualified = result.getString(18);
            request.setQualifiedCustomerOrNot(isQualified);
            String isAgreed = result.getString(19);
            request.setAgreesToAllTermsAndConditions(isAgreed);
            String isPaymentDone = result.getString(20);
            request.setIsPaymentsCompleted(isPaymentDone);
            String requestStatus = result.getString(21);
            request.setRequestStatus(requestStatus);
            String customerRequestStatus = result.getString(22);
            request.setCustomerRequestStatus(customerRequestStatus);
            String houseId = result.getString(23);
            if(houseId==null){
                houseId = "-";
            }
            System.out.println("house id: "+houseId);
            request.setHouseId(houseId);

        }

        return request;
    }


    public String update(Request request1) throws SQLException, ClassNotFoundException {

        String sqlState = "select houseId from buyandrentrequest where requestId = ?";
        ResultSet resultTwo = SQLUtil.execute(sqlState,request1.getRequestId());

        if (resultTwo.next()) {

            String houseId = resultTwo.getString("houseId");

            String sql = "UPDATE buyAndRentRequest SET \n" +
                    "    houseType = ?,\n" +
                    "    noOfFamilyMembers = ?,\n" +
                    "    monthlyIncome = ?,\n" +
                    "    annualIncome = ?,\n" +
                    "    bankAccountDetails = ?,\n" +
                    "    reasonForLeaving = ?,\n" +
                    "    estimatedMonthlyBudgetForRent = ?,\n" +
                    "    lengthOfLeaseTurnDesire = ?,\n" +
                    "    previousLandlordNumber = ?,\n" +
                    "    allDocumentsProvided = ?,\n" +
                    "    qualifiedCustomerOrNot = ?,\n" +
                    "    agreesToAllTermsAndConditions = ?,\n" +
                    "    isPaymentsCompleted = ?,\n" +
                    "    requestStatus = ?,\n" +
                    "    customerRequestStatus = ?,\n" +
                    "    houseId = ?\n"+// Removed the comma here
                    "WHERE requestId = ?";

            boolean result = SQLUtil.execute(sql,
                    request1.getHouseType(),
                    request1.getNoOfFamilyMembers(),
                    request1.getMonthlyIncome(),
                    request1.getAnnualIncome(),
                    request1.getBankAccountDetails(),
                    request1.getReasonForLeaving(),
                    request1.getEstimatedMonthlyBudgetForRent(),
                    request1.getLeaseTurnDesire(),
                    request1.getPreviousLandlordNumber(),
                    request1.getAllDocumentsProvided(),
                    request1.getQualifiedCustomerOrNot(),
                    request1.getAgreesToAllTermsAndConditions(),
                    request1.getIsPaymentsCompleted(),
                    request1.getRequestStatus(),
                    request1.getCustomerRequestStatus(),
                    houseId,
                    request1.getRequestId()
            );

            return result ? "Success" : "Failed";

        } else {

            String sql = "UPDATE buyAndRentRequest SET \n" +
                    "    houseType = ?,\n" +
                    "    noOfFamilyMembers = ?,\n" +
                    "    monthlyIncome = ?,\n" +
                    "    annualIncome = ?,\n" +
                    "    bankAccountDetails = ?,\n" +
                    "    reasonForLeaving = ?,\n" +
                    "    estimatedMonthlyBudgetForRent = ?,\n" +
                    "    lengthOfLeaseTurnDesire = ?,\n" +
                    "    previousLandlordNumber = ?,\n" +
                    "    allDocumentsProvided = ?,\n" +
                    "    qualifiedCustomerOrNot = ?,\n" +
                    "    agreesToAllTermsAndConditions = ?,\n" +
                    "    isPaymentsCompleted = ?,\n" +
                    "    requestStatus = ?,\n" +
                    "    customerRequestStatus = ?\n" + // Removed the comma here
                    "WHERE requestId = ?";

            boolean result = SQLUtil.execute(sql,
                    request1.getHouseType(),
                    request1.getNoOfFamilyMembers(),
                    request1.getMonthlyIncome(),
                    request1.getAnnualIncome(),
                    request1.getBankAccountDetails(),
                    request1.getReasonForLeaving(),
                    request1.getEstimatedMonthlyBudgetForRent(),
                    request1.getLeaseTurnDesire(),
                    request1.getPreviousLandlordNumber(),
                    request1.getAllDocumentsProvided(),
                    request1.getQualifiedCustomerOrNot(),
                    request1.getAgreesToAllTermsAndConditions(),
                    request1.getIsPaymentsCompleted(),
                    request1.getRequestStatus(),
                    request1.getCustomerRequestStatus(),
                    request1.getRequestId()
            );

            return result ? "Success" : "Failed";
        }
    }


    public boolean updateAssignedHouse(Request request) throws SQLException, ClassNotFoundException {

        String sqlStatementOne = "UPDATE buyAndRentRequest SET houseId = ? WHERE requestId = ?";
        return SQLUtil.execute(sqlStatementOne, request.getHouseId(), request.getRequestId());
    }

}












