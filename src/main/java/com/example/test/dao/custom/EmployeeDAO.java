package com.example.test.dao.custom;

import com.example.test.dao.CrudDAO;
import com.example.test.entity.Employee;
import javafx.collections.ObservableList;

import java.sql.SQLException;

public interface EmployeeDAO extends CrudDAO<Employee> {

    public ObservableList<String> getAllIds() throws SQLException, ClassNotFoundException;

    public ObservableList<String> getAllDistinctPositions() throws SQLException, ClassNotFoundException;

    public ObservableList<String> getAllEmployeeAddresses(String input) throws SQLException, ClassNotFoundException;

    public ObservableList<String> getEmployeeNames(String input) throws SQLException, ClassNotFoundException;

    public ObservableList<Employee> getEmployeeById(String emId) throws SQLException, ClassNotFoundException;

    public ObservableList<Employee> getEmployeeByName(String name) throws SQLException, ClassNotFoundException;

    public ObservableList<Employee> getEmployeeByAddress(String address) throws SQLException, ClassNotFoundException;

    public ObservableList<Employee> getEmployeeByPosition(String position) throws SQLException, ClassNotFoundException;

    public ObservableList<String> getTechnicians() throws SQLException, ClassNotFoundException;

}
