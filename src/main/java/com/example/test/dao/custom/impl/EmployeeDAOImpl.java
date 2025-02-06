package com.example.test.dao.custom.impl;

import com.example.test.dao.SQLUtil;
import com.example.test.dao.custom.EmployeeDAO;
import com.example.test.db.DBConnection;
import com.example.test.entity.Employee;
import com.example.test.view.tdm.EmployeeTM;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public class EmployeeDAOImpl implements EmployeeDAO {


    public ObservableList<Employee> getAll() throws SQLException, ClassNotFoundException {

        String sql = "select * from employee";
        ResultSet result = SQLUtil.execute(sql);

        ObservableList<Employee> employees = FXCollections.observableArrayList();

        while (result.next()){

            String employeeId = result.getString(1);
            String name = result.getString(2);
            String address = result.getString(3);
            String phoneNo = result.getString(4);
            double basicSalary = result.getDouble(5);
            double allowances = result.getDouble(6);
            String dob = result.getString(7);
            String position = result.getString(8);

            Employee employee = new Employee(employeeId,name,address,phoneNo,basicSalary,allowances,dob,position);
            employees.add(employee);

        }
        return employees;

    }


    public String delete(Employee selectedItem) throws SQLException, ClassNotFoundException {

        String sql = "delete from employee where employeeId = ?";
        boolean result = SQLUtil.execute(sql,selectedItem.getEmployeeId());

        return result ? "successfully delete the employee" : "failed to delete the employee, try again later";

    }


    @Override
    public boolean isExist(Employee entity) throws SQLException, ClassNotFoundException {

        String sql = "select * from employee where employeeId = ?";
        ResultSet rst =  SQLUtil.execute(sql,entity.getEmployeeId());
        return rst.next();
    }


    public ObservableList<String> getAllIds() throws SQLException, ClassNotFoundException {

        String sql = "select employeeId from employee order by employeeId asc";
        ResultSet result = SQLUtil.execute(sql);

        ObservableList<String> employeeIds = FXCollections.observableArrayList();
        employeeIds.add("Select");

        while (result.next()){

            employeeIds.add(result.getString("employeeId"));
        }

        return employeeIds;

    }


    public ObservableList<String> getAllDistinctPositions() throws SQLException, ClassNotFoundException {

        String sql = "select distinct position from employee";
        ResultSet result = SQLUtil.execute(sql);

        ObservableList<String> positions = FXCollections.observableArrayList();
        positions.add("Select");

        while (result.next()){

            positions.add(result.getString("position"));
        }

        return positions;

    }


    public ObservableList<String> getAllEmployeeAddresses(String input) throws SQLException, ClassNotFoundException {

        String sql = "SELECT distinct address FROM employee WHERE address LIKE ?";
        ResultSet result = SQLUtil.execute(sql, input + "%");

        ObservableList<String> addresses = FXCollections.observableArrayList();

        while (result.next()) {
            addresses.add(result.getString("address"));
        }

        return addresses;

    }


    public ObservableList<String> getEmployeeNames(String input) throws SQLException, ClassNotFoundException {

        String sql = "SELECT distinct name FROM employee WHERE name LIKE ?";
        ResultSet result = SQLUtil.execute(sql, input + "%");

        ObservableList<String> names = FXCollections.observableArrayList();

        while (result.next()) {
            names.add(result.getString("name"));
        }

        return names;
    }


    public Employee search(String id) throws SQLException, ClassNotFoundException {

        String sql = "select * from employee where employeeId = ?";
        ResultSet result = SQLUtil.execute(sql,id);

        if(result.next()){

            String employeeId = result.getString(1);
            String name = result.getString(2);
            String address = result.getString(3);
            String phoneNo = result.getString(4);
            double basicSalary = result.getDouble(5);
            double allowances = result.getDouble(6);
            String dob = result.getString(7);
            String position = result.getString(8);

            Employee employee = new Employee(employeeId,name,address,phoneNo,basicSalary,allowances,dob,position);

            return employee;

        }
        return null;

    }


    public ObservableList<Employee> getEmployeeById(String emId) throws SQLException, ClassNotFoundException {

        String sql = "select * from employee where employeeId = ?";
        ResultSet result = SQLUtil.execute(sql,emId);

        ObservableList<Employee> employees = FXCollections.observableArrayList();

        if(result.next()){

                String employeeId = result.getString(1);
                String name = result.getString(2);
                String address = result.getString(3);
                String phoneNo = result.getString(4);
                double basicSalary = result.getDouble(5);
                double allowances = result.getDouble(6);
                String dob = result.getString(7);
                String position = result.getString(8);

                Employee employee = new Employee(employeeId,name,address,phoneNo,basicSalary,allowances,dob,position);
                employees.add(employee);

        }

        return employees;
    }


    public ObservableList<Employee> getEmployeeByName(String name) throws SQLException, ClassNotFoundException {

        String sql = "select * from employee where name = ?";
        ResultSet result = SQLUtil.execute(sql,name);

        ObservableList<Employee> employees = FXCollections.observableArrayList();

            while(result.next()) {
                String employeeId = result.getString(1);
                String emName = result.getString(2);
                String address = result.getString(3);
                String phoneNo = result.getString(4);
                double basicSalary = result.getDouble(5);
                double allowances = result.getDouble(6);
                String dob = result.getString(7);
                String position = result.getString(8);

                Employee employee = new Employee(employeeId, emName, address, phoneNo, basicSalary, allowances, dob, position);
                employees.add(employee);
            }

        return employees;
    }


    public ObservableList<Employee> getEmployeeByAddress(String address) throws SQLException, ClassNotFoundException {

        String sql = "select * from employee where address = ?";
        ResultSet result = SQLUtil.execute(sql,address);

        ObservableList<Employee> employees = FXCollections.observableArrayList();

        while(result.next()) {
            String employeeId = result.getString(1);
            String emName = result.getString(2);
            String emAddress = result.getString(3);
            String phoneNo = result.getString(4);
            double basicSalary = result.getDouble(5);
            double allowances = result.getDouble(6);
            String dob = result.getString(7);
            String position = result.getString(8);

            Employee employee = new Employee(employeeId, emName, emAddress, phoneNo, basicSalary, allowances, dob, position);
            employees.add(employee);
        }

        return employees;
    }


    public ObservableList<Employee> getEmployeeByPosition(String position) throws SQLException, ClassNotFoundException {

        String sql = "select * from employee where position = ?";
        ResultSet result = SQLUtil.execute(sql,position);

        ObservableList<Employee> employees = FXCollections.observableArrayList();

        while(result.next()) {
            String employeeId = result.getString(1);
            String emName = result.getString(2);
            String emAddress = result.getString(3);
            String phoneNo = result.getString(4);
            double basicSalary = result.getDouble(5);
            double allowances = result.getDouble(6);
            String dob = result.getString(7);
            String emPosition = result.getString(8);

            Employee employee = new Employee(employeeId, emName, emAddress, phoneNo, basicSalary, allowances, dob, emPosition);
            employees.add(employee);
        }

        return employees;
    }


    public ObservableList<String> getTechnicians() throws SQLException, ClassNotFoundException {

        String sql = "select name from employee where position like'Maintenance%' or 'Technician%'";
        ResultSet result = SQLUtil.execute(sql);

        ObservableList<String> technicians = FXCollections.observableArrayList();
        technicians.add("Select");

        while(result.next()){
            technicians.add(result.getString("name"));
        }
        return technicians;
    }


    public String update(Employee employee) throws SQLException, ClassNotFoundException {

        String sql = "UPDATE employee SET name = ?, address = ?, phoneNo = ?, basicSalary = ?, allowances = ?, dob = ?, position = ? WHERE employeeId = ?";

        boolean result = SQLUtil.execute(sql,employee.getName(),employee.getAddress(),employee.getPhoneNo(),employee.getBasicSalary(),
                employee.getAllowances(),employee.getDob(),employee.getPosition(),employee.getEmployeeId());

        return result ? "successfully update the employee" : "failed to update, please try again later";

    }


    public String generateNewId() throws SQLException, ClassNotFoundException {

        String sql = "select employeeId from employee order by employeeId desc limit 1";
        ResultSet result = SQLUtil.execute(sql);

        if(result.next()){

            String lastId = result.getString("employeeId");
            String subStr = lastId.substring(2);
            System.out.println(subStr);
            int id = Integer.parseInt(subStr);
            id+=1;
            return String.format("EM%03d", id);

        }
        else{
            return "EM001";
        }
    }


    public String add(Employee employee) throws SQLException, ClassNotFoundException {

        String sql = "insert into employee values(?,?,?,?,?,?,?,?)";
        boolean result = SQLUtil.execute(sql,employee.getEmployeeId(),employee.getName(),employee.getAddress(),employee.getPhoneNo(),employee.getBasicSalary(),
                employee.getAllowances(),employee.getDob(),employee.getPosition());

        return result ? "successfully add new employee" : "failed add new employee, please try again later";

    }
}








