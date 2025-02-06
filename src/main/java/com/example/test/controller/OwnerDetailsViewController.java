package com.example.test.controller;

import com.example.test.bo.BOFactory;
import com.example.test.bo.custom.OwnerBO;
import com.example.test.dto.OwnerDTO;
import com.example.test.dto.UnitDTO;
import com.example.test.view.tdm.OwnerTM;
import com.example.test.dao.custom.impl.TenantDAOImpl;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

import java.sql.SQLException;

public class OwnerDetailsViewController {

    @FXML
    private Label houseId;

    @FXML
    private Label houseType;

    @FXML
    private Label purchaseDate;

    @FXML
    private Label membersCount;

    @FXML
    private Label email;

    @FXML
    private Label phoneNo;

    @FXML
    private Label name;

    @FXML
    private Label ownerId;

    @FXML
    private Label invoiceNo;

    @FXML
    private Label purchasePrice;


    private OwnerTM owner;
    private final OwnerBO ownerBO = (OwnerBO) BOFactory.getInstance().getBO(BOFactory.BOType.OWNER);


    public void setSelectedOwnerDetails(OwnerTM selectedOwner) {

        owner = selectedOwner;

        ownerId.setText(owner.getOwnerId());
        name.setText(owner.getName());
        phoneNo.setText(owner.getPhoneNo());
        membersCount.setText(String.valueOf(owner.getMembersCount()));
        invoiceNo.setText(owner.getInvoiceNo());
        purchaseDate.setText(owner.getPurchaseDate());
        houseId.setText(owner.getOwnerId());

        try {
            OwnerDTO ownerDTO =  ownerBO.search(owner.getOwnerId());
            email.setText(ownerDTO.getEmail());

            UnitDTO unitDTO = ownerBO.getPurchasePriceByHouseId(owner.getHouseId());
            purchasePrice.setText(unitDTO.getTotalValue());
            houseType.setText(unitDTO.getHouseType());

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            System.err.println("Error while getting the owner details: " + e.getMessage());
        }

    }

}




