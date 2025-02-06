package com.example.test.bo.custom.impl;

import com.example.test.bo.custom.PaymentBO;
import com.example.test.dao.DAOFactory;
import com.example.test.dao.custom.HouseInspectDAO;
import com.example.test.dao.custom.PaymentDAO;
import com.example.test.dao.custom.TenantDAO;
import com.example.test.db.DBConnection;
import com.example.test.dto.HouseInspectDTO;
import com.example.test.dto.PaymentDTO;
import com.example.test.dto.TenantDTO;
import com.example.test.entity.HouseInspect;
import com.example.test.entity.Payment;
import com.example.test.entity.Tenant;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.SQLException;

public class PaymentBOImpl implements PaymentBO {

    PaymentDAO paymentDAO = (PaymentDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.PAYMENT);
    HouseInspectDAO houseInspectDAO = (HouseInspectDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.HOUSEINSPECT);
    TenantDAO tenantDAO = (TenantDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.TENANT);


    public String add(PaymentDTO paymentDTO) throws SQLException, ClassNotFoundException{

        return paymentDAO.add(new Payment().toEntity(paymentDTO));
    }


    public String generateNewId() throws SQLException, ClassNotFoundException{

        return paymentDAO.generateNewId();
    }


    public ObservableList<PaymentDTO> getAll() throws SQLException, ClassNotFoundException{

        ObservableList<Payment> payments = paymentDAO.getAll();
        ObservableList<PaymentDTO> paymentDTOS = FXCollections.observableArrayList();

        for(Payment x : payments){
            paymentDTOS.add(new PaymentDTO().toDTO(x));
        }

        return paymentDTOS;
    }


    public ObservableList<TenantDTO> getAllTenantIds() throws SQLException, ClassNotFoundException{

        ObservableList<Tenant> tenants = tenantDAO.getAll();
        ObservableList<TenantDTO> tenantDTOS = FXCollections.observableArrayList();

        for(Tenant x : tenants){
            tenantDTOS.add(new TenantDTO().toDTO(x));
        }
        return tenantDTOS;
    }


    public ObservableList<String> getInvoiceNoSuggestions(String input) throws SQLException, ClassNotFoundException{

        return paymentDAO.getInvoiceNoSuggestions(input);
    }


    public boolean checkIfThisPaymentIsFirstPaymentOrNot(PaymentDTO selectedPayment) throws SQLException, ClassNotFoundException{

        return paymentDAO.checkIfThisPaymentIsFirstPaymentOrNot(new Payment().toEntity(selectedPayment));
    }


    public String delete(PaymentDTO selectedPayment) throws SQLException, ClassNotFoundException{

        Connection connection = DBConnection.getInstance().getConnection();
        connection.setAutoCommit(false);

        try {
            String result = paymentDAO.delete(new Payment().toEntity(selectedPayment));

            if(!result.equals("Success")){
                connection.rollback();
                return "Something Went Wrong, Failed To Delete Payment, Try Again Later";
            }

            if(selectedPayment.getPaymentType().equals("Monthly Rent Payment")){

                Tenant tenant = new Tenant();
                tenant.setTenantId(selectedPayment.getTenantId());

                boolean isUpdate = tenantDAO.updateLastPaymentMonth(tenant);
                if(!isUpdate){
                    connection.rollback();
                    return "Something Went Wrong, Failed To Delete Payment, Try Again Later";
                }

                connection.commit();
                return "Successfully Delete The Payment";
            }

            connection.rollback();
            return "Something Went Wrong, Failed To Delete Payment, Try Again Later";

        }
        catch (Exception e){
            connection.rollback();
            e.printStackTrace();

        }
        finally {
            connection.setAutoCommit(true);
        }

        return "0";
    }


    public String addNewMonthlyPayment(PaymentDTO paymentDTO,TenantDTO tenantDTO) throws SQLException, ClassNotFoundException{

        Connection connection = DBConnection.getInstance().getConnection();
        connection.setAutoCommit(false);

        try {
            String result = paymentDAO.add(new Payment().toEntity(paymentDTO));
            if(!result.equals("Success")){
                connection.rollback();
                return "Filed To Add New Monthly Payment Of Tenant Id: "+paymentDTO.getTenantId()+", Try Again Later";
            }

            boolean isUpdateMonth = tenantDAO.setNewLastPaidMonth(new Tenant().toEntity(tenantDTO));
            if(!isUpdateMonth){
                connection.rollback();
                return "Filed To Add New Monthly Payment Of Tenant Id: "+paymentDTO.getTenantId()+", Try Again Later";
            }

            connection.commit();
            return "A new monthly payment has been successfully processed for Tenant ID: "+paymentDTO.getTenantId()+" for the month of "+tenantDTO.getLastPaidMonth();

        }
        catch (Exception e){
            connection.rollback();
            e.printStackTrace();
        }
        finally {
            connection.setAutoCommit(true);
        }
        return "0";
    }


    public String addNewPropertyDamagePayment(PaymentDTO paymentDTO,HouseInspectDTO houseInspectDTO) throws SQLException, ClassNotFoundException{

        Connection connection = DBConnection.getInstance().getConnection();
        connection.setAutoCommit(false);

        try {
            String result = paymentDAO.add(new Payment().toEntity(paymentDTO));
            if(!result.equals("Success")){
                connection.rollback();
                return "Filed To Add New Property Damage Charge Of Tenant Id: "+paymentDTO.getTenantId()+", Try Again Later";
            }

            HouseInspect houseInspect = new HouseInspect();
            houseInspect.setCheckNumber(houseInspectDTO.getCheckNumber());
            houseInspect.setIsPaymentDone("Paid");

            String isUpdate = houseInspectDAO.update(houseInspect);
            if(!isUpdate.equals("Successfully")){
                connection.rollback();
                return "Filed To Add New Property Damage Charge Of Tenant Id: "+paymentDTO.getTenantId()+", Try Again Later";
            }

            connection.commit();
            return "A new Property Damage Charge has been successfully processed for Tenant ID: "+paymentDTO.getTenantId()+" for the house inspection Id: "+houseInspect.getCheckNumber()+ " for house Id: "+houseInspect.getHouseId();

        }
        catch (Exception e){
            connection.rollback();
            e.printStackTrace();
        }
        finally {
            connection.setAutoCommit(true);
        }
        return "0";
    }


    public String getLastPayment() throws SQLException, ClassNotFoundException{

        return paymentDAO.getLastPayment();
    }


    public double getTotalIncome() throws SQLException, ClassNotFoundException{

        return paymentDAO.getTotalIncome();
    }


    public ObservableList<String> getHouseCheckNumbersSuggestions(String input)throws SQLException, ClassNotFoundException{

        return houseInspectDAO.getHouseCheckNumbersSuggestions(input);
    }


    public HouseInspectDTO getHouseInspectionDetails(String inspectionId) throws SQLException, ClassNotFoundException{

        return new HouseInspectDTO().toDTO(houseInspectDAO.search(inspectionId));
    }


    public TenantDTO getTenantDetailsUsingId(String tenantId) throws SQLException, ClassNotFoundException{

        return new TenantDTO().toDTO(tenantDAO.search(tenantId));
    }


    public TenantDTO getTenantDetailsUsingPhoneNo(String phoneNo)throws SQLException, ClassNotFoundException{

        return new TenantDTO().toDTO(tenantDAO.searchByPhoneNo(phoneNo));
    }
}


