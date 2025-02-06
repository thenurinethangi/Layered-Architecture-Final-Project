package com.example.test.dao.custom.impl;

import com.example.test.dao.SQLUtil;
import com.example.test.dao.custom.PaymentDAO;
import com.example.test.db.DBConnection;
import com.example.test.dto.HouseInspectDTO;
import com.example.test.dto.TenantDTO;
import com.example.test.entity.HouseInspect;
import com.example.test.entity.Payment;
import com.example.test.entity.Tenant;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class PaymentDAOImpl implements PaymentDAO {


    public String add(Payment payment) throws SQLException, ClassNotFoundException {

        String invoiceNo = generateNewId();
        System.out.println("Invoice :"+invoiceNo);

        String today = String.valueOf(LocalDate.now());
        System.out.println("Local date now: "+today);

        String sql = "insert into payment values(?,?,?,?,?,?,?)";
        boolean result = SQLUtil.execute(sql,invoiceNo,payment.getAmount(),today,payment.getPaymentType(),payment.getTenantId(),payment.getIsFirstPayment(),payment.getOwnerPayment());

        return result ? "Success" : "Failed";

    }


    public String generateNewId() throws SQLException, ClassNotFoundException {

        String sql = "select invoiceNo from payment order by invoiceNo desc limit 1";
        ResultSet result = SQLUtil.execute(sql);

        LocalDate date = LocalDate.now();
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        String dateString = date.format(dateFormatter);

        if(result.next()){

            String lastId = result.getString("invoiceNo");
            String subStr = lastId.substring(10);
            int id = Integer.parseInt(subStr);
            id+=1;
            String newId = String.format("I%04d", id);

            String invoiceNo = dateString+"-"+newId;
            return invoiceNo;

        }

        return dateString+"-"+"I0001";

    }

    @Override
    public String update(Payment entity) throws SQLException, ClassNotFoundException {
        return "";
    }



    public ObservableList<Payment> getAll() throws SQLException, ClassNotFoundException {

        String sql = "select * from payment";
        ResultSet result = SQLUtil.execute(sql);

        ObservableList<Payment> allPayments = FXCollections.observableArrayList();

        while(result.next()){

            String invoiceNo = result.getString(1);
            double amount = result.getDouble(2);
            String date  = result.getString(3);
            String paymentType = result.getString(4);
            String tenantId = result.getString(5);
            if(tenantId==null){
                tenantId = "N/A";
            }

            Payment payment = new Payment(invoiceNo,amount,date,paymentType,tenantId);
            allPayments.add(payment);
        }

        return  allPayments;
    }


    public ObservableList<String> getInvoiceNoSuggestions(String input) throws SQLException, ClassNotFoundException {

        String sql = "SELECT invoiceNo FROM payment WHERE invoiceNo LIKE ?";
        ResultSet result = SQLUtil.execute(sql, input + "%");

        ObservableList<String> invoices = FXCollections.observableArrayList();

        while (result.next()) {
            invoices.add(result.getString("invoiceNo"));
        }

        return invoices;
    }


    public boolean checkIfThisPaymentIsFirstPaymentOrNot(Payment selectedPayment) throws SQLException, ClassNotFoundException {

        String sql = "select isFirstPayment from payment where invoiceNo = ?";
        ResultSet result = SQLUtil.execute(sql,selectedPayment.getInvoiceNo());

        boolean isFirstPayment = false;

        if(result.next()) {
            isFirstPayment = result.getBoolean("isFirstPayment");

        }

        return isFirstPayment;
    }


    public String delete(Payment selectedPayment) throws SQLException, ClassNotFoundException {

        String sql = "delete from payment where invoiceNo = ?";
        boolean result =  SQLUtil.execute(sql, selectedPayment.getInvoiceNo());

        return result ? "Success" : "failed";

    }


    @Override
    public boolean isExist(Payment entity) throws SQLException, ClassNotFoundException {

        String sql = "select * from payment where invoiceNo = ?";
        ResultSet rst = SQLUtil.execute(sql,entity.getInvoiceNo());
        return rst.next();
    }


    @Override
    public Payment search(String id) throws SQLException, ClassNotFoundException {

        String sql = "select * from payment where invoiceNo = ?";
        ResultSet rst = SQLUtil.execute(sql,id);

        Payment payment = new Payment();

        if(rst.next()){
            payment.setInvoiceNo(rst.getString(1));
            payment.setAmount(rst.getDouble(2));
            payment.setDate(rst.getString(3));
            payment.setPaymentType(rst.getString(4));
            payment.setTenantId(rst.getString(5));
            payment.setIsFirstPayment(rst.getInt(6));
            payment.setOwnerPayment(rst.getInt(7));
        }

        return payment;
    }


    public String getLastPayment() throws SQLException, ClassNotFoundException {

        String sql = "select invoiceNo from payment order by invoiceNo desc limit 1";
        ResultSet result = SQLUtil.execute(sql);

        if(result.next()){
            return result.getString("invoiceNo");
        }
        return "0";
    }


    public double getTotalIncome() throws SQLException, ClassNotFoundException {

        String sql = "select sum(amount) from payment";
        ResultSet result = SQLUtil.execute(sql);

        if(result.next()){

            return result.getDouble("sum(amount)");
        }
        return 0;
    }
}






