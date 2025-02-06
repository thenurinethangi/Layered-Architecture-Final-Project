package com.example.test.controller;

import com.example.test.bo.BOFactory;
import com.example.test.bo.custom.HouseTypeBO;
import com.example.test.dto.HouseTypeDTO;
import com.example.test.view.tdm.HouseTypeTM;
import com.example.test.dao.custom.impl.HouseTypeDAOImpl;
import com.example.test.UserInputValidation;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;

public class HouseTypeController implements Initializable {

    @FXML
    private Button editbtn;

    @FXML
    private Button deletebtn;

    @FXML
    private Button clearbtn;

    @FXML
    private Button addbtn;

    @FXML
    private TableView<HouseTypeTM> table;

    @FXML
    private TableColumn<HouseTypeTM, String> houseTypeColumn;

    @FXML
    private TableColumn<HouseTypeTM, String> descriptionColumn;

    @FXML
    private ComboBox<Integer> tableRowsCmb;

    @FXML
    private ComboBox<String> sortCmb;

    @FXML
    private ComboBox<String> houseTypeCmb;

    @FXML
    private Button refreshbtn;

    @FXML
    private Button searchbtn;

    @FXML
    private TextField houseTypetxt;

    @FXML
    private TextField descriptiontxt;

    @FXML
    private Label HouseTypeLabel;

    private HouseTypeBO houseTypeBO = (HouseTypeBO) BOFactory.getInstance().getBO(BOFactory.BOType.HOUSETYPE);
    private ObservableList<HouseTypeTM> tableData;
    private ObservableList<Integer> rows;
    private ObservableList<String> sort;
    private ObservableList<String> allHouseTypes;
    private String selectHouseType;



    @FXML
    void addOnAction(ActionEvent event) {

        String houseType = houseTypetxt.getText();
        String desc = descriptiontxt.getText();

        if(houseType.isEmpty() || desc.isEmpty()){

            notification("You should enter all new house type details to" +
                    " add new house type to the system");

        }
        else{
            boolean bool1 = UserInputValidation.checkTextValidation(houseType);
            boolean bool2 = UserInputValidation.checkTextValidation(desc);

            if(bool1 && bool2){

                HouseTypeDTO houseTypeDto = new HouseTypeDTO(houseType,desc);
                try {
                    String response = houseTypeBO.add(houseTypeDto);
                    notification(response);
                    clean();
                } catch (SQLException | ClassNotFoundException e) {
                    e.printStackTrace();
                    System.err.println("Error while saving new House Type: " + e.getMessage());
                    notification("An error occurred while saving the new House Type. Please try again or contact support.");
                }

            }
            else{
                if(!bool1 && bool2){
                    notification("The house type you entered is in an invalid format. Please enter a valid house type.");
                }
                else if(!bool2 && bool1){
                    notification("The description you entered is in an invalid format. Please enter a valid description.");
                }
                else if(!bool1 && !bool2){
                    notification("The house type and description you entered is in an invalid format. Please enter a valid details.");
                }
            }

        }

    }

    @FXML
    void clearOnAction(ActionEvent event) {

        houseTypetxt.setText("");
        descriptiontxt.setText("");
    }


