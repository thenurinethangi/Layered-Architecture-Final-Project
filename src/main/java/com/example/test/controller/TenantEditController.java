package com.example.test.controller;

import com.example.test.bo.BOFactory;
import com.example.test.bo.custom.TenantBO;
import com.example.test.dto.TenantDTO;
import com.example.test.view.tdm.TenantTM;
import com.example.test.dao.custom.impl.TenantDAOImpl;
import com.example.test.UserInputValidation;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;

import java.sql.SQLException;

public class TenantEditController {

    @FXML
    private Label tenantIdLabel;

    @FXML
    private TextField nameTxt;

    @FXML
    private TextField phoneNoTxt;

    @FXML
    private TextField emailTxt;

    @FXML
    private TextField membersCountTxt;

    @FXML
    private Label nameAlert;

    @FXML
    private Label phoneNoAlert;

    @FXML
    private Label membersCountAlert;

    @FXML
    private Label emailAlert;

    @FXML
    private Button editbtn;

    @FXML
    private Button clearbtn;

    @FXML
    private Button canclebtn;


    private TenantTM selectedTenant;
    private final TenantBO tenantBO = (TenantBO) BOFactory.getInstance().getBO(BOFactory.BOType.TENANT);


    @FXML
    void cancleOnAction(ActionEvent event) {

        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.close();

    }


    @FXML
    void clearOnAction(ActionEvent event) {

        clean();
    }


    @FXML
    void editOnAction(ActionEvent event) {

        String name = nameTxt.getText();
        String phoneNo = phoneNoTxt.getText();
        String membersCount = membersCountTxt.getText();
        String email = emailTxt.getText();

        boolean isEmpty = checkAllFieldNotEmpty(name,phoneNo,membersCount,email);

        if(!isEmpty){
            return;
        }

        boolean result = getAlertDependOnUserInput(name,phoneNo,membersCount,email);
        if(!result) {
            return;
        }

        else{
            nameAlert.setText("");
            phoneNoAlert.setText("");
            membersCountAlert.setText("");
            emailAlert.setText("");

            String id = tenantIdLabel.getText();

            TenantDTO tenantDto = new TenantDTO();
            tenantDto.setTenantId(id);
            tenantDto.setName(name);
            tenantDto.setPhoneNo(phoneNo);
            tenantDto.setMembersCount(Integer.parseInt(membersCount));
            tenantDto.setEmail(email);


            try {
                String response = tenantBO.update(tenantDto);
                notification(response);

            } catch (SQLException | ClassNotFoundException e) {
                e.printStackTrace();
                System.err.println("Error while updating tenant id: "+selectedTenant.getTenantId()+" details"+ e.getMessage());
                notification("An error occurred while updating tenant id: "+selectedTenant.getTenantId()+" details, Please try again or contact support.");
            }

            clean();
        }

    }


    public boolean getAlertDependOnUserInput(String name,String phoneNo,String membersCount, String email){

        boolean b1 = UserInputValidation.checkNameValidation(name);
        boolean b2 = UserInputValidation.checkPhoneNoValidation(phoneNo);
        boolean b3 = UserInputValidation.checkNumberLessThanTenValidation(membersCount);
        boolean b4 = UserInputValidation.checkEmailValidation(email);

        if(!b1 || !b2 || !b3 || !b4){
            if(!b1){
                nameAlert.setText("The name format you provided is incorrect, please provide the correct name");
            }
            else{
                nameAlert.setText("");
            }
            if(!b2){
                phoneNoAlert.setText("The Phone Number format you provided is incorrect, please provide the correct Phone number");
            }
            else{
                phoneNoAlert.setText("");
            }
            if(!b3){
                membersCountAlert.setText("The Resident Count you provided is illegal, please provide the correct Resident Count");
            }
            else{
                membersCountAlert.setText("");
            }
            if(!b4){
                emailAlert.setText("The email format you provided is incorrect, please provide the correct email");
            }
            else{
                emailAlert.setText("");
            }

            return false;
        }
        else{
            return true;
        }
    }



    public boolean checkAllFieldNotEmpty(String name,String phoneNo,String membersCount, String email){

        if(name.isEmpty() || phoneNo.isEmpty() || membersCount.isEmpty() || email.isEmpty()){

            notification("No field can be empty");
            return false;
        }

        return true;
    }

    public void setSelectedTenantDetails(TenantTM tenantTm) {

        selectedTenant = tenantTm;

        tenantIdLabel.setText(selectedTenant.getTenantId());
        nameTxt.setText(selectedTenant.getName());
        phoneNoTxt.setText(selectedTenant.getPhoneNo());
        membersCountTxt.setText(String.valueOf(selectedTenant.getMembersCount()));

        try {
            TenantDTO tenantDto = tenantBO.search(selectedTenant.getTenantId());
            emailTxt.setText(tenantDto.getEmail());
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            System.err.println("Error while setting tenant id: "+selectedTenant.getTenantId()+" details"+ e.getMessage());
            notification("An error occurred while setting tenant id: "+selectedTenant.getTenantId()+" details, Please try again or contact support.");
        }
    }


    public void clean(){

        nameTxt.setText("");
        phoneNoTxt.setText("");
        membersCountTxt.setText("");
        emailTxt.setText("");
        nameAlert.setText("");
        phoneNoAlert.setText("");
        membersCountAlert.setText("");
        emailAlert.setText("");
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
