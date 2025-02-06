package com.example.test.dto;

import com.example.test.entity.Customer;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class CustomerDTO {

    private String customerId;
    private String name;
    private String nic;
    private String address;
    private String phoneNo;
    private String jobTitle;
    private String livingArrangement;
    private String email;

    public CustomerDTO toDTO(Customer customer){
        this.customerId = customer.getCustomerId();
        this.name =customer.getName();
        this.nic = customer.getNic();
        this.address = customer.getAddress();
        this.phoneNo = customer.getPhoneNo();
        this.jobTitle = customer.getJobTitle();
        this.livingArrangement = customer.getLivingArrangement();
        this.email = customer.getEmail();
        return this;
    }
}
