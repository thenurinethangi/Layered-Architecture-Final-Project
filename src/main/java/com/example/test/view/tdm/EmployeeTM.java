package com.example.test.view.tdm;

import com.example.test.dto.EmployeeDTO;
import com.example.test.entity.Employee;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class EmployeeTM {

    private String employeeId;
    private String name;
    private String address;
    private String phoneNo;
    private double basicSalary;
    private double allowances;
    private String dob;
    private String position;

    public EmployeeTM() {
    }

    public EmployeeTM(String employeeId, String name, String address, String phoneNo, double basicSalary, double allowances, String dob, String position) {
        this.employeeId = employeeId;
        this.name = name;
        this.address = address;
        this.phoneNo = phoneNo;
        this.basicSalary = basicSalary;
        this.allowances = allowances;
        this.dob = dob;
        this.position = position;
    }

    public EmployeeTM toTM(EmployeeDTO employeeDTO){
        this.employeeId = employeeDTO.getEmployeeId();
        this.name = employeeDTO.getName();
        this.address = employeeDTO.getAddress();
        this.phoneNo = employeeDTO.getPhoneNo();
        this.basicSalary = employeeDTO.getBasicSalary();
        this.allowances = employeeDTO.getAllowances();
        this.dob = employeeDTO.getDob();
        this.position = employeeDTO.getPosition();
        return this;
    }
}
