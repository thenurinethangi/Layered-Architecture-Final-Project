package com.example.test.controller;

import com.example.test.bo.BOFactory;
import com.example.test.bo.custom.HouseReturnBO;
import com.example.test.dto.HouseReturnDTO;
import com.example.test.view.tdm.HouseReturnTM;
import com.example.test.dao.custom.impl.HouseReturnDAOImpl;
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
import javafx.stage.Stage;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Comparator;
import java.util.ResourceBundle;

public class HouseReturnController implements Initializable {
    @FXML
    private Button editbtn;

    @FXML
    private TableView<HouseReturnTM> table;

    @FXML
    private TableColumn<HouseReturnTM, String> returnNoColumn;

    @FXML
    private TableColumn<HouseReturnTM, String> reasonColumn;

    @FXML
    private TableColumn<HouseReturnTM, String> dateColumn;

    @FXML
    private TableColumn<HouseReturnTM, String> tenantIdColumn;

    @FXML
    private TableColumn<HouseReturnTM, String> houseIdColumn;

    @FXML
    private TableColumn<HouseReturnTM, String> refundedAmountColumn;

    @FXML
    private TableColumn<HouseReturnTM, String> expenseNoColumn;

    @FXML
    private Button addNewReturnBtn;

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
    private ComboBox<String> returnNoCmb;

    @FXML
    private ComboBox<String> expenseCmb;

    @FXML
    private ComboBox<String> tenantIdCmb;

    @FXML
    private ComboBox<String> houseIdCmb;

    @FXML
    private TextField searchTxt;


    private ObservableList<HouseReturnTM> tableData;
    private final HouseReturnBO houseReturnBO = (HouseReturnBO) BOFactory.getInstance().getBO(BOFactory.BOType.HOUSERETURN);



