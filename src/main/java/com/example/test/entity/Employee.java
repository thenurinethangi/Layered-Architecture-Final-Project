package com.example.test.entity;

import com.example.test.dto.EmployeeDTO;
import com.example.test.view.tdm.EmployeeTM;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class Employee {

    private String employeeId;
    private String name;
    private String address;
    private String phoneNo;
    private double basicSalary;
    private double allowances;
    private String dob;
    private String position;

    public Employee toEntity(EmployeeDTO employeeDto){
        this.employeeId = employeeDto.getEmployeeId();
        this.name = employeeDto.getName();
        this.address = employeeDto.getAddress();
        this.phoneNo = employeeDto.getPhoneNo();
        this.basicSalary = employeeDto.getBasicSalary();
        this.allowances = employeeDto.getAllowances();
        this.dob = employeeDto.getDob();
        this.position = employeeDto.getPosition();
        return this;
    }

}
