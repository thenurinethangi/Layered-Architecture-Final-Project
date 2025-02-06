package com.example.test.bo;

import com.example.test.bo.custom.impl.*;
import com.example.test.dao.custom.impl.*;

import java.sql.SQLException;

public class BOFactory {

    private static BOFactory boFactory;

    private BOFactory(){

    }

    public static BOFactory getInstance(){
        if(boFactory==null){
            boFactory = new BOFactory();
        }

        return boFactory;
    }


    public enum BOType{

        CUSTOMER,EMPLOYEE,TENANT,OWNER,SIGNIN,SIGNUP,FLOOR,HOUSETYPE,
        UNIT,LEASEAGREEMENT,PURCHASEAGREEMENT,PAYMENT,HOUSEINSPECT,MAINTENANCEREQUEST,EXPENSE,
        HOUSERETURN,REQUEST,NOTIFICATION,USERPROFILE
    }


    public SuperBO getBO(BOType type) {

        switch (type){

            case SIGNIN -> {
                return new SignInBOImpl();
            }
            case SIGNUP -> {
                return new SignUpBOImpl();
            }
            case CUSTOMER -> {
                return new CustomerBOImpl();
            }
            case FLOOR -> {
                return new FloorBOImpl();
            }
            case HOUSETYPE -> {
                return new HouseTypeBOImpl();
            }
            case UNIT -> {
                return new UnitBOImpl();
            }
            case EMPLOYEE -> {
                return new EmployeeBOImpl();
            }
            case TENANT -> {
                return new TenantBOImpl();
            }
            case OWNER -> {
                return new OwnerBOImpl();
            }
            case LEASEAGREEMENT -> {
                return new LeaseAgreementBOImpl();
            }
            case PURCHASEAGREEMENT -> {
                return new PurchaseAgreementBOImpl();
            }
            case PAYMENT -> {
                return new PaymentBOImpl();
            }
            case HOUSEINSPECT -> {
                return new HouseInspectBOImpl();
            }
            case MAINTENANCEREQUEST -> {
                return new MaintenanceRequestBOImpl();
            }
            case EXPENSE -> {
                return new ExpenseBOImpl();
            }
            case HOUSERETURN -> {
                return new HouseReturnBOImpl();
            }
            case REQUEST -> {
                return new RequestBOImpl();
            }
            case NOTIFICATION -> {
                return new NotificationBOImpl();
            }
            case USERPROFILE -> {
                return new UserProfileBOImpl();
            }
            default -> {
                return null;
            }
        }

    }

}


