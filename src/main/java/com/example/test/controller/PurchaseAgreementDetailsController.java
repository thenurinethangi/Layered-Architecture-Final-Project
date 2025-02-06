package com.example.test.controller;

import com.example.test.bo.BOFactory;
import com.example.test.bo.custom.PurchaseAgreementBO;
import com.example.test.dao.custom.PurchaseAgreementDAO;
import com.example.test.dto.OwnerDTO;
import com.example.test.dto.UnitDTO;
import com.example.test.entity.Owner;
import com.example.test.view.tdm.PurchaseAgreementTM;
import com.example.test.dao.custom.impl.OwnerDAOImpl;
import com.example.test.dao.custom.impl.TenantDAOImpl;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

import java.sql.SQLException;

public class PurchaseAgreementDetailsController {

    @FXML
    private Label houseType;

    @FXML
    private Label houseId;

    @FXML
    private Label purchasePrice;

    @FXML
    private Label purchaseDate;

    @FXML
    private Label name;

    @FXML
    private Label ownerId;

    @FXML
    private Label agreementId;


    private PurchaseAgreementTM purchaseAgreement;
    private PurchaseAgreementBO purchaseAgreementBO = (PurchaseAgreementBO) BOFactory.getInstance().getBO(BOFactory.BOType.PURCHASEAGREEMENT);


    public void setSelectedAgreementDetails(PurchaseAgreementTM selectedAgreement) {

        purchaseAgreement = selectedAgreement;

        agreementId.setText(purchaseAgreement.getPurchaseAgreementId());
        purchaseDate.setText(purchaseAgreement.getSignedDate());
        purchasePrice.setText(String.valueOf(purchaseAgreement.getPurchasePrice()));
        houseId.setText(purchaseAgreement.getHouseId());
        ownerId.setText(purchaseAgreement.getHomeOwnerId());

        try{
            UnitDTO unitDTO = purchaseAgreementBO.getUnitDetails(purchaseAgreement.getHouseId());
            houseType.setText(unitDTO.getHouseType());

            OwnerDTO ownerDTO = purchaseAgreementBO.getOwnerDetails(purchaseAgreement.getHomeOwnerId());
            name.setText(ownerDTO.getName());
        }
        catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            System.err.println("Error while loading Purchase Agreement data: " + e.getMessage());
        }

    }
}







