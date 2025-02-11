package com.example.test.controller;

import com.example.test.bo.BOFactory;
import com.example.test.bo.custom.CustomerBO;
import com.example.test.dto.CustomerDTO;
import com.example.test.view.tdm.CustomerTM;
import com.example.test.UserInputValidation;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;

public class CustomerController implements Initializable {

    @FXML
    private Button editbtn;

    @FXML
    private TableView<CustomerTM> table;

    @FXML
    private TableColumn<CustomerTM, String> clientIdColumn;

    @FXML
    private TableColumn<CustomerTM, String> nameColumn;

    @FXML
    private TableColumn<CustomerTM, String> nicColumn;

    @FXML
    private TableColumn<CustomerTM, String> addressColumn;

    @FXML
    private TableColumn<CustomerTM, String> phoneNoColumn;

    @FXML
    private TableColumn<CustomerTM, String> jobTitleColumn;

    @FXML
    private TableColumn<CustomerTM, String> livingArrangementColumn;

    @FXML
    private Button addNewClientbtn;

    @FXML
    private ComboBox<Integer> tableRowCmb;

    @FXML
    private Button deletebtn;

    @FXML
    private ComboBox<String> sortByCmb;

    @FXML
    private Button searchbtn;

    @FXML
    private Button refreshbtn;

    @FXML
    private TextField nictxt;

    @FXML
    private TextField nameTxt;

    @FXML
    private ListView<String> nicList;

    @FXML
    private ListView<String> jobTitleList;

    @FXML
    private ComboBox<String> livingArrangementCmb;

    @FXML
    private ComboBox<String> customerIdCmb;

    @FXML
    private TextField phoneNoTxt;

    @FXML
    private TextField searchTxt;

    @FXML
    private Button searchPhoneNoBtn;

    @FXML
    private ListView<String> phoneNoList;

    @FXML
    private ListView<String> nameList;


    private ObservableList<CustomerTM> tableData;
    private CustomerBO customerBO = (CustomerBO) BOFactory.getInstance().getBO(BOFactory.BOType.CUSTOMER);
    private ObservableList<String> phoneNumbers = FXCollections.observableArrayList();
    private ObservableList<String> nicNumbers = FXCollections.observableArrayList();
    private ObservableList<String> customerNames = FXCollections.observableArrayList();
    private ObservableList<String> jobTitles = FXCollections.observableArrayList();


