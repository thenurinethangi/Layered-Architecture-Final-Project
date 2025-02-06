package com.example.test.controller;

import com.example.test.bo.BOFactory;
import com.example.test.bo.custom.UserProfileBO;
import com.example.test.dto.UserDTO;
import com.example.test.dao.custom.impl.UserProfileDAOImpl;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class UserProfileDetailsController implements Initializable {

    @FXML
    private AnchorPane bodyPane;

    @FXML
    private Label name;

    @FXML
    private Label userName;

    @FXML
    private Label email;

    @FXML
    private Label password;

    @FXML
    private Button exitbtn;

    private UserDTO userDto;

    private UserProfileBO userProfileBO = (UserProfileBO) BOFactory.getInstance().getBO(BOFactory.BOType.USERPROFILE);


    @FXML
    void exitOnAction(ActionEvent event) {

        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.close();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        getUserDetails();

        if(userDto !=null){

            userName.setText(userDto.getUserName());
            name.setText(userDto.getName());
            email.setText(userDto.getEmail());
            password.setText(userDto.getPassword());

        }
        else {
            return;
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
