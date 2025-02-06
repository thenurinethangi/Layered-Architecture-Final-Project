package com.example.test.controller;

import com.example.test.bo.BOFactory;
import com.example.test.bo.custom.RequestBO;
import com.example.test.dto.RequestDTO;
import com.example.test.entity.Customer;
import com.example.test.view.tdm.RequestTM;
import com.example.test.dao.custom.impl.CustomerDAOImpl;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

import java.sql.SQLException;

public class PurchaseRequestDetailsController {

    @FXML
    private Label smoking;

    @FXML
    private Label pets;

    @FXML
    private Label payment;

    @FXML
    private Label customerRequestStatus;

    @FXML
    private Label isQualified;

    @FXML
    private Label reasonToMove;

    @FXML
    private Label bankDetails;

    @FXML
    private Label annualIncome;

    @FXML
    private Label rentOrBuy;

    @FXML
    private Label livingArrangemnt;

    @FXML
    private Label phoneNo;

    @FXML
    private Label nic;

    @FXML
    private Label customerId;

    @FXML
    private Label requestId;

    @FXML
    private Label termsAndConditions;

    @FXML
    private Label houseId;

    @FXML
    private Label requestStatus;

    @FXML
    private Label docProvided;

    @FXML
    private Label criminalBackground;

    @FXML
    private Label landlordNo;

    @FXML
    private Label monthlyIncome;

    @FXML
    private Label familyMembersCount;

    @FXML
    private Label houseType;

    @FXML
    private Label job;

    @FXML
    private Label address;

    @FXML
    private Label name;

    @FXML
    private Label date;

    private final CustomerDAOImpl customerDAOImpl = new CustomerDAOImpl();
    private final RequestBO requestBO = (RequestBO) BOFactory.getInstance().getBO(BOFactory.BOType.REQUEST);
    private RequestTM requestTm;


    public void setSelectedRequestData(RequestTM request) {

        requestTm = request;

        requestId.setText(requestTm.getRequestId());
        customerId.setText(requestTm.getCustomerId());

        getCustomerDetails();

        houseType.setText(requestTm.getHouseType());
        rentOrBuy.setText(requestTm.getRentOrBuy());
        docProvided.setText(requestTm.getAllDocumentsProvided());
        isQualified.setText(requestTm.getQualifiedCustomerOrNot());
        termsAndConditions.setText(requestTm.getAgreesToAllTermsAndConditions());
        payment.setText(requestTm.getIsPaymentsCompleted());
        requestStatus.setText(requestTm.getRequestStatus());
        customerRequestStatus.setText(requestTm.getCustomerRequestStatus());
        houseId.setText(requestTm.getHouseId());

        getRentRequestDetails();
    }

    public void getRentRequestDetails(){

        try {
            RequestDTO requestDto = requestBO.search(requestTm.getRequestId());

            date.setText(requestDto.getDate());
            familyMembersCount.setText(String.valueOf(requestDto.getNoOfFamilyMembers()));
            monthlyIncome.setText(String.valueOf(requestDto.getMonthlyIncome()));
            annualIncome.setText(String.valueOf(requestDto.getAnnualIncome()));
            bankDetails.setText(requestDto.getBankAccountDetails());
            reasonToMove.setText(requestDto.getReasonForLeaving());
            landlordNo.setText(requestDto.getPreviousLandlordNumber());
            smoking.setText(requestDto.getIsSmoking());
            criminalBackground.setText(requestDto.getHasCriminalBackground());
            pets.setText(requestDto.getHasPets());

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

    }


    public void getCustomerDetails(){

        if(requestTm!=null) {

            try {
                Customer customer = customerDAOImpl.search(requestTm.getCustomerId());
                name.setText(customer.getName());
                nic.setText(customer.getNic());
                address.setText(customer.getAddress());
                phoneNo.setText(customer.getPhoneNo());
                job.setText(customer.getJobTitle());
                livingArrangemnt.setText(customer.getLivingArrangement());

            } catch (SQLException e) {
                throw new RuntimeException(e);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }

    }

}



