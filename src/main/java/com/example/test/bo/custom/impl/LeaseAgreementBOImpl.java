package com.example.test.bo.custom.impl;

import com.example.test.bo.custom.HouseInspectBO;
import com.example.test.bo.custom.LeaseAgreementBO;
import com.example.test.dao.DAOFactory;
import com.example.test.dao.custom.HouseInspectDAO;
import com.example.test.dao.custom.LeaseAgreementDAO;
import com.example.test.dao.custom.TenantDAO;
import com.example.test.dto.HouseInspectDTO;
import com.example.test.dto.LeaseAgreementDTO;
import com.example.test.dto.TenantDTO;
import com.example.test.entity.LeaseAgreement;
import com.example.test.entity.Tenant;
import com.example.test.view.tdm.LeaseAgreementTM;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.SQLException;

public class LeaseAgreementBOImpl implements LeaseAgreementBO {

    LeaseAgreementDAO leaseAgreementDAO = (LeaseAgreementDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.LEASEAGREEMENT);
    TenantDAO tenantDAO = (TenantDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.TENANT);
    HouseInspectDAO houseInspectDAO = (HouseInspectDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.HOUSEINSPECT);


    public String add(LeaseAgreementDTO leaseAgreementDTO) throws SQLException, ClassNotFoundException{

        return leaseAgreementDAO.add(new LeaseAgreement().toEntity(leaseAgreementDTO));
    }


    public String generateNewId() throws SQLException, ClassNotFoundException{

        return leaseAgreementDAO.generateNewId();
    }


    public ObservableList<LeaseAgreementDTO> getAll() throws SQLException, ClassNotFoundException{

        ObservableList<LeaseAgreement> leaseAgreements = leaseAgreementDAO.getAll();
        ObservableList<LeaseAgreementDTO> leaseAgreementDTOS = FXCollections.observableArrayList();

        for(LeaseAgreement x : leaseAgreements){
            leaseAgreementDTOS.add(new LeaseAgreementDTO().toDTO(x));
        }

        return leaseAgreementDTOS;
    }


    public LeaseAgreementDTO search(String leaseId) throws SQLException, ClassNotFoundException{

        return new LeaseAgreementDTO().toDTO(leaseAgreementDAO.search(leaseId));
    }


    public HouseInspectDTO getLastHouseInspectCheckDetails(LeaseAgreementTM selectedLeaseAgreement) throws SQLException, ClassNotFoundException{

        return new HouseInspectDTO().toDTO(houseInspectDAO.getLatestHouseInspectOfTenant(selectedLeaseAgreement.getTenantId()));
    }


    public String update(LeaseAgreementDTO selectedLeaseAgreement) throws SQLException, ClassNotFoundException{

        return leaseAgreementDAO.update(new LeaseAgreement().toEntity(selectedLeaseAgreement));
    }


    public TenantDTO getTenantEmail(String tenantId) throws SQLException, ClassNotFoundException{

        return new TenantDTO().toDTO(tenantDAO.search(tenantId));
    }


    public boolean updateLeaseAgreementStatus(LeaseAgreementDTO leaseAgreement) throws SQLException, ClassNotFoundException{

        return leaseAgreementDAO.updateLeaseAgreementStatus(new LeaseAgreement().toEntity(leaseAgreement));
    }


    public String getLeaseAgreementByTenantId(String tenantId) throws SQLException, ClassNotFoundException{

        return leaseAgreementDAO.getLeaseAgreementByTenantId(tenantId);
    }


    public ObservableList<LeaseAgreementDTO> getOnlyActiveAgreements() throws SQLException, ClassNotFoundException{

        ObservableList<LeaseAgreement> leaseAgreements = leaseAgreementDAO.getOnlyActiveAgreements();
        ObservableList<LeaseAgreementDTO> leaseAgreementDTOS = FXCollections.observableArrayList();

        for(LeaseAgreement x : leaseAgreements){
            leaseAgreementDTOS.add(new LeaseAgreementDTO().toDTO(x));
        }

        return leaseAgreementDTOS;
    }


    public ObservableList<String> getDistinctHouseIds() throws SQLException, ClassNotFoundException{

        return leaseAgreementDAO.getDistinctHouseIds();
    }
}
