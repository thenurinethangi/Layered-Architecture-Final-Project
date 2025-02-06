package com.example.test.bo.custom.impl;

import com.example.test.bo.custom.OwnerBO;
import com.example.test.dao.DAOFactory;
import com.example.test.dao.custom.OwnerDAO;
import com.example.test.dao.custom.PurchaseAgreementDAO;
import com.example.test.dao.custom.TenantDAO;
import com.example.test.dao.custom.UnitDAO;
import com.example.test.dto.OwnerDTO;
import com.example.test.dto.PurchaseAgreementDTO;
import com.example.test.dto.UnitDTO;
import com.example.test.entity.Owner;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.SQLException;

public class OwnerBOImpl implements OwnerBO {

    OwnerDAO ownerDAO = (OwnerDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.OWNER);
    UnitDAO unitDAO = (UnitDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.UNIT);
    PurchaseAgreementDAO purchaseAgreementDAO = (PurchaseAgreementDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.PURCHASEAGREEMENT);
    TenantDAO tenantDAO = (TenantDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.TENANT);


    public ObservableList<OwnerDTO> getAll() throws SQLException, ClassNotFoundException{

        ObservableList<Owner> owners = ownerDAO.getAll();
        ObservableList<OwnerDTO> ownerDTOS = FXCollections.observableArrayList();

        for(Owner x : owners){
            ownerDTOS.add(new OwnerDTO().toDTO(x));
        }
        return ownerDTOS;
    }


    public ObservableList<String> getAllDistinctInvoiceNos() throws SQLException, ClassNotFoundException{

        return ownerDAO.getAllDistinctInvoiceNos();
    }


    public ObservableList<String> getAllHouseIds() throws SQLException, ClassNotFoundException{

        return ownerDAO.getAllHouseIds();
    }


    public ObservableList<String> getNameSuggestions(String input) throws SQLException, ClassNotFoundException{

        return ownerDAO.getNameSuggestions(input);
    }


    public ObservableList<String> getPhoneNosSuggestions(String input) throws SQLException, ClassNotFoundException{

        return ownerDAO.getPhoneNosSuggestions(input);
    }


    public String add(OwnerDTO ownerDTO) throws SQLException, ClassNotFoundException{

        return ownerDAO.add(new Owner().toEntity(ownerDTO));
    }


    public String generateNewId() throws SQLException, ClassNotFoundException{

        return ownerDAO.generateNewId();
    }


    public String getLastAddedOwnerId() throws SQLException, ClassNotFoundException{

        return ownerDAO.getLastAddedOwnerId();
    }


    public OwnerDTO search(String ownerId) throws SQLException, ClassNotFoundException{

        return new OwnerDTO().toDTO(ownerDAO.search(ownerId));
    }


    public PurchaseAgreementDTO getAgreementDetailsById(String ownerId) throws SQLException, ClassNotFoundException{

        return new PurchaseAgreementDTO().toDTO(purchaseAgreementDAO.search(ownerId));
    }


    public UnitDTO getPurchasePriceByHouseId(String houseId) throws SQLException, ClassNotFoundException{

        return new UnitDTO().toDTO(unitDAO.search(houseId));
    }


    public String update(OwnerDTO ownerDTO) throws SQLException, ClassNotFoundException{

        return ownerDAO.update(new Owner().toEntity(ownerDTO));
    }


    public UnitDTO getUnitDetails(String houseId) throws SQLException, ClassNotFoundException{

        return new UnitDTO().toDTO(unitDAO.search(houseId));
    }

}
