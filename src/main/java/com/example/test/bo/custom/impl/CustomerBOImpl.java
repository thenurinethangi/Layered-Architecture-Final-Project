package com.example.test.bo.custom.impl;

import com.example.test.bo.custom.CustomerBO;
import com.example.test.dao.DAOFactory;
import com.example.test.dao.custom.CustomerDAO;
import com.example.test.dto.CustomerDTO;
import com.example.test.entity.Customer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.SQLException;

public class CustomerBOImpl implements CustomerBO {

    CustomerDAO customerDAO = (CustomerDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.CUSTOMER);


    public ObservableList<CustomerDTO> getAll() throws SQLException, ClassNotFoundException {

        ObservableList<Customer> customers = customerDAO.getAll();

        ObservableList<CustomerDTO> customerDTOS = FXCollections.observableArrayList();
        for(Customer x : customers){
            customerDTOS.add(new CustomerDTO().toDTO(x));
        }
        return customerDTOS;
    }

    public ObservableList<String> getAllIds() throws SQLException, ClassNotFoundException {

        return customerDAO.getAllIds();
    }

    public String delete(CustomerDTO customerDto) throws SQLException, ClassNotFoundException {

        return customerDAO.delete(new Customer().toEntity(customerDto));
    }

    public boolean isExist(CustomerDTO customerDto) throws SQLException, ClassNotFoundException {

        return customerDAO.isExist(new Customer().toEntity(customerDto));
    }

    public ObservableList<String> getPhoneNoSuggestions(String input) throws SQLException, ClassNotFoundException {

        return customerDAO.getPhoneNoSuggestions(input);
    }

    public ObservableList<String> getNicSuggestions(String input) throws SQLException, ClassNotFoundException {

        return customerDAO.getNicSuggestions(input);
    }

    public ObservableList<String> getNameSuggestions(String input) throws SQLException, ClassNotFoundException {

        return customerDAO.getNameSuggestions(input);
    }

    public ObservableList<String> getJobTitlesSuggestions(String input) throws SQLException, ClassNotFoundException {

        return customerDAO.getJobTitlesSuggestions(input);
    }

    public CustomerDTO search(String customerId) throws SQLException, ClassNotFoundException {

        Customer customer = customerDAO.search(customerId);
        return new CustomerDTO().toDTO(customer);
    }

    public String isExistByNic(String nic) throws SQLException, ClassNotFoundException {

        return customerDAO.isExistByNic(nic);
    }

    @Override
    public CustomerDTO isExistByPhoneNo(String phoneNo) throws SQLException, ClassNotFoundException {

        Customer customer = customerDAO.isExistByPhoneNo(phoneNo);
        if(customer==null){
            return null;
        }
        return new CustomerDTO().toDTO(customer);
    }


    public String generateNewId() throws SQLException, ClassNotFoundException {

        return customerDAO.generateNewId();
    }


    public String update(CustomerDTO customerDto) throws SQLException, ClassNotFoundException {

        return customerDAO.update(new Customer().toEntity(customerDto));
    }


    public String add(CustomerDTO customerDto) throws SQLException, ClassNotFoundException {

        return customerDAO.add(new Customer().toEntity(customerDto));
    }

}
