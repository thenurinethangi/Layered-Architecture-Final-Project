package com.example.test.controller;

import com.example.test.LoginDetails;
import com.example.test.UserInputValidation;
import com.example.test.bo.BOFactory;
import com.example.test.bo.custom.UserProfileBO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.sql.SQLException;

public class UserProfileEditController {

    public AnchorPane bodyPane;
    @FXML
    private Button savebtn;

    @FXML
    private TextField fnametxt;

    @FXML
    private TextField unametxt;

    @FXML
    private TextField emailtxt;

    @FXML
    private TextField pwordtxt;

    @FXML
    private Button exitbtn;


    private UserProfileBO userProfileBO = (UserProfileBO) BOFactory.getInstance().getBO(BOFactory.BOType.USERPROFILE);


    @FXML
    void exitOnAction(ActionEvent event) {

        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/UserProfile.fxml"));
            Parent root = fxmlLoader.load();
            Scene scene = new Scene(root);
            Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();


        }
        catch (Exception e){

            e.printStackTrace();
        }


    }

    @FXML
    void saveOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {

        String userName = unametxt.getText();
        String name = fnametxt.getText();
        String email = emailtxt.getText();
        String password = pwordtxt.getText();

        String[] ar = {userName,name,email,password};
        String[] data  = new String[0];
        String[] column = new String[0];

        for (int i = 0; i < ar.length; i++) {
            if(!ar[i].isEmpty()){

                if(i==0){
                    Boolean bool = UserInputValidation.checkUserNameValidation(ar[i]);
                    System.out.println(bool);
                    if(!bool) {
                        Alert alert = new Alert(Alert.AlertType.WARNING, "Your user name not valid, user name should have at least 7 characters including special characters and numbers");
                        alert.showAndWait();
                        clear();
                        return;
                    }
                }
                else if(i==1){
                    Boolean bool = UserInputValidation.checkNameValidation(ar[i]);
                    System.out.println(bool);
                    if(!bool) {
                        Alert alert = new Alert(Alert.AlertType.WARNING, "This name not valid, please enter your full name");
                        alert.showAndWait();
                        clear();
                        return;
                    }
                }
                else if(i==2){
                    Boolean bool = UserInputValidation.checkEmailValidation(ar[i]);
                    System.out.println(bool);
                    if(!bool) {
                        Alert alert = new Alert(Alert.AlertType.WARNING, "This email not valid, please enter valid email address,");
                        alert.showAndWait();
                        clear();
                        return;
                    }
                }
                else if(i==3){
                    Boolean bool = UserInputValidation.checkPasswordValidation(ar[i]);
                    System.out.println(bool);
                    if(!bool) {
                        Alert alert = new Alert(Alert.AlertType.WARNING, "This password not valid, password should have at least 7 characters including special characters and numbers");
                        alert.showAndWait();
                        clear();
                        return;
                    }

                }
            }
        }


        for (int i = 0; i < ar.length; i++) {
            if(!ar[i].isEmpty()){
                data = grow(data,ar[i]);

                if(i==0){
                    column = grow(column,"userName");
                }
                else if(i==1){
                    column = grow(column,"name");
                }
                else if(i==2){
                    column = grow(column,"email");
                }
                else if(i==3){
                    column = grow(column,"password");
                }
            }

        }

        int count = data.length;

        switch (count){


            case 1 -> {
                String result = userProfileBO.changeUserDetailsOne(data, column);
                Alert alert = new Alert(Alert.AlertType.INFORMATION, result);
                alert.showAndWait();
                if(result.equals("Successfully updated your profile") && column[0].equals("userName")){
                    LoginDetails.setUserName(data[0]);
                }
            }
            case 2 -> {
                String result = userProfileBO.changeUserDetailsTwo(data, column);
                Alert alert = new Alert(Alert.AlertType.INFORMATION, result);
                alert.showAndWait();
                int index = 0;
                boolean bool = false;
                for (int i = 0; i < column.length; i++) {
                    if(column[i].equals("userName")){
                        index = i;
                        bool = true;

                    }
                }
                if(result.equals("Successfully updated your profile") && bool){
                    LoginDetails.setUserName(data[index]);
                }
            }
            case 3 -> {
                String result = userProfileBO.changeUserDetailsThree(data, column);
                Alert alert = new Alert(Alert.AlertType.INFORMATION, result);
                alert.showAndWait();

                int index = 0;
                boolean bool = false;
                for (int i = 0; i < column.length; i++) {
                    if(column[i].equals("userName")){
                        index = i;
                        bool = true;

                    }
                }
                if(result.equals("Successfully updated your profile") && bool){
                    LoginDetails.setUserName(data[index]);
                }
            }
            case 4 -> {
                String result = userProfileBO.changeUserDetailsFour(data, column);
                Alert alert = new Alert(Alert.AlertType.INFORMATION, result);
                alert.showAndWait();

                int index = 0;
                boolean bool = false;
                for (int i = 0; i < column.length; i++) {
                    if(column[i].equals("userName")){
                        index = i;
                        bool = true;

                    }
                }
                if(result.equals("Successfully updated your profile") && bool){
                    LoginDetails.setUserName(data[index]);
                }
            }
            default -> {
                return;
            }
        }

        clear();


    }



    public String[] grow(String[] ar,String x){

        String[] temp = new String[ar.length+1];

        for (int i = 0; i < ar.length; i++) {

            temp[i] = ar[i];
        }

        temp[temp.length-1] = x;
        ar = temp;
        return ar;

    }

    public void clear(){

        unametxt.setText("");
        fnametxt.setText("");
        emailtxt.setText("");
        pwordtxt.setText("");
    }

}





