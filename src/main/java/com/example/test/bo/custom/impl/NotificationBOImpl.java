package com.example.test.bo.custom.impl;

import com.example.test.bo.custom.NotificationBO;
import com.example.test.dao.DAOFactory;
import com.example.test.dao.custom.HouseInspectDAO;
import com.example.test.dao.custom.LeaseAgreementDAO;
import com.example.test.dao.custom.MaintenanceRequestDAO;
import com.example.test.dao.custom.TenantDAO;
import com.example.test.dto.TenantDTO;
import com.example.test.entity.Tenant;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.SQLException;


public class NotificationBOImpl implements NotificationBO {

    TenantDAO tenantDAO = (TenantDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.TENANT);
    HouseInspectDAO houseInspectDAO = (HouseInspectDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.HOUSEINSPECT);
    MaintenanceRequestDAO maintenanceRequestDAO = (MaintenanceRequestDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.MAINTENANCEREQUEST);
    LeaseAgreementDAO leaseAgreementDAO = (LeaseAgreementDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.LEASEAGREEMENT);


    public ObservableList<TenantDTO> getTenantWhoNotDonePayment() throws SQLException, ClassNotFoundException {

       ObservableList<Tenant> tenants = tenantDAO.getTenantWhoNotDonePayment();
       ObservableList<TenantDTO> tenantDTOS = FXCollections.observableArrayList();

       for(Tenant x : tenants){
           tenantDTOS.add(new TenantDTO().toDTO(x));
       }
       return tenantDTOS;
    }


    public ObservableList<TenantDTO> checkTenantsWhoHaveNotPaidForEarlierMonths() throws SQLException, ClassNotFoundException {

        ObservableList<Tenant> tenants = tenantDAO.checkTenantsWhoHaveNotPaidForEarlierMonths();
        ObservableList<TenantDTO> tenantDTOS = FXCollections.observableArrayList();

        for(Tenant x : tenants){
            tenantDTOS.add(new TenantDTO().toDTO(x));
        }
        return tenantDTOS;

    }


    public ObservableList<String> getExpiredAgreements() throws SQLException, ClassNotFoundException {

        return leaseAgreementDAO.getExpiredAgreements();
    }


    public ObservableList<String> getSoonExpiredAgreements() throws SQLException, ClassNotFoundException {

        return leaseAgreementDAO.getSoonExpiredAgreements();
    }


    public ObservableList<String> getInProcessMaintenanceRequest() throws SQLException, ClassNotFoundException {

        return maintenanceRequestDAO.getInProcessMaintenanceRequest();
    }


    public ObservableList<String> getWhoNotPaidDamageCost() throws SQLException, ClassNotFoundException {

        return houseInspectDAO.getWhoNotPaidDamageCost();
    }
}
