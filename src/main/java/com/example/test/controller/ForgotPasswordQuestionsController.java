package com.example.test.controller;

import com.example.test.bo.BOFactory;
import com.example.test.bo.custom.SignInBO;
import com.example.test.dto.UserDTO;
import com.example.test.dto.UserValidationDTO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import lombok.Setter;

import java.io.IOException;
import java.sql.SQLException;

public class ForgotPasswordQuestionsController {

    @FXML
    private TextField fquestion;

    @FXML
    private TextField squestion;

    @FXML
    private TextField tquestion;

    @FXML
    private Button submit;

    @FXML
    private Button back;

    private Stage stage;

    @Setter
    private String userName;
    private SignInBO signInBO = (SignInBO) BOFactory.getInstance().getBO(BOFactory.BOType.SIGNIN);


    @FXML
    void backOnAction(ActionEvent event) {

        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.close();
    }


    @FXML
    void submitOnAction(ActionEvent event) throws IOException {

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

           UserValidationDTO userValidationDto = new UserValidationDTO(fAnswer,sAnswer,tAnswer,userName);

           try {
               String result = signInBO.getPassword(userValidationDto);

               if(!result.equals("This User Name does not exit") && !result.equals("Something wrong with getting password back, try again later") && !result.equals("Can't get password because your answers are incorrect")){

                   UserDTO userDTO = new UserDTO();
                   userDTO.setUserName(userName);
                   userDTO.setPassword(result);

                   FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/SignIn.fxml"));
                   Parent root = fxmlLoader.load();

                   SignInController controller = fxmlLoader.getController();
                   controller.setData(userDTO);

                   Scene scene = new Scene(root);
                   stage = (Stage)((Node)event.getSource()).getScene().getWindow();
                   stage.setScene(scene);
                   stage.centerOnScreen();
               }
               else{

                   stage = (Stage)((Node)event.getSource()).getScene().getWindow();
                   stage.close();

                   Alert alert = new Alert(Alert.AlertType.INFORMATION);
                   alert.setTitle("Information");
                   alert.setHeaderText("Something wrong with getting your password");
                   alert.setContentText(result);
                   alert.showAndWait();
               }
           }
           catch (Exception e){
               e.printStackTrace();
           }

        }
        clear();
    }


    public void clear(){

        fquestion.setText("");
        squestion.setText("");
        tquestion.setText("");
    }

}








