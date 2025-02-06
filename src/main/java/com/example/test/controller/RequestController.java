package com.example.test.controller;

import com.example.test.bo.BOFactory;
import com.example.test.bo.custom.RequestBO;
import com.example.test.dto.HouseTypeDTO;
import com.example.test.dto.RequestDTO;
import com.example.test.entity.HouseType;
import com.example.test.entity.Request;
import com.example.test.view.tdm.RequestTM;
import com.example.test.dao.custom.impl.HouseTypeDAOImpl;
import com.example.test.dao.custom.impl.RequestDAOImpl;
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

import java.io.IOException;
import java.sql.SQLException;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;

import java.net.URL;
import java.util.Comparator;
import java.util.Optional;
import java.util.ResourceBundle;

public class RequestController implements Initializable {

    @FXML
    private Button editbtn;

    @FXML
    private Button addNewRentRequestBtn;

    @FXML
    private ComboBox<Integer> tableRowsCmb;

    @FXML
    private Button deletebtn;

    @FXML
    private ComboBox<String> sortByCmb;

    @FXML
    private Button searchbtn;

    @FXML
    private Button refreshbtn;

    @FXML
    private ComboBox<?> employeeIdCmb;

    @FXML
    private ComboBox<?> positionCmb;

    @FXML
    private TextField nameTxt;

    @FXML
    private ListView<?> nameList;

    @FXML
    private ListView<?> addressList;

    @FXML
    private TextField addressTxt;

    @FXML
    private TextField searchTxt;

    @FXML
    private Button addNewBuyRequestBtn;

    @FXML
    private ComboBox<String> rentOrBuyCmb;

    @FXML
    private ComboBox<String> customerIdCmb;

    @FXML
    private ComboBox<String> requestIdCmb;

    @FXML
    private ComboBox<String> houseTypeCmb;

    @FXML
    private TableView<RequestTM> table;
    @FXML
    private TableColumn<RequestTM, String> requestIdColumn;
    @FXML
    private TableColumn<RequestTM, String> customerIdColumn;
    @FXML
    private TableColumn<RequestTM, String> rentOrBuyColumn;
    @FXML
    private TableColumn<RequestTM, String> houseTypeColumn;
    @FXML
    private TableColumn<RequestTM, String> leaseTurnDesireColumn;
    @FXML
    private TableColumn<RequestTM, String> allDocumentsProvidedColumn;
    @FXML
    private TableColumn<RequestTM, String> qualifiedCustomerOrNotColumn;
    @FXML
    TableColumn<RequestTM, String> agreesToAllTermsAndConditionsColumn;
    @FXML
    TableColumn<RequestTM, String> isPaymentsCompletedColumn;
    @FXML
    TableColumn<RequestTM, String> customerRequestStatusColumn;
    @FXML
    TableColumn<RequestTM, String> requestStatusColumn;
    @FXML
    TableColumn<RequestTM, String> houseIdColumn;
    @FXML
    TableColumn<RequestTM, String> actionColumn;


    private RequestBO requestBO = (RequestBO) BOFactory.getInstance().getBO(BOFactory.BOType.REQUEST);
    private ObservableList<RequestTM> tableData;


    @FXML
    void addNewBuyRequestOnAction(ActionEvent event) {

        try{
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/AddNewBuyRequest.fxml"));
            Parent root = fxmlLoader.load();
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Error while loading Add New Purchase Request Form: " + e.getMessage());
            notification("An error occurred while loading Add New Purchase Request Form. Please try again or contact support.");
        }

    }

    @FXML
    void addNewRentRequestOnAction(ActionEvent event) {

        try{
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/AddNewRentRequest.fxml"));
            Parent root = fxmlLoader.load();
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Error while loading Add New Rent Request Form: " + e.getMessage());
            notification("An error occurred while loading Add New Rent Request Form. Please try again or contact support.");
        }

    }


