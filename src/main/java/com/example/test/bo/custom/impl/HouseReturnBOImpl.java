package com.example.test.bo.custom.impl;

import com.example.test.bo.custom.HouseReturnBO;
import com.example.test.dao.DAOFactory;
import com.example.test.dao.custom.*;
import com.example.test.db.DBConnection;
import com.example.test.dto.*;
import com.example.test.entity.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.SQLException;

public class HouseReturnBOImpl implements HouseReturnBO {

    private HouseReturnDAO houseReturnDAO = (HouseReturnDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.HOUSERETURN);
    private UnitDAO unitDAO = (UnitDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.UNIT);
    private LeaseAgreementDAO leaseAgreementDAO = (LeaseAgreementDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.LEASEAGREEMENT);
    private TenantDAO tenantDAO = (TenantDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.TENANT);
    private ExpenseDAO expenseDAO = (ExpenseDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.EXPENSE);
    private HouseInspectDAO houseInspectDAO = (HouseInspectDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.HOUSEINSPECT);


    public ObservableList<HouseReturnDTO> getAll() throws SQLException, ClassNotFoundException{

        ObservableList<HouseReturn> houseReturns = houseReturnDAO.getAll();
        ObservableList<HouseReturnDTO> houseReturnDTOS = FXCollections.observableArrayList();

        for(HouseReturn x : houseReturns){
            houseReturnDTOS.add(new HouseReturnDTO().toDTO(x));
        }
        return houseReturnDTOS;
    }


    public String reclaimHouse(HouseReturnDTO houseReturnDTO,String agreementId) throws SQLException, ClassNotFoundException{

        Connection connection = DBConnection.getInstance().getConnection();
        connection.setAutoCommit(false);

        try {
            Unit unit = new Unit();
            unit.setStatus("Available");
            unit.setHouseId(houseReturnDTO.getHouseId());
            boolean isMakeAvailable = unitDAO.updateHouseStatus(unit);
            if(!isMakeAvailable){
                connection.rollback();
                return "Something Went Wrong With Reclaiming The House. Please Try Again Later";
            }

            Tenant tenant = new Tenant();
            tenant.setTenantId(houseReturnDTO.getTenantId());
            String isMakeTenantDeactivate = tenantDAO.delete(tenant);
            if(!isMakeTenantDeactivate.equals("Success")){
                connection.rollback();
                return "Something Went Wrong With Reclaiming The House. Please Try Again Later";
            }

            LeaseAgreement leaseAgreement = new LeaseAgreement();
            leaseAgreement.setLeaseId(agreementId);
            leaseAgreement.setStatus("Canceled");
            boolean isMakeCancelled = leaseAgreementDAO.updateLeaseAgreementStatus(leaseAgreement);
            if(!isMakeCancelled){
                connection.rollback();
                return "Something Went Wrong With Reclaiming The House. Please Try Again Later";
            }

            boolean isAddNewReturnDetails = houseReturnDAO.addNewHouseReturnWithoutRefund(new HouseReturn().toEntity(houseReturnDTO));
            if(!isAddNewReturnDetails){
                connection.rollback();
                return "Something Went Wrong With Reclaiming The House. Please Try Again Later";
            }

            connection.commit();
            return "Successfully Reclaiming The House!";

        }
        catch (Exception e){
            e.printStackTrace();
            connection.rollback();
        }
        finally {
            connection.setAutoCommit(true);
        }
        return "0";
    }


    public String generateNewId() throws SQLException, ClassNotFoundException{

        return houseReturnDAO.generateNewId();
    }


    public String reclaimHouseWithRefundSecurityDeposit(HouseReturnDTO houseReturnDTO, String agreementId) throws SQLException, ClassNotFoundException{

        Connection connection = DBConnection.getInstance().getConnection();
        connection.setAutoCommit(false);

        try {
            Unit unit= new Unit();
            unit.setStatus("Available");
            unit.setHouseId(houseReturnDTO.getHouseId());
            boolean isMakeAvailable = unitDAO.updateHouseStatus(unit);
            if(!isMakeAvailable){
                connection.rollback();
                return "Something Went Wrong With Reclaiming The House. Please Try Again Later";
            }

            Tenant tenant1 = new Tenant();
            tenant1.setTenantId(houseReturnDTO.getTenantId());
            String isMakeTenantDeactivate = tenantDAO.delete(tenant1);
            if(!isMakeTenantDeactivate.equals("Success")){
                connection.rollback();
                return "Something Went Wrong With Reclaiming The House. Please Try Again Later";
            }

            LeaseAgreement leaseAgreement = new LeaseAgreement();
            leaseAgreement.setLeaseId(agreementId);
            leaseAgreement.setStatus("Canceled");
            boolean isMakeCancelled = leaseAgreementDAO.updateLeaseAgreementStatus(leaseAgreement);
            if(!isMakeCancelled){
                connection.rollback();
                return "Something Went Wrong With Reclaiming The House. Please Try Again Later";
            }
            Expense expense = new Expense();
            expense.setAmount(Double.parseDouble(houseReturnDTO.getRefundedAmount()));

            String isAddNewExpense = expenseDAO.add(expense);
            if(!isAddNewExpense.equals("Success")){
                connection.rollback();
                return "Something Went Wrong With Reclaiming The House. Please Try Again Later";
            }

            boolean isAddNewReturnDetails = houseReturnDAO.addNewHouseReturnWithRefund(new HouseReturn().toEntity(houseReturnDTO));
            if(!isAddNewReturnDetails){
                connection.rollback();
                return "Something Went Wrong With Reclaiming The House. Please Try Again Later";
            }

            connection.commit();
            return "Successfully Refund The Security Payment And Reclaiming The House!";

        }
        catch (Exception e){
            e.printStackTrace();
            connection.rollback();
        }
        finally {
            connection.setAutoCommit(true);
        }
        return "0";
    }


    public ObservableList<String> getAllDistinctTenantIds() throws SQLException, ClassNotFoundException{

        return houseReturnDAO.getAllDistinctTenantIds();
    }


    public ObservableList<String> getAllDistinctHouseIds() throws SQLException, ClassNotFoundException{

        return houseReturnDAO.getAllDistinctHouseIds();
    }


    public String delete(HouseReturnDTO selectedRow) throws SQLException, ClassNotFoundException{

        return houseReturnDAO.delete(new HouseReturn().toEntity(selectedRow));
    }


    public TenantDTO getTenantDetails(String tenantId)throws SQLException, ClassNotFoundException{

        return new TenantDTO().toDTO(tenantDAO.search(tenantId));
    }


    public UnitDTO getUnitDetails(String houseId)throws SQLException, ClassNotFoundException{

        return new UnitDTO().toDTO(unitDAO.search(houseId));
    }


    public String getLeaseAgreementByTenantId(String tenantId) throws SQLException, ClassNotFoundException{

        return leaseAgreementDAO.getLeaseAgreementByTenantId(tenantId);
    }

    public HouseInspectDTO getLatestHouseInspectOfTenant(String tenantId) throws SQLException, ClassNotFoundException{

        return new HouseInspectDTO().toDTO(houseInspectDAO.getLatestHouseInspectOfTenant(tenantId));
    }
}
