package com.example.test.view.tdm;

import com.example.test.dto.CustomerDTO;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class CustomerTM {

    private String customerId;
    private String name;
    private String nic;
    private String address;
    private String phoneNo;
    private String jobTitle;
    private String livingArrangement;

    public CustomerTM toTM(CustomerDTO customerDto){
        this.customerId = customerDto.getCustomerId();
        this.name =customerDto.getName();
        this.nic = customerDto.getNic();
        this.address = customerDto.getAddress();
        this.phoneNo = customerDto.getPhoneNo();
        this.jobTitle = customerDto.getJobTitle();
        this.livingArrangement = customerDto.getLivingArrangement();
        return this;
    }
}
