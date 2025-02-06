package com.example.test.controller;

import com.example.test.bo.BOFactory;
import com.example.test.bo.custom.UserProfileBO;
import com.example.test.dto.UserDTO;
import com.example.test.dao.custom.impl.UserProfileDAOImpl;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class UserProfileController implements Initializable {

    @FXML
    private Label nameLabel;

    @FXML
    private Label emailLabel;

    @FXML
    private Button editbtn;

    @FXML
    private AnchorPane bodyPane;

    private UserDTO userDto;
    private UserProfileBO userProfileBO = (UserProfileBO) BOFactory.getInstance().getBO(BOFactory.BOType.USERPROFILE);


    @FXML
    void editOnAction(ActionEvent event) {

        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/UserProfileEdit.fxml"));
            AnchorPane anchorPane = fxmlLoader.load();
            bodyPane.getChildren().clear();
            bodyPane.getChildren().add(anchorPane);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/UserProfileDetails.fxml"));
            AnchorPane anchorPane = fxmlLoader.load();
            bodyPane.getChildren().clear();
            bodyPane.getChildren().add(anchorPane);
        }
        catch (Exception e){

            e.printStackTrace();
        }


        getUserDetails();

        if(userDto !=null){

            nameLabel.setText(userDto.getName());
            emailLabel.setText(userDto.getEmail());

        }
        else {
            nameLabel.setText("ABCD Nanayakkara");
            emailLabel.setText("abcd@gmail.com");
        }
    }


    public void getUserDetails(){

        try{

            userDto = userProfileBO.getUserDetails();

        }
        catch (Exception e){
            e.printStackTrace();
        }

    }
}
