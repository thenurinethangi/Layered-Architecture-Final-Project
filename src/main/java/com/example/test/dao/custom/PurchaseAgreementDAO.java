package com.example.test.dao.custom;

import com.example.test.dao.CrudDAO;
import com.example.test.entity.PurchaseAgreement;
import javafx.collections.ObservableList;

import java.sql.SQLException;

public interface PurchaseAgreementDAO extends CrudDAO<PurchaseAgreement> {

    public ObservableList<String> getAllHouseIds() throws SQLException, ClassNotFoundException;

    public ObservableList<String> getAllOwnerIds() throws SQLException, ClassNotFoundException;

}
