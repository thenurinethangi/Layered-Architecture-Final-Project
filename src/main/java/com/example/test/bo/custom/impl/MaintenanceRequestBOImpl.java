package com.example.test.bo.custom.impl;

import com.example.test.bo.custom.MaintenanceRequestBO;
import com.example.test.dao.DAOFactory;
import com.example.test.dao.custom.*;
import com.example.test.dto.MaintenanceRequestDTO;
import com.example.test.dto.TenantDTO;
import com.example.test.dto.UnitDTO;
import com.example.test.entity.MaintainRequest;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.SQLException;

public class MaintenanceRequestBOImpl implements MaintenanceRequestBO {

    private MaintenanceRequestDAO maintenanceRequestDAO = (MaintenanceRequestDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.MAINTENANCEREQUEST);
    private TenantDAO tenantDAO = (TenantDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.TENANT);
    private UnitDAO unitDAO = (UnitDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.UNIT);
    private EmployeeDAO employeeDAO = (EmployeeDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.EMPLOYEE);


    public ObservableList<MaintenanceRequestDTO> getAll() throws SQLException, ClassNotFoundException{

        ObservableList<MaintainRequest> maintainRequests = maintenanceRequestDAO.getAll();
        ObservableList<MaintenanceRequestDTO> maintenanceRequestDTOS = FXCollections.observableArrayList();

        for(MaintainRequest x : maintainRequests){
            maintenanceRequestDTOS.add(new MaintenanceRequestDTO().toDTO(x));
        }

        return maintenanceRequestDTOS;
    }


    public ObservableList<String> getDistinctTenantIds() throws SQLException, ClassNotFoundException{

        return maintenanceRequestDAO.getDistinctTenantIds();
    }


    public String updateRequestStatus(MaintenanceRequestDTO selectedRequest) throws SQLException, ClassNotFoundException{

        return maintenanceRequestDAO.updateRequestStatus(new MaintainRequest().toEntity(selectedRequest));
    }


    public String generateNewId() throws SQLException, ClassNotFoundException{

        return maintenanceRequestDAO.generateNewId();
    }


    public String add(MaintenanceRequestDTO maintenanceRequestDTO) throws SQLException, ClassNotFoundException{

        return maintenanceRequestDAO.add(new MaintainRequest().toEntity(maintenanceRequestDTO));
    }


    public String delete(MaintenanceRequestDTO selectedRequest) throws SQLException, ClassNotFoundException{

        return maintenanceRequestDAO.delete(new MaintainRequest().toEntity(selectedRequest));
    }


    public String update(MaintenanceRequestDTO maintenanceRequestDTO) throws SQLException, ClassNotFoundException{

        return maintenanceRequestDAO.update(new MaintainRequest().toEntity(maintenanceRequestDTO));
    }


    public boolean setRequestNotComplete(MaintenanceRequestDTO maintenanceRequestDTO) throws SQLException, ClassNotFoundException{

        return maintenanceRequestDAO.setRequestNotComplete(new MaintainRequest().toEntity(maintenanceRequestDTO));
    }


    public ObservableList<String> getRequestIdSuggestions(String input) throws SQLException, ClassNotFoundException{

        return maintenanceRequestDAO.getRequestIdSuggestions(input);
    }


    public boolean checkEnteredRequestIdValid(String maintenanceRequestNo) throws SQLException, ClassNotFoundException{

        return maintenanceRequestDAO.checkEnteredRequestIdValid(maintenanceRequestNo);
    }


    public boolean setActualCost(MaintenanceRequestDTO maintenanceRequestDTO) throws SQLException, ClassNotFoundException{

        return maintenanceRequestDAO.setActualCost(new MaintainRequest().toEntity(maintenanceRequestDTO));
    }


    public ObservableList<MaintenanceRequestDTO> getAllInProgressRequests() throws SQLException, ClassNotFoundException{

        ObservableList<MaintainRequest> maintainRequests = maintenanceRequestDAO.getAllInProgressRequests();
        ObservableList<MaintenanceRequestDTO> maintenanceRequestDTOS = FXCollections.observableArrayList();

        for(MaintainRequest x : maintainRequests){
            maintenanceRequestDTOS.add(new MaintenanceRequestDTO().toDTO(x));
        }

        return maintenanceRequestDTOS;
    }


    public TenantDTO getTenantDetails(String tenantId)throws SQLException, ClassNotFoundException{

        return new TenantDTO().toDTO(tenantDAO.search(tenantId));
    }


    public UnitDTO getUnitDetails(String houseId)throws SQLException, ClassNotFoundException{

        return new UnitDTO().toDTO(unitDAO.search(houseId));
    }


    public ObservableList<String> getAllTechnicians()throws SQLException, ClassNotFoundException{

        return employeeDAO.getTechnicians();
    }
}

