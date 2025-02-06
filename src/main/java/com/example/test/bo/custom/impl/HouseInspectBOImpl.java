package com.example.test.bo.custom.impl;

import com.example.test.bo.custom.HouseInspectBO;
import com.example.test.dao.DAOFactory;
import com.example.test.dao.custom.HouseInspectDAO;
import com.example.test.dao.custom.TenantDAO;
import com.example.test.dao.custom.UnitDAO;
import com.example.test.dto.HouseInspectDTO;
import com.example.test.dto.TenantDTO;
import com.example.test.dto.UnitDTO;
import com.example.test.entity.HouseInspect;
import com.example.test.entity.Tenant;
import com.example.test.entity.Unit;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.SQLException;

public class HouseInspectBOImpl implements HouseInspectBO {

    HouseInspectDAO houseInspectDAO = (HouseInspectDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.HOUSEINSPECT);
    TenantDAO tenantDAO = (TenantDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.TENANT);
    UnitDAO unitDAO = (UnitDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.UNIT);


    public ObservableList<HouseInspectDTO> getAll() throws SQLException, ClassNotFoundException{

        ObservableList<HouseInspect> houseInspects = houseInspectDAO.getAll();
        ObservableList<HouseInspectDTO> houseInspectDTOS = FXCollections.observableArrayList();

        for(HouseInspect x : houseInspects){
            houseInspectDTOS.add(new HouseInspectDTO().toDTO(x));
        }

        return houseInspectDTOS;
    }


    public HouseInspectDTO getLatestHouseInspectOfTenant(String tenantId) throws SQLException, ClassNotFoundException{

        return new HouseInspectDTO().toDTO(houseInspectDAO.getLatestHouseInspectOfTenant(tenantId));
    }


    public String add(HouseInspectDTO houseInspectDTO) throws SQLException, ClassNotFoundException{

        return houseInspectDAO.add(new HouseInspect().toEntity(houseInspectDTO));
    }


    public String generateNewId() throws SQLException, ClassNotFoundException{

        return houseInspectDAO.generateNewId();
    }


    public ObservableList<String> getHouseCheckNumbersSuggestions(String input) throws SQLException, ClassNotFoundException{

        return houseInspectDAO.getHouseCheckNumbersSuggestions(input);
    }


    public HouseInspectDTO search(String houseInspectionNumber) throws SQLException, ClassNotFoundException{

        return new HouseInspectDTO().toDTO(houseInspectDAO.search(houseInspectionNumber));
    }


    public String update(HouseInspectDTO houseInspectDTO) throws SQLException, ClassNotFoundException{

        return houseInspectDAO.update(new HouseInspect().toEntity(houseInspectDTO));
    }


    public boolean checkIfThisCheckLastCheck(HouseInspectDTO selectedHouseCheck) throws SQLException, ClassNotFoundException{

        return houseInspectDAO.checkIfThisCheckLastCheck(new HouseInspect().toEntity(selectedHouseCheck));
    }


    public String delete(HouseInspectDTO selectedHouseCheck) throws SQLException, ClassNotFoundException{

        return houseInspectDAO.delete(new HouseInspect().toEntity(selectedHouseCheck));
    }


    public TenantDTO getTenantDetails(String tenantId)throws SQLException, ClassNotFoundException{

        return new TenantDTO().toDTO(tenantDAO.search(tenantId));
    }


    public UnitDTO getUnitDetails(String houseId)throws SQLException, ClassNotFoundException{

        return new UnitDTO().toDTO(unitDAO.search(houseId));
    }


    public ObservableList<TenantDTO> getAllTenants() throws SQLException, ClassNotFoundException{

        ObservableList<Tenant> tenants = tenantDAO.getAll();
        ObservableList<TenantDTO> tenantDTOS = FXCollections.observableArrayList();

        for(Tenant x : tenants){
            tenantDTOS.add(new TenantDTO().toDTO(x));
        }
        return tenantDTOS;
    }


    public ObservableList<UnitDTO> getAllUnits() throws SQLException, ClassNotFoundException{

        ObservableList<Unit> units = unitDAO.getAll();
        ObservableList<UnitDTO> unitDTOS = FXCollections.observableArrayList();

        for(Unit x : units){
            unitDTOS.add(new UnitDTO().toDTO(x));
        }

        return unitDTOS;
    }

    public boolean checkRemainingSecurityFundEnoughOrNot(String tenantId,String costOfRepair) throws SQLException, ClassNotFoundException{

        return tenantDAO.checkRemainingSecurityFundEnoughOrNot(tenantId,costOfRepair);
    }


    public String reduceRepairCostFromSecurityCharge(String tenantId,String costOfRepair) throws SQLException, ClassNotFoundException{

        return tenantDAO.reduceRepairCostFromSecurityCharge(tenantId,costOfRepair);
    }
}