    @FXML
    void addNewReturnOnAction(ActionEvent event) {

        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/AddNewHouseReturn.fxml"));
            Parent root = fxmlLoader.load();
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.show();
        }
        catch (IOException e){
            e.printStackTrace();
            System.err.println("Error while loading the Add New House Return form: " + e.getMessage());
            notification("An error occurred while loading the Add New House Return form. Please try again or contact support.");
        }

    }


    @FXML
    void refreshOnAction(ActionEvent event) {

        clean();

    }

    @FXML
    void searchOnAction(ActionEvent event) {

        ObservableList<HouseReturnTM> searchedReturns = FXCollections.observableArrayList();

        String selectedReturnNo = returnNoCmb.getValue();
        String selectedTenantId = tenantIdCmb.getValue();
        String selectedHouseId = houseIdCmb.getValue();
        String selectedExpenseNo = expenseCmb.getValue();

        boolean returnNoSelected = selectedReturnNo != null && !selectedReturnNo.equals("Select");
        boolean tenantIdSelected = selectedTenantId != null && !selectedTenantId.equals("Select");
        boolean houseIdSelected = selectedHouseId != null && !selectedHouseId.equals("Select");
        boolean expenseNoSelected = selectedExpenseNo != null && !selectedExpenseNo.equals("Select");


        if (returnNoSelected) {
            ObservableList<HouseReturnTM> returnsByReturnNo = getReturnByReturnNo(selectedReturnNo);

            if (returnsByReturnNo.isEmpty()) {
                table.setItems(returnsByReturnNo);
            } else {
                searchedReturns.addAll(returnsByReturnNo);

                if (tenantIdSelected) {
                    ObservableList<HouseReturnTM> filteredByTenantId = filterReturnsByTenantId(searchedReturns, selectedTenantId);
                    searchedReturns.clear();
                    searchedReturns.addAll(filteredByTenantId);
                }

                if (houseIdSelected) {
                    ObservableList<HouseReturnTM> filteredByHouseId = filterReturnsByHouseId(searchedReturns, selectedHouseId);
                    searchedReturns.clear();
                    searchedReturns.addAll(filteredByHouseId);
                }

                if (expenseNoSelected) {
                    ObservableList<HouseReturnTM> filteredByExpenseNo = filterReturnsByExpenseNo(searchedReturns, selectedExpenseNo);
                    searchedReturns.clear();
                    searchedReturns.addAll(filteredByExpenseNo);
                }

                table.setItems(searchedReturns);
            }


        } else if (tenantIdSelected || houseIdSelected || expenseNoSelected) {
            ObservableList<HouseReturnTM> allReturns = tableData;
            searchedReturns.addAll(allReturns);

            if (tenantIdSelected) {
                searchedReturns = filterReturnsByTenantId(searchedReturns, selectedTenantId);
            }

            if (houseIdSelected) {
                searchedReturns = filterReturnsByHouseId(searchedReturns, selectedHouseId);
            }

            if (expenseNoSelected) {
                searchedReturns = filterReturnsByExpenseNo(searchedReturns, selectedExpenseNo);
            }

            table.setItems(searchedReturns);

        } else {
            ObservableList<HouseReturnTM> allReturns = tableData;
            table.setItems(allReturns);
        }
    }



    private ObservableList<HouseReturnTM> getReturnByReturnNo(String returnNo) {
        return FXCollections.observableArrayList(
                tableData.stream()
                        .filter(returnHouse -> returnHouse.getReturnNo().equalsIgnoreCase(returnNo))
                        .toList()
        );
    }


    private ObservableList<HouseReturnTM> filterReturnsByTenantId(ObservableList<HouseReturnTM> returns, String tenantId) {
        return FXCollections.observableArrayList(
                returns.stream()
                        .filter(returnHouse -> returnHouse.getTenantId().equalsIgnoreCase(tenantId))
                        .toList()
        );
    }


    private ObservableList<HouseReturnTM> filterReturnsByHouseId(ObservableList<HouseReturnTM> returns, String houseId) {
        return FXCollections.observableArrayList(
                returns.stream()
                        .filter(returnHouse -> returnHouse.getHouseId().equalsIgnoreCase(houseId))
                        .toList()
        );
    }


    private ObservableList<HouseReturnTM> filterReturnsByExpenseNo(ObservableList<HouseReturnTM> returns, String expenseNo) {
        return FXCollections.observableArrayList(
                returns.stream()
                        .filter(returnHouse -> returnHouse.getExpenseNo() != null && returnHouse.getExpenseNo().equalsIgnoreCase(expenseNo))
                        .toList()
        );
    }



    @FXML
    void sortCmbOnAction(ActionEvent event) {

        String sortType = sortCmb.getSelectionModel().getSelectedItem();
        ObservableList<HouseReturnTM> houseReturnTMS = FXCollections.observableArrayList(tableData);

        if (sortType == null) {
            return;
        }

        Comparator<HouseReturnTM> comparator = null;

        switch (sortType) {
            case "Return NO (Ascending)":
                comparator = Comparator.comparing(HouseReturnTM::getReturnNo);
                break;

            case "Return NO (Descending)":
                comparator = Comparator.comparing(HouseReturnTM::getReturnNo).reversed();
                break;

            case "Date (Ascending)":
                comparator = Comparator.comparing(HouseReturnTM::getDate);
                break;

            case "Date (Descending)":
                comparator = Comparator.comparing(HouseReturnTM::getDate).reversed();
                break;

            case "Refund Amount (Ascending)":
                comparator = Comparator.comparing(HouseReturnTM::getRefundedAmount);
                break;

            case "Refund Amount (Descending)":
                comparator = Comparator.comparing(HouseReturnTM::getRefundedAmount).reversed();
                break;

            case "Tenant ID (Ascending)":
                comparator = Comparator.comparing(HouseReturnTM::getTenantId);
                break;

            case "Tenant ID (Descending)":
                comparator = Comparator.comparing(HouseReturnTM::getTenantId).reversed();
                break;

            default:
                break;
        }

        if (comparator != null) {
            FXCollections.sort(houseReturnTMS, comparator);
            table.setItems(houseReturnTMS);
        }
    }



    @FXML
    void tableRowsCmbOnAction(ActionEvent event) {

        Integer value = tableRowsCmb.getSelectionModel().getSelectedItem();

        if(value==null){
            return;
        }

        ObservableList<HouseReturnTM> houseReturnTMS = FXCollections.observableArrayList();

        for (int i=0; i<value; i++){
            houseReturnTMS.add(tableData.get(i));
        }

        table.setItems(houseReturnTMS);

    }



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        setTableColumnsValues();
        loadTable();
        setRowCmbValues();
        setReturnNoCmbValues();
        setExpenseCmbValues();
        setTenantIdCmbValues();
        setHouseIdCmbValues();
        setSortCmbValues();
        tableSearch();

    }


    public void tableSearch() {

        FilteredList<HouseReturnTM> filteredData = new FilteredList<>(tableData, b -> true);

        searchTxt.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(returnHouse -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                String lowerCaseFilter = newValue.toLowerCase();

                if (returnHouse.getReturnNo().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else if (returnHouse.getReason().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else if (String.valueOf(returnHouse.getDate()).toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else if (returnHouse.getTenantId().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else if (returnHouse.getHouseId().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else if (String.valueOf(returnHouse.getRefundedAmount()).contains(lowerCaseFilter)) {
                    return true;
                } else if (returnHouse.getExpenseNo() != null && returnHouse.getExpenseNo().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                }
                return false;
            });
        });

        SortedList<HouseReturnTM> sortedData = new SortedList<>(filteredData);

        sortedData.comparatorProperty().bind(table.comparatorProperty());

        table.setItems(sortedData);
    }


    public void setTableColumnsValues(){

        returnNoColumn.setCellValueFactory(new PropertyValueFactory<>("returnNo"));
        reasonColumn.setCellValueFactory(new PropertyValueFactory<>("reason"));
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
        tenantIdColumn.setCellValueFactory(new PropertyValueFactory<>("tenantId"));
        houseIdColumn.setCellValueFactory(new PropertyValueFactory<>("houseId"));
        refundedAmountColumn.setCellValueFactory(new PropertyValueFactory<>("refundedAmount"));
        expenseNoColumn.setCellValueFactory(new PropertyValueFactory<>("expenseNo"));

    }


    public void loadTable(){

        try {
            ObservableList<HouseReturnDTO> houseReturnDTOS = houseReturnBO.getAll();
            ObservableList<HouseReturnTM> houseReturnTMS = FXCollections.observableArrayList();

            for(HouseReturnDTO x : houseReturnDTOS){
                houseReturnTMS.add(new HouseReturnTM().toTM(x));
            }
            tableData = houseReturnTMS;
            table.setItems(tableData);

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            System.err.println("Error while loading the table data: " + e.getMessage());
            notification("An error occurred while loading the table data. Please try again or contact support.");
        }

    }



    public void setSortCmbValues(){

        ObservableList<String> sortTypes = FXCollections.observableArrayList("Sort By","Return NO (Ascending)","Return NO (Descending)","Date (Ascending)","Date (Descending)","Refund Amount (Ascending)","Refund Amount (Descending)","Tenant ID (Ascending)","Tenant ID (Descending)");
        sortCmb.setItems(sortTypes);
        sortCmb.getSelectionModel().selectFirst();

    }


    public void setRowCmbValues(){

        ObservableList<Integer> rows = FXCollections.observableArrayList();
        int count = 0;

        for (HouseReturnTM x : tableData){
            count++;
            rows.add(count);

        }

        tableRowsCmb.setItems(rows);
        tableRowsCmb.getSelectionModel().selectLast();

    }


    public void setReturnNoCmbValues(){

        ObservableList<String> returnNos = FXCollections.observableArrayList();
        returnNos.add("Select");

        for(HouseReturnTM x : tableData){
            returnNos.add(x.getReturnNo());
        }

        returnNoCmb.setItems(returnNos);
        returnNoCmb.getSelectionModel().selectFirst();

    }


    public void setExpenseCmbValues(){

        ObservableList<String> expenseNos = FXCollections.observableArrayList();
        expenseNos.add("Select");

        for(HouseReturnTM x : tableData){
            if(!x.getExpenseNo().equals("N/A")) {
                expenseNos.add(x.getExpenseNo());
            }
        }

        expenseCmb.setItems(expenseNos);
        expenseCmb.getSelectionModel().selectFirst();

    }


    public void setTenantIdCmbValues(){

        try {
            ObservableList<String> distinctTenantIds = houseReturnBO.getAllDistinctTenantIds();
            tenantIdCmb.setItems(distinctTenantIds);
            tenantIdCmb.getSelectionModel().selectFirst();
        }
        catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            System.err.println("Error while setting values to tenant id combo box: " + e.getMessage());
            notification("An error occurred while setting values to tenant id combo box. Please try again or contact support.");
        }
    }


    public void setHouseIdCmbValues(){

        try {
            ObservableList<String> distinctHouseIds = houseReturnBO.getAllDistinctHouseIds();
            houseIdCmb.setItems(distinctHouseIds);
            houseIdCmb.getSelectionModel().selectFirst();
        }
        catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            System.err.println("Error while setting values to house id combo box: " + e.getMessage());
            notification("An error occurred while setting values to house id combo box. Please try again or contact support.");
        }
    }


    public void clean(){

        loadTable();
        setRowCmbValues();
        setReturnNoCmbValues();
        setExpenseCmbValues();
        setTenantIdCmbValues();
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





