package com.example.test.controller;

import com.example.test.bo.BOFactory;
import com.example.test.bo.custom.OwnerBO;
import com.example.test.dto.OwnerDTO;
import com.example.test.view.tdm.OwnerTM;
import com.example.test.dao.custom.impl.OwnerDAOImpl;
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
import javafx.scene.input.KeyEvent;
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

public class OwnerController implements Initializable {

    @FXML
    private TableView<OwnerTM> table;

    @FXML
    private TableColumn<OwnerTM, String> ownerIdColumn;

    @FXML
    private TableColumn<OwnerTM, String> nameColumn;

    @FXML
    private TableColumn<OwnerTM, String> phoneNoColumn;

    @FXML
    private TableColumn<OwnerTM, Integer> membersCountColumn;

    @FXML
    private TableColumn<OwnerTM, String> purchaseDateColumn;

    @FXML
    private TableColumn<OwnerTM, String> houseIdColumn;

    @FXML
    private TableColumn<OwnerTM, String> invoiceNoColumn;

    @FXML
    private TableColumn<OwnerTM, String> actionColumn;

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
    private ComboBox<String> ownerIdCmb;

    @FXML
    private ComboBox<String> invoiceNoCmb;

    @FXML
    private TextField phoneNoTxt;

    @FXML
    private TextField nameTxt;

    @FXML
    private ListView<String> nameList;

    @FXML
    private ListView<String> phoneNoList;

    @FXML
    private TextField searchTxt;


    private ObservableList<OwnerTM> tableData;
    private ObservableList<String> names;
    private ObservableList<String> phoneNos;
    private OwnerBO ownerBO = (OwnerBO) BOFactory.getInstance().getBO(BOFactory.BOType.OWNER);


    @FXML
    void nameListOnMouseClicked(MouseEvent event) {

        nameTxt.setText(nameList.getSelectionModel().getSelectedItem());
        nameList.getItems().clear();
    }

    @FXML
    void nameTxtOnKeyReleased(KeyEvent event) {

        String input = nameTxt.getText();


        try {
            names = ownerBO.getNameSuggestions(input);
            nameList.setItems(names);
        }
        catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            System.err.println("Error while setting owners names suggestions: " + e.getMessage());
            notification("An error occurred while setting owners names suggestions. Please try again or contact support.");
        }