    @FXML
    void deleteOnAction(ActionEvent event) {

        RequestTM selectedRequest = table.getSelectionModel().getSelectedItem();

        if (selectedRequest == null) {
            notification("No request selected to delete.");
            return;
        }


        if (selectedRequest.getRequestStatus().equalsIgnoreCase("Completed") ||
                selectedRequest.getRequestStatus().equalsIgnoreCase("Closed")) {
            notification("Cannot delete Completed or Closed requests.");
            return;
        }


        if (selectedRequest.getRequestStatus().equalsIgnoreCase("In Process") &&
                selectedRequest.getCustomerRequestStatus().equalsIgnoreCase("Confirmed")) {
            notification("Cannot delete Confirmed In-Process requests.");
            return;
        }


        if (selectedRequest.getRequestStatus().equalsIgnoreCase("Rejected")) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION,
                    "Are you sure you want to delete this Rejected request?");
            Optional<ButtonType> response = alert.showAndWait();
            deleteTheSelectedRequest(response, selectedRequest);
            return;
        }


        if (selectedRequest.getRequestStatus().equalsIgnoreCase("In Process")) {
            boolean notQualified = selectedRequest.getQualifiedCustomerOrNot().equalsIgnoreCase("No");
            boolean noAgreement = selectedRequest.getAgreesToAllTermsAndConditions().equalsIgnoreCase("No");
            boolean documentsMissing = selectedRequest.getAllDocumentsProvided().equalsIgnoreCase("No");

            if (notQualified || noAgreement || documentsMissing) {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION,
                        "This request is In-Process but does not meet requirements. Are you sure you want to delete it?");
                Optional<ButtonType> response = alert.showAndWait();
                deleteTheSelectedRequest(response, selectedRequest);
            } else {
                notification("This In-Process request meets requirements and cannot be deleted.");
            }
        }

    }


    public void deleteTheSelectedRequest(Optional<ButtonType> response, RequestTM selectedRequest) {

        if (response.isPresent() && response.get() == ButtonType.OK) {
            try {
                String result = requestBO.delete(new RequestDTO().toDTO(selectedRequest));
                notification(result);
                loadTable();
            } catch (SQLException | ClassNotFoundException e) {
                e.printStackTrace();
                System.err.println("Error while deleting the request: " + e.getMessage());
                notification("An error occurred while deleting the request. Please try again or contact support.");
            }
        }
    }



    @FXML
    void editOnAction(ActionEvent event) {

        RequestTM selectedRequest = table.getSelectionModel().getSelectedItem();

        if(selectedRequest==null){
            return;
        }

        if(selectedRequest.getRequestStatus().equals("Completed") || selectedRequest.getRequestStatus().equals("Closed")){
            notification("This Is Completed Or Closed Request, Can't Edit");
            return;
        }

        try{
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/EditRentRequest.fxml"));
            Parent root = fxmlLoader.load();
            EditRentRequestController editRentRequestController = fxmlLoader.getController();
            editRentRequestController.setSelectedRequestData(selectedRequest);
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Error while loading Edit Rent Request Form: " + e.getMessage());
            notification("An error occurred while loading Edit Rent Request Form. Please try again or contact support.");
        }

    }



    @FXML
    void refreshOnAction(ActionEvent event) {

        clean();
    }


    @FXML
    void searchOnAction(ActionEvent event) {

        ObservableList<RequestTM> searchedRequests = FXCollections.observableArrayList();

        String selectedRequestId = requestIdCmb.getValue();
        String selectedCustomerId = customerIdCmb.getValue();
        String selectedRentOrBuy = rentOrBuyCmb.getValue();
        String selectedHouseType = houseTypeCmb.getValue();

        boolean requestIdSelected = selectedRequestId != null && !selectedRequestId.equals("Select");
        boolean customerIdSelected = selectedCustomerId != null && !selectedCustomerId.equals("Select");
        boolean rentOrBuySelected = selectedRentOrBuy != null && !selectedRentOrBuy.equals("Select");
        boolean houseTypeSelected = selectedHouseType != null && !selectedHouseType.equals("Select");

        if (requestIdSelected) {
            ObservableList<RequestTM> requestsById = getRequestsByRequestId(selectedRequestId);

            if (requestsById.isEmpty()) {
                table.setItems(requestsById);
            } else {
                searchedRequests.addAll(requestsById);

                if (customerIdSelected) {
                    ObservableList<RequestTM> filteredByCustomerId = filterRequestsByCustomerId(searchedRequests, selectedCustomerId);
                    searchedRequests.clear();
                    searchedRequests.addAll(filteredByCustomerId);
                }

                if (rentOrBuySelected) {
                    ObservableList<RequestTM> filteredByRentOrBuy = filterRequestsByRentOrBuy(searchedRequests, selectedRentOrBuy);
                    searchedRequests.clear();
                    searchedRequests.addAll(filteredByRentOrBuy);
                }

                if (houseTypeSelected) {
                    ObservableList<RequestTM> filteredByHouseType = filterRequestsByHouseType(searchedRequests, selectedHouseType);
                    searchedRequests.clear();
                    searchedRequests.addAll(filteredByHouseType);
                }

                table.setItems(searchedRequests);
            }

        }
        else if (customerIdSelected || rentOrBuySelected || houseTypeSelected) {
            ObservableList<RequestTM> allRequests = tableData;
            searchedRequests.addAll(allRequests);

            if (customerIdSelected) {
                searchedRequests = filterRequestsByCustomerId(searchedRequests, selectedCustomerId);
            }

            if (rentOrBuySelected) {
                searchedRequests = filterRequestsByRentOrBuy(searchedRequests, selectedRentOrBuy);
            }

            if (houseTypeSelected) {
                searchedRequests = filterRequestsByHouseType(searchedRequests, selectedHouseType);
            }

            table.setItems(searchedRequests);

        } else {
            ObservableList<RequestTM> allRequests = tableData;
            table.setItems(allRequests);
        }
    }


    private ObservableList<RequestTM> getRequestsByRequestId(String requestId) {
        return FXCollections.observableArrayList(
                tableData.stream()
                        .filter(request -> request.getRequestId().equalsIgnoreCase(requestId))
                        .toList()
        );
    }

    private ObservableList<RequestTM> filterRequestsByCustomerId(ObservableList<RequestTM> requests, String customerId) {
        return FXCollections.observableArrayList(
                requests.stream()
                        .filter(request -> request.getCustomerId().equalsIgnoreCase(customerId))
                        .toList()
        );
    }

    private ObservableList<RequestTM> filterRequestsByRentOrBuy(ObservableList<RequestTM> requests, String rentOrBuy) {
        return FXCollections.observableArrayList(
                requests.stream()
                        .filter(request -> request.getRentOrBuy().equalsIgnoreCase(rentOrBuy))
                        .toList()
        );
    }

    private ObservableList<RequestTM> filterRequestsByHouseType(ObservableList<RequestTM> requests, String houseType) {
        return FXCollections.observableArrayList(
                requests.stream()
                        .filter(request -> request.getHouseType().equalsIgnoreCase(houseType))
                        .toList()
        );

    }


    @FXML
    void sortByCmbOnAction(ActionEvent event) {

        String sortType = sortByCmb.getSelectionModel().getSelectedItem();
        ObservableList<RequestTM> requestTMS = FXCollections.observableArrayList(tableData);

        if (sortType == null) {
            return;
        }

        Comparator<RequestTM> comparator = null;

        switch (sortType) {
            case "Request ID (Ascending)":
                comparator = Comparator.comparing(RequestTM::getRequestId);
                break;

            case "Request ID (Descending)":
                comparator = Comparator.comparing(RequestTM::getRequestId).reversed();
                break;

            case "Customer ID (Ascending)":
                comparator = Comparator.comparing(RequestTM::getCustomerId);
                break;

            case "Customer ID (Descending)":
                comparator = Comparator.comparing(RequestTM::getCustomerId).reversed();
                break;

            case "Lease Turn (Ascending)":
                comparator = Comparator.comparing(RequestTM::getLeaseTurnDesire);
                break;

            case "Lease Turn (Descending)":
                comparator = Comparator.comparing(RequestTM::getLeaseTurnDesire).reversed();
                break;

            default:
                break;
        }

        if (comparator != null) {
            FXCollections.sort(requestTMS, comparator);
            table.setItems(requestTMS);
        }
    }


    @FXML
    void tableRowsCmbOnAction(ActionEvent event) {

        Integer value = tableRowsCmb.getSelectionModel().getSelectedItem();

        if(value==null){
            return;
        }

        ObservableList<RequestTM> requestTMS = FXCollections.observableArrayList();

        for (int i=0; i<value; i++){
            requestTMS.add(tableData.get(i));
        }

        table.setItems(requestTMS);
    }



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        setColumnToTable();
        setValuesToTableRowCmb();
        setSortByCmbValues();
        setCustomerIdCmbValues();
        setRequestIdCmbValue();
        setRentOrBuyCmbValues();
        setRentOrBuyCmbValues();
        setHouseTypeCmbValues();
        requestTableSearch();
    }


    public void requestTableSearch() {

        FilteredList<RequestTM> filteredData = new FilteredList<>(tableData, b -> true);

        searchTxt.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(request -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                String lowerCaseFilter = newValue.toLowerCase();

                if (request.getRequestId().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else if (request.getCustomerId().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else if (request.getRentOrBuy().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else if (request.getHouseType().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else if (request.getAllDocumentsProvided().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else if (request.getQualifiedCustomerOrNot().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else if (request.getAgreesToAllTermsAndConditions().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else if (request.getIsPaymentsCompleted().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else if (request.getRequestStatus().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else if (request.getCustomerRequestStatus().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else if (request.getHouseId() != null && request.getHouseId().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                }
                return false;
            });
        });

        SortedList<RequestTM> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(table.comparatorProperty());
        table.setItems(sortedData);
    }



    public void setHouseTypeCmbValues(){

        ObservableList<String> houseTypes = FXCollections.observableArrayList();
        houseTypes.add("Select");
        try {
            ObservableList<HouseTypeDTO> houseTypeDTOS = requestBO.getAllHouseTypes();

            for(HouseTypeDTO x : houseTypeDTOS){
               houseTypes.add(x.getHouseType());
            }

            houseTypeCmb.setItems(houseTypes);
            houseTypeCmb.getSelectionModel().selectFirst();
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

    }


    public void setRentOrBuyCmbValues(){

        ObservableList<String> values = FXCollections.observableArrayList("Select","Rent","Buy");
        rentOrBuyCmb.setItems(values);
        rentOrBuyCmb.getSelectionModel().selectFirst();

    }


    public void setRequestIdCmbValue(){

        ObservableList<String> ids = FXCollections.observableArrayList();
        ids.add("Select");

        for(RequestTM x : tableData){
           ids.add(x.getRequestId());
        }

        requestIdCmb.setItems(ids);
        requestIdCmb.getSelectionModel().selectFirst();

    }


    public void setCustomerIdCmbValues(){

        try {
            ObservableList<String> ids = requestBO.getDistinctCustomers();
            customerIdCmb.setItems(ids);
            customerIdCmb.getSelectionModel().selectFirst();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

    }


    public void setSortByCmbValues(){

        ObservableList<String> sortTypes = FXCollections.observableArrayList("Sort By","Request ID (Ascending)","Request ID (Descending)","Customer ID (Ascending)","Customer ID (Descending)","Lease Turn (Ascending)","Lease Turn (Descending)");
        sortByCmb.setItems(sortTypes);
        sortByCmb.getSelectionModel().selectFirst();
    }


    public void setColumnToTable(){

        requestIdColumn.setCellValueFactory(new PropertyValueFactory<>("requestId"));
        customerIdColumn.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        rentOrBuyColumn.setCellValueFactory(new PropertyValueFactory<>("rentOrBuy"));
        houseTypeColumn.setCellValueFactory(new PropertyValueFactory<>("houseType"));
        leaseTurnDesireColumn.setCellValueFactory(new PropertyValueFactory<>("leaseTurnDesire"));
        allDocumentsProvidedColumn.setCellValueFactory(new PropertyValueFactory<>("allDocumentsProvided"));
        qualifiedCustomerOrNotColumn.setCellValueFactory(new PropertyValueFactory<>("qualifiedCustomerOrNot"));
        agreesToAllTermsAndConditionsColumn.setCellValueFactory(new PropertyValueFactory<>("agreesToAllTermsAndConditions"));
        isPaymentsCompletedColumn.setCellValueFactory(new PropertyValueFactory<>("isPaymentsCompleted"));
        customerRequestStatusColumn.setCellValueFactory(new PropertyValueFactory<>("customerRequestStatus"));
        requestStatusColumn.setCellValueFactory(new PropertyValueFactory<>("requestStatus"));
        houseIdColumn.setCellValueFactory(new PropertyValueFactory<>("houseId"));


        Callback<TableColumn<RequestTM, String>, TableCell<RequestTM, String>> cellFoctory = (TableColumn<RequestTM, String> param) -> {

            final TableCell<RequestTM, String> cell = new TableCell<RequestTM, String>() {
                @Override
                public void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);

                    if (empty) {
                        setGraphic(null);
                        setText(null);

                    } else {
                        Image image1 = new Image("C:\\Users\\PCWORLD\\IdeaProjects\\Semester_final\\src\\main\\resources\\assets\\image\\visibility.png");
                        Image image2 = new Image("C:\\Users\\PCWORLD\\IdeaProjects\\Semester_final\\src\\main\\resources\\assets\\image\\building.png");
                        Image image3 = new Image("C:\\Users\\PCWORLD\\IdeaProjects\\Semester_final\\src\\main\\resources\\assets\\image\\ethics (1).png");
                        Image image4 = new Image("C:\\Users\\PCWORLD\\IdeaProjects\\Semester_final\\src\\main\\resources\\assets\\image\\rent.png");
                        ImageView viewDetails = new ImageView();
                        viewDetails.setImage(image1);
                        viewDetails.setFitHeight(20);
                        viewDetails.setFitWidth(20);

                        ImageView searchHouse = new ImageView();
                        searchHouse.setImage(image2);
                        searchHouse.setFitHeight(20);
                        searchHouse.setFitWidth(20);

                        ImageView process = new ImageView();
                        process.setImage(image3);
                        process.setFitHeight(20);
                        process.setFitWidth(20);

                        ImageView complete = new ImageView();
                        complete.setImage(image4);
                        complete.setFitHeight(20);
                        complete.setFitWidth(20);


                        viewDetails.setStyle(" -fx-cursor: hand ;");
                        searchHouse.setStyle(" -fx-cursor: hand ;");
                        process.setStyle(" -fx-cursor: hand ;");
                        complete.setStyle(" -fx-cursor: hand ;");


                        viewDetails.setOnMouseClicked((MouseEvent event) -> {

                            RequestTM request = table.getSelectionModel().getSelectedItem();

                           if(request.getRentOrBuy().equals("Rent")){

                               try{
                                   FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/RentRequestDetails.fxml"));
                                   Parent root = fxmlLoader.load();
                                   RentRequestDetailsController rentRequestDetailsController = fxmlLoader.getController();
                                   rentRequestDetailsController.setSelectedRequestData(request);
                                   Scene scene = new Scene(root);
                                   Stage stage = new Stage();
                                   stage.setScene(scene);
                                   stage.show();

                               } catch (IOException e) {
                                   e.printStackTrace();
                                   System.err.println("Error while loading Rent Request Details Page: " + e.getMessage());
                                   notification("An error occurred while loading Rent Request Details Page. Please try again or contact support.");
                               }
                           }

                           else{

                               try{
                                   FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/PurchaseRequestDetails.fxml"));
                                   Parent root = fxmlLoader.load();
                                   PurchaseRequestDetailsController purchaseRequestDetailsController = fxmlLoader.getController();
                                   purchaseRequestDetailsController.setSelectedRequestData(request);
                                   Scene scene = new Scene(root);
                                   Stage stage = new Stage();
                                   stage.setScene(scene);
                                   stage.show();

                               } catch (IOException e) {
                                   e.printStackTrace();
                                   System.err.println("Error while loading Purchase Request Details Page: " + e.getMessage());
                                   notification("An error occurred while loading Purchase Request Details Page. Please try again or contact support.");
                               }

                           }

                        });

                        searchHouse.setOnMouseClicked((MouseEvent event) -> {

                            RequestTM request = table.getSelectionModel().getSelectedItem();

                            if(request.getRequestStatus().equals("Rejected") || request.getQualifiedCustomerOrNot().equals("No") || request.getAgreesToAllTermsAndConditions().equals("No") || request.getCustomerRequestStatus().equals("Canceled") || request.getRequestStatus().equals("Closed") || request.getRequestStatus().equals("Completed")){
                                return;
                            }

                            try{
                                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/RecommendedHouses.fxml"));
                                Parent root = fxmlLoader.load();
                                RecommendedHousesController recommendedHousesController = fxmlLoader.getController();
                                recommendedHousesController.setSelectedRequestData(request);
                                Scene scene = new Scene(root);
                                Stage stage = new Stage();
                                stage.setScene(scene);
                                stage.show();

                            } catch (IOException e) {
                                e.printStackTrace();
                                System.err.println("Error while loading Recommended Houses Page: " + e.getMessage());
                                notification("An error occurred while loading Recommended Houses Page. Please try again or contact support.");
                            }

                        });

                        process.setOnMouseClicked((MouseEvent event) -> {

                            RequestTM request = table.getSelectionModel().getSelectedItem();

                            if(request.getRequestStatus().equals("Completed")){

                                if(request.getRentOrBuy().equals("Rent")){

                                    try {
                                        String response = requestBO.addNewTenant(new RequestDTO().toDTO(request));
                                        loadTable();
                                        notification(response);

                                    } catch (SQLException | ClassNotFoundException e) {
                                        e.printStackTrace();
                                        System.err.println("Error while adding a new tenant: " + e.getMessage());
                                        notification("An error occurred while adding a new tenant. Please try again or contact support.");
                                    }
                                }

                                else{

                                    try {
                                        String response = requestBO.addNewOwner(new RequestDTO().toDTO(request));
                                        loadTable();
                                        notification(response);

                                    } catch (SQLException | ClassNotFoundException e) {
                                        e.printStackTrace();
                                        System.err.println("Error while adding a new owner: " + e.getMessage());
                                        notification("An error occurred while adding a new owner. Please try again or contact support.");
                                    }

                                }

                            }

                            else if(request.getRequestStatus().equals("Closed")){
                                return;
                            }

                            else{
                                notification("Not A Completed Request");
                            }

                        });


                        complete.setOnMouseClicked((MouseEvent event) -> {

                            RequestTM request = table.getSelectionModel().getSelectedItem();

                            if(request.getRequestStatus().equals("Completed")){
                                return;
                            }

                            if(request.getCustomerRequestStatus().equals("Confirmed") && request.getAllDocumentsProvided().equals("Yes") && request.getAgreesToAllTermsAndConditions().equals("Yes") && request.getIsPaymentsCompleted().equals("Yes") && request.getQualifiedCustomerOrNot().equals("Yes") && !request.getHouseId().equals("-")){

                                try {
                                    RequestDTO requestDTO = new RequestDTO();
                                    requestDTO.setRequestId(request.getRequestId());
                                    requestDTO.setRequestStatus("Completed");
                                    boolean response = requestBO.update(requestDTO);

                                    if(response){
                                      notification("The change request status has been successfully updated to \"Completed\" You can now proceed with further actions.");
                                    }
                                    else{
                                        notification("Failed To Update The Request Status As \"Completed\", Try Again Later");
                                    }
                                    loadTable();
                                }
                                catch (SQLException | ClassNotFoundException e) {
                                    e.printStackTrace();
                                    System.err.println("Error while making request status \"Completed\": " + e.getMessage());
                                    notification("An error occurred while making request status \"Completed\". Please try again or contact support.");
                                }
                            }
                            else if(request.getRequestStatus().equals("Rejected")){
                                notification("Rejected Request");
                            }
                            else if(request.getCustomerRequestStatus().equals("Canceled")){

                                notification("The customer has canceled this request,Please mark this request as rejected");
                            }
                            else if(request.getQualifiedCustomerOrNot().equals("No")){

                                notification("Not A Qualified Customer,Please mark this request as rejected");
                            }
                            else if(request.getAgreesToAllTermsAndConditions().equals("No")){

                                notification("Customer Not Agreed With All Terms & Conditions,Please mark this request as rejected");
                            }
                            else{
                                notification("Can't Make This Request Complete, Because This Request Not Complete All Requires");
                            }


                        });

                        HBox manageBtn = new HBox(viewDetails,searchHouse,complete,process);


                        manageBtn.setAlignment(Pos.CENTER);
                        manageBtn.setSpacing(3);
                        manageBtn.setPadding(new Insets(2));

                        HBox.setMargin(viewDetails, new Insets(2, 2, 0, 3));
                        HBox.setMargin(searchHouse, new Insets(2, 3, 0, 3));
                        HBox.setMargin(complete, new Insets(2, 3, 0, 3));
                        HBox.setMargin(process, new Insets(2, 3, 0, 3));

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
            ObservableList<RequestDTO> requestDTOS = requestBO.getAll();
            ObservableList<RequestTM> requestTMS = FXCollections.observableArrayList();

            for(RequestDTO x : requestDTOS){
                requestTMS.add(new RequestTM().toTM(x));
            }
            tableData = requestTMS;
            System.out.println("Number of requests: " + tableData.size());
            table.setItems(tableData);
        }
        catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            System.err.println("Error while loading the table data: " + e.getMessage());
            notification("An error occurred while loading the table data. Please try again or contact support.");
        }

    }


    public void setValuesToTableRowCmb(){

        int count = 0;
        ObservableList<Integer> tableRows = FXCollections.observableArrayList();

        for (RequestTM x : tableData){
            count++;
            tableRows.add(count);

        }

        tableRowsCmb.setItems(tableRows);
        tableRowsCmb.getSelectionModel().selectLast();
    }


    public void getOnlyClosedRequest() {

        try {
            ObservableList<RequestDTO> requestDTOS = requestBO.getOnlyClosedRequests();
            ObservableList<RequestTM> requestTMS = FXCollections.observableArrayList();

            for(RequestDTO x : requestDTOS){
                requestTMS.add(new RequestTM().toTM(x));
            }
            tableData = requestTMS;
            table.setItems(tableData);
        }
        catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            System.err.println("Error while loading the table data: " + e.getMessage());
            notification("An error occurred while loading the table data. Please try again or contact support.");
        }

        setValuesToTableRowCmb();
    }


    public void clean(){

        loadTable();
        setValuesToTableRowCmb();
        setSortByCmbValues();
        setCustomerIdCmbValues();
        setRequestIdCmbValue();
        setRentOrBuyCmbValues();
        setRentOrBuyCmbValues();
        setHouseTypeCmbValues();
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
