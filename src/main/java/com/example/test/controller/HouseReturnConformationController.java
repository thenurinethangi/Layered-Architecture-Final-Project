package com.example.test.controller;

import com.example.test.bo.BOFactory;
import com.example.test.bo.custom.HouseReturnBO;
import com.example.test.dto.HouseInspectDTO;
import com.example.test.dto.HouseReturnDTO;
import com.example.test.dto.TenantDTO;
import com.example.test.entity.HouseInspect;
import com.example.test.entity.HouseReturn;
import com.example.test.entity.Tenant;
import com.example.test.view.tdm.LeaseAgreementTM;
import com.example.test.dao.custom.impl.HouseInspectDAOImpl;
import com.example.test.dao.custom.impl.HouseReturnDAOImpl;
import com.example.test.dao.custom.impl.TenantDAOImpl;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;

import java.sql.SQLException;

public class HouseReturnConformationController {

    @FXML
    private Button rePayAndReturnBtn;

    @FXML
    private Button returnBtn;

    @FXML
    private Label tenantNameLabel;

    @FXML
    private Label houseIdLabel;

    @FXML
    private Label lastPaidDateLabel;

    @FXML
    private Label remainingDepositLabel;

    @FXML
    private Label lastHouseStatusCheckLabel;

    @FXML
    private Label tenantIdLabel;

    @FXML
    private TextArea messageLabel;

    private LeaseAgreementTM selectedLeaseAgreementDetails;
    private final HouseReturnBO houseReturnBO = (HouseReturnBO) BOFactory.getInstance().getBO(BOFactory.BOType.HOUSERETURN);
    private HouseReturnDTO houseReturnDto;
    private TenantDTO tenant;



    @FXML
    void onlyReturnOnAction(ActionEvent event) {

        try {
            String response = houseReturnBO.reclaimHouse(houseReturnDto,selectedLeaseAgreementDetails.getLeaseId());
            notification(response);

            if(response.equals("Successfully Reclaiming The House!")){
                returnBtn.setDisable(true);
            }

        }  catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            System.err.println("Error while reclaiming the house: " + e.getMessage());
            notification("An error occurred while reclaiming the house. Please try again or contact support.");
        }

    }


    @FXML
    void rePayAndReturnOnAction(ActionEvent event) {

        try {
            String response = houseReturnBO.reclaimHouseWithRefundSecurityDeposit(houseReturnDto,selectedLeaseAgreementDetails.getLeaseId());
            notification(response);

            if(response.equals("Successfully Refund The Security Payment And Reclaiming The House!")){
                returnBtn.setDisable(true);
                rePayAndReturnBtn.setDisable(true);
            }

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            System.err.println("Error while reclaiming the house: " + e.getMessage());
            notification("An error occurred while reclaiming the house. Please try again or contact support.");
        }

    }


    public void setSelectedAgreementDetailsToReturn(LeaseAgreementTM selectedLeaseAgreement) {

        this.selectedLeaseAgreementDetails = selectedLeaseAgreement;

        houseReturnDto = new HouseReturnDTO();
        houseReturnDto.setTenantId(selectedLeaseAgreement.getTenantId());
        houseReturnDto.setHouseId(selectedLeaseAgreement.getHouseId());
        houseReturnDto.setReason("Expiration of the Lease Turn");

        tenantIdLabel.setText(selectedLeaseAgreement.getTenantId());
        houseIdLabel.setText(selectedLeaseAgreement.getHouseId());

        try{

            tenant = houseReturnBO.getTenantDetails(selectedLeaseAgreement.getTenantId());
            tenantNameLabel.setText(tenant.getName());
            lastPaidDateLabel.setText(tenant.getLastPaidMonth());
            remainingDepositLabel.setText(String.valueOf(tenant.getSecurityPaymentRemain()));
            houseReturnDto.setRefundedAmount(String.valueOf(tenant.getSecurityPaymentRemain()));

            HouseInspectDTO houseInspectDto = houseReturnBO.getLatestHouseInspectOfTenant(selectedLeaseAgreementDetails.getTenantId());
            if(houseInspectDto.getTotalHouseStatus()==null){

                lastHouseStatusCheckLabel.setText("No Inspect For This Rented House");
            }
            else{
                if(houseInspectDto.getIsPaymentDone().equals("N/A")){

                    lastHouseStatusCheckLabel.setText("Total House Status: "+ houseInspectDto.getTotalHouseStatus() + "  ,  No Damages Noted In The Last Inspection");
                }
                else{
                    lastHouseStatusCheckLabel.setText("Total House Status: "+ houseInspectDto.getTotalHouseStatus() + "  ,  Is Payment Done For Damages: "+ houseInspectDto.getIsPaymentDone());

                    if(houseInspectDto.getTotalHouseStatus().equals("Damaged") && houseInspectDto.getIsPaymentDone().equals("Not Yet")){
                        messageLabel.setText("The tenant has damaged the house without compensation.\nIt is advised to seek payment and reclaim the property");
                        rePayAndReturnBtn.setDisable(true);
                    }

                }
            }
        }
        catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            System.err.println("Error while setting the agreement details to return: " + e.getMessage());
            notification("An error occurred while setting the agreement details to return. Please try again or contact support.");
        }

    }


    public void notification(String message){

        Notifications notifications = Notifications.create();
        notifications.title("Notification");
        notifications.text(message);
        notifications.hideCloseButton();
        notifications.hideAfter(Duration.seconds(4));
        notifications.position(Pos.CENTER);
        notifications.darkStyle();
        notifications.showInformation();
    }
}
