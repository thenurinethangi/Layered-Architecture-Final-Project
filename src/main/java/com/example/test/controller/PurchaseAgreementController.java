package com.example.test.controller;

import com.example.test.bo.BOFactory;
import com.example.test.bo.custom.PurchaseAgreementBO;
import com.example.test.dto.PurchaseAgreementDTO;
import com.example.test.entity.PurchaseAgreement;
import com.example.test.view.tdm.PurchaseAgreementTM;
import com.example.test.dao.custom.impl.PurchaseAgreementDAOImpl;
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
import javafx.util.Callback;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Comparator;
import java.util.ResourceBundle;

public class PurchaseAgreementController implements Initializable {

    @FXML
    private TableView<PurchaseAgreementTM> table;

    @FXML
    private TableColumn<PurchaseAgreementTM, String> agreementColumn;

    @FXML
    private TableColumn<PurchaseAgreementTM, String> ownerIdColumn;

    @FXML
    private TableColumn<PurchaseAgreementTM, String> houseIdColumn;

    @FXML
    private TableColumn<PurchaseAgreementTM, Double> purchasePriceColumn;

    @FXML
    private TableColumn<PurchaseAgreementTM, String> signedDateColumn;

    @FXML
    private TableColumn<PurchaseAgreementTM, String> statusColumn;

    @FXML
    private TableColumn<PurchaseAgreementTM, String> actionColumn;

    @FXML
    private ComboBox<Integer> tableRowsCmb;

    @FXML
    private ComboBox<String> sortCmb;

    @FXML
    private Button searchbtn;

    @FXML
    private Button refreshbtn;

    @FXML
    private ComboBox<String> houseIdCmb;

    @FXML
    private ComboBox<String> agreementIdCmb;

    @FXML
    private ComboBox<String> ownerIdCmb;

    @FXML
    private TextField searchTxt;


    private final PurchaseAgreementBO purchaseAgreementBO = (PurchaseAgreementBO) BOFactory.getInstance().getBO(BOFactory.BOType.PURCHASEAGREEMENT);
    private ObservableList<PurchaseAgreementTM> tableData;


    @FXML
    void refreshOnAction(ActionEvent event) {

        clean();
    }

    @FXML
    void searchOnAction(ActionEvent event) {

        ObservableList<PurchaseAgreementTM> searchedAgreements = FXCollections.observableArrayList();

        String selectedAgreementId = agreementIdCmb.getValue();
        String selectedOwnerId = ownerIdCmb.getValue();
        String selectedHouseId = houseIdCmb.getValue();

        boolean agreementIdSelected = selectedAgreementId != null && !selectedAgreementId.equals("Select");
        boolean ownerIdSelected = selectedOwnerId != null && !selectedOwnerId.equals("Select");
        boolean houseIdSelected = selectedHouseId != null && !selectedHouseId.equals("Select");

        if (agreementIdSelected) {

            ObservableList<PurchaseAgreementTM> agreementsById = getAgreementById(selectedAgreementId);

            if (agreementsById.isEmpty()) {
                table.setItems(agreementsById);
            } else {
                searchedAgreements.addAll(agreementsById);

                if (ownerIdSelected) {
                    searchedAgreements = filterAgreementsByOwnerId(searchedAgreements, selectedOwnerId);
                }

                if (houseIdSelected) {
                    searchedAgreements = filterAgreementsByHouseId(searchedAgreements, selectedHouseId);
                }

                table.setItems(searchedAgreements);
            }

        }
        else if (ownerIdSelected || houseIdSelected) {

            ObservableList<PurchaseAgreementTM> allAgreements = tableData;
            searchedAgreements.addAll(allAgreements);

            if (ownerIdSelected) {
                searchedAgreements = filterAgreementsByOwnerId(searchedAgreements, selectedOwnerId);
            }

            if (houseIdSelected) {
                searchedAgreements = filterAgreementsByHouseId(searchedAgreements, selectedHouseId);
            }

            table.setItems(searchedAgreements);

        } else {
            ObservableList<PurchaseAgreementTM> allAgreements = tableData;
            table.setItems(allAgreements);
        }

    }



    public ObservableList<PurchaseAgreementTM> getAgreementById(String agreementId) {

        return FXCollections.observableArrayList(
                tableData.stream()
                        .filter(agreement -> agreement.getPurchaseAgreementId().equalsIgnoreCase(agreementId))
                        .toList()
        );
    }

    public ObservableList<PurchaseAgreementTM> filterAgreementsByOwnerId(ObservableList<PurchaseAgreementTM> agreements, String ownerId) {

        return FXCollections.observableArrayList(
                agreements.stream()
                        .filter(agreement -> agreement.getHomeOwnerId().equalsIgnoreCase(ownerId))
                        .toList()
        );
    }

    public ObservableList<PurchaseAgreementTM> filterAgreementsByHouseId(ObservableList<PurchaseAgreementTM> agreements, String houseId) {

        return FXCollections.observableArrayList(
                agreements.stream()
                        .filter(agreement -> agreement.getHouseId().equalsIgnoreCase(houseId))
                        .toList()
        );
    }



