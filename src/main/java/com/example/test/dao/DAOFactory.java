package com.example.test.dao;

import com.example.test.bo.custom.impl.*;
import com.example.test.dao.custom.impl.*;

import java.sql.SQLException;

public class DAOFactory {

    private static DAOFactory daoFactory;

    private DAOFactory(){

    }

    public static DAOFactory getInstance(){
        if(daoFactory==null){
            daoFactory = new DAOFactory();
        }

        return daoFactory;
    }


    public enum DAOType{

        CUSTOMER,EMPLOYEE,TENANT,OWNER,SIGNIN,SIGNUP,FLOOR,HOUSETYPE,
        UNIT,LEASEAGREEMENT,PURCHASEAGREEMENT,PAYMENT,HOUSEINSPECT,MAINTENANCEREQUEST,EXPENSE,
        HOUSERETURN,REQUEST,NOTIFICATION,USERPROFILE
    }


    public SuperDAO getDAO(DAOFactory.DAOType type) {

        switch (type){

            case SIGNIN -> {
                return new SignInDAOImpl();
            }
            case SIGNUP -> {
                return new SignUpDAOImpl();
            }
            case CUSTOMER -> {
                return new CustomerDAOImpl();
            }
            case FLOOR -> {
                return new FloorDAOImpl();
            }
            case HOUSETYPE -> {
                return new HouseTypeDAOImpl();
            }
            case UNIT -> {
                return new UnitDAOImpl();
            }
            case EMPLOYEE -> {
                return new EmployeeDAOImpl();
            }
            case TENANT -> {
                return new TenantDAOImpl();
            }
            case OWNER -> {
                return new OwnerDAOImpl();
            }
            case LEASEAGREEMENT -> {
                return new LeaseAgreementDAOImpl();
            }
            case PURCHASEAGREEMENT -> {
                return new PurchaseAgreementDAOImpl();
            }
            case PAYMENT -> {
                return new PaymentDAOImpl();
            }
            case HOUSEINSPECT -> {
                return new HouseInspectDAOImpl();
            }
            case MAINTENANCEREQUEST -> {
                return new MaintenanceRequestDAOImpl();
            }
            case EXPENSE -> {
                return new ExpenseDAOImpl();
            }
            case HOUSERETURN -> {
                return new HouseReturnDAOImpl();
            }
            case REQUEST -> {
                return new RequestDAOImpl();
            }
            case USERPROFILE -> {
                return new UserProfileDAOImpl();
            }
            default -> {
                return null;
            }
        }

    }
}
