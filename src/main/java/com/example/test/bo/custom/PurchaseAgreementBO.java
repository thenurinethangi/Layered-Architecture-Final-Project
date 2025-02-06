package com.example.test.bo.custom;

import com.example.test.bo.SuperBO;
import com.example.test.dto.OwnerDTO;
import com.example.test.dto.PurchaseAgreementDTO;
import com.example.test.dto.UnitDTO;
import javafx.collections.ObservableList;

import java.sql.SQLException;

public interface PurchaseAgreementBO extends SuperBO {

    public ObservableList<PurchaseAgreementDTO> getAll() throws SQLException, ClassNotFoundException;

    public ObservableList<String> getAllHouseIds() throws SQLException, ClassNotFoundException;

    public ObservableList<String> getAllOwnerIds() throws SQLException, ClassNotFoundException;

    public String add(PurchaseAgreementDTO purchaseAgreementDTO) throws SQLException, ClassNotFoundException;

    public String generateNewId() throws SQLException, ClassNotFoundException;

    public OwnerDTO getOwnerDetails(String ownerId) throws SQLException, ClassNotFoundException;

    public UnitDTO getUnitDetails(String unitId)throws SQLException, ClassNotFoundException;
}