        if(input.isEmpty()){
            names.clear();
        }
    }

    @FXML
    void phoneNoListOnMouseClicked(MouseEvent event) {

        phoneNoTxt.setText(phoneNoList.getSelectionModel().getSelectedItem());
        phoneNoList.getItems().clear();
    }

    @FXML
    void phoneNoTxtOnKeyReleased(KeyEvent event) {

        String input = phoneNoTxt.getText();


        try {
            phoneNos = ownerBO.getPhoneNosSuggestions(input);
            phoneNoList.setItems(phoneNos);
        }
        catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            System.err.println("Error while setting owners phone numbers suggestions: " + e.getMessage());
            notification("An error occurred while setting owners phone numbers suggestions. Please try again or contact support.");
        }

        if(input.isEmpty()){
            phoneNos.clear();
        }
    }


    @FXML
    void refreshOnAction(ActionEvent event) {

        clean();

    }


    @FXML
    void searchOnAction(ActionEvent event) {

        ObservableList<OwnerTM> searchedOwners = FXCollections.observableArrayList();

        String selectedOwnerId = ownerIdCmb.getValue();
        String selectedName = nameTxt.getText();
        String selectedPhoneNo = phoneNoTxt.getText();
        String selectedHouseId = houseIdCmb.getValue();
        String selectedInvoiceNo = invoiceNoCmb.getValue();

        boolean ownerIdSelected = selectedOwnerId != null && !selectedOwnerId.equals("Select");
        boolean nameSelected = selectedName != null && !selectedName.isEmpty();
        boolean phoneNoSelected = selectedPhoneNo != null && !selectedPhoneNo.isEmpty();
        boolean houseIdSelected = selectedHouseId != null && !selectedHouseId.equals("Select");
        boolean invoiceNoSelected = selectedInvoiceNo != null && !selectedInvoiceNo.equals("Select");

        if (ownerIdSelected) {

            ObservableList<OwnerTM> ownersById = getOwnerById(selectedOwnerId);

            if (ownersById.isEmpty()) {
                table.setItems(ownersById);
            } else {
                searchedOwners.addAll(ownersById);

                if (nameSelected) {
                    searchedOwners = filterOwnersByName(searchedOwners, selectedName);
                }

                if (phoneNoSelected) {
                    searchedOwners = filterOwnersByPhoneNo(searchedOwners, selectedPhoneNo);
                }

                if (houseIdSelected) {
                    searchedOwners = filterOwnersByHouseId(searchedOwners, selectedHouseId);
                }

                if (invoiceNoSelected) {
                    searchedOwners = filterOwnersByInvoiceNo(searchedOwners, selectedInvoiceNo);
                }

                table.setItems(searchedOwners);
            }


        } else if (nameSelected || phoneNoSelected || houseIdSelected || invoiceNoSelected) {

            ObservableList<OwnerTM> allOwners = tableData;
            searchedOwners.addAll(allOwners);

            if (nameSelected) {
                searchedOwners = filterOwnersByName(searchedOwners, selectedName);
            }

            if (phoneNoSelected) {
                searchedOwners = filterOwnersByPhoneNo(searchedOwners, selectedPhoneNo);
            }

            if (houseIdSelected) {
                searchedOwners = filterOwnersByHouseId(searchedOwners, selectedHouseId);
            }

            if (invoiceNoSelected) {
                searchedOwners = filterOwnersByInvoiceNo(searchedOwners, selectedInvoiceNo);
            }

            table.setItems(searchedOwners);

        } else {
            ObservableList<OwnerTM> allOwners = tableData;
            table.setItems(allOwners);
        }
    }


    public ObservableList<OwnerTM> getOwnerById(String ownerId) {
        return FXCollections.observableArrayList(
                tableData.stream()
                        .filter(owner -> owner.getOwnerId().equalsIgnoreCase(ownerId))
                        .toList()
        );
    }


    public ObservableList<OwnerTM> filterOwnersByName(ObservableList<OwnerTM> owners, String name) {
        return FXCollections.observableArrayList(
                owners.stream()
                        .filter(owner -> owner.getName().toLowerCase().contains(name.toLowerCase()))
                        .toList()
        );
    }


    public ObservableList<OwnerTM> filterOwnersByPhoneNo(ObservableList<OwnerTM> owners, String phoneNo) {
        return FXCollections.observableArrayList(
                owners.stream()
                        .filter(owner -> owner.getPhoneNo().contains(phoneNo))
                        .toList()
        );
    }


    public ObservableList<OwnerTM> filterOwnersByHouseId(ObservableList<OwnerTM> owners, String houseId) {
        return FXCollections.observableArrayList(
                owners.stream()
                        .filter(owner -> owner.getHouseId().equalsIgnoreCase(houseId))
                        .toList()
        );
    }


    public ObservableList<OwnerTM> filterOwnersByInvoiceNo(ObservableList<OwnerTM> owners, String invoiceNo) {
        return FXCollections.observableArrayList(
                owners.stream()
                        .filter(owner -> owner.getInvoiceNo().equalsIgnoreCase(invoiceNo))
                        .toList()
        );
    }


    @FXML
    void sortCmbOnAction(ActionEvent event) {

        String sortType = sortCmb.getSelectionModel().getSelectedItem();
        ObservableList<OwnerTM> ownerTMS = FXCollections.observableArrayList(tableData);

        if (sortType == null) {
            return;
        }

        Comparator<OwnerTM> comparator = null;

        switch (sortType) {
            case "Owner ID (Ascending)":
                comparator = Comparator.comparing(OwnerTM::getOwnerId);
                break;

            case "Owner ID (Descending)":
                comparator = Comparator.comparing(OwnerTM::getOwnerId).reversed();
                break;

            case "Owner Name (Ascending)":
                comparator = Comparator.comparing(OwnerTM::getName);
                break;

            case "Owner Name (Descending)":
                comparator = Comparator.comparing(OwnerTM::getName).reversed();
                break;

            case "Resident Count (Ascending)":
                comparator = Comparator.comparing(OwnerTM::getMembersCount);
                break;

            case "Resident Count (Descending)":
                comparator = Comparator.comparing(OwnerTM::getMembersCount).reversed();
                break;

            case "Purchase Date (Ascending)":
                comparator = Comparator.comparing(OwnerTM::getPurchaseDate);
                break;

            case "Purchase Date (Descending)":
                comparator = Comparator.comparing(OwnerTM::getPurchaseDate).reversed();
                break;

            case "Invoice Number (Ascending)":
                comparator = Comparator.comparing(OwnerTM::getInvoiceNo);
                break;

            case "Invoice Number (Descending)":
                comparator = Comparator.comparing(OwnerTM::getInvoiceNo).reversed();
                break;

            default:
                break;
        }

        if (comparator != null) {
            FXCollections.sort(ownerTMS, comparator);
            table.setItems(ownerTMS);
        }
    }


    @FXML
    void tableRowsCmbOnAction(ActionEvent event) {

        Integer value = tableRowsCmb.getSelectionModel().getSelectedItem();

        if(value==null){
            return;
        }

        ObservableList<OwnerTM> ownerTMS = FXCollections.observableArrayList();

        for (int i=0; i<value; i++){
            ownerTMS.add(tableData.get(i));
        }

        table.setItems(ownerTMS);
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        setTableColumnsValues();
        setTableAction();
        setRowCmbValues();
        setInvoiceNoCmbValues();
        setHouseIdCmbValues();
        setOwnerIdCmbValues();
        setSortCmbValues();
        tableSearch();

    }


    public void tableSearch() {

        FilteredList<OwnerTM> filteredData = new FilteredList<>(tableData, b -> true);

        searchTxt.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(owner -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                String lowerCaseFilter = newValue.toLowerCase();

                if (owner.getOwnerId().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else if (owner.getName().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else if (owner.getPhoneNo().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else if (String.valueOf(owner.getMembersCount()).contains(lowerCaseFilter)) {
                    return true;
                } else if (String.valueOf(owner.getPurchaseDate()).toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else if (owner.getHouseId().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else if (owner.getInvoiceNo().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                }
                return false;
            });
        });

        SortedList<OwnerTM> sortedData = new SortedList<>(filteredData);

        sortedData.comparatorProperty().bind(table.comparatorProperty());

        table.setItems(sortedData);
    }


    public void setOwnerIdCmbValues(){

        ObservableList<String> ownerIds = FXCollections.observableArrayList();
        ownerIds.add("Select");

        for(OwnerTM x : tableData){
           ownerIds.add(x.getOwnerId());
        }

        ownerIdCmb.setItems(ownerIds);
        ownerIdCmb.getSelectionModel().selectFirst();
    }


    public void setSortCmbValues(){

        ObservableList<String> sortTypes = FXCollections.observableArrayList("Sort By","Owner ID (Ascending)","Owner ID (Descending)","Owner Name (Ascending)","Owner Name (Descending)","Resident Count (Ascending)","Resident Count (Descending)","Purchase Date (Ascending)","Purchase Date (Descending)","Invoice Number (Ascending)","Invoice Number (Descending)");
        sortCmb.setItems(sortTypes);
        sortCmb.getSelectionModel().selectFirst();
    }


    public void setInvoiceNoCmbValues(){

        try {
            ObservableList<String> invoiceNos = ownerBO.getAllDistinctInvoiceNos();
            invoiceNoCmb.setItems(invoiceNos);
            invoiceNoCmb.getSelectionModel().selectFirst();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            System.err.println("Error while setting values to invoice number combo box: " + e.getMessage());
            notification("An error occurred while setting values to invoice number combo box. Please try again or contact support.");
        }
    }


    public void setHouseIdCmbValues(){

        try {
            ObservableList<String> houseIds = ownerBO.getAllHouseIds();
            houseIdCmb.setItems(houseIds);
            houseIdCmb.getSelectionModel().selectFirst();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            System.err.println("Error while setting values to house id combo box: " + e.getMessage());
            notification("An error occurred while setting values to house id combo box. Please try again or contact support.");
        }

    }


    public void setRowCmbValues(){

        ObservableList<Integer> rows = FXCollections.observableArrayList();
        int count = 0;

        for (OwnerTM x : tableData){
            count++;
            rows.add(count);

        }

        tableRowsCmb.setItems(rows);
        tableRowsCmb.getSelectionModel().selectLast();

    }


    public void setTableColumnsValues(){

        ownerIdColumn.setCellValueFactory(new PropertyValueFactory<>("ownerId"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        phoneNoColumn.setCellValueFactory(new PropertyValueFactory<>("phoneNo"));
        membersCountColumn.setCellValueFactory(new PropertyValueFactory<>("membersCount"));
        purchaseDateColumn.setCellValueFactory(new PropertyValueFactory<>("purchaseDate"));
        houseIdColumn.setCellValueFactory(new PropertyValueFactory<>("houseId"));
        invoiceNoColumn.setCellValueFactory(new PropertyValueFactory<>("invoiceNo"));

    }


    public void setTableAction(){

        Callback<TableColumn<OwnerTM, String>, TableCell<OwnerTM, String>> cellFoctory = (TableColumn<OwnerTM, String> param) -> {

            final TableCell<OwnerTM, String> cell = new TableCell<OwnerTM, String>() {
                @Override
                public void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);

                    if (empty) {
                        setGraphic(null);
                        setText(null);

                    } else {
                        Image image1 = new Image("C:\\Users\\PCWORLD\\IdeaProjects\\Semester_final\\src\\main\\resources\\assets\\image\\visibility.png");
                        Image image2 = new Image("C:\\Users\\PCWORLD\\IdeaProjects\\Semester_final\\src\\main\\resources\\assets\\image\\editText.png");

                        ImageView viewDetails = new ImageView();
                        viewDetails.setImage(image1);
                        viewDetails.setFitHeight(20);
                        viewDetails.setFitWidth(20);

                        ImageView edit = new ImageView();
                        edit.setImage(image2);
                        edit.setFitHeight(20);
                        edit.setFitWidth(20);

                        viewDetails.setStyle(" -fx-cursor: hand ;");
                        edit.setStyle(" -fx-cursor: hand ;");


                        viewDetails.setOnMouseClicked((MouseEvent event) -> {

                            OwnerTM selectedOwner = table.getSelectionModel().getSelectedItem();

                            try {
                                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/OwnerDetailsView.fxml"));
                                Parent root = fxmlLoader.load();
                                OwnerDetailsViewController ownerDetailsViewController = fxmlLoader.getController();
                                ownerDetailsViewController.setSelectedOwnerDetails(selectedOwner);
                                Scene scene = new Scene(root);
                                Stage stage = new Stage();
                                stage.setScene(scene);
                                stage.show();

                            } catch (IOException e) {
                                e.printStackTrace();
                                System.err.println("Error while loading owner details: " + e.getMessage());
                                notification("An error occurred while loading owner details. Please try again or contact support.");
                            }

                        });

                        edit.setOnMouseClicked((MouseEvent event) -> {

                            OwnerTM selectedOwner = table.getSelectionModel().getSelectedItem();

                            try {
                                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/EditOwner.fxml"));
                                Parent root = fxmlLoader.load();
                                EditOwnerController editOwnerController = fxmlLoader.getController();
                                editOwnerController.setSelectedOwnerDetailsToUpdate(selectedOwner);
                                Scene scene = new Scene(root);
                                Stage stage = new Stage();
                                stage.setScene(scene);
                                stage.show();

                            } catch (IOException e) {
                                e.printStackTrace();
                                System.err.println("Error while loading owner update page: " + e.getMessage());
                                notification("An error occurred while loading update page. Please try again or contact support.");
                            }

                        });

                        HBox manageBtn = new HBox(viewDetails,edit);

                        manageBtn.setAlignment(Pos.CENTER);
                        manageBtn.setSpacing(3);
                        manageBtn.setPadding(new Insets(2));

                        HBox.setMargin(viewDetails, new Insets(2, 2, 0, 3));
                        HBox.setMargin(edit, new Insets(2, 3, 0, 3));
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
            ObservableList<OwnerDTO> ownerDTOS = ownerBO.getAll();
            ObservableList<OwnerTM> ownerTMS = FXCollections.observableArrayList();

            for(OwnerDTO x :  ownerDTOS){
                ownerTMS.add(new OwnerTM().toTM(x));
            }
            tableData = ownerTMS;
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
        setInvoiceNoCmbValues();
        setHouseIdCmbValues();
        setSortCmbValues();
        setOwnerIdCmbValues();
        table.getSelectionModel().clearSelection();
        nameTxt.clear();
        phoneNoTxt.clear();
        phoneNoList.getItems().clear();
        nameList.getItems().clear();
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



