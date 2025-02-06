package com.example.test.bo.custom.impl;

import com.example.test.bo.custom.EmployeeBO;
import com.example.test.dao.DAOFactory;
import com.example.test.dao.SQLUtil;
import com.example.test.dao.custom.EmployeeDAO;
import com.example.test.dto.EmployeeDTO;
import com.example.test.entity.Employee;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.SQLException;

public class EmployeeBOImpl implements EmployeeBO {

    EmployeeDAO employeeDAO = (EmployeeDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.EMPLOYEE);


    public ObservableList<EmployeeDTO> getAll() throws SQLException, ClassNotFoundException {

        ObservableList<Employee> employees = employeeDAO.getAll();
        ObservableList<EmployeeDTO> employeeDTOS = FXCollections.observableArrayList();

        for(Employee x : employees){
            employeeDTOS.add(new EmployeeDTO().toDTO(x));
        }
        return employeeDTOS;
    }


    public String delete(EmployeeDTO selectedItem) throws SQLException, ClassNotFoundException {

        return employeeDAO.delete(new Employee().toEntity(selectedItem));
    }


    public ObservableList<String> getAllIds() throws SQLException, ClassNotFoundException {

        return employeeDAO.getAllIds();
    }


    public ObservableList<String> getAllDistinctPositions() throws SQLException, ClassNotFoundException {

        return employeeDAO.getAllDistinctPositions();
    }


    public ObservableList<String> getAllEmployeeAddresses(String input) throws SQLException, ClassNotFoundException {

        return employeeDAO.getAllEmployeeAddresses(input);
    }


    public ObservableList<String> getEmployeeNames(String input) throws SQLException, ClassNotFoundException {

        return employeeDAO.getEmployeeNames(input);
    }


    public EmployeeDTO search(String id) throws SQLException, ClassNotFoundException {

        return new EmployeeDTO().toDTO(employeeDAO.search(id));
    }


    public ObservableList<EmployeeDTO> getEmployeeById(String emId) throws SQLException, ClassNotFoundException {

        ObservableList<Employee> employees = employeeDAO.getEmployeeById(emId);
        ObservableList<EmployeeDTO> employeeDTOS = FXCollections.observableArrayList();

        for(Employee x : employees){
            employeeDTOS.add(new EmployeeDTO().toDTO(x));
        }
        return employeeDTOS;
    }


    public ObservableList<EmployeeDTO> getEmployeeByName(String name) throws SQLException, ClassNotFoundException {

        ObservableList<Employee> employees = employeeDAO.getEmployeeByName(name);
        ObservableList<EmployeeDTO> employeeDTOS = FXCollections.observableArrayList();

        for(Employee x : employees){
            employeeDTOS.add(new EmployeeDTO().toDTO(x));
        }
        return employeeDTOS;
    }


    public ObservableList<EmployeeDTO> getEmployeeByAddress(String address) throws SQLException, ClassNotFoundException {

        ObservableList<Employee> employees = employeeDAO.getEmployeeByAddress(address);
        ObservableList<EmployeeDTO> employeeDTOS = FXCollections.observableArrayList();

        for(Employee x : employees){
            employeeDTOS.add(new EmployeeDTO().toDTO(x));
        }
        return employeeDTOS;
    }


    public ObservableList<EmployeeDTO> getEmployeeByPosition(String position) throws SQLException, ClassNotFoundException {

        ObservableList<Employee> employees = employeeDAO.getEmployeeByPosition(position);
        ObservableList<EmployeeDTO> employeeDTOS = FXCollections.observableArrayList();

        for(Employee x : employees){
            employeeDTOS.add(new EmployeeDTO().toDTO(x));
        }
        return employeeDTOS;
    }


    public ObservableList<String> getTechnicians() throws SQLException, ClassNotFoundException {

        return employeeDAO.getTechnicians();
    }


    public String update(EmployeeDTO employeeDTO) throws SQLException, ClassNotFoundException {

        return employeeDAO.update(new Employee().toEntity(employeeDTO));
    }


    public String generateNewId() throws SQLException, ClassNotFoundException {

        return employeeDAO.generateNewId();
    }


    public String add(EmployeeDTO employeeDTO) throws SQLException, ClassNotFoundException {

        return employeeDAO.add(new Employee().toEntity(employeeDTO));
    }
}
