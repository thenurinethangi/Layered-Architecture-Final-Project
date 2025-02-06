package com.example.test.bo.custom.impl;

import com.example.test.bo.custom.RequestBO;
import com.example.test.dao.DAOFactory;
import com.example.test.dao.custom.*;
import com.example.test.db.DBConnection;
import com.example.test.dto.CustomerDTO;
import com.example.test.dto.HouseTypeDTO;
import com.example.test.dto.RequestDTO;
import com.example.test.dto.UnitDTO;
import com.example.test.entity.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;

public class RequestBOImpl implements RequestBO {

    RequestDAO requestDAO = (RequestDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.REQUEST);
    UnitDAO unitDAO = (UnitDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.UNIT);
    HouseTypeDAO houseTypeDAO = (HouseTypeDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.HOUSETYPE);
    CustomerDAO customerDAO = (CustomerDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.CUSTOMER);
    LeaseAgreementDAO leaseAgreementDAO = (LeaseAgreementDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.LEASEAGREEMENT);
    PurchaseAgreementDAO purchaseAgreementDAO = (PurchaseAgreementDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.PURCHASEAGREEMENT);
    PaymentDAO paymentDAO = (PaymentDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.PAYMENT);
    TenantDAO tenantDAO = (TenantDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.TENANT);
    OwnerDAO ownerDAO = (OwnerDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.OWNER);


    public ObservableList<RequestDTO> getAll() throws SQLException, ClassNotFoundException{

        ObservableList<Request> requests = requestDAO.getAll();
        ObservableList<RequestDTO> RequestDTOS = FXCollections.observableArrayList();

        for(Request x : requests){
            RequestDTOS.add(new RequestDTO().toDTO(x));
        }
        return RequestDTOS;
    }


    public String delete(RequestDTO selectedRequest) throws SQLException, ClassNotFoundException{

        Connection connection = DBConnection.getInstance().getConnection();
        connection.setAutoCommit(false);

        try {
            String result = requestDAO.delete(new Request().toEntity(selectedRequest));

            if(!result.equals("Success")){
                connection.rollback();
                return "Filed To Delete The Request, Try Again Later";
            }

            if(!selectedRequest.getHouseId().equals("-")){

                Unit unit = new Unit();
                unit.setHouseId(selectedRequest.getHouseId());
                unit.setStatus("Available");
                boolean res = unitDAO.updateHouseStatus(unit);

                if(!res){
                    connection.rollback();
                    return "Filed To Delete The Request, Try Again Later";
                }

                connection.commit();
                return "Successfully Deleted The Request";
            }

            connection.commit();
            return "Successfully Deleted The Request";
        }
        catch (SQLException | ClassNotFoundException e) {
            connection.rollback();
            e.printStackTrace();
            System.err.println("Error while deleting the request: " + e.getMessage());
        }
        finally {
            connection.setAutoCommit(true);
        }
        return "0";
    }


