package com.example.test.controller;

import com.example.test.bo.BOFactory;
import com.example.test.bo.custom.SignInBO;
import com.example.test.dto.UserDTO;
import com.example.test.dto.UserValidationDTO;
import com.example.test.LoginDetails;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Optional;

public class SignInController {

    public Button forgotbtn;
    @FXML
    private Pane pane1;

    @FXML
    private TextField userName;

    @FXML
    private TextField password;

    @FXML
    private Button signUpbtn;

    @FXML
    private Button signInbtn;

    @FXML
    private Button exitbtn;

    private SignInBO signInBO = (SignInBO) BOFactory.getInstance().getBO(BOFactory.BOType.SIGNIN);


    @FXML
    void signUpOnAction(ActionEvent event) throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/SignUp.fxml"));
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root);
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();

        TranslateTransition translateTransition = new TranslateTransition(Duration.millis(1000));
        translateTransition.setFromX(0);
        translateTransition.setToX(-300);
        translateTransition.setCycleCount(1);
        translateTransition.setAutoReverse(true);

        translateTransition.setNode(stage.getScene().getRoot());

        translateTransition.setOnFinished(e -> {
            stage.setScene(scene);
            stage.centerOnScreen();
        });

        translateTransition.play();

    }



    public void forgotPasswordOnAction(ActionEvent event) {

        String uName = userName.getText();

        if(uName.equals("")){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Incomplete Data");
            alert.setHeaderText("Please enter data to require field");
            alert.setContentText("You should enter your User Name to get back your password");
            alert.showAndWait();
        }

        else {

            try {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/ForgotPasswordQuestions.fxml"));
                Parent root = fxmlLoader.load();

                ForgotPasswordQuestionsController forgotPasswordQuestionsController = fxmlLoader.getController();
                forgotPasswordQuestionsController.setUserName(uName);
                Scene scene = new Scene(root);
                Stage stage = new Stage();
                stage.initStyle(StageStyle.UNDECORATED);
                stage.setScene(scene);
                stage.setX(70);
                stage.setY(55);
                stage.show();


            } catch (Exception e) {

                e.printStackTrace();

            }
        }

    }


    public void signInOnAction(ActionEvent event) throws IOException {

        String uName = userName.getText();
        String pWord = password.getText();

        if(uName.equals("") || pWord.equals("")){

            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Incomplete Data");
            alert.setHeaderText("Please fill in all required fields");
            alert.setContentText("You have not entered all the required information. Please fill in all the necessary fields before submitting.");
            alert.showAndWait();
        }
        else{

            UserDTO userDTO = new UserDTO();
            userDTO.setUserName(uName);
            userDTO.setPassword(pWord);

            try {
                String response = signInBO.signInAuthentication(userDTO);
                if(response.equals("All Correct")){

                    LoginDetails loginDetails = LoginDetails.getInstance(uName,pWord);
                    System.out.println(LoginDetails.getUserName());

                    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Home.fxml"));
                    Parent root = fxmlLoader.load();
                    Scene scene = new Scene(root);
                    Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();

                    TranslateTransition translateTransition = new TranslateTransition(Duration.millis(1000));
                    translateTransition.setFromX(0);
                    translateTransition.setToX(-300);
                    translateTransition.setCycleCount(1);
                    translateTransition.setAutoReverse(true);

                    translateTransition.setNode(stage.getScene().getRoot());

                    translateTransition.setOnFinished(e -> {
                        stage.setScene(scene);
                        stage.centerOnScreen();
                    });

                    translateTransition.play();

                }
                else{

                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Information");
                    alert.setHeaderText("Double check user name and password");
                    alert.setContentText(response);
                    alert.showAndWait();
                }
            }
            catch (Exception e){
                e.printStackTrace();
            }

        }

        clear();

    }


    public void exitOnAction(ActionEvent event) {

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText("Are you sure you want to exit?");
        alert.setContentText("Click OK to confirm, or Cancel to abort.");

        Optional<ButtonType> result = alert.showAndWait();

        if(result.isPresent() && result.get() == ButtonType.OK){

            System.exit(0);
        }
        else{
            return;
        }
    }


    public void clear(){

        userName.setText("");
        password.setText("");
    }


    public void setData(UserDTO userDto){

        userName.setText(userDto.getUserName());
        password.setText(userDto.getPassword());

    }

}
