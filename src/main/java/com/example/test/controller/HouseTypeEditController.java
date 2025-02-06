package com.example.test.controller;

import com.example.test.bo.BOFactory;
import com.example.test.bo.custom.HouseTypeBO;
import com.example.test.dto.HouseTypeDTO;
import com.example.test.dao.custom.impl.HouseTypeDAOImpl;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class HouseTypeEditController implements Initializable {


    @FXML
    private TextField houseTypetxt;

    @FXML
    private TextField descriptiontxt;

    @FXML
    private Button clearbtn;

    @FXML
    private Button canclebtn;

    @FXML
    private Button editbtn;

    private static HouseTypeDTO houseTypeDetails;
    private HouseTypeBO houseTypeBO = (HouseTypeBO) BOFactory.getInstance().getBO(BOFactory.BOType.HOUSETYPE);



    @FXML
    void cancleOnAction(ActionEvent event) {

        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.close();

    }


    @FXML
    void clearOnAction(ActionEvent event) {

        houseTypetxt.setText("");
        descriptiontxt.setText("");
    }


    @FXML
    void editOnAction(ActionEvent event) {

        String hType = houseTypetxt.getText();
        String desc = descriptiontxt.getText();

        HouseTypeDTO houseTypeDto = new HouseTypeDTO(hType,desc);

        if(hType.isEmpty() || desc.isEmpty()){
            notification("Enter the new details to update the House Type");
        }
        else if(hType.equals(houseTypeDetails.getHouseType()) && desc.equals(houseTypeDetails.getDescription())){
            return;
        }
        else if(!hType.equals(houseTypeDetails.getHouseType()) && !desc.equals(houseTypeDetails.getDescription())){

            try {
                String response = houseTypeBO.Update(houseTypeDto, houseTypeDetails);
                notification(response);

            } catch (SQLException | ClassNotFoundException e) {
                e.printStackTrace();
                System.err.println("Error while editing the house type: " + e.getMessage());
                notification("An error occurred while editing the house type. Please try again or contact support.");
            }

        }
        else if(hType.equals(houseTypeDetails.getHouseType()) && !desc.equals(houseTypeDetails.getDescription())){

            try {
                HouseTypeDTO houseTypeDTO = new HouseTypeDTO("",desc);
                String response = houseTypeBO.Update(houseTypeDTO, houseTypeDetails);
                notification(response);

            }
            catch (SQLException |ClassNotFoundException e) {
                e.printStackTrace();
                System.err.println("Error while editing the house type: " + e.getMessage());
                notification("An error occurred while editing the house type. Please try again or contact support.");
            }

        }
        else if(!hType.equals(houseTypeDetails.getHouseType()) && desc.equals(houseTypeDetails.getDescription())){

            try {
                HouseTypeDTO houseTypeDTO = new HouseTypeDTO(hType,"");
                String response = houseTypeBO.Update(houseTypeDTO, houseTypeDetails);;
                notification(response);

            }
            catch (SQLException |ClassNotFoundException e) {
                e.printStackTrace();
                System.err.println("Error while editing the house type: " + e.getMessage());
                notification("An error occurred while editing the house type. Please try again or contact support.");
            }

        }

    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        if(houseTypeDetails !=null) {
            houseTypetxt.setText(houseTypeDetails.getHouseType());
            descriptiontxt.setText(houseTypeDetails.getDescription());

            try {
                boolean isUsing = houseTypeBO.isHouseTypeUsing(houseTypeDetails.getHouseType());

                houseTypetxt.setDisable(isUsing);

            } catch (SQLException e) {
                e.printStackTrace();
                System.err.println("Error while setting house type details: " + e.getMessage());
                notification("An error occurred while setting house type details. Please try again or contact support.");
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
                System.err.println("Error while setting house type details: " + e.getMessage());
                notification("An error occurred while setting house type details. Please try again or contact support.");
            }
        }
        else {
            return;
        }

    }

    public static void setData(HouseTypeDTO houseTypeDto){

        houseTypeDetails = houseTypeDto;

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
