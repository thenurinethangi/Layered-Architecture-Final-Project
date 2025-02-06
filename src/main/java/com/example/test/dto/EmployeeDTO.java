package com.example.test.dto;

import com.example.test.entity.Employee;
import com.example.test.view.tdm.EmployeeTM;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class EmployeeDTO {

    private String employeeId;
    private String name;
    private String address;
    private String phoneNo;
    private double basicSalary;
    private double allowances;
    private String dob;
    private String position;

    public EmployeeDTO toDTO(Employee employee){
        this.employeeId = employee.getEmployeeId();
        this.name = employee.getName();
        this.address = employee.getAddress();
        this.phoneNo = employee.getPhoneNo();
        this.basicSalary = employee.getBasicSalary();
        this.allowances = employee.getAllowances();
        this.dob = employee.getDob();
        this.position = employee.getPosition();
        return this;
    }

    public EmployeeDTO toDTO(EmployeeTM employeeTM){
        this.employeeId = employeeTM.getEmployeeId();
        this.name = employeeTM.getName();
        this.address = employeeTM.getAddress();
        this.phoneNo = employeeTM.getPhoneNo();
        this.basicSalary = employeeTM.getBasicSalary();
        this.allowances = employeeTM.getAllowances();
        this.dob = employeeTM.getDob();
        this.position = employeeTM.getPosition();
        return this;
    }

}
