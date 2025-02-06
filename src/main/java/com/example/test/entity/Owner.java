package com.example.test.entity;

import com.example.test.dto.OwnerDTO;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Owner {

    private String ownerId;
    private String name;
    private String phoneNo;
    private int membersCount;
    private String purchaseDate;
    private String houseId;
    private String invoiceNo;
    private String email;

    public Owner toEntity(OwnerDTO ownerDto){
        this.ownerId = ownerDto.getOwnerId();
        this.name = ownerDto.getName();
        this.phoneNo = ownerDto.getPhoneNo();
        this.membersCount = ownerDto.getMembersCount();
        this.purchaseDate = ownerDto.getPurchaseDate();
        this.houseId = ownerDto.getHouseId();
        this.invoiceNo = ownerDto.getInvoiceNo();
        this.email = ownerDto.getEmail();
        return this;

    }
}
