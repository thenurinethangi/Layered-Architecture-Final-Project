package com.example.test.entity;

import com.example.test.dto.CustomerDTO;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Customer {

    private String customerId;
    private String name;
    private String nic;
    private String address;
    private String phoneNo;
    private String jobTitle;
    private String livingArrangement;
    private String email;

    public Customer(String customerId, String name, String nic, String address, String phoneNo, String jobTitle, String livingArrangement) {
        this.customerId = customerId;
        this.name = name;
        this.nic = nic;
        this.address = address;
        this.phoneNo = phoneNo;
        this.jobTitle = jobTitle;
        this.livingArrangement = livingArrangement;
    }

    public Customer toEntity(CustomerDTO customerDto){
        this.customerId = customerDto.getCustomerId();
        this.name =customerDto.getName();
        this.nic = customerDto.getNic();
        this.address = customerDto.getAddress();
        this.phoneNo = customerDto.getPhoneNo();
        this.jobTitle = customerDto.getJobTitle();
        this.livingArrangement = customerDto.getLivingArrangement();
        this.email = customerDto.getEmail();
        return this;
    }
}
