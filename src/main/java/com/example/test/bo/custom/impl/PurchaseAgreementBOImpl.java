package com.example.test.bo.custom.impl;

import com.example.test.bo.custom.PurchaseAgreementBO;
import com.example.test.dao.DAOFactory;
import com.example.test.dao.custom.OwnerDAO;
import com.example.test.dao.custom.PurchaseAgreementDAO;
import com.example.test.dao.custom.UnitDAO;
import com.example.test.dto.OwnerDTO;
import com.example.test.dto.PurchaseAgreementDTO;
import com.example.test.dto.UnitDTO;
import com.example.test.entity.PurchaseAgreement;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.SQLException;

public class PurchaseAgreementBOImpl implements PurchaseAgreementBO {

    PurchaseAgreementDAO purchaseAgreementDAO = (PurchaseAgreementDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.PURCHASEAGREEMENT);
    OwnerDAO ownerDAO = (OwnerDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.OWNER);
    UnitDAO unitDAO = (UnitDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.UNIT);


    public ObservableList<PurchaseAgreementDTO> getAll() throws SQLException, ClassNotFoundException{

        ObservableList<PurchaseAgreement> purchaseAgreements = purchaseAgreementDAO.getAll();
        ObservableList<PurchaseAgreementDTO> purchaseAgreementDTOS = FXCollections.observableArrayList();

        for(PurchaseAgreement x : purchaseAgreements){
            purchaseAgreementDTOS.add(new PurchaseAgreementDTO().toDTO(x));
        }
        return purchaseAgreementDTOS;
    }


    public ObservableList<String> getAllHouseIds() throws SQLException, ClassNotFoundException{

        return purchaseAgreementDAO.getAllHouseIds();
    }


    public ObservableList<String> getAllOwnerIds() throws SQLException, ClassNotFoundException{

        return purchaseAgreementDAO.getAllOwnerIds();
    }


    public String add(PurchaseAgreementDTO purchaseAgreementDTO) throws SQLException, ClassNotFoundException{

        return purchaseAgreementDAO.add(new PurchaseAgreement().toEntity(purchaseAgreementDTO));
    }


    public String generateNewId() throws SQLException, ClassNotFoundException{

        return purchaseAgreementDAO.generateNewId();
    }

    public OwnerDTO getOwnerDetails(String ownerId)throws SQLException, ClassNotFoundException{

        return new OwnerDTO().toDTO(ownerDAO.search(ownerId));
    }


    public UnitDTO getUnitDetails(String unitId)throws SQLException, ClassNotFoundException{

        return new UnitDTO().toDTO(unitDAO.search(unitId));
    }
}
