package com.example.test.controller;

import com.example.test.SendMail;
import com.example.test.bo.BOFactory;
import com.example.test.bo.custom.HouseInspectBO;
import com.example.test.dao.custom.impl.HouseInspectDAOImpl;
import com.example.test.dao.custom.impl.PaymentDAOImpl;
import com.example.test.dao.custom.impl.TenantDAOImpl;
import com.example.test.dao.custom.impl.UnitDAOImpl;
import com.example.test.UserInputValidation;
import com.example.test.dto.HouseInspectDTO;
import com.example.test.dto.TenantDTO;
import com.example.test.dto.UnitDTO;
import com.example.test.entity.Tenant;
import com.example.test.entity.Unit;
import com.example.test.view.tdm.HouseInspectTM;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Comparator;
import java.util.Optional;
import java.util.ResourceBundle;

public class HouseInspectController implements Initializable {

    @FXML
    private Button editbtn;

    @FXML
    private TableView<HouseInspectTM> table;

    @FXML
    private TableColumn<HouseInspectTM, String> checkNoColumn;

    @FXML
    private TableColumn<HouseInspectTM, String> livingRoomStatusColumn;

    @FXML
    private TableColumn<HouseInspectTM, String> kitchenStatusColumn;

    @FXML
    private TableColumn<HouseInspectTM, String> bedRoomStatusColumn;

    @FXML
    private TableColumn<HouseInspectTM, String> bathRoomStatusColumn;

    @FXML
    private TableColumn<HouseInspectTM, String> totalHouseStatusColumn;

    @FXML
    private TableColumn<HouseInspectTM, String> tenantIdColumn;

    @FXML
    private TableColumn<HouseInspectTM, String> houseIdColumn;

    @FXML
    private TableColumn<HouseInspectTM, String> costColumn;

    @FXML
    private TableColumn<HouseInspectTM, String> isPaymentDoneColumn;

    @FXML
    private TableColumn<HouseInspectTM, String> actionColumn;

    @FXML
    private Button addNewHouseStatusCheckBtn;

    @FXML
    private ComboBox<Integer> tableRowsCmb;

    @FXML
    private Button deletebtn;

    @FXML
    private ComboBox<String> sortCmb;

    @FXML
    private Button searchbtn;

    @FXML
    private Button refreshbtn;

    @FXML
    private ComboBox<String> houseStatusCmb;

    @FXML
    private ComboBox<String> checkNoCmb;

    @FXML
    private ComboBox<String> paymentDoneCmb;

    @FXML
    private ComboBox<String> houseIdCmb;

    @FXML
    private ComboBox<String> tenantIdCmb;

    @FXML
    private TextField searchTxt;

    private ObservableList<HouseInspectTM> tableData;
    private final HouseInspectBO houseInspectBO = (HouseInspectBO) BOFactory.getInstance().getBO(BOFactory.BOType.HOUSEINSPECT);