    @FXML
    void deleteOnAction(ActionEvent event) {

        HouseTypeTM selectHouseType = table.getSelectionModel().getSelectedItem();

        if(selectHouseType==null){
            return;
        }

        try {
            boolean isUsing = houseTypeBO.isHouseTypeUsing(selectHouseType.getHouseType());

            if(isUsing){
                notification("House type: "+selectHouseType.getHouseType()+" is already in use and cannot be deleted.");
                return;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        ButtonType yesButton = new ButtonType("Yes", ButtonBar.ButtonData.YES);
        ButtonType cancelButton = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);

        Alert a1 = new Alert(Alert.AlertType.CONFIRMATION,"Are you sure you want to delete this house type?");
        a1.getButtonTypes().setAll(yesButton, cancelButton);
        Optional<ButtonType> options = a1.showAndWait();

            if(options.isPresent() && options.get()==yesButton) {

                try {
                    String response = houseTypeBO.delete(new HouseTypeDTO(selectHouseType.getHouseType(),selectHouseType.getDescription()));
                    notification(response);
                    clean();
                }
                catch (SQLException | ClassNotFoundException e) {
                    e.printStackTrace();
                    System.err.println("Error while deleting the house type: " + e.getMessage());
                    notification("An error occurred while deleting the house type. Please try again or contact support.");
                }
            }
    }



    @FXML
    void editOnAction(ActionEvent event) {

        HouseTypeTM selectHouseType = table.getSelectionModel().getSelectedItem();

        if(selectHouseType==null){
            return;
        }
        else {
            try {
                HouseTypeDTO houseTypeDto = new HouseTypeDTO(selectHouseType.getHouseType(),selectHouseType.getDescription());
                HouseTypeEditController.setData(houseTypeDto);
                System.out.println(houseTypeDto.getHouseType());

                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/HouseTypeEdit.fxml"));
                Parent root = fxmlLoader.load();
                Scene scene = new Scene(root);
                Stage stage = new Stage();
                stage.setScene(scene);
                stage.show();

            } catch (IOException e) {
                e.printStackTrace();
                System.err.println("Error while loading house type edit page: " + e.getMessage());
                notification("An error occurred while loading a house type edit page. Please try again or contact support.");
            }


        }
    }


    @FXML
    void refreshOnAction(ActionEvent event) {

        clean();
    }



    @FXML
    void searchOnAction(ActionEvent event) {

        if(selectHouseType==null){
            return;
        }
        else{
            ObservableList<HouseTypeTM> selectHouseTypeTMS = FXCollections.observableArrayList();
            for (HouseTypeTM x : tableData){
                if(x.getHouseType().equals(selectHouseType)){
                    selectHouseTypeTMS.add(x);
                }
            }
            table.setItems(selectHouseTypeTMS);
            selectHouseType = null;
        }
    }


    @FXML
    void houseTypeCmbOnAction(ActionEvent event) {

        selectHouseType = houseTypeCmb.getSelectionModel().getSelectedItem();

    }


    @FXML
    void sortCmbOnAction(ActionEvent event) {

        String text = sortCmb.getSelectionModel().getSelectedItem();

        ObservableList<HouseTypeTM> houseTypeTmsAr = tableData;

        if(text==null){
            return;
        }

        if(text.equals("Retrieve by house type (ascending)")){

            for(int j = 0; j < houseTypeTmsAr.size(); j++) {
                for (int i = 0; i < houseTypeTmsAr.size()-1; i++) {
                    if (houseTypeTmsAr.get(i).getHouseType().compareTo(houseTypeTmsAr.get(i + 1).getHouseType())>0) {
                        HouseTypeTM temp = houseTypeTmsAr.get(i);
                        houseTypeTmsAr.set(i, houseTypeTmsAr.get(i + 1));
                        houseTypeTmsAr.set((i + 1), temp);

                    }
                }
            }
            table.setItems(houseTypeTmsAr);
        }
        else if(text.equals("Retrieve by house type (descending)")){

            for(int j = 0; j < houseTypeTmsAr.size(); j++) {
                for (int i = 0; i < houseTypeTmsAr.size()-1; i++) {
                    if (houseTypeTmsAr.get(i).getHouseType().compareTo(houseTypeTmsAr.get(i + 1).getHouseType())<0) {
                        HouseTypeTM temp = houseTypeTmsAr.get(i);
                        houseTypeTmsAr.set(i, houseTypeTmsAr.get(i + 1));
                        houseTypeTmsAr.set((i + 1), temp);

                    }
                }
            }
            table.setItems(houseTypeTmsAr);
        }


    }



    @FXML
    void tableRowsCmbOnAction(ActionEvent event) {

        Integer rows = tableRowsCmb.getSelectionModel().getSelectedItem();
        System.out.println(rows);

        if(rows==null) {
            return;
        }

        ObservableList<HouseTypeTM> houseTypeTMS = FXCollections.observableArrayList();
        int count = 0;

        for(HouseTypeTM x : tableData){
            if(rows==count){
                break;
            }
            count++;
            houseTypeTMS.add(x);
        }

        table.setItems(houseTypeTMS);

    }



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        houseTypeColumn.setCellValueFactory(new PropertyValueFactory<>("houseType"));
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));

        loadTableData();
        setItemsToRowsCmb();
        setItemsToSortCmb();
        setItemsToHouseTypeCmb();
    }


    public void loadTableData(){

        try {
            ObservableList<HouseTypeDTO> houseTypeDTOS = houseTypeBO.getAll();
            ObservableList<HouseTypeTM> houseTypeTMS = FXCollections.observableArrayList();
            for(HouseTypeDTO x : houseTypeDTOS){
                houseTypeTMS.add(new HouseTypeTM(x.getHouseType(),x.getDescription()));
            }
            tableData = houseTypeTMS;
            table.setItems(tableData);

        } catch (SQLException |ClassNotFoundException e) {
            e.printStackTrace();
            System.err.println("Error while loading table data: " + e.getMessage());
            notification("An error occurred while loading table data. Please try again or contact support.");

        }

    }


    public void setItemsToRowsCmb() {

        rows = FXCollections.observableArrayList();
        int count = 0;
        for(HouseTypeTM x : tableData){
            count++;
            rows.add(count);
        }
        tableRowsCmb.setItems(rows);
        tableRowsCmb.getSelectionModel().selectLast();

    }


    public void setItemsToSortCmb() {

        sort = FXCollections.observableArrayList();
        sort.addAll("Select","Retrieve by house type (ascending)","Retrieve by house type (descending)");
        sortCmb.setItems(sort);
        sortCmb.getSelectionModel().selectFirst();
    }


      public void setItemsToHouseTypeCmb(){

        getHouseTypes();
        houseTypeCmb.setItems(allHouseTypes);
        houseTypeCmb.getSelectionModel().selectFirst();

      }

      public void getHouseTypes(){

        allHouseTypes = FXCollections.observableArrayList();
        allHouseTypes.add("Select");

        for(HouseTypeTM x : tableData){
            allHouseTypes.add(x.getHouseType());
        }

      }


    public void clean(){

        loadTableData();
        setItemsToSortCmb();
        setItemsToHouseTypeCmb();
        setItemsToRowsCmb();
        houseTypetxt.clear();
        descriptiontxt.clear();
        table.getSelectionModel().clearSelection();

    }


    public void setHouseTypeTextInvisible() {

        HouseTypeLabel.setStyle("-fx-text-fill: #ffffff");
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