    @FXML
    void sortCmbOnAction(ActionEvent event) {


        String sortType = sortCmb.getSelectionModel().getSelectedItem();
        ObservableList<PurchaseAgreementTM> purchaseAgreementTMS = FXCollections.observableArrayList(tableData);

        if (sortType == null) {
            return;
        }

        Comparator<PurchaseAgreementTM> comparator = null;

        switch (sortType) {
            case "Purchase Agreement ID (Ascending)":
                comparator = Comparator.comparing(PurchaseAgreementTM::getPurchaseAgreementId);
                break;

            case "Purchase Agreement ID (Descending)":
                comparator = Comparator.comparing(PurchaseAgreementTM::getPurchaseAgreementId).reversed();
                break;

            case "Owner ID (Ascending)":
                comparator = Comparator.comparing(PurchaseAgreementTM::getHomeOwnerId);
                break;

            case "Owner ID (Descending)":
                comparator = Comparator.comparing(PurchaseAgreementTM::getHomeOwnerId).reversed();
                break;

            case "Signed Date (Ascending)":
                comparator = Comparator.comparing(PurchaseAgreementTM::getSignedDate);
                break;

            case "Signed Date (Descending)":
                comparator = Comparator.comparing(PurchaseAgreementTM::getSignedDate).reversed();
                break;

            case "Purchase Price (Ascending)":
                comparator = Comparator.comparing(PurchaseAgreementTM::getPurchasePrice);
                break;

            case "Purchase Price (Descending)":
                comparator = Comparator.comparing(PurchaseAgreementTM::getPurchasePrice).reversed();
                break;

            default:
                break;
        }

        if (comparator != null) {
            FXCollections.sort(purchaseAgreementTMS, comparator);
            table.setItems(purchaseAgreementTMS);
        }
    }


    @FXML
    void tableRowsCmbOnAction(ActionEvent event) {

        Integer value = tableRowsCmb.getSelectionModel().getSelectedItem();

        if(value==null){
            return;
        }

        ObservableList<PurchaseAgreementTM> purchaseAgreementTMS = FXCollections.observableArrayList();

        for (int i=0; i<value; i++){
            purchaseAgreementTMS.add(tableData.get(i));
        }

        table.setItems(purchaseAgreementTMS);
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        setTableColumnsValues();
        setTableAction();
        setRowCmbValues();
        setAgreementIdCmbValues();
        setOwnerIdCmbValues();
        setHouseIdCmbValues();
        setSortCmbValues();
        tableSearch();
    }


