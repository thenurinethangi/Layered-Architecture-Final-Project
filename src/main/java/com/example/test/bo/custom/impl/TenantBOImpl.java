package com.example.test.bo.custom.impl;

import com.example.test.bo.custom.TenantBO;
import com.example.test.dao.DAOFactory;
import com.example.test.dao.custom.TenantDAO;
import com.example.test.dao.custom.UnitDAO;
import com.example.test.dto.TenantDTO;
import com.example.test.dto.UnitDTO;
import com.example.test.entity.Tenant;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.SQLException;

public class TenantBOImpl implements TenantBO {

    TenantDAO tenantDAO = (TenantDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.TENANT);
    UnitDAO unitDAO = (UnitDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.UNIT);


    public String generateNewId() throws SQLException, ClassNotFoundException{

        return tenantDAO.generateNewId();
    }


    public String add(TenantDTO tenantDTO) throws SQLException, ClassNotFoundException{

        return tenantDAO.add(new Tenant().toEntity(tenantDTO));
    }


    public ObservableList<TenantDTO> getAll() throws SQLException, ClassNotFoundException{

        ObservableList<Tenant> tenants = tenantDAO.getAll();
        ObservableList<TenantDTO> tenantDTOS = FXCollections.observableArrayList();

        for(Tenant x : tenants){
            tenantDTOS.add(new TenantDTO().toDTO(x));
        }

        return tenantDTOS;
    }


    public ObservableList<String> getHouseIds() throws SQLException, ClassNotFoundException{

        return tenantDAO.getHouseIds();
    }


    public ObservableList<String> getNameSuggestions(String input) throws SQLException, ClassNotFoundException{

        return tenantDAO.getNameSuggestions(input);
    }


    public TenantDTO search(String tenantId) throws SQLException, ClassNotFoundException{

        return new TenantDTO().toDTO(tenantDAO.search(tenantId));
    }


    public String update(TenantDTO tenantDTO) throws SQLException, ClassNotFoundException{

        return tenantDAO.update(new Tenant().toEntity(tenantDTO));
    }


    public boolean updateLastPaymentMonth(TenantDTO tenantDTO) throws SQLException, ClassNotFoundException{

        return tenantDAO.updateLastPaymentMonth(new Tenant().toEntity(tenantDTO));
    }


    public String delete(TenantDTO tenantDTO) throws SQLException, ClassNotFoundException{

        return tenantDAO.delete(new Tenant().toEntity(tenantDTO));
    }


    public boolean checkRemainingSecurityFundEnoughOrNot(String tenantId, String costOfRepair) throws SQLException, ClassNotFoundException{

        return tenantDAO.checkRemainingSecurityFundEnoughOrNot(tenantId,costOfRepair);
    }


    public String reduceRepairCostFromSecurityCharge(String tenantId, String costOfRepair) throws SQLException, ClassNotFoundException{

        return tenantDAO.reduceRepairCostFromSecurityCharge(tenantId,costOfRepair);
    }


    public TenantDTO searchByPhoneNo(String phoneNo) throws SQLException, ClassNotFoundException{

        return new TenantDTO().toDTO(tenantDAO.searchByPhoneNo(phoneNo));
    }


    public boolean setNewLastPaidMonth(TenantDTO tenant) throws SQLException, ClassNotFoundException{

        return tenantDAO.setNewLastPaidMonth(new Tenant().toEntity(tenant));
    }


    public UnitDTO getUnitDetails(String unitId)throws SQLException, ClassNotFoundException{

        return new UnitDTO().toDTO(unitDAO.search(unitId));
    }
}
