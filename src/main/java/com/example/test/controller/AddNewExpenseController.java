package com.example.test.controller;

import com.example.test.bo.BOFactory;
import com.example.test.bo.custom.ExpenseBO;
import com.example.test.dto.ExpenseDTO;
import com.example.test.dao.custom.impl.ExpenseDAOImpl;
import com.example.test.UserInputValidation;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;

import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class AddNewExpenseController implements Initializable {

    @FXML
    private Button addBtn;

    @FXML
    private Button cancelBtn;

    @FXML
    private TextField descriptionTxt;

    @FXML
    private Label expenseNoLabel;

    @FXML
    private TextField amountTxt;

    @FXML
    private TextField requestIdTxt;

    @FXML
    private ListView<String> requestIdList;

    @FXML
    private Label descriptionErrorLabel;

    @FXML
    private Label amountErrorLabel;


    private final ExpenseBO expenseBO = (ExpenseBO) BOFactory.getInstance().getBO(BOFactory.BOType.EXPENSE);
    private ObservableList<String> requestIds = FXCollections.observableArrayList();


    @FXML
    void addBtnOnAction(ActionEvent event) {

        String expenseNo = expenseNoLabel.getText();
        String description = descriptionTxt.getText();
        String amount = amountTxt.getText();
        String date = String.valueOf(LocalDate.now());
        String maintenanceRequestNo = requestIdTxt.getText();

        if(description.isEmpty() || amount.isEmpty() || maintenanceRequestNo.isEmpty()){

            notification("Please Fill All Field To Add Expense");
            return;
        }

        boolean b1 = UserInputValidation.checkDecimalValidation(amount);
        if(!b1){
           amountErrorLabel.setText("Invalid input for this field!");
        }
        else{
           amountErrorLabel.setText("");
        }
        boolean b2 = UserInputValidation.checkTextValidation(description);
        if(!b2){
            descriptionErrorLabel.setText("Invalid input for this field!");
        }
        else{
            descriptionTxt.setText("");
        }
        boolean b3 = false;
        try {
            b3 = expenseBO.checkEnteredRequestIdValid(maintenanceRequestNo);
            if(!b3){

                notification("Maintenance Request Number Is Not Valid.");
            }

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            System.err.println("Error while checking maintenance request numbers valid: " + e.getMessage());
            notification("An error occurred while checking maintenance request numbers valid, Please try again or contact support.");
        }

        if(b1 && b2 && b3){

            ExpenseDTO newExpense = new ExpenseDTO(expenseNo,description,Double.parseDouble(amount),maintenanceRequestNo,date);
            try {
                String response = expenseBO.addNewExpenseForMaintenanceRequest(newExpense);
                notification(response);

            } catch (SQLException | ClassNotFoundException e) {
                e.printStackTrace();
                System.err.println("Error while adding new expense: " + e.getMessage());
                notification("An error occurred while adding new expense, Please try again or contact support.");
            }
        }

    }

    @FXML
    void cancelBtnOnAction(ActionEvent event) {

        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.close();
    }


    @FXML
    void requestIdListOnMouseClicked(MouseEvent event) {

        requestIdTxt.setText(requestIdList.getSelectionModel().getSelectedItem());
        requestIdList.getItems().clear();
    }


    @FXML
    void requestIdOnKeyPressed(KeyEvent event) {

        String input = requestIdTxt.getText();

        try {
            requestIds = expenseBO.getRequestIdSuggestions(input);
            requestIdList.setItems(requestIds);
        }
        catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            System.err.println("Error while getting maintenance request id suggestions: " + e.getMessage());
            notification("An error occurred while getting maintenance request id suggestions, Please try again or contact support.");
        }

        if(input.isEmpty() && !requestIds.isEmpty()){
            requestIds.clear();
        }

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        generateNewExpenseNo();

    }


    public void generateNewExpenseNo(){

        try {
            String id = expenseBO.generateNewId();
            expenseNoLabel.setText(id);

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            System.err.println("Error while generating the new expense number: " + e.getMessage());
            notification("An error occurred while generating the new expense number, Please try again or contact support.");
        }
    }


    public void clean(){

        descriptionTxt.clear();
        amountTxt.clear();
        requestIdTxt.clear();
        requestIdList.getItems().clear();
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
