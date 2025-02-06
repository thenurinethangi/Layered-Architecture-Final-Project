package com.example.test.dto;


import com.example.test.entity.Owner;
import com.example.test.view.tdm.OwnerTM;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class OwnerDTO {

    private String ownerId;
    private String name;
    private String phoneNo;
    private int membersCount;
    private String purchaseDate;
    private String houseId;
    private String invoiceNo;
    private String email;

    public OwnerDTO toDTO(Owner owner){
        this.ownerId = owner.getOwnerId();
        this.name = owner.getName();
        this.phoneNo = owner.getPhoneNo();
        this.membersCount = owner.getMembersCount();
        this.purchaseDate = owner.getPurchaseDate();
        this.houseId = owner.getHouseId();
        this.invoiceNo = owner.getInvoiceNo();
        this.email = owner.getEmail();
        return this;

    }

    public OwnerDTO toDTO(OwnerTM ownerTM){
        this.ownerId = ownerTM.getOwnerId();
        this.name = ownerTM.getName();
        this.phoneNo = ownerTM.getPhoneNo();
        this.membersCount = ownerTM.getMembersCount();
        this.purchaseDate = ownerTM.getPurchaseDate();
        this.houseId = ownerTM.getHouseId();
        this.invoiceNo = ownerTM.getInvoiceNo();
        return this;

    }
}
