package com.example.test.controller;

import com.example.test.bo.BOFactory;
import com.example.test.bo.custom.SignUpBO;
import com.example.test.dto.UserValidationDTO;
import com.example.test.dto.UserDTO;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;

import java.io.IOException;
import java.sql.SQLException;

public class SignUpQuestionsController {

    @FXML
    private TextField fquestion;

    @FXML
    private TextField squestion;

    @FXML
    private TextField tquestion;

    @FXML
    private Button next;

    @FXML
    private Button back;

    private UserDTO dto;
    private Stage s1;

    private SignUpBO signUpBO = (SignUpBO) BOFactory.getInstance().getBO(BOFactory.BOType.SIGNUP);


    @FXML
    void backOnAction(ActionEvent event) throws IOException {

        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.close();
    }


    @FXML
    void nextOnAction(ActionEvent event) throws IOException, SQLException {

        String fAnswer = fquestion.getText();
        String sAnswer = squestion.getText();
        String tAnswer = tquestion.getText();

        if(fAnswer.equals("") || sAnswer.equals("") || tAnswer.equals("")){

            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Incomplete Data");
            alert.setHeaderText("Please answer all the questions");
            alert.setContentText("You should answer all the three questions before submit the answers");
            alert.showAndWait();
        }
        else{

            try {
                UserValidationDTO userValidationDto = new UserValidationDTO(fAnswer, sAnswer, tAnswer, dto.getUserName());

                String response = signUpBO.add(dto);
                if(response.equals("Success")){
                    boolean result = signUpBO.addUserValidationDetails(userValidationDto);
                    if(result){

                        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
                        stage.close();

                        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Splash.fxml"));
                        Parent root = fxmlLoader.load();
                        Scene scene = new Scene(root);
                        stage = s1;

                        TranslateTransition translateTransition = new TranslateTransition(Duration.millis(1000));
                        translateTransition.setFromX(0);
                        translateTransition.setToX(-300);
                        translateTransition.setCycleCount(1);
                        translateTransition.setAutoReverse(true);

                        translateTransition.setNode(s1.getScene().getRoot());

                        Stage finalStage = stage;
                        translateTransition.setOnFinished(e -> {
                            finalStage.setScene(scene);
                            finalStage.centerOnScreen();
                        });

                        translateTransition.play();

                        notification("Welcome to the Grand View Residency Apartment Management System.");
                    }
                    else{

                        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
                        stage.close();

                        notification("Please try again later");

                    }
                }
                else{
                    notification("Please try again later");
                }

            }
            catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }

        }


    }


    public void setSignUpData(UserDTO userDto, Stage stage){

        dto = userDto;
        s1 = stage;

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
