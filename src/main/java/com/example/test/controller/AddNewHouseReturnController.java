package com.example.test.controller;

import com.example.test.bo.BOFactory;
import com.example.test.bo.custom.HouseReturnBO;
import com.example.test.dao.custom.impl.*;
import com.example.test.dto.HouseInspectDTO;
import com.example.test.dto.HouseReturnDTO;
import com.example.test.dto.TenantDTO;
import com.example.test.dto.UnitDTO;
import com.example.test.entity.HouseInspect;
import com.example.test.entity.Tenant;
import com.example.test.entity.Unit;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;

import java.sql.SQLException;


public class AddNewHouseReturnController {

    @FXML
    private TextField tenantIdTxt;

    @FXML
    private Button refundAndReclaimBtn;

    @FXML
    private Button reclaimBtn;

    @FXML
    private Label tenantNameLabel;

    @FXML
    private Label houseIdLabel;

    @FXML
    private Label houseTypeLabel;

    @FXML
    private TextField reasonToLeaveTxt;

    @FXML
    private Label remainingSecurityFundLabel;

    @FXML
    private Label houseInspectDetailsLabel;

    @FXML
    private Label messageLabel;

    @FXML
    private Label lastPaidMonthLabel;

    private final HouseReturnBO houseReturnBO = (HouseReturnBO) BOFactory.getInstance().getBO(BOFactory.BOType.HOUSERETURN);
    private HouseReturnDTO houseReturnDto;
    private TenantDTO tenantDetails;
    private String leaseAgreementId;


    @FXML
    void reclaimOnAction(ActionEvent event) {

        houseReturnDto.setReason(reasonToLeaveTxt.getText());

        if(houseReturnDto.getTenantId()==null || houseReturnDto.getReason()==null){
            return;
        }

        try {
            String response = houseReturnBO.reclaimHouse(houseReturnDto,leaseAgreementId);
            notification(response);

            if(response.equals("Successfully Reclaiming The House!")){
                clean();
            }

        }
        catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            System.err.println("Error while returning the house: " + e.getMessage());
            notification("An error occurred while returning the house. Please try again or contact support.");
        }

    }


    @FXML
    void refundAndReclaimOnAction(ActionEvent event) {

        houseReturnDto.setReason(reasonToLeaveTxt.getText());

        if(houseReturnDto.getTenantId()==null || houseReturnDto.getReason()==null){
            return;
        }

        try {
            String response = houseReturnBO.reclaimHouseWithRefundSecurityDeposit(houseReturnDto,leaseAgreementId);
            notification(response);

            if(response.equals("Successfully Refund The Security Payment And Reclaiming The House!")){
                clean();
            }
        }
        catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            System.err.println("Error while returning the house: " + e.getMessage());
            notification("An error occurred while returning the house. Please try again or contact support.");
        }

    }


    @FXML
    void searchIcon(MouseEvent event) {

        String tenantId = tenantIdTxt.getText();

        if(!tenantId.isEmpty()){
            try {
                TenantDTO tenantDTO = houseReturnBO.getTenantDetails(tenantId);

                if(tenantDTO.getName()==null){

                    clean();
                    notification("Please Enter Correct Tenant ID");
                    return;
                }

                tenantDetails = houseReturnBO.getTenantDetails(tenantDTO.getTenantId());

                tenantNameLabel.setText(tenantDTO.getName());
                houseIdLabel.setText(tenantDTO.getHouseId());
                remainingSecurityFundLabel.setText(String.valueOf(tenantDTO.getSecurityPaymentRemain()));
                lastPaidMonthLabel.setText(tenantDTO.getLastPaidMonth());

                UnitDTO unitDTO =  houseReturnBO.getUnitDetails(tenantDTO.getHouseId());
                houseTypeLabel.setText(unitDTO.getHouseType());

                houseReturnDto = new HouseReturnDTO();
                houseReturnDto.setTenantId(tenantDTO.getTenantId());
                houseReturnDto.setHouseId(tenantDTO.getHouseId());
                houseReturnDto.setRefundedAmount(String.valueOf(tenantDTO.getSecurityPaymentRemain()));

                leaseAgreementId = houseReturnBO.getLeaseAgreementByTenantId(tenantDTO.getTenantId());

                HouseInspectDTO houseInspectDto = houseReturnBO.getLatestHouseInspectOfTenant(tenantDTO.getTenantId());
                if(houseInspectDto.getTotalHouseStatus()==null){

                    houseInspectDetailsLabel.setText("No Inspect For This Rented House");
                }

                else{
                    if(houseInspectDto.getIsPaymentDone().equals("N/A")){

                        houseInspectDetailsLabel.setText("Total House Status: "+ houseInspectDto.getTotalHouseStatus() + "  ,  No Damages Noted In The Last Inspection");
                    }
                    else{
                        houseInspectDetailsLabel.setText("Total House Status: "+ houseInspectDto.getTotalHouseStatus() + "  ,  Is Payment Done For Damages: "+ houseInspectDto.getIsPaymentDone());

                        if(houseInspectDto.getTotalHouseStatus().equals("Damaged") && houseInspectDto.getIsPaymentDone().equals("Not Yet")){
                            messageLabel.setText("The tenant has damaged the house without compensation.\nIt is advised to seek payment and reclaim the property");
                            refundAndReclaimBtn.setDisable(true);
                        }

                    }
                }
            }
            catch (SQLException | ClassNotFoundException e) {
                e.printStackTrace();
                System.err.println("Error while searching the tenant details: " + e.getMessage());
                notification("An error occurred while searching the tenant details. Please try again or contact support.");
            }

        }

    }


    public void clean(){

       tenantIdTxt.clear();
       tenantNameLabel.setText("");
       houseTypeLabel.setText("");
       houseIdLabel.setText("");
       lastPaidMonthLabel.setText("");
       houseInspectDetailsLabel.setText("");
       messageLabel.setText("");
       remainingSecurityFundLabel.setText("");
       reasonToLeaveTxt.clear();
       refundAndReclaimBtn.setDisable(false);
       reclaimBtn.setDisable(false);

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
