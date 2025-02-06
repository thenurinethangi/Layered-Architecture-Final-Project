package com.example.test.controller;

import com.example.test.bo.BOFactory;
import com.example.test.bo.custom.UnitBO;
import com.example.test.dto.FloorDTO;
import com.example.test.dto.HouseTypeDTO;
import com.example.test.dto.UnitDTO;
import com.example.test.entity.Floor;
import com.example.test.entity.HouseType;
import com.example.test.dao.custom.impl.FloorDAOImpl;
import com.example.test.dao.custom.impl.HouseTypeDAOImpl;
import com.example.test.UserInputValidation;
import com.example.test.view.tdm.UnitTM;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class AddNewUnitController implements Initializable {

    @FXML
    public Button editbtn;

    @FXML
    private Label houseIdLable;

    @FXML
    private TextField bedRoomtxt;

    @FXML
    private ComboBox<String> rentOrSellCmb;

    @FXML
    private ComboBox<String> statusCmb;

    @FXML
    private ComboBox<String> houseTypeCmb;

    @FXML
    private ComboBox<Integer> floorNoCmb;

    @FXML
    private TextField bathRoomtxt;

    @FXML
    private TextField totalValuetxt;

    @FXML
    private TextField securityChargetxt;

    @FXML
    private TextField monthlyRenttxt;

    @FXML
    private Button addbtn;

    @FXML
    private Button clearbtn;

    @FXML
    private Button canclebtn;

    @FXML
    private Label bedRoomNoErrorMsg;

    @FXML
    private Label totalValueErrorMsg;

    @FXML
    private Label securityChargeErrorMsg;

    @FXML
    private Label monthlyRentErrorMsg;

    @FXML
    private Label bathRoomNoErrorMsg;

    private String newHouseId;
    private UnitBO unitBO = (UnitBO) BOFactory.getInstance().getBO(BOFactory.BOType.UNIT);
    private Integer floorNo;
    private String houseType;
    private String status;
    private String rentOrSell;
    private UnitTM selectedUnitTM;


    @FXML
    void addOnAction(ActionEvent event) {

        String id = houseIdLable.getText();
        String bedrooms = bedRoomtxt.getText();
        String bathroom = bathRoomtxt.getText();
        String totalValue = totalValuetxt.getText();
        String securityCharge = securityChargetxt.getText();
        String monthlyRent = monthlyRenttxt.getText();


        if(rentOrSell!=null && rentOrSell.equals("Rent")){
            if(!bedrooms.isEmpty() && !bathroom.isEmpty() && securityCharge!=null && monthlyRent!=null && floorNo!=null && houseType!=null && status!=null){
                if(UserInputValidation.checkNumberLessThanTenValidation(bedrooms) && UserInputValidation.checkNumberLessThanTenValidation(bathroom) && UserInputValidation.checkDecimalValidation(securityCharge) && UserInputValidation.checkDecimalValidation(monthlyRent)){

                    handleInputValidationErrors(bedrooms,bathroom,securityCharge,monthlyRent,null);

                    UnitDTO newUnitDTO = new UnitDTO();
                    newUnitDTO.setHouseId(id);
                    newUnitDTO.setBedroom(Integer.parseInt(bedrooms));
                    newUnitDTO.setBathroom(Integer.parseInt(bathroom));
                    newUnitDTO.setRentOrBuy(rentOrSell);
                    newUnitDTO.setTotalValue("N/A");
                    newUnitDTO.setSecurityCharge(securityCharge);
                    newUnitDTO.setMonthlyRent(monthlyRent);
                    newUnitDTO.setStatus(status);
                    newUnitDTO.setHouseType(houseType);
                    newUnitDTO.setFloorNo(floorNo);

                    try {
                        String response = unitBO.add(newUnitDTO);//
                        notification(response);
                        if(response.equals("successfully add new unit to the system")){
                            setNewHouseId();
                        }
                    }
                    catch (SQLException |ClassNotFoundException e) {
                        e.printStackTrace();
                        System.err.println("Error while adding the new unit: " + e.getMessage());
                        notification("An error occurred while adding the new unit, Please try again or contact support.");
                    }
                    clean();
                }
                else{
                    handleInputValidationErrors(bedrooms,bathroom,securityCharge,monthlyRent,null);
                }
            }
            else{
                handleInputValidationErrors(bedrooms,bathroom,securityCharge,monthlyRent,null);
                notification("Please provide all the details for the new unit to be added to the system");
            }
        }

        else if(rentOrSell!=null && rentOrSell.equals("Sell")){

            if(!bedrooms.isEmpty() && !bathroom.isEmpty() && totalValue!=null && floorNo!=null && houseType!=null && status!=null){
                if(UserInputValidation.checkNumberLessThanTenValidation(String.valueOf(bedrooms)) && UserInputValidation.checkNumberLessThanTenValidation(String.valueOf(bathroom)) && UserInputValidation.checkDecimalValidation(totalValue)){

                    handleInputValidationErrors(bedrooms,bathroom,null,null,totalValue);

                    UnitDTO newUnitDTO = new UnitDTO();
                    newUnitDTO.setHouseId(id);
                    newUnitDTO.setBedroom(Integer.parseInt(bedrooms));
                    newUnitDTO.setBathroom(Integer.parseInt(bathroom));
                    newUnitDTO.setRentOrBuy(rentOrSell);
                    newUnitDTO.setTotalValue(totalValue);
                    newUnitDTO.setSecurityCharge("N/A");
                    newUnitDTO.setMonthlyRent("N/A");
                    newUnitDTO.setStatus(status);
                    newUnitDTO.setHouseType(houseType);
                    newUnitDTO.setFloorNo(floorNo);

                    try {
                        String response = unitBO.add(newUnitDTO);
                        notification(response);
                        if(response.equals("successfully add new unit to the system")){
                            setNewHouseId();
                        }
                    }
                    catch (SQLException |ClassNotFoundException e) {
                        e.printStackTrace();
                        System.err.println("Error while adding the new unit: " + e.getMessage());
                        notification("An error occurred while adding the new unit, Please try again or contact support.");
                    }
                    clean();
                }
                else{
                    handleInputValidationErrors(bedrooms,bathroom,null,null,totalValue);
                }
            }
            else{
                handleInputValidationErrors(bedrooms,bathroom,null,null,totalValue);
               notification("Please provide all the details for the new unit to be added to the system");
            }
        }
        else{
            notification("Please provide all the details for the new unit to be added to the system");
        }
    }


    private void notification(String message) {

        Notifications notifications = Notifications.create();
        notifications.title("Notification");
        notifications.text(message);
        notifications.hideCloseButton();
        notifications.hideAfter(Duration.seconds(5));
        notifications.position(Pos.CENTER);
        notifications.darkStyle();
        notifications.showInformation();
    }


    public void clean(){

        floorNo = null;
        houseType = null;
        status = null;
        rentOrSell = null;
        bedRoomtxt.setText("");
        bathRoomtxt.setText("");
        totalValuetxt.setText("");
        securityChargetxt.setText("");
        monthlyRenttxt.setText("");
        rentOrSellCmb.getSelectionModel().clearSelection();
        statusCmb.getSelectionModel().clearSelection();
        houseTypeCmb.getSelectionModel().clearSelection();
        floorNoCmb.getSelectionModel().clearSelection();

        bedRoomNoErrorMsg.setText("");
        bathRoomNoErrorMsg.setText("");
        monthlyRentErrorMsg.setText("");
        securityChargeErrorMsg.setText("");
        totalValueErrorMsg.setText("");

    }


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
    void floorNoCmbOnAction(ActionEvent event) {

        floorNo = floorNoCmb.getSelectionModel().getSelectedItem();
        System.out.println(floorNo);
    }


    @FXML
    void houseTypeCmbOnAction(ActionEvent event) {

        houseType = houseTypeCmb.getSelectionModel().getSelectedItem();
        System.out.println(houseType);
    }


    @FXML
    void rentOrSellCmbOnAction(ActionEvent event) {

        rentOrSell = rentOrSellCmb.getSelectionModel().getSelectedItem();
        System.out.println(rentOrSell);
    }


    @FXML
    void statusCmbOnAction(ActionEvent event) {

        status = statusCmb.getSelectionModel().getSelectedItem();
        System.out.println(status);
    }


    public void setItemsFloorNoCmb(){

        ObservableList<Integer> floorNumbers = FXCollections.observableArrayList();

        try{
            ObservableList<FloorDTO> allFloors = unitBO.getAllFloors();
            for(FloorDTO x : allFloors){
                floorNumbers.add(x.getFloorNo());
            }

        }
        catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            System.err.println("Error while setting the floor numbers: " + e.getMessage());
            notification("An error occurred while setting the floor numbers, Please try again or contact support.");
        }

        floorNoCmb.setItems(floorNumbers);

    }


    public void setItemsToHouseTypeCmb(){

        ObservableList<String> houseType = FXCollections.observableArrayList();

        try{
            ObservableList<HouseTypeDTO> allHouseTypes = unitBO.getAllHouseTypes();
            for(HouseTypeDTO x : allHouseTypes){
                houseType.add(x.getHouseType());
            }

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            System.err.println("Error while setting the house types: " + e.getMessage());
            notification("An error occurred while setting the house types, Please try again or contact support.");
        }

        houseTypeCmb.setItems(houseType);

    }

    public void setItemsToRentOrBuyCmb(){

        ObservableList<String> rentOrBuy = FXCollections.observableArrayList();
        rentOrBuy.addAll("Rent","Sell");
        rentOrSellCmb.setItems(rentOrBuy);

    }

    public void setItemsToStatusCmb(){

        ObservableList<String> status = FXCollections.observableArrayList();
        status.addAll("Available");
        statusCmb.setItems(status);

    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        setNewHouseId();
        setItemsToStatusCmb();
        setItemsToRentOrBuyCmb();
        setItemsToHouseTypeCmb();
        setItemsFloorNoCmb();
        editbtn.setDisable(true);

    }


    public void setNewHouseId(){

        try {
            newHouseId = unitBO.generateNewId();
            houseIdLable.setText(newHouseId);
        } catch (SQLException |ClassNotFoundException e) {
            e.printStackTrace();
            System.err.println("Error while setting the house ids: " + e.getMessage());
            notification("An error occurred while setting the house ids, Please try again or contact support.");
        }


    }

    public void setEditRowDetails(com.example.test.view.tdm.UnitTM unitTM){

        selectedUnitTM = unitTM;

        houseIdLable.setText(selectedUnitTM.getHouseId());
        bedRoomtxt.setText(String.valueOf(selectedUnitTM.getBedroom()));
        bathRoomtxt.setText(String.valueOf(selectedUnitTM.getBathroom()));
        totalValuetxt.setText(selectedUnitTM.getTotalValue());
        securityChargetxt.setText(selectedUnitTM.getSecurityCharge());
        monthlyRenttxt.setText(selectedUnitTM.getMonthlyRent());
        rentOrSellCmb.getSelectionModel().select(selectedUnitTM.getRentOrBuy());
        statusCmb.getSelectionModel().select(selectedUnitTM.getStatus());
        houseTypeCmb.getSelectionModel().select(selectedUnitTM.getHouseType());
        floorNoCmb.getSelectionModel().select(selectedUnitTM.getFloorNo());

        addbtn.setDisable(true);
        editbtn.setDisable(false);

        rentOrSell = selectedUnitTM.getRentOrBuy();
        status = selectedUnitTM.getStatus();
        houseType = selectedUnitTM.getHouseType();
        floorNo = selectedUnitTM.getFloorNo();

    }

    @FXML
    public void editOnAction(ActionEvent event) {


        String id = houseIdLable.getText();
        String bedrooms = bedRoomtxt.getText();
        String bathroom = bathRoomtxt.getText();
        String totalValue = totalValuetxt.getText();
        String securityCharge = securityChargetxt.getText();
        String monthlyRent = monthlyRenttxt.getText();


        if(rentOrSell!=null && rentOrSell.equals("Rent")){
            if(!bedrooms.isEmpty() && !bathroom.isEmpty() && securityCharge!=null && monthlyRent!=null && floorNo!=null && houseType!=null && status!=null){
                if(UserInputValidation.checkNumberLessThanTenValidation(bedrooms) && UserInputValidation.checkNumberLessThanTenValidation(bathroom) && UserInputValidation.checkDecimalValidation(securityCharge) && UserInputValidation.checkDecimalValidation(securityCharge)){

                    handleInputValidationErrors(bedrooms,bathroom,securityCharge,monthlyRent,null);

                    UnitDTO newUnitDTO = new UnitDTO();
                    newUnitDTO.setHouseId(id);
                    newUnitDTO.setBedroom(Integer.parseInt(bedrooms));
                    newUnitDTO.setBathroom(Integer.parseInt(bathroom));
                    newUnitDTO.setRentOrBuy(rentOrSell);
                    newUnitDTO.setTotalValue("N/A");
                    newUnitDTO.setSecurityCharge(securityCharge);
                    newUnitDTO.setMonthlyRent(monthlyRent);
                    newUnitDTO.setStatus(status);
                    newUnitDTO.setHouseType(houseType);
                    newUnitDTO.setFloorNo(floorNo);

                    try {
                        String response = unitBO.update(newUnitDTO);
                        notification(response);
                    }
                    catch (SQLException |ClassNotFoundException e) {
                        e.printStackTrace();
                        System.err.println("Error while updating the unit: " + e.getMessage());
                        notification("An error occurred while updating the unit: "+ newUnitDTO.getHouseId()+", Please try again or contact support.");
                    }
                    clean();
                }
                else{
                    handleInputValidationErrors(bedrooms,bathroom,securityCharge,monthlyRent,null);
                }
            }
            else{
                handleInputValidationErrors(bedrooms,bathroom,securityCharge,monthlyRent,null);
                notification("No Field can be empty");
            }
        }

        else if(rentOrSell!=null && rentOrSell.equals("Sell")){

            if(!bedrooms.isEmpty() && !bathroom.isEmpty() && totalValue!=null && floorNo!=null && houseType!=null && status!=null){
                if(UserInputValidation.checkNumberLessThanTenValidation(String.valueOf(bedrooms)) && UserInputValidation.checkNumberLessThanTenValidation(String.valueOf(bathroom)) && UserInputValidation.checkDecimalValidation(totalValue)){

                    handleInputValidationErrors(bedrooms,bathroom,null,null,totalValue);

                    UnitDTO newUnitDTO = new UnitDTO();
                    newUnitDTO.setHouseId(id);
                    newUnitDTO.setBedroom(Integer.parseInt(bedrooms));
                    newUnitDTO.setBathroom(Integer.parseInt(bathroom));
                    newUnitDTO.setRentOrBuy(rentOrSell);
                    newUnitDTO.setTotalValue(totalValue);
                    newUnitDTO.setSecurityCharge("N/A");
                    newUnitDTO.setMonthlyRent("N/A");
                    newUnitDTO.setStatus(status);
                    newUnitDTO.setHouseType(houseType);
                    newUnitDTO.setFloorNo(floorNo);

                    try {
                        String response = unitBO.update(newUnitDTO);
                        notification(response);

                        if(response.equals("successfully update the unit")){
                            Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
                            stage.close();
                        }
                    } catch (SQLException |ClassNotFoundException e) {
                        e.printStackTrace();
                        System.err.println("Error while updating the unit: " + e.getMessage());
                        notification("An error occurred while updating the unit: "+ newUnitDTO.getHouseId()+", Please try again or contact support.");
                    }

                    clean();
                }
                else{
                  handleInputValidationErrors(bedrooms,bathroom,null,null,totalValue);

                }
            }
            else{
                handleInputValidationErrors(bedrooms,bathroom,null,null,totalValue);
                notification("No Field can be empty");
            }
        }
        else{
            notification("No Field can be empty");
        }

    }


    private void handleInputValidationErrors(String bedrooms, String bathroom, String securityCharge, String monthlyRent,String totalValue) {

        if (!UserInputValidation.checkNumberLessThanTenValidation(bedrooms)) {
            bedRoomNoErrorMsg.setText("The bedroom count you provided is invalid");
        }
        else{
            bedRoomNoErrorMsg.setText("");
        }
        if (!UserInputValidation.checkNumberLessThanTenValidation(bathroom)) {
            bathRoomNoErrorMsg.setText("The bathroom count you provided is invalid");
        }
        else{
            bathRoomNoErrorMsg.setText("");
        }
        if (securityCharge != null && !UserInputValidation.checkDecimalValidation(securityCharge)) {
            securityChargeErrorMsg.setText("The security charge you provided is invalid");
        }
        else{
            securityChargeErrorMsg.setText("");
        }
        if (monthlyRent != null && !UserInputValidation.checkDecimalValidation(monthlyRent)) {
            monthlyRentErrorMsg.setText("The monthly rent you provided is invalid");
        }
        else{
            monthlyRentErrorMsg.setText("");
        }
        if (totalValue != null && !UserInputValidation.checkDecimalValidation(totalValue)) {
            totalValueErrorMsg.setText("The total value of the you provided is invalid");
        }
        else{
            totalValueErrorMsg.setText("");
        }
    }
}
