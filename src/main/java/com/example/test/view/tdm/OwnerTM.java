package com.example.test.view.tdm;

import com.example.test.dto.OwnerDTO;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class OwnerTM {

    private String ownerId;
    private String name;
    private String phoneNo;
    private int membersCount;
    private String purchaseDate;
    private String houseId;
    private String invoiceNo;

    public OwnerTM toTM(OwnerDTO ownerDTO){
        this.ownerId = ownerDTO.getOwnerId();
        this.name = ownerDTO.getName();
        this.phoneNo = ownerDTO.getPhoneNo();
        this.membersCount = ownerDTO.getMembersCount();
        this.purchaseDate = ownerDTO.getPurchaseDate();
        this.houseId = ownerDTO.getHouseId();
        this.invoiceNo = ownerDTO.getInvoiceNo();
        return this;

    }

}
