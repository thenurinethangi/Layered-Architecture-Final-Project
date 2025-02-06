package com.example.test.dao.custom.impl;

import com.example.test.dao.SQLUtil;
import com.example.test.dao.custom.CustomerDAO;
import com.example.test.entity.Customer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CustomerDAOImpl implements CustomerDAO {


    public ObservableList<Customer> getAll() throws SQLException, ClassNotFoundException {

        String sql = "select * from customer";
        ResultSet result = SQLUtil.execute(sql);

        ObservableList<Customer> allCustomers = FXCollections.observableArrayList();

        while(result.next()){

            String id = result.getString(1);
            String name = result.getString(2);
            String nic = result.getString(3);
            String address = result.getString(4);
            String phoneNo = result.getString(5);
            String jobTitle = result.getString(6);
            String livingArrangement = result.getString(7);

            Customer customer = new Customer(id,name,nic,address,phoneNo,jobTitle,livingArrangement);
            allCustomers.add(customer);
        }

        return allCustomers;
    }


    public ObservableList<String> getAllIds() throws SQLException, ClassNotFoundException {

        String sql = "select customerId from customer order by customerId asc";
        ResultSet result = SQLUtil.execute(sql);

        ObservableList<String> customerIds = FXCollections.observableArrayList();
        customerIds.add("Select");

        while (result.next()){

            customerIds.add(result.getString("customerId"));
        }

        return customerIds;
    }


    public String delete(Customer customer) throws SQLException, ClassNotFoundException {

        String sql = "delete from customer where customerId = ?";
        boolean result = SQLUtil.execute(sql,customer.getCustomerId());

        return result ? "Successfully delete the customer" : "Failed to delete the customer";
    }


    public boolean isExist(Customer customer) throws SQLException, ClassNotFoundException {

        String sql = "select * from customer where phoneNo = ?";
        ResultSet result = SQLUtil.execute(sql,customer.getCustomerId());
        return result.next();
    }


    public ObservableList<String> getPhoneNoSuggestions(String input) throws SQLException, ClassNotFoundException {

        String sql = "SELECT distinct phoneNo FROM customer WHERE phoneNo LIKE ?";
        ResultSet result = SQLUtil.execute(sql,input);

        ObservableList<String> phoneNumbers = FXCollections.observableArrayList();

        while (result.next()) {
            phoneNumbers.add(result.getString("phoneNo"));
        }

        return phoneNumbers;
    }


    public ObservableList<String> getNicSuggestions(String input) throws SQLException, ClassNotFoundException {

        String sql = "SELECT nic FROM customer WHERE nic LIKE ?";
        ResultSet result = SQLUtil.execute(sql, input + "%");

        ObservableList<String> nicNumbers = FXCollections.observableArrayList();

        while (result.next()) {
            nicNumbers.add(result.getString("nic"));
        }

        return nicNumbers;
    }


    public ObservableList<String> getNameSuggestions(String input) throws SQLException, ClassNotFoundException {

        String sql = "SELECT distinct name FROM customer WHERE name LIKE ?";
        ResultSet result = SQLUtil.execute(sql, input + "%");

        ObservableList<String> names = FXCollections.observableArrayList();

        while (result.next()) {
            names.add(result.getString("name"));
        }

        return names;
    }


    public ObservableList<String> getJobTitlesSuggestions(String input) throws SQLException, ClassNotFoundException {

        String sql = "SELECT jobTitle FROM customer WHERE jobTitle LIKE ?";
        ResultSet result = SQLUtil.execute(sql, input + "%");

        ObservableList<String> jobTitles = FXCollections.observableArrayList();

        while (result.next()) {
            jobTitles.add(result.getString("jobTitle"));
        }

        return jobTitles;
    }


    public Customer search(String customerId) throws SQLException, ClassNotFoundException {

        String sql = "select * from customer where customerId = ?";
        ResultSet result = SQLUtil.execute(sql,customerId);

        if(result.next()){

            String id = result.getString(1);
            String name = result.getString(2);
            String nic = result.getString(3);
            String address = result.getString(4);
            String customerPhoneNo = result.getString(5);
            String jobTitle = result.getString(6);
            String livingArrangement = result.getString(7);
            String email = result.getString(8);

            Customer customer = new Customer(id,name,nic,address,customerPhoneNo,jobTitle,livingArrangement,email);
            return customer;
        }
        return null;
    }


    public String isExistByNic(String nic) throws SQLException, ClassNotFoundException {

        String sql = "select customerId from  customer where  nic =?";
        ResultSet result = SQLUtil.execute(sql,nic);

        String id = "";

        if(result.next()){

            id = result.getString("customerId");
        }

        return id;
    }


    public Customer isExistByPhoneNo(String phoneNo) throws SQLException, ClassNotFoundException {

        String sql = "select * from customer where phoneNo = ?";
        ResultSet result = SQLUtil.execute(sql,phoneNo);

        if(result.next()){
            Customer customer = new Customer(result.getString(1),result.getString(2),result.getString(3),result.getString(4),result.getString(5),result.getString(6),result.getString(7));
            return customer;
        }

        return null;
    }


    @Override
    public String generateNewId() throws SQLException, ClassNotFoundException {

        String sql = "select customerId from customer order by customerId desc limit 1";
        ResultSet result = SQLUtil.execute(sql);

        if(result.next()){

            String lastId = result.getString("customerId");
            String subStr = lastId.substring(1);
            System.out.println(subStr);
            int id = Integer.parseInt(subStr);
            id+=1;
            return String.format("C%04d", id);

        }
        else{
            return "C0001";
        }
    }


    @Override
    public String update(Customer customer) throws SQLException, ClassNotFoundException {

        String sql = "UPDATE customer SET name = ?, nic = ?, address = ?, phoneNo = ?, jobTitle = ?, livingArrangement = ?, email = ? WHERE customerId = ?";

        boolean result = SQLUtil.execute(sql,customer.getName(),customer.getNic(),customer.getAddress(),customer.getPhoneNo(),customer.getJobTitle(),
                         customer.getLivingArrangement(),customer.getEmail(),customer.getCustomerId());

        return result ? "successfully update the customer" : "failed to update, please try again later";
    }


    @Override
    public String add(Customer customer) throws SQLException, ClassNotFoundException {

        String sql = "insert into customer values(?,?,?,?,?,?,?,?)";

        boolean result = SQLUtil.execute(sql,customer.getCustomerId(),customer.getName(),customer.getNic(),customer.getAddress(),customer.getPhoneNo(),
                customer.getJobTitle(), customer.getLivingArrangement(),customer.getEmail());

        return result ? "Successfully add the new customer" : "Failed to add the new customer, please try again later";
    }

}