    @FXML
    void addNewClientOnAction(ActionEvent event) {

        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/AddNewCustomer.fxml"));
            Parent root = fxmlLoader.load();
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.show();
        }
        catch (IOException e){
            e.printStackTrace();
            System.err.println("Error while loading Add New Customer page: " + e.getMessage());
            notification("An error occurred while loading Add New Customer page. Please try again or contact support.");
        }

    }



    @FXML
    void customerIdCmbOnAction(ActionEvent event) {

    }


    @FXML
    void deleteOnAction(ActionEvent event) {

        CustomerTM selectedItem = table.getSelectionModel().getSelectedItem();

        if(selectedItem==null){
            return;
        }

        else{
            ButtonType yesButton = new ButtonType("Yes", ButtonBar.ButtonData.YES);
            ButtonType cancelButton = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);

            Alert a1 = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete the customer?");
            a1.getButtonTypes().setAll(yesButton, cancelButton);
            Optional<ButtonType> options = a1.showAndWait();

            if (options.isPresent() && options.get() == yesButton) {

                try {
                    CustomerDTO customerDto = new CustomerDTO();
                    customerDto.setCustomerId(selectedItem.getCustomerId());

                    String response = customerBO.delete(customerDto);
                    loadTable();
                    notification(response);
                }
                catch (SQLException e) {
                    notification("Can't delete the customer id: "+selectedItem.getCustomerId()+", Because this customer currently Active with company");
                }
                catch (ClassNotFoundException e){
                    e.printStackTrace();
                    System.err.println("Error while deleting the customer: " + e.getMessage());
                    notification("An error occurred while deleting the customer id: "+selectedItem.getCustomerId()+", Please try again or contact support.");
                }
            }
            table.getSelectionModel().clearSelection();
        }

    }


    @FXML
    void editOnAction(ActionEvent event) {

        CustomerTM selectedItem = table.getSelectionModel().getSelectedItem();

        if(selectedItem==null){
            return;
        }

        else{

            try {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/AddNewCustomer.fxml"));
                Parent root = fxmlLoader.load();
                AddNewCustomerController addNewCustomerController = fxmlLoader.getController();
                addNewCustomerController.selectedCustomerData(selectedItem);
                Scene scene = new Scene(root);
                Stage s1 = new Stage();
                s1.setScene(scene);
                s1.show();
            }
            catch (IOException e){
                e.printStackTrace();
                System.err.println("Error while loading Add New Customer page: " + e.getMessage());
                notification("An error occurred while loading Add New Customer page. Please try again or contact support.");
            }
        }

        table.getSelectionModel().clearSelection();

    }


    @FXML
    void livingArrangementCmbOnAction(ActionEvent event) {

    }


    @FXML
    void nameListOnMouseClicked(MouseEvent event) {

        nameTxt.setText(nameList.getSelectionModel().getSelectedItem());
        nameList.getItems().clear();

    }


    @FXML
    void nameTxtOnKeyReleased(KeyEvent event) {

        String input = nameTxt.getText();


        try {
            customerNames = customerBO.getNameSuggestions(input);
            nameList.setItems(customerNames);
        }
        catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            System.err.println("Error while getting customer names suggestions: " + e.getMessage());
            notification("An error occurred while getting customer names suggestions. Please try again or contact support.");
        }

        if(input.isEmpty()){
            customerNames.clear();
        }

    }


    @FXML
    void nicListOnMouseClicked(MouseEvent event) {

        nictxt.setText(nicList.getSelectionModel().getSelectedItem());
        nicList.getItems().clear();

    }



    @FXML
    void nicTxtOnKeyReleased(KeyEvent event) {

        String input = nictxt.getText();


        try {
            nicNumbers = customerBO.getNicSuggestions(input);
            nicList.setItems(nicNumbers);
        }
        catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            System.err.println("Error while getting phone nic numbers suggestions: " + e.getMessage());
            notification("An error occurred while getting nic numbers suggestions. Please try again or contact support.");
        }

        if(input.isEmpty()){
            nicNumbers.clear();
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
            phoneNumbers = customerBO.getPhoneNoSuggestions(input);
            phoneNoList.setItems(phoneNumbers);
        }
        catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            System.err.println("Error while getting phone numbers suggestions: " + e.getMessage());
            notification("An error occurred while getting phone numbers suggestions. Please try again or contact support.");
        }

        if(input.isEmpty()){
            phoneNumbers.clear();
        }

    }



    @FXML
    void refreshOnAction(ActionEvent event) {

        clean();
    }



    @FXML
    void searchOnAction(ActionEvent event) {

        ObservableList<CustomerTM> searchedCustomers = FXCollections.observableArrayList();

        String selectedCustomerId = customerIdCmb.getValue();
        String selectedName = nameTxt.getText();
        String selectedNic = nictxt.getText();
        String selectedLivingArrangement = livingArrangementCmb.getValue();

        boolean customerIdSelected = selectedCustomerId != null && !selectedCustomerId.equals("Select");
        boolean nameSelected = selectedName != null && !selectedName.isEmpty();
        boolean nicSelected = selectedNic != null && !selectedNic.isEmpty();
        boolean livingArrangementSelected = selectedLivingArrangement != null && !selectedLivingArrangement.equals("Select");

        if (customerIdSelected) {
            ObservableList<CustomerTM> customersById = getCustomerById(selectedCustomerId);

            if (customersById.isEmpty()) {
                table.setItems(customersById);
            } else {
                searchedCustomers.addAll(customersById);

                if (nameSelected) {
                    ObservableList<CustomerTM> filteredByName = filterCustomersByName(searchedCustomers, selectedName);
                    searchedCustomers.clear();
                    searchedCustomers.addAll(filteredByName);
                }

                if (nicSelected) {
                    ObservableList<CustomerTM> filteredByNic = filterCustomersByNic(searchedCustomers, selectedNic);
                    searchedCustomers.clear();
                    searchedCustomers.addAll(filteredByNic);
                }

                if (livingArrangementSelected) {
                    ObservableList<CustomerTM> filteredByLivingArrangement = filterCustomersByLivingArrangement(searchedCustomers, selectedLivingArrangement);
                    searchedCustomers.clear();
                    searchedCustomers.addAll(filteredByLivingArrangement);
                }

                table.setItems(searchedCustomers);
            }

        } else if (nameSelected || nicSelected || livingArrangementSelected) {
            ObservableList<CustomerTM> allCustomers = tableData;
            searchedCustomers.addAll(allCustomers);

            if (nameSelected) {
                searchedCustomers = filterCustomersByName(searchedCustomers, selectedName);
            }

            if (nicSelected) {
                searchedCustomers = filterCustomersByNic(searchedCustomers, selectedNic);
            }

            if (livingArrangementSelected) {
                searchedCustomers = filterCustomersByLivingArrangement(searchedCustomers, selectedLivingArrangement);
            }

            table.setItems(searchedCustomers);

        } else {
            ObservableList<CustomerTM> allCustomers = tableData;
            table.setItems(allCustomers);
        }
    }


    private ObservableList<CustomerTM> getCustomerById(String customerId) {

        return FXCollections.observableArrayList(
                tableData.stream().filter(customer -> customer.getCustomerId().equalsIgnoreCase(customerId)).toList());
    }


    private ObservableList<CustomerTM> filterCustomersByName(ObservableList<CustomerTM> customers, String name) {

        return FXCollections.observableArrayList(
                customers.stream().filter(customer -> customer.getName().toLowerCase().contains(name.toLowerCase())).toList());
    }


    private ObservableList<CustomerTM> filterCustomersByNic(ObservableList<CustomerTM> customers, String nic) {

        return FXCollections.observableArrayList(
                customers.stream().filter(customer -> customer.getNic().equalsIgnoreCase(nic)).toList());
    }


    private ObservableList<CustomerTM> filterCustomersByLivingArrangement(ObservableList<CustomerTM> customers, String livingArrangement) {

        return FXCollections.observableArrayList(
                customers.stream().filter(customer -> customer.getLivingArrangement().equalsIgnoreCase(livingArrangement)).toList());
    }


    @FXML
    void searchPhoneNoOnAction(ActionEvent event) {

        String phoneNo = phoneNoTxt.getText();

        if(phoneNo.isEmpty()){
            notification("Enter Phone Number to search");
            return;
        }

        boolean b1 = UserInputValidation.checkPhoneNoValidation(phoneNo);

        if(!b1){
            notification("Not a valid phone no");

            phoneNoTxt.setText("");
        }

        else{
            try {
                CustomerDTO customerDto = customerBO.isExistByPhoneNo(phoneNo);

                if(customerDto==null){

                    notification("No registered customer in this phone number, Please Add to the system as a new customer");

                    try {
                        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/AddNewCustomer.fxml"));
                        Parent root = fxmlLoader.load();
                        Scene scene = new Scene(root);
                        Stage stage = new Stage();
                        stage.setScene(scene);
                        stage.show();
                    }
                    catch (IOException e){
                        e.printStackTrace();
                        System.err.println("Error while loading Add New Customer page: " + e.getMessage());
                        notification("An error occurred while loading Add New Customer page. Please try again or contact support.");
                    }
                }

                else{
                    notification("Registered phone no");

                    ObservableList<CustomerTM> customerTMS = FXCollections.observableArrayList();
                    customerTMS.add(new CustomerTM().toTM(customerDto));
                    table.setItems(customerTMS);
                }

            }
            catch (SQLException | ClassNotFoundException e) {
                e.printStackTrace();
                System.err.println("Error while setting values to position combo box: " + e.getMessage());
                notification("An error occurred while setting values to position combo box. Please try again or contact support.");
            }

            phoneNoTxt.setText("");
        }

    }


    @FXML
    void sortByCmbOnAction(ActionEvent event) {

        String sortType = sortByCmb.getSelectionModel().getSelectedItem();
        ObservableList<CustomerTM> customerTMS = tableData;

        if(sortType==null){
            return;
        }

        if(sortType.equals("Customer ID (Ascending)")){

            for(int j = 0; j < customerTMS.size(); j++) {
                for (int i = 0; i < customerTMS.size()-1; i++) {
                    if (customerTMS.get(i).getCustomerId().compareTo(customerTMS.get(i + 1).getCustomerId())>0) {
                        CustomerTM temp = customerTMS.get(i);
                        customerTMS.set(i, customerTMS.get(i + 1));
                        customerTMS.set((i + 1), temp);

                    }
                }
            }
            table.setItems(customerTMS);
        }

        else if(sortType.equals("Customer ID (Descending)")){

            for(int j = 0; j < customerTMS.size(); j++) {
                for (int i = 0; i < customerTMS.size()-1; i++) {
                    if (customerTMS.get(i).getCustomerId().compareTo(customerTMS.get(i + 1).getCustomerId())<0) {
                        CustomerTM temp = customerTMS.get(i);
                        customerTMS.set(i, customerTMS.get(i + 1));
                        customerTMS.set((i + 1), temp);

                    }
                }
            }
            table.setItems(customerTMS);
        }

        else if(sortType.equals("Customer Name (Ascending)")){

            for(int j = 0; j < customerTMS.size(); j++) {
                for (int i = 0; i < customerTMS.size()-1; i++) {
                    if (customerTMS.get(i).getName().compareTo(customerTMS.get(i + 1).getName())>0) {
                        CustomerTM temp = customerTMS.get(i);
                        customerTMS.set(i, customerTMS.get(i + 1));
                        customerTMS.set((i + 1), temp);

                    }
                }
            }
            table.setItems(customerTMS);
        }

        else if(sortType.equals("Customer Name (Descending)")){

            for(int j = 0; j < customerTMS.size(); j++) {
                for (int i = 0; i < customerTMS.size()-1; i++) {
                    if (customerTMS.get(i).getName().compareTo(customerTMS.get(i + 1).getName())<0) {
                        CustomerTM temp = customerTMS.get(i);
                        customerTMS.set(i, customerTMS.get(i + 1));
                        customerTMS.set((i + 1), temp);

                    }
                }
            }
            table.setItems(customerTMS);
        }

        else if(sortType.equals("NIC (Ascending)")){

            for(int j = 0; j < customerTMS.size(); j++) {
                for (int i = 0; i < customerTMS.size()-1; i++) {
                    if (customerTMS.get(i).getNic().compareTo(customerTMS.get(i + 1).getNic())>0) {
                        CustomerTM temp = customerTMS.get(i);
                        customerTMS.set(i, customerTMS.get(i + 1));
                        customerTMS.set((i + 1), temp);

                    }
                }
            }
            table.setItems(customerTMS);
        }

        else if(sortType.equals("NIC (Descending)")){

            for(int j = 0; j < customerTMS.size(); j++) {
                for (int i = 0; i < customerTMS.size()-1; i++) {
                    if (customerTMS.get(i).getNic().compareTo(customerTMS.get(i + 1).getNic())<0) {
                        CustomerTM temp = customerTMS.get(i);
                        customerTMS.set(i, customerTMS.get(i + 1));
                        customerTMS.set((i + 1), temp);

                    }
                }
            }
            table.setItems(customerTMS);
        }

        else if(sortType.equals("Living Arrangement (Ascending)")){

            for(int j = 0; j < customerTMS.size(); j++) {
                for (int i = 0; i < customerTMS.size()-1; i++) {
                    if (customerTMS.get(i).getLivingArrangement().compareTo(customerTMS.get(i + 1).getLivingArrangement())>0) {
                        CustomerTM temp = customerTMS.get(i);
                        customerTMS.set(i, customerTMS.get(i + 1));
                        customerTMS.set((i + 1), temp);

                    }
                }
            }
            table.setItems(customerTMS);
        }

        else if(sortType.equals("Living Arrangement (Descending)")){

            for(int j = 0; j < customerTMS.size(); j++) {
                for (int i = 0; i < customerTMS.size()-1; i++) {
                    if (customerTMS.get(i).getLivingArrangement().compareTo(customerTMS.get(i + 1).getLivingArrangement())<0) {
                        CustomerTM temp = customerTMS.get(i);
                        customerTMS.set(i, customerTMS.get(i + 1));
                        customerTMS.set((i + 1), temp);

                    }
                }
            }
            table.setItems(customerTMS);
        }

    }

    @FXML
    void tableOnSort(ActionEvent event) {

    }


    @FXML
    void tableRowCmbOnAction(ActionEvent event) {

        Integer value = tableRowCmb.getSelectionModel().getSelectedItem();

        if(value==null){
            return;
        }

        ObservableList<CustomerTM> customerTMS = FXCollections.observableArrayList();

        for (int i=0; i<value; i++){
            customerTMS.add(tableData.get(i));
        }

        table.setItems(customerTMS);

    }



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        setTableColumnsValues();
        loadTable();
        setCustomerIdCmbValues();
        setLivingArrangementCmbValues();
        setRowCmbValues();
        setSortByCmbValues();
        tableSearch();

    }


    public void tableSearch() {

        FilteredList<CustomerTM> filteredData = new FilteredList<>(tableData, b -> true);

        searchTxt.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(customer -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                String lowerCaseFilter = newValue.toLowerCase();

                if (customer.getCustomerId().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else if (customer.getName().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else if (customer.getNic().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else if (customer.getAddress().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else if (customer.getPhoneNo().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else if (customer.getJobTitle().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else if (customer.getLivingArrangement().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                }
                return false;
            });
        });

        SortedList<CustomerTM> sortedData = new SortedList<>(filteredData);

        sortedData.comparatorProperty().bind(table.comparatorProperty());

        table.setItems(sortedData);
    }



    public void setTableColumnsValues(){

        clientIdColumn.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        nicColumn.setCellValueFactory(new PropertyValueFactory<>("nic"));
        addressColumn.setCellValueFactory(new PropertyValueFactory<>("address"));
        phoneNoColumn.setCellValueFactory(new PropertyValueFactory<>("phoneNo"));
        jobTitleColumn.setCellValueFactory(new PropertyValueFactory<>("jobTitle"));
        livingArrangementColumn.setCellValueFactory(new PropertyValueFactory<>("livingArrangement"));

    }


    public void loadTable(){

        tableData = FXCollections.observableArrayList();

        try {
            ObservableList<CustomerDTO> customerDTOS = customerBO.getAll();
            for(CustomerDTO x : customerDTOS){
                tableData.add(new CustomerTM().toTM(x));
            }
            table.setItems(tableData);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            System.err.println("Error while loading data to the table: " + e.getMessage());
            notification("An error occurred while loading data to the table. Please try again or contact support.");
        }

    }


    public void setCustomerIdCmbValues(){

        try {
            ObservableList<String> customerIds = customerBO.getAllIds();
            customerIdCmb.setItems(customerIds);
            customerIdCmb.getSelectionModel().selectFirst();

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            System.err.println("Error while setting values to customer id combo box: " + e.getMessage());
            notification("An error occurred while setting values to customer id combo box. Please try again or contact support.");
        }

    }


    public void setLivingArrangementCmbValues(){

        ObservableList<String> livingArrangement = FXCollections.observableArrayList("Select","Single Living Arrangement","Couple Living Arrangement","Family Living Arrangement");
        livingArrangementCmb.setItems(livingArrangement);
        livingArrangementCmb.getSelectionModel().selectFirst();

    }


    public void setRowCmbValues(){

        ObservableList<Integer> rows = FXCollections.observableArrayList();
        int count = 0;

        for (CustomerTM x : tableData){
            count++;
            rows.add(count);

        }

        tableRowCmb.setItems(rows);
        tableRowCmb.getSelectionModel().selectLast();

    }


    public void setSortByCmbValues(){

        ObservableList<String> sortTypes = FXCollections.observableArrayList();
        sortTypes.addAll("Sort By","Customer ID (Ascending)","Customer ID (Descending)","Customer Name (Ascending)","Customer Name (Descending)","NIC (Ascending)","NIC (Descending)","Living Arrangement (Ascending)","Living Arrangement (Descending)");
        sortByCmb.setItems(sortTypes);
        sortByCmb.getSelectionModel().selectFirst();

    }


    public void clean(){

        loadTable();
        setCustomerIdCmbValues();
        setRowCmbValues();
        setCustomerIdCmbValues();
        sortByCmb.getSelectionModel().selectFirst();
        livingArrangementCmb.getSelectionModel().selectFirst();
        table.getSelectionModel().clearSelection();
        nameTxt.setText("");
        nictxt.setText("");
        phoneNoTxt.setText("");
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