    public void tableSearch() {

        FilteredList<PurchaseAgreementTM> filteredData = new FilteredList<>(tableData, b -> true);

        searchTxt.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(purchaseAgreement -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                String lowerCaseFilter = newValue.toLowerCase();

                if (purchaseAgreement.getPurchaseAgreementId().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else if (purchaseAgreement.getHomeOwnerId().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else if (purchaseAgreement.getHouseId().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else if (String.valueOf(purchaseAgreement.getPurchasePrice()).contains(lowerCaseFilter)) {
                    return true;
                } else if (String.valueOf(purchaseAgreement.getSignedDate()).toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else if (purchaseAgreement.getStatus().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                }
                return false;
            });
        });

        SortedList<PurchaseAgreementTM> sortedData = new SortedList<>(filteredData);

        sortedData.comparatorProperty().bind(table.comparatorProperty());

        table.setItems(sortedData);
    }



    public void setSortCmbValues(){

        ObservableList<String> statuses = FXCollections.observableArrayList("Sort By","Purchase Agreement ID (Ascending)","Purchase Agreement ID (Descending)","Owner ID (Ascending)","Owner ID (Descending)","Signed Date (Ascending)","Signed Date (Descending)","Purchase Price (Ascending)","Purchase Price (Descending)");
        sortCmb.setItems(statuses);
        sortCmb.getSelectionModel().selectFirst();

    }


    public void setOwnerIdCmbValues(){

        try {
            ObservableList<String> ownerIds = purchaseAgreementBO.getAllOwnerIds();
            ownerIdCmb.setItems(ownerIds);
            ownerIdCmb.getSelectionModel().selectFirst();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            System.err.println("Error while setting values to owner id combo box: " + e.getMessage());
            notification("An error occurred setting values to owner id combo box. Please try again or contact support.");
        }

    }


    public void setHouseIdCmbValues(){

        try {
            ObservableList<String> houseIds = purchaseAgreementBO.getAllHouseIds();
            houseIdCmb.setItems(houseIds);
            houseIdCmb.getSelectionModel().selectFirst();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            System.err.println("Error while setting values to house id combo box: " + e.getMessage());
            notification("An error occurred setting values to house id combo box. Please try again or contact support.");
        }

    }


    public void setAgreementIdCmbValues(){

        ObservableList<String> agreementIds = FXCollections.observableArrayList();
        agreementIds.add("Select");

        for(PurchaseAgreementTM x : tableData){
            agreementIds.add(x.getPurchaseAgreementId());
        }

        agreementIdCmb.setItems(agreementIds);
        agreementIdCmb.getSelectionModel().selectFirst();
    }


    public void setRowCmbValues(){

        ObservableList<Integer> rows = FXCollections.observableArrayList();
        int count = 0;

        for (PurchaseAgreementTM x : tableData){
            count++;
            rows.add(count);

        }

        tableRowsCmb.setItems(rows);
        tableRowsCmb.getSelectionModel().selectLast();

    }

    public void setTableColumnsValues(){

        agreementColumn.setCellValueFactory(new PropertyValueFactory<>("purchaseAgreementId"));
        ownerIdColumn.setCellValueFactory(new PropertyValueFactory<>("homeOwnerId"));
        houseIdColumn.setCellValueFactory(new PropertyValueFactory<>("houseId"));
        purchasePriceColumn.setCellValueFactory(new PropertyValueFactory<>("purchasePrice"));
        signedDateColumn.setCellValueFactory(new PropertyValueFactory<>("signedDate"));
        statusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));

    }


    public void setTableAction(){

        Callback<TableColumn<PurchaseAgreementTM, String>, TableCell<PurchaseAgreementTM, String>> cellFoctory = (TableColumn<PurchaseAgreementTM, String> param) -> {

            final TableCell<PurchaseAgreementTM, String> cell = new TableCell<PurchaseAgreementTM, String>() {
                @Override
                public void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);

                    if (empty) {
                        setGraphic(null);
                        setText(null);

                    } else {
                        Image image1 = new Image("C:\\Users\\PCWORLD\\IdeaProjects\\Semester_final\\src\\main\\resources\\assets\\image\\visibility.png");
                        Image image2 = new Image("C:\\Users\\PCWORLD\\IdeaProjects\\Semester_final\\src\\main\\resources\\assets\\image\\send.png");

                        ImageView viewDetails = new ImageView();
                        viewDetails.setImage(image1);
                        viewDetails.setFitHeight(20);
                        viewDetails.setFitWidth(20);

                        ImageView sendAgreement = new ImageView();
                        sendAgreement.setImage(image2);
                        sendAgreement.setFitHeight(20);
                        sendAgreement.setFitWidth(20);

                        viewDetails.setStyle(" -fx-cursor: hand ;");
                        sendAgreement.setStyle(" -fx-cursor: hand ;");


                        viewDetails.setOnMouseClicked((MouseEvent event) -> {

                            PurchaseAgreementTM selectedAgreement = table.getSelectionModel().getSelectedItem();

                            try {
                                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/PurchaseAgreementDetails.fxml"));
                                Parent root = fxmlLoader.load();
                                PurchaseAgreementDetailsController purchaseAgreementDetailsController = fxmlLoader.getController();
                                purchaseAgreementDetailsController.setSelectedAgreementDetails(selectedAgreement);
                                Scene scene = new Scene(root);
                                Stage stage = new Stage();
                                stage.setScene(scene);
                                stage.show();

                            } catch (IOException e) {
                                e.printStackTrace();
                                System.err.println("Error while loading the details of purchase id: "+selectedAgreement.getPurchaseAgreementId()+ " + e.getMessage()");
                                notification("An error occurred while loading the details of purchase id: "+selectedAgreement.getPurchaseAgreementId()+", Please try again or contact support.");
                            }

                        });

                        sendAgreement.setOnMouseClicked((MouseEvent event) -> {



                        });

                        HBox manageBtn = new HBox(viewDetails,sendAgreement);

                        manageBtn.setAlignment(Pos.CENTER);
                        manageBtn.setSpacing(3);
                        manageBtn.setPadding(new Insets(2));

                        HBox.setMargin(viewDetails, new Insets(2, 2, 0, 3));
                        HBox.setMargin(sendAgreement, new Insets(2, 3, 0, 3));
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
            ObservableList<PurchaseAgreementDTO> purchaseAgreementDTOS = purchaseAgreementBO.getAll();
            ObservableList<PurchaseAgreementTM> purchaseAgreementTMS = FXCollections.observableArrayList();

            for(PurchaseAgreementDTO x : purchaseAgreementDTOS){
                purchaseAgreementTMS.add(new PurchaseAgreementTM().toTM(x));
            }
            tableData = purchaseAgreementTMS;
            table.setItems(tableData);

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            System.err.println("Error while loading table data: " + e.getMessage());
            notification("An error occurred while loading table data. Please try again or contact support.");
        }
    }


    public void clean(){

        loadTable();
        setRowCmbValues();
        setAgreementIdCmbValues();
        setOwnerIdCmbValues();
        setHouseIdCmbValues();
        setSortCmbValues();
        table.getSelectionModel().clearSelection();
        searchTxt.clear();

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
