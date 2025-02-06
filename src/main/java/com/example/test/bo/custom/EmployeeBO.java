package com.example.test.bo.custom;

import com.example.test.bo.SuperBO;
import com.example.test.dto.EmployeeDTO;
import javafx.collections.ObservableList;

import java.sql.SQLException;

public interface EmployeeBO extends SuperBO {

    public ObservableList<EmployeeDTO> getAll() throws SQLException, ClassNotFoundException;

    public String delete(EmployeeDTO selectedItem) throws SQLException, ClassNotFoundException;

    public ObservableList<String> getAllIds() throws SQLException, ClassNotFoundException;

    public ObservableList<String> getAllDistinctPositions() throws SQLException, ClassNotFoundException;

    public ObservableList<String> getAllEmployeeAddresses(String input) throws SQLException, ClassNotFoundException;

    public ObservableList<String> getEmployeeNames(String input) throws SQLException, ClassNotFoundException;

    public EmployeeDTO search(String id) throws SQLException, ClassNotFoundException;

    public ObservableList<EmployeeDTO> getEmployeeById(String emId) throws SQLException, ClassNotFoundException;

    public ObservableList<EmployeeDTO> getEmployeeByName(String name) throws SQLException, ClassNotFoundException;

    public ObservableList<EmployeeDTO> getEmployeeByAddress(String address) throws SQLException, ClassNotFoundException;

    public ObservableList<EmployeeDTO> getEmployeeByPosition(String position) throws SQLException, ClassNotFoundException;

    public ObservableList<String> getTechnicians() throws SQLException, ClassNotFoundException;

    public String update(EmployeeDTO employeeDTO) throws SQLException, ClassNotFoundException;

    public String generateNewId() throws SQLException, ClassNotFoundException;

    public String add(EmployeeDTO employeeDTO) throws SQLException, ClassNotFoundException;
}
