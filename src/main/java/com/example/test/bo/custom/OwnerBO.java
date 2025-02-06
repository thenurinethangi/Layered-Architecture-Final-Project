package com.example.test.bo.custom;

import com.example.test.bo.SuperBO;
import com.example.test.dto.OwnerDTO;
import com.example.test.dto.PurchaseAgreementDTO;
import com.example.test.dto.UnitDTO;
import javafx.collections.ObservableList;

import java.sql.SQLException;

public interface OwnerBO extends SuperBO {

    public ObservableList<OwnerDTO> getAll() throws SQLException, ClassNotFoundException;

    public ObservableList<String> getAllDistinctInvoiceNos() throws SQLException, ClassNotFoundException;

    public ObservableList<String> getAllHouseIds() throws SQLException, ClassNotFoundException;

    public ObservableList<String> getNameSuggestions(String input) throws SQLException, ClassNotFoundException;

    public ObservableList<String> getPhoneNosSuggestions(String input) throws SQLException, ClassNotFoundException;

    public String add(OwnerDTO ownerDTO) throws SQLException, ClassNotFoundException;

    public String generateNewId() throws SQLException, ClassNotFoundException;

    public String getLastAddedOwnerId() throws SQLException, ClassNotFoundException;

    public OwnerDTO search(String ownerId) throws SQLException, ClassNotFoundException;

    public PurchaseAgreementDTO getAgreementDetailsById(String id) throws SQLException, ClassNotFoundException;

    public UnitDTO getPurchasePriceByHouseId(String houseId) throws SQLException, ClassNotFoundException;

    public String update(OwnerDTO ownerDTO) throws SQLException, ClassNotFoundException;

    public UnitDTO getUnitDetails(String houseId) throws SQLException, ClassNotFoundException;
}
