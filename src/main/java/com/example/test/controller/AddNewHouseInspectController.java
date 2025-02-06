package com.example.test.controller;

import com.example.test.bo.BOFactory;
import com.example.test.bo.custom.HouseInspectBO;
import com.example.test.dto.HouseInspectDTO;
import com.example.test.dao.custom.impl.HouseInspectDAOImpl;
import com.example.test.dao.custom.impl.TenantDAOImpl;
import com.example.test.dao.custom.impl.UnitDAOImpl;
import com.example.test.UserInputValidation;
import com.example.test.dto.TenantDTO;
import com.example.test.dto.UnitDTO;
import com.example.test.entity.Tenant;
import com.example.test.entity.Unit;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class AddNewHouseInspectController implements Initializable {

    @FXML
    private TextField tenantIdTxt;

    @FXML
    private ComboBox<String> livingRoomStatusCmb;

    @FXML
    private ComboBox<String> kitchenStatusCmb;

    @FXML
    private ComboBox<String> bedRoomStatusCmb;

    @FXML
    private ComboBox<String> bathRoomStatusCmb;

    @FXML
    private ComboBox<String> totalHouseStatusCmb;

    @FXML
    private TextField costForRepairTxt;

    @FXML
    private Label tenantNameLabel;

    @FXML
    private Label houseIdLabel;

    @FXML
    private Label houseTypeLabel;

    @FXML
    private Label costForRepairErrorLabel;

    ObservableList<String> houseStatus = FXCollections.observableArrayList("Select","Excellent","Good","Moderate","Damaged");
    private TenantDTO tenant;
    private final HouseInspectBO houseInspectBO = (HouseInspectBO) BOFactory.getInstance().getBO(BOFactory.BOType.HOUSEINSPECT);


    @FXML
    void addBtnOnAction(ActionEvent event) {

        if( tenant==null || tenant.getTenantId()==null){
            return;
        }

        String livingRoomStatus = livingRoomStatusCmb.getSelectionModel().getSelectedItem();
        String kitchenStatus = kitchenStatusCmb.getSelectionModel().getSelectedItem();
        String bedRoomStatus = bedRoomStatusCmb.getSelectionModel().getSelectedItem();
        String bathRoomStatus = bathRoomStatusCmb.getSelectionModel().getSelectedItem();
        String totalHouseStatus = totalHouseStatusCmb.getSelectionModel().getSelectedItem();
        String costForRepair = costForRepairTxt.getText();
        boolean repairCostValidation = UserInputValidation.checkDecimalValidation(costForRepair);


        if(livingRoomStatus.isEmpty() || livingRoomStatus.equals("Select") || kitchenStatus.isEmpty() || kitchenStatus.equals("Select") ||
                bedRoomStatus.isEmpty() || bedRoomStatus.equals("Select") || bathRoomStatus.isEmpty() || bathRoomStatus.equals("Select") || totalHouseStatus.isEmpty() || totalHouseStatus.equals("Select")){

            notification("Select the status of all house parts in order to proceed with saving the new inspection");
            return;
        }

        else if(!costForRepair.isEmpty() && !repairCostValidation){

            costForRepairErrorLabel.setText("This is not a valid input for this field");
            return;
        }

        if(totalHouseStatus.equals("Damaged") && costForRepair.isEmpty()){

            notification("Please Enter Estimated Cost Of Repair For This Damaged House");

            costForRepairTxt.setStyle("-fx-border-color: #E53935");
            return;
        }
        else{
            costForRepairTxt.setStyle("-fx-border-color: #a4b0be");
        }

        HouseInspectDTO houseInspectDto = new HouseInspectDTO();

        houseInspectDto.setLivingRoomStatus(livingRoomStatus);
        houseInspectDto.setBedRoomsStatus(bedRoomStatus);
        houseInspectDto.setBathRoomsStatus(bathRoomStatus);
        houseInspectDto.setTotalHouseStatus(totalHouseStatus);
        houseInspectDto.setKitchenStatus(kitchenStatus);
        houseInspectDto.setHouseId(tenant.getHouseId());
        houseInspectDto.setTenantId(tenant.getTenantId());

        if(!totalHouseStatus.equals("Damaged")){

            houseInspectDto.setEstimatedCostForRepair("N/A");
            houseInspectDto.setIsPaymentDone("N/A");
        }
        else{

            houseInspectDto.setEstimatedCostForRepair(costForRepair);
            houseInspectDto.setIsPaymentDone("Not Yet");
        }

        try {
            String response = houseInspectBO.add(houseInspectDto);
            notification(response);

            clean();

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            System.err.println("Error while adding new house inspection: " + e.getMessage());
            notification("An error occurred while adding new house inspection. Please try again or contact support.");
        }
    }


    @FXML
    void cancelBtnOnAction(ActionEvent event) {

        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.close();
    }


    @FXML
    void totalHouseStatusCmbOnAction(ActionEvent event) {

        if(!totalHouseStatusCmb.getSelectionModel().getSelectedItem().equals("Damaged")){
            costForRepairTxt.setDisable(true);
        }
        else{
            costForRepairTxt.setDisable(false);
        }
    }


    @FXML
    void searchIconClicked(MouseEvent event) {

        String tenantId = tenantIdTxt.getText();

        if (!tenantId.isEmpty()) {
            try {
                tenant = houseInspectBO.getTenantDetails(tenantId);

                if (tenant.getName() == null) {
                    clean();
                    notification("Please Enter Correct Tenant ID");
                    return;
                }

                tenantNameLabel.setText(tenant.getName());
                houseIdLabel.setText(tenant.getHouseId());
                UnitDTO unitDTO = houseInspectBO.getUnitDetails(tenant.getHouseId());
                houseTypeLabel.setText(unitDTO.getHouseType());
            }
            catch (SQLException | ClassNotFoundException e) {
                e.printStackTrace();
                System.err.println("Error while searching tenant details: " + e.getMessage());
                notification("An error occurred while searching tenant details. Please try again or contact support.");
            }
        }
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        totalHouseStatusCmb.setItems(houseStatus);
        totalHouseStatusCmb.getSelectionModel().selectFirst();
        livingRoomStatusCmb.setItems(houseStatus);
        livingRoomStatusCmb.getSelectionModel().selectFirst();
        bedRoomStatusCmb.setItems(houseStatus);
        bedRoomStatusCmb.getSelectionModel().selectFirst();
        bathRoomStatusCmb.setItems(houseStatus);
        bathRoomStatusCmb.getSelectionModel().selectFirst();
        kitchenStatusCmb.setItems(houseStatus);
        kitchenStatusCmb.getSelectionModel().selectFirst();
    }


    public void clean(){

        tenantIdTxt.clear();
        tenantNameLabel.setText("");
        houseIdLabel.setText("");
        houseTypeLabel.setText("");
        totalHouseStatusCmb.getSelectionModel().selectFirst();
        livingRoomStatusCmb.getSelectionModel().selectFirst();
        bedRoomStatusCmb.getSelectionModel().selectFirst();
        bathRoomStatusCmb.getSelectionModel().selectFirst();
        kitchenStatusCmb.getSelectionModel().selectFirst();
        costForRepairTxt.setDisable(false);
        costForRepairTxt.clear();
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