    @FXML
    void addNewHouseStatusCheckOnAction(ActionEvent event) {

        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/AddNewHouseInspectCheck.fxml"));
            Parent root = fxmlLoader.load();
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Error while loading Add New House Inspection page: " + e.getMessage());
            notification("An error occurred while loading Add New House Inspection page. Please try again or contact support.");
        }


    }

    @FXML
    void deleteOnAction(ActionEvent event) {

        HouseInspectTM selectedHouseCheck = table.getSelectionModel().getSelectedItem();

        if(selectedHouseCheck==null){
           return;
        }

        if(selectedHouseCheck.getIsPaymentDone().equals("Not Yet")){
            return;
        }

        try {
            boolean isLastCheck = houseInspectBO.checkIfThisCheckLastCheck(new HouseInspectDTO().toDTO(selectedHouseCheck));

            if(isLastCheck){
               notification("Can't Delete Selected House Inspection, Because It's The Latest House Inspection Of That House");
            }
            else{
                ButtonType yesButton = new ButtonType("Yes", ButtonBar.ButtonData.YES);
                ButtonType cancelButton = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);

                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Confirmation");
                alert.setHeaderText("Please confirm first");
                alert.setContentText("Are you sure, Do you want to delete the selected HouseInspection? ");
                alert.getButtonTypes().setAll(yesButton, cancelButton);
                Optional<ButtonType> options = alert.showAndWait();

                if(options.isPresent() && options.get()==yesButton) {
                    String response = houseInspectBO.delete(new HouseInspectDTO().toDTO(selectedHouseCheck));
                    notification(response);
                    loadTable();
                }
                else{
                    table.getSelectionModel().clearSelection();
                }
            }

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            System.err.println("Error while checking is selected check last check of the tenant: " + e.getMessage());
            notification("An error occurred while checking is selected check last check of the tenant. Please try again or contact support.");
        }
    }



    @FXML
    void refreshOnAction(ActionEvent event) {

        clean();
    }



    @FXML
    void searchOnAction(ActionEvent event) {

        ObservableList<HouseInspectTM> searchedChecks = FXCollections.observableArrayList();

        String selectedCheckNumber = checkNoCmb.getValue();
        String selectedTenantId = tenantIdCmb.getValue();
        String selectedHouseId = houseIdCmb.getValue();
        String selectedTotalHouseStatus = houseStatusCmb.getValue();
        String selectedIsPaymentDone = paymentDoneCmb.getValue();

        boolean checkNumberSelected = selectedCheckNumber != null && !selectedCheckNumber.equals("Select");
        boolean tenantIdSelected = selectedTenantId != null && !selectedTenantId.equals("Select");
        boolean houseIdSelected = selectedHouseId != null && !selectedHouseId.equals("Select");
        boolean totalHouseStatusSelected = selectedTotalHouseStatus != null && !selectedTotalHouseStatus.equals("Select");
        boolean isPaymentDoneSelected = selectedIsPaymentDone != null && !selectedIsPaymentDone.equals("Select");


        if (checkNumberSelected) {
            ObservableList<HouseInspectTM> checksByCheckNumber = getChecksByCheckNumber(selectedCheckNumber);

            if (checksByCheckNumber.isEmpty()) {
                table.setItems(checksByCheckNumber);
            } else {
                searchedChecks.addAll(checksByCheckNumber);

                if (tenantIdSelected) {
                    searchedChecks = filterChecksByTenantId(searchedChecks, selectedTenantId);
                }

                if (houseIdSelected) {
                    searchedChecks = filterChecksByHouseId(searchedChecks, selectedHouseId);
                }

                if (totalHouseStatusSelected) {
                    searchedChecks = filterChecksByTotalHouseStatus(searchedChecks, selectedTotalHouseStatus);
                }

                if (isPaymentDoneSelected) {
                    searchedChecks = filterChecksByIsPaymentDone(searchedChecks, selectedIsPaymentDone);
                }

                table.setItems(searchedChecks);
            }

        } else if (tenantIdSelected || houseIdSelected || totalHouseStatusSelected || isPaymentDoneSelected) {
            ObservableList<HouseInspectTM> allChecks = tableData;
            searchedChecks.addAll(allChecks);

            if (tenantIdSelected) {
                searchedChecks = filterChecksByTenantId(searchedChecks, selectedTenantId);
            }

            if (houseIdSelected) {
                searchedChecks = filterChecksByHouseId(searchedChecks, selectedHouseId);
            }

            if (totalHouseStatusSelected) {
                searchedChecks = filterChecksByTotalHouseStatus(searchedChecks, selectedTotalHouseStatus);
            }

            if (isPaymentDoneSelected) {
                searchedChecks = filterChecksByIsPaymentDone(searchedChecks, selectedIsPaymentDone);
            }

            table.setItems(searchedChecks);

        }
        else {
            ObservableList<HouseInspectTM> allChecks = tableData;
            table.setItems(allChecks);
        }
    }



    private ObservableList<HouseInspectTM> getChecksByCheckNumber(String checkNumber) {
        return FXCollections.observableArrayList(
                tableData.stream()
                        .filter(check -> check.getCheckNumber().equalsIgnoreCase(checkNumber))
                        .toList()
        );
    }


    private ObservableList<HouseInspectTM> filterChecksByTenantId(ObservableList<HouseInspectTM> checks, String tenantId) {
        return FXCollections.observableArrayList(
                checks.stream()
                        .filter(check -> check.getTenantId().equalsIgnoreCase(tenantId))
                        .toList()
        );
    }


    private ObservableList<HouseInspectTM> filterChecksByHouseId(ObservableList<HouseInspectTM> checks, String houseId) {
        return FXCollections.observableArrayList(
                checks.stream()
                        .filter(check -> check.getHouseId().equalsIgnoreCase(houseId))
                        .toList()
        );
    }


    private ObservableList<HouseInspectTM> filterChecksByTotalHouseStatus(ObservableList<HouseInspectTM> checks, String totalHouseStatus) {
        return FXCollections.observableArrayList(
                checks.stream()
                        .filter(check -> check.getTotalHouseStatus().equalsIgnoreCase(totalHouseStatus))
                        .toList()
        );
    }


    private ObservableList<HouseInspectTM> filterChecksByIsPaymentDone(ObservableList<HouseInspectTM> checks, String isPaymentDone) {
        return FXCollections.observableArrayList(
                checks.stream()
                        .filter(check -> check.getIsPaymentDone().equalsIgnoreCase(isPaymentDone))
                        .toList()
        );
    }


    @FXML
    void sortCmbOnAction(ActionEvent event) {

        String sortType = sortCmb.getSelectionModel().getSelectedItem();
        ObservableList<HouseInspectTM> houseInspectTMS = FXCollections.observableArrayList(tableData);

        if (sortType == null) {
            return;
        }

        Comparator<HouseInspectTM> comparator = null;

        switch (sortType) {
            case "Check No (Ascending)":
                comparator = Comparator.comparing(HouseInspectTM::getCheckNumber);
                break;

            case "Check No (Descending)":
                comparator = Comparator.comparing(HouseInspectTM::getCheckNumber).reversed();
                break;

            case "Tenant ID (Ascending)":
                comparator = Comparator.comparing(HouseInspectTM::getTenantId);
                break;

            case "Tenant ID (Descending)":
                comparator = Comparator.comparing(HouseInspectTM::getTenantId).reversed();
                break;

            case "House ID (Ascending)":
                comparator = Comparator.comparing(HouseInspectTM::getHouseId);
                break;

            case "House ID (Descending)":
                comparator = Comparator.comparing(HouseInspectTM::getHouseId).reversed();
                break;

            default:
                break;
        }

        if (comparator != null) {
            FXCollections.sort(houseInspectTMS, comparator);
            table.setItems(houseInspectTMS);
        }
    }



    @FXML
    void tableRowsCmbOnAction(ActionEvent event) {

        Integer value = tableRowsCmb.getSelectionModel().getSelectedItem();

        if(value==null){
            return;
        }

        ObservableList<HouseInspectTM> houseInspectTMS = FXCollections.observableArrayList();

        for (int i=0; i<value; i++){
            houseInspectTMS.add(tableData.get(i));
        }

        table.setItems(houseInspectTMS);
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        setTableColumns();
        setTableColumnsValue();
        setRowCmbValues();
        setTenantIdCmbValues();
        setTenantIdCmbValues();
        setHouseIdCmbValues();
        setCheckNoCmbValues();
        setHouseStatusCmbValues();
        setPaymentDoneCmbValues();
        setSortCmbValues();
        tableSearch();
    }


    public void tableSearch() {

        FilteredList<HouseInspectTM> filteredData = new FilteredList<>(tableData, b -> true);

        searchTxt.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(check -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                String lowerCaseFilter = newValue.toLowerCase();

                if (check.getCheckNumber().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else if (check.getLivingRoomStatus().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else if (check.getKitchenStatus().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else if (check.getBedRoomsStatus().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else if (check.getBathRoomsStatus().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else if (check.getTotalHouseStatus().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else if (check.getTenantId().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else if (check.getHouseId().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else if (check.getEstimatedCostForRepair().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else if (check.getIsPaymentDone().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                }
                return false;
            });
        });

        SortedList<HouseInspectTM> sortedData = new SortedList<>(filteredData);

        sortedData.comparatorProperty().bind(table.comparatorProperty());

        table.setItems(sortedData);
    }



    public void setSortCmbValues(){

        ObservableList<String> sortTypes = FXCollections.observableArrayList("Sort By","Check No (Ascending)","Check No (Descending)","Tenant ID (Ascending)","Tenant ID (Descending)","House ID (Ascending)","House ID (Descending)");
        sortCmb.setItems(sortTypes);
        sortCmb.getSelectionModel().selectFirst();
    }


    public void setPaymentDoneCmbValues(){

        ObservableList<String> isPaymentDone = FXCollections.observableArrayList("Select","N/A","Paid","Reduced from Security Deposit","Not Yet");
        paymentDoneCmb.setItems(isPaymentDone);
        paymentDoneCmb.getSelectionModel().selectFirst();

    }

    public void setHouseStatusCmbValues(){

        ObservableList<String> finalHouseStatus = FXCollections.observableArrayList("Select","Excellent","Good","Moderate","Damaged");
        houseStatusCmb.setItems(finalHouseStatus);
        houseStatusCmb.getSelectionModel().selectFirst();

    }

    public void setCheckNoCmbValues(){

        ObservableList<String> checkNos = FXCollections.observableArrayList();
        checkNos.add("Select");

        for (HouseInspectTM x : tableData){
            checkNos.add(x.getCheckNumber());
        }

        checkNoCmb.setItems(checkNos);
        checkNoCmb.getSelectionModel().selectFirst();

    }


    public void setHouseIdCmbValues(){

        ObservableList<String> houseIds = FXCollections.observableArrayList();
        houseIds.add("Select");

        try {
            ObservableList<UnitDTO> allUnitTMS = houseInspectBO.getAllUnits();

            for (UnitDTO x: allUnitTMS){
                houseIds.add(x.getHouseId());
            }
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        houseIdCmb.setItems(houseIds);
        houseIdCmb.getSelectionModel().selectFirst();

    }


    public void setTenantIdCmbValues(){

        ObservableList<String> tenantIds = FXCollections.observableArrayList();
        tenantIds.add("Select");
        try {
            TenantDAOImpl tenantDAO = new TenantDAOImpl();
            ObservableList<TenantDTO> allTenants = houseInspectBO.getAllTenants();

            for (TenantDTO x: allTenants){
                tenantIds.add(x.getTenantId());
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            System.err.println("Error while setting tenant id combo box values: " + e.getMessage());
            notification("An error occurred while setting tenant id combo box values. Please try again or contact support.");
        }

        tenantIdCmb.setItems(tenantIds);
        tenantIdCmb.getSelectionModel().selectFirst();

    }


    public void setRowCmbValues(){

        ObservableList<Integer> rows = FXCollections.observableArrayList();
        int count = 0;

        for (HouseInspectTM x : tableData){
            count++;
            rows.add(count);

        }

        tableRowsCmb.setItems(rows);
        tableRowsCmb.getSelectionModel().selectLast();
    }


    public void setTableColumns(){

        checkNoColumn.setCellValueFactory(new PropertyValueFactory<>("checkNumber"));
        livingRoomStatusColumn.setCellValueFactory(new PropertyValueFactory<>("livingRoomStatus"));
        kitchenStatusColumn.setCellValueFactory(new PropertyValueFactory<>("kitchenStatus"));
        bedRoomStatusColumn.setCellValueFactory(new PropertyValueFactory<>("bedRoomsStatus"));
        bathRoomStatusColumn.setCellValueFactory(new PropertyValueFactory<>("bathRoomsStatus"));
        totalHouseStatusColumn.setCellValueFactory(new PropertyValueFactory<>("totalHouseStatus"));
        tenantIdColumn.setCellValueFactory(new PropertyValueFactory<>("tenantId"));
        houseIdColumn.setCellValueFactory(new PropertyValueFactory<>("houseId"));
        costColumn.setCellValueFactory(new PropertyValueFactory<>("estimatedCostForRepair"));
        isPaymentDoneColumn.setCellValueFactory(new PropertyValueFactory<>("isPaymentDone"));

    }


    public void setTableColumnsValue(){

        Callback<TableColumn<HouseInspectTM, String>, TableCell<HouseInspectTM, String>> cellFoctory = (TableColumn<HouseInspectTM, String> param) -> {

            final TableCell<HouseInspectTM, String> cell = new TableCell<HouseInspectTM, String>() {
                @Override
                public void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);

                    if (empty) {
                        setGraphic(null);
                        setText(null);

                    } else {
                        Image image1 = new Image("C:\\Users\\PCWORLD\\IdeaProjects\\Semester_final\\src\\main\\resources\\assets\\image\\email (2).png");
                        Image image2 = new Image("C:\\Users\\PCWORLD\\IdeaProjects\\Semester_final\\src\\main\\resources\\assets\\image\\cutting.png");


                        ImageView mail = new ImageView();
                        mail.setImage(image1);
                        mail.setFitHeight(19);
                        mail.setFitWidth(19);

                        ImageView reduceFromSecurityCharge = new ImageView();
                        reduceFromSecurityCharge.setImage(image2);
                        reduceFromSecurityCharge.setFitHeight(20);
                        reduceFromSecurityCharge.setFitWidth(20);

                        mail.setStyle(" -fx-cursor: hand ;");
                        reduceFromSecurityCharge.setStyle(" -fx-cursor: hand ;");


                        mail.setOnMouseClicked((MouseEvent event) -> {

                            HouseInspectTM selectedHouseCheck = table.getSelectionModel().getSelectedItem();

                            String costOfRepair = selectedHouseCheck.getEstimatedCostForRepair();
                            boolean costOfRepairValidation = UserInputValidation.checkDecimalValidation(costOfRepair);
                            boolean isEnough = false;

                            if(costOfRepairValidation){
                                try {
                                    isEnough = houseInspectBO.checkRemainingSecurityFundEnoughOrNot(selectedHouseCheck.getTenantId(),costOfRepair);
                                }
                                catch (SQLException | ClassNotFoundException e) {
                                    e.printStackTrace();
                                    System.err.println("Error while checking remaining security charge balance: " + e.getMessage());
                                    notification("An error occurred while checking remaining security charge balance. Please try again or contact support.");
                                }

                                if(selectedHouseCheck.getIsPaymentDone().equals("Not Yet") && !isEnough){

                                    try{
                                        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/SendMail.fxml"));
                                        Parent root = fxmlLoader.load();
                                        SendMailController sendMailController = fxmlLoader.getController();
                                        sendMailController.prepareMail(selectedHouseCheck);
                                        Scene scene = new Scene(root);
                                        Stage stage = new Stage();
                                        stage.setScene(scene);
                                        stage.initStyle(StageStyle.UNDECORATED);
                                        stage.setX(50);
                                        stage.setY(50);
                                        stage.show();

                                    }
                                    catch (IOException e) {
                                        e.printStackTrace();
                                        System.err.println("Error while loading Add Send Mail page " + e.getMessage());
                                        notification("An error occurred while loading Add Send Mail page. Please try again or contact support.");
                                    }
                                }

                            }

                        });

                        reduceFromSecurityCharge.setOnMouseClicked((MouseEvent event) -> {

                            HouseInspectTM selectedHouseCheck = table.getSelectionModel().getSelectedItem();

                            String costOfRepair = selectedHouseCheck.getEstimatedCostForRepair();
                            boolean costOfRepairValidation = UserInputValidation.checkDecimalValidation(costOfRepair);
                            boolean isEnough = false;

                            if(costOfRepairValidation) {
                                try {
                                    isEnough = houseInspectBO.checkRemainingSecurityFundEnoughOrNot(selectedHouseCheck.getTenantId(), costOfRepair);
                                }
                                catch (SQLException | ClassNotFoundException e) {
                                    e.printStackTrace();
                                    System.err.println("Error while checking remaining security charge balance is enough or not: " + e.getMessage());
                                    notification("An error occurred while checking remaining security charge balance is enough or not. Please try again or contact support.");
                                }

                                if(selectedHouseCheck.getIsPaymentDone().equals("Not Yet") && isEnough) {

                                    ButtonType yesButton = new ButtonType("Yes", ButtonBar.ButtonData.YES);
                                    ButtonType cancelButton = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);

                                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                                    alert.setTitle("Confirmation");
                                    alert.setHeaderText("Please confirm first");
                                    alert.setContentText("Do you want to reduce damage cost from the tenant security charge?");

                                    alert.getButtonTypes().setAll(yesButton, cancelButton);
                                    Optional<ButtonType> options = alert.showAndWait();

                                    if(options.isPresent() && options.get()==yesButton){

                                        try {
                                            String response = houseInspectBO.reduceRepairCostFromSecurityCharge(selectedHouseCheck.getTenantId(), costOfRepair);
                                            notification(response);

                                            if(response.equals("Repair costs were successfully deducted from the security deposit.")){
                                                HouseInspectDTO houseInspectDTO = new HouseInspectDTO();
                                                houseInspectDTO.setCheckNumber(selectedHouseCheck.getCheckNumber());
                                                houseInspectDTO.setIsPaymentDone("Reduced from Security Deposit");
                                                String isChangeTheStatus = houseInspectBO.update(houseInspectDTO);
                                                loadTable();

                                                TenantDTO tenantDto = houseInspectBO.getTenantDetails(selectedHouseCheck.getTenantId());

                                                notification("Tenant : "+tenantDto.getTenantId()+ ", deducted amount is: "+costOfRepair+ " Remaining Security Deposit Is: "+ tenantDto.getSecurityPaymentRemain());
                                                notification("Sent Email To Tenant ID: "+tenantDto.getTenantId()+" , regarding deduction frm security payment");

                                                SendMail sendMail = new SendMail();
                                                new Thread(() -> sendMail.sendMail(tenantDto.getEmail(),"Reduced House Damage Repair Cost From Security payment","We would like to inform you we have deduced Rs. "+ costOfRepair+" from your security payment,\nupon the property damage of last house inspection,\nCurrent security payment balance is: "+ tenantDto.getSecurityPaymentRemain()+"\nThank You attending for this matter!\n\n\nThe Grand View Residences\nColombo 08")).start();
                                            }

                                        } catch (SQLException | ClassNotFoundException e) {
                                            e.printStackTrace();
                                            System.err.println("Error while deducting repair cost from security charge: " + e.getMessage());
                                            notification("An error occurred while deducting repair cost from security charge. Please try again or contact support.");
                                        }
                                    }
                                    else{

                                        table.getSelectionModel().clearSelection();
                                    }

                                }
                                else if(selectedHouseCheck.getIsPaymentDone().equals("Not Yet") && !isEnough){

                                    notification("The security deposit doesn't cover repairs. Please email the relevant parties for prompt payment");
                                }
                            }

                        });

                        HBox manageBtn = new HBox(reduceFromSecurityCharge,mail);

                        manageBtn.setAlignment(Pos.CENTER);
                        manageBtn.setSpacing(3);
                        manageBtn.setPadding(new Insets(2));

                        HBox.setMargin(reduceFromSecurityCharge, new Insets(2, 3, 0, 3));
                        HBox.setMargin(mail, new Insets(2, 2, 0, 3));
                        setGraphic(manageBtn);

                        setText(null);

                    }
                }
            };

            return cell;
        };

        actionColumn.setCellFactory(cellFoctory);
        loadTable();


    }


    public void loadTable(){

        try {
            ObservableList<HouseInspectDTO> houseInspectDTOS = houseInspectBO.getAll();
            ObservableList<HouseInspectTM> houseInspectTMS = FXCollections.observableArrayList();

            for(HouseInspectDTO x : houseInspectDTOS){
                houseInspectTMS.add(new HouseInspectTM().toTM(x));
            }
            tableData = houseInspectTMS;
            table.setItems(tableData);

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            System.err.println("Error while loading the table data: " + e.getMessage());
            notification("An error occurred while loading the table data. Please try again or contact support.");
        }
    }



    public void clean(){

        loadTable();
        setRowCmbValues();
        setTenantIdCmbValues();
        setHouseStatusCmbValues();
        setCheckNoCmbValues();
        setSortCmbValues();
        sortCmb.getSelectionModel().selectFirst();
        houseStatusCmb.getSelectionModel().selectFirst();
        paymentDoneCmb.getSelectionModel().selectFirst();
        table.getSelectionModel().clearSelection();

    }


    public void notification(String message){

        Notifications notifications = Notifications.create();
        notifications.title("Notification");
        notifications.text(message);
        notifications.hideCloseButton();
        notifications.hideAfter(Duration.seconds(5));
        notifications.position(Pos.CENTER);
        notifications.darkStyle();
        notifications.showInformation();
    }
}