    public String addNewTenant(RequestDTO requestDTO) throws SQLException, ClassNotFoundException{

        Connection connection = DBConnection.getInstance().getConnection();
        connection.setAutoCommit(false);

        try {

            String tenantId = tenantDAO.generateNewId();
            System.out.println("tenant id: " + tenantId);

            Customer customerDetails = customerDAO.search(requestDTO.getCustomerId());
            if(customerDetails==null){
                connection.rollback();
                return "Something Went Wrong With Add New Tenant,Try Again Later";
            }
            System.out.println("customer name: "+customerDetails.getName());

            Request request1 = requestDAO.search(requestDTO.getRequestId());
            if(request1.getNoOfFamilyMembers()==0){
                connection.rollback();
                return "Something Went Wrong With Add New Tenant,Try Again Later";
            }
            System.out.println(request1.getNoOfFamilyMembers());

            Unit unit = unitDAO.search(requestDTO.getHouseId());
            if(unit == null){
                connection.rollback();
                return "Something Went Wrong With Add New Tenant,Try Again Later";
            }
            System.out.println("rent: "+ unit.getMonthlyRent());

            Tenant tenant = new Tenant();
            tenant.setTenantId(tenantId);
            tenant.setHeadOfHouseholdName(customerDetails.getName());
            tenant.setPhoneNo(customerDetails.getPhoneNo());
            tenant.setEmail(customerDetails.getEmail());
            tenant.setMembersCount(request1.getNoOfFamilyMembers());
            tenant.setHouseId(requestDTO.getHouseId());
            tenant.setMonthlyRent(Double.parseDouble(unit.getMonthlyRent()));
            tenant.setIsActiveTenant(1);
            tenant.setSecurityPaymentRemain(Double.parseDouble(unit.getMonthlyRent()));

            String isSavedNewCustomer = tenantDAO.add(tenant);

            if(!isSavedNewCustomer.equals("Success")){
                connection.rollback();
                return "Something Went Wrong With Add New Tenant,Try Again Later";
            }

            double securityPayment = Double.parseDouble(unit.getSecurityCharge());
            Payment payment = new Payment();
            payment.setTenantId(tenantId);
            payment.setAmount(securityPayment);
            payment.setPaymentType("Security Deposit");
            payment.setIsFirstPayment(1);
            payment.setOwnerPayment(0);
            payment.setDate(String.valueOf(LocalDate.now()));

            String isAddSecurityPayment = paymentDAO.add(payment);
            if(!isAddSecurityPayment.equals("Success")){
                connection.rollback();
                return "Something Went Wrong With Add New Tenant,Try Again Later";
            }

            double monthlyPayment = Double.parseDouble(unit.getMonthlyRent());
            Payment payment1 = new Payment();
            payment1.setTenantId(tenantId);
            payment1.setAmount(monthlyPayment);
            payment1.setPaymentType("Monthly Rent Payment");
            payment1.setIsFirstPayment(1);
            payment1.setOwnerPayment(0);
            payment1.setDate(String.valueOf(LocalDate.now()));

            String isAddFirstMonthPayment = paymentDAO.add(payment1);
            if(!isAddFirstMonthPayment.equals("Success")){
                connection.rollback();
                return "Something Went Wrong With Add New Tenant,Try Again Later";
            }

            LeaseAgreement leaseAgreement = new LeaseAgreement();
            leaseAgreement.setTenantId(tenantId);
            leaseAgreement.setLeaseTurn(requestDTO.getLeaseTurnDesire());
            leaseAgreement.setSecurityPayment(securityPayment);
            leaseAgreement.setStartDate(String.valueOf(LocalDate.now()));
            leaseAgreement.setHouseId(requestDTO.getHouseId());
            leaseAgreement.setMonthlyRent(monthlyPayment);
            leaseAgreement.setStatus("Active");

            String isAddNewAgreement = leaseAgreementDAO.add(leaseAgreement);
            if(!isAddNewAgreement.equals("Success")){
                connection.rollback();
                return "Something Went Wrong With Add New Tenant,Try Again Later";
            }

            boolean isUpdateRequestStatus = requestDAO.updateRequestStatus(new Request().toEntity(requestDTO));
            if(!isUpdateRequestStatus){
                connection.rollback();
                return "Something Went Wrong With Add New Tenant,Try Again Later";
            }

            connection.commit();
            return "Successfully Add As New Tenant!";

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


    public String addNewOwner(RequestDTO requestDTO) throws SQLException, ClassNotFoundException{

        Connection connection = DBConnection.getInstance().getConnection();
        connection.setAutoCommit(false);

        try {

            Unit unitDTO = unitDAO.search(requestDTO.getHouseId());
            if(unitDTO.getTotalValue()==null){
                return "Something Went Wrong With Process Of Adding A New Owner For Completed Purchase Request, Try Again Later";
            }

            Payment payment1 = new Payment();
            payment1.setTenantId(null);
            payment1.setAmount(Double.parseDouble(unitDTO.getTotalValue()));
            payment1.setPaymentType("Full House Purchase Payment");
            payment1.setIsFirstPayment(1);
            payment1.setOwnerPayment(0);
            payment1.setDate(String.valueOf(LocalDate.now()));

            String isAddNewPurchasePayment = paymentDAO.add(payment1);
            if(!isAddNewPurchasePayment.equals("Success")){
                connection.rollback();
                return "Something Went Wrong With Process Of Adding A New Owner For Completed Purchase Request, Try Again Later";
            }

            String invoiceNo = paymentDAO.getLastPayment();
            if(invoiceNo.equals("0")){
                return "Something Went Wrong With Process Of Adding A New Owner For Completed Purchase Request, Try Again Later";
            }

            Customer customerDetails = customerDAO.search(requestDTO.getCustomerId());
            if(customerDetails==null){
                return "Something Went Wrong With Process Of Adding A New Owner For Completed Purchase Request, Try Again Later";
            }

            Request request1 = requestDAO.search(requestDTO.getRequestId());
            if(request1.getNoOfFamilyMembers()==0){
                return "Something Went Wrong With Process Of Adding A New Owner For Completed Purchase Request, Try Again Later";
            }

            Owner owner = new Owner();
            owner.setName(customerDetails.getName());
            owner.setMembersCount(request1.getNoOfFamilyMembers());
            owner.setPurchaseDate(String.valueOf(LocalDate.now()));
            owner.setInvoiceNo(invoiceNo);
            owner.setPhoneNo(customerDetails.getPhoneNo());
            owner.setHouseId(requestDTO.getHouseId());
            owner.setEmail(customerDetails.getEmail());

            String isAddNewOwner = ownerDAO.add(owner);
            if(!isAddNewOwner.equals("Success")){
                connection.rollback();
                return "Something Went Wrong With Process Of Adding A New Owner For Completed Purchase Request, Try Again Later";
            }

            String ownerId = ownerDAO.getLastAddedOwnerId();
            if(ownerId.equals("0")){
                return "Something Went Wrong With Process Of Adding A New Owner For Completed Purchase Request, Try Again Later";
            }

            PurchaseAgreement purchaseAgreement = new PurchaseAgreement();
            purchaseAgreement.setHomeOwnerId(ownerId);
            purchaseAgreement.setStatus("Active");
            purchaseAgreement.setSignedDate(String.valueOf(LocalDate.now()));
            purchaseAgreement.setPurchasePrice(Double.parseDouble(unitDTO.getTotalValue()));
            purchaseAgreement.setHouseId(unitDTO.getHouseId());

            String isAddNewPurchaseAgreement = purchaseAgreementDAO.add(purchaseAgreement);
            if(!isAddNewPurchaseAgreement.equals("Success")){
                connection.rollback();
                return "Something Went Wrong With Process Of Adding A New Owner For Completed Purchase Request, Try Again Later";
            }

            boolean isUpdateRequestStatus = requestDAO.updateRequestStatus(new Request().toEntity(requestDTO));
            if(!isUpdateRequestStatus){
                connection.rollback();
                return "Something Went Wrong With Process Of Adding A New Owner For Completed Purchase Request, Try Again Later";
            }

            connection.commit();
            return "Successfully \"Closed\" The Request And Added New Owner To The System, Owner Id is "+ownerId;

        }
        catch (Exception e){
            connection.rollback();
            e.printStackTrace();
            return "Something Went Wrong With Process Of Adding A New Owner For Completed Purchase Request, Try Again Later";
        }
        finally {
            connection.setAutoCommit(true);
        }

    }


    public ObservableList<RequestDTO> getOnlyClosedRequests() throws SQLException, ClassNotFoundException{

        ObservableList<Request> requests = requestDAO.getOnlyClosedRequests();
        ObservableList<RequestDTO> RequestDTOS = FXCollections.observableArrayList();

        for(Request x : requests){
            RequestDTOS.add(new RequestDTO().toDTO(x));
        }
        return RequestDTOS;
    }


    public ObservableList<String> getDistinctCustomers() throws SQLException, ClassNotFoundException{

        return requestDAO.getDistinctCustomers();
    }


    public String generateNewId() throws SQLException, ClassNotFoundException{

        return requestDAO.generateNewId();
    }


    public ObservableList<HouseTypeDTO> getAllHouseTypes() throws SQLException, ClassNotFoundException{

        ObservableList<HouseType> houseTypes = houseTypeDAO.getAll();
        ObservableList<HouseTypeDTO> houseTypeDTOS = FXCollections.observableArrayList();

        for(HouseType x : houseTypes){
            houseTypeDTOS.add(new HouseTypeDTO().toDTO(x));
        }

        return houseTypeDTOS;
    }


    public String add(RequestDTO request) throws SQLException, ClassNotFoundException{

        return requestDAO.add(new Request().toEntity(request));
    }


    public String editRequest(RequestDTO request1, RequestDTO request2) throws SQLException, ClassNotFoundException{

        Connection connection = DBConnection.getInstance().getConnection();
        connection.setAutoCommit(false);

        try {
            if (request1.getRequestStatus().equals("Rejected") && !request2.getHouseId().equals("-")) {

                Unit unit = new Unit();
                unit.setHouseId(request2.getHouseId());
                unit.setStatus("Available");

                boolean resultOne = unitDAO.updateHouseStatus(unit);
                if (resultOne) {

                    String resultTwo = requestDAO.update(new Request().toEntity(request1));

                    if (resultTwo.equals("Success")) {
                        connection.commit();
                        return "Successfully Updated Request";
                    }
                    else {
                        connection.rollback();
                        return "Failed To Update Request, Try Again Later";
                    }
                }
                else{
                    connection.rollback();
                    return "Failed To Update Request, Try Again Later";
                }

            }
            else{
                String resultTwo = requestDAO.update(new Request().toEntity(request1));

                if (resultTwo.equals("Success")) {
                    connection.commit();
                    return "Successfully Updated Request";
                }

                connection.rollback();
                return "Failed To Update Request, Try Again Later";
            }
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


    public RequestDTO search(String requestId) throws SQLException, ClassNotFoundException{

        return new RequestDTO().toDTO(requestDAO.search(requestId));
    }


    public boolean update(RequestDTO requestDTO) throws SQLException, ClassNotFoundException{

        return requestDAO.updateRequestStatus(new Request().toEntity(requestDTO));
    }


    public ObservableList<String> getAvailableRentHouses(RequestDTO requestDTO) throws SQLException, ClassNotFoundException {

        Unit unit = new Unit();
        unit.setHouseId(requestDTO.getHouseId());
        unit.setRentOrBuy(requestDTO.getRentOrBuy());
        unit.setHouseType(requestDTO.getHouseType());
        unit.setMonthlyRent(requestDTO.getEstimatedMonthlyBudgetForRent());

        return unitDAO.getAvailableRentHouses(unit);
    }


    public ObservableList<String> getRecommendedRentHouses(RequestDTO requestDTO) throws SQLException, ClassNotFoundException {

        Unit unit = new Unit();
        unit.setHouseId(requestDTO.getHouseId());
        unit.setRentOrBuy(requestDTO.getRentOrBuy());
        unit.setHouseType(requestDTO.getHouseType());
        unit.setMonthlyRent(requestDTO.getEstimatedMonthlyBudgetForRent());

        return unitDAO.getRecommendedRentHouses(unit);
    }


    public ObservableList<String> getAvailableSellHouses(RequestDTO requestDTO) throws SQLException, ClassNotFoundException {

        Unit unit = new Unit();
        unit.setHouseId(requestDTO.getHouseId());
        unit.setRentOrBuy(requestDTO.getRentOrBuy());
        unit.setHouseType(requestDTO.getHouseType());
        unit.setMonthlyRent(requestDTO.getEstimatedMonthlyBudgetForRent());

        return unitDAO.getAvailableSellHouses(unit);
    }


    public ObservableList<Unit> getAvailableRentHousesAsUnitObject(RequestDTO requestDTO) throws SQLException, ClassNotFoundException {

        Unit unit = new Unit();
        unit.setHouseId(requestDTO.getHouseId());
        unit.setRentOrBuy(requestDTO.getRentOrBuy());
        unit.setHouseType(requestDTO.getHouseType());
        unit.setMonthlyRent(requestDTO.getEstimatedMonthlyBudgetForRent());

        return unitDAO.getAvailableRentHousesAsUnitObject(unit);
    }


    public ObservableList<Unit> getRecommendedRentHousesAsUnitObject(RequestDTO requestDTO) throws SQLException, ClassNotFoundException {

        Unit unit = new Unit();
        unit.setHouseId(requestDTO.getHouseId());
        unit.setRentOrBuy(requestDTO.getRentOrBuy());
        unit.setHouseType(requestDTO.getHouseType());
        unit.setMonthlyRent(requestDTO.getEstimatedMonthlyBudgetForRent());

        return unitDAO.getRecommendedRentHousesAsUnitObject(unit);
    }


    public ObservableList<Unit> getAvailableSellHousesAsUnitObject(RequestDTO requestDTO) throws SQLException, ClassNotFoundException {

        Unit unit = new Unit();
        unit.setHouseId(requestDTO.getHouseId());
        unit.setRentOrBuy(requestDTO.getRentOrBuy());
        unit.setHouseType(requestDTO.getHouseType());
        unit.setMonthlyRent(requestDTO.getEstimatedMonthlyBudgetForRent());

        return unitDAO.getAvailableSellHousesAsUnitObject(unit);
    }


    public ObservableList<String> getRecommendedSellHouses(RequestDTO requestDTO) throws SQLException, ClassNotFoundException {

        Unit unit = new Unit();
        unit.setHouseId(requestDTO.getHouseId());
        unit.setRentOrBuy(requestDTO.getRentOrBuy());
        unit.setHouseType(requestDTO.getHouseType());
        unit.setMonthlyRent(requestDTO.getEstimatedMonthlyBudgetForRent());

        return unitDAO.getRecommendedSellHouses(unit);
    }


    public ObservableList<Unit> getRecommendedSellHousesAsUnitObject(RequestDTO requestDTO) throws SQLException, ClassNotFoundException {

        Unit unit = new Unit();
        unit.setHouseId(requestDTO.getHouseId());
        unit.setRentOrBuy(requestDTO.getRentOrBuy());
        unit.setHouseType(requestDTO.getHouseType());
        unit.setMonthlyRent(requestDTO.getEstimatedMonthlyBudgetForRent());

        return unitDAO.getRecommendedSellHousesAsUnitObject(unit);
    }


    public boolean setHouseAvailable(UnitDTO unitDTO) throws SQLException, ClassNotFoundException{

        return unitDAO.updateHouseStatus(new Unit().toEntity(unitDTO));
    }


    public String updateAssignedHouse(RequestDTO requestDTO) throws SQLException, ClassNotFoundException{

        Connection connection = DBConnection.getInstance().getConnection();

        connection.setAutoCommit(false);

        try {
            boolean result = requestDAO.updateAssignedHouse(new Request().toEntity(requestDTO));

            if (result) {

                Unit unit = new Unit();
                unit.setHouseId(requestDTO.getHouseId());
                unit.setStatus("Unavailable");
                boolean res = unitDAO.updateHouseStatus(unit);
                if(res){
                    connection.commit();
                    return "Successfully assign the house to the request";
                }
                else{
                    connection.rollback();
                    return "Something went wrong while updating the house status. Failed to assign the house to the request; please try again later.";
                }

            }
            else {
                connection.rollback();
                return "Something went wrong,Failed to assign the house to the request; please try again later";
            }
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


    public CustomerDTO getCustomerDetails(String id)throws SQLException, ClassNotFoundException{

        return new CustomerDTO().toDTO(customerDAO.search(id));
    }


    public String checkCustomerExistByNic(String nic)throws SQLException, ClassNotFoundException{

        return customerDAO.isExistByNic(nic);
    }
}










