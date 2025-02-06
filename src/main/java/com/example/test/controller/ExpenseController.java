package com.example.test.controller;

import com.example.test.bo.BOFactory;
import com.example.test.bo.custom.ExpenseBO;
import com.example.test.dto.ExpenseDTO;
import com.example.test.view.tdm.ExpenseTM;
import com.example.test.dao.custom.impl.ExpenseDAOImpl;
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
import java.time.LocalDate;
import java.util.Comparator;
import java.util.Optional;
import java.util.ResourceBundle;

public class ExpenseController implements Initializable {

    @FXML
    private TableView<ExpenseTM> table;

    @FXML
    private TableColumn<ExpenseTM, String> expenseNoColumn;

    @FXML
    private TableColumn<ExpenseTM, String> descriptionColumn;

    @FXML
    private TableColumn<ExpenseTM, Double> amountColumn;

    @FXML
    private TableColumn<ExpenseTM, String> dateColumn;

    @FXML
    private TableColumn<ExpenseTM, String> requestNoColumn;

    @FXML
    private Button addNewExpenseBtn;

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
    private ComboBox<String> expenseNoCmb;

    @FXML
    private ComboBox<String> requestNoCmb;

    @FXML
    private TextField searchTxt;


    private final ExpenseBO expenseBO = (ExpenseBO) BOFactory.getInstance().getBO(BOFactory.BOType.EXPENSE);
    private ObservableList<ExpenseTM> tableData;



    @FXML
    void addNewExpenseOnAction(ActionEvent event) {

        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/AddNewExpense.fxml"));
            Parent root = fxmlLoader.load();
            Scene scene = new Scene(root);
            Stage s1 = new Stage();
            s1.setScene(scene);
            s1.show();
        }
        catch (IOException e){
            e.printStackTrace();
            System.err.println("Error while loading Add New Expense form: " + e.getMessage());
            notification("An error occurred while loading Add New Expense form. Please try again or contact support.");
        }
    }


    @FXML
    void deleteOnAction(ActionEvent event) {

        ExpenseTM selectedExpense = table.getSelectionModel().getSelectedItem();

        if(selectedExpense==null){
            return;
        }

        if(selectedExpense.getRequestNo().equals("N/A")){
           return;
        }

        LocalDate currentDate = LocalDate.now();
        LocalDate date = LocalDate.parse(selectedExpense.getDate());
        int recentDays = 60;

        boolean isDateRecent = !date.isBefore(currentDate.minusDays(recentDays));

        if (isDateRecent) {

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation Dialog");
            alert.setHeaderText("Please Confirm First");
            alert.setContentText("Are you sure you want to delete this expense?");

            ButtonType buttonYes = new ButtonType("Yes");
            ButtonType buttonCancel = new ButtonType("Cancel");

            alert.getButtonTypes().setAll(buttonYes, buttonCancel);
            Optional<ButtonType> result = alert.showAndWait();

            if (result.isPresent() && result.get() == buttonYes) {

                try {
                    String response = expenseBO.delete(new ExpenseDTO().toDTO(selectedExpense));
                    notification(response);
                    loadTable();

                }
                catch (SQLException | ClassNotFoundException e) {
                    e.printStackTrace();
                    System.err.println("Error while deleting the expense: " + e.getMessage());
                    notification("An error occurred while deleting the Expense No: "+selectedExpense.getExpenseNo()+", Please try again or contact support.");
                }

            } else {
                table.getSelectionModel().clearSelection();
            }

        } else {

            notification("The selected expense is not recent and cannot be deleted, as the associated process may have already been completed. Reverting is not an option.");
            return;
        }
    }


    @FXML
    void refreshOnAction(ActionEvent event) {

        clean();
    }


    @FXML
    void searchOnAction(ActionEvent event) {

        ObservableList<ExpenseTM> searchedExpenses = FXCollections.observableArrayList();

        String selectedExpenseNo = expenseNoCmb.getSelectionModel().getSelectedItem();
        String selectedRequestNo = requestNoCmb.getSelectionModel().getSelectedItem();

        boolean expenseNoSelected = selectedExpenseNo != null && !selectedExpenseNo.equals("Select");
        boolean requestNoSelected = selectedRequestNo != null && !selectedRequestNo.equals("Select");

        if (expenseNoSelected) {
            ObservableList<ExpenseTM> expensesByExpenseNo = getExpenseByExpenseNo(selectedExpenseNo);

            if (expensesByExpenseNo.isEmpty()) {
                table.setItems(expensesByExpenseNo);
            } else {
                searchedExpenses.addAll(expensesByExpenseNo);

                if (requestNoSelected) {
                    ObservableList<ExpenseTM> filteredByRequestNo = filterExpensesByRequestNo(searchedExpenses, selectedRequestNo);
                    searchedExpenses.clear();
                    searchedExpenses.addAll(filteredByRequestNo);
                }

                table.setItems(searchedExpenses);
            }

        } else if (requestNoSelected) {
            ObservableList<ExpenseTM> allExpenses = tableData;
            searchedExpenses.addAll(allExpenses);

            searchedExpenses = filterExpensesByRequestNo(searchedExpenses, selectedRequestNo);
            table.setItems(searchedExpenses);

        } else {
            ObservableList<ExpenseTM> allExpenses = tableData;
            table.setItems(allExpenses);
        }
    }



    private ObservableList<ExpenseTM> getExpenseByExpenseNo(String expenseNo) {
        return FXCollections.observableArrayList(
                tableData.stream()
                        .filter(expense -> expense.getExpenseNo().equalsIgnoreCase(expenseNo))
                        .toList()
        );
    }


    private ObservableList<ExpenseTM> filterExpensesByRequestNo(ObservableList<ExpenseTM> expenses, String requestNo) {
        return FXCollections.observableArrayList(
                expenses.stream()
                        .filter(expense -> expense.getRequestNo() != null && expense.getRequestNo().equalsIgnoreCase(requestNo))
                        .toList()
        );

    }


    @FXML
    void sortCmbOnAction(ActionEvent event) {

        String sortType = sortCmb.getSelectionModel().getSelectedItem();
        ObservableList<ExpenseTM> expenseTMS = FXCollections.observableArrayList(tableData);

        if (sortType == null) {
            return;
        }

        Comparator<ExpenseTM> comparator = null;

        switch (sortType) {
            case "Expense No (Ascending)":
                comparator = Comparator.comparing(ExpenseTM::getExpenseNo);
                break;

            case "Expense No (Descending)":
                comparator = Comparator.comparing(ExpenseTM::getExpenseNo).reversed();
                break;

            case "Amount (Ascending)":
                comparator = Comparator.comparing(ExpenseTM::getAmount);
                break;

            case "Amount (Descending)":
                comparator = Comparator.comparing(ExpenseTM::getAmount).reversed();
                break;

            case "Date (Ascending)":
                comparator = Comparator.comparing(ExpenseTM::getDate);
                break;

            case "Date (Descending)":
                comparator = Comparator.comparing(ExpenseTM::getDate).reversed();
                break;

            default:
                break;
        }

        if (comparator != null) {
            FXCollections.sort(expenseTMS, comparator);
            table.setItems(expenseTMS);
        }
    }



    @FXML
    void tableRowsCmbOnAction(ActionEvent event) {

        Integer value = tableRowsCmb.getSelectionModel().getSelectedItem();

        if(value==null){
            return;
        }

        ObservableList<ExpenseTM> expenseTMS = FXCollections.observableArrayList();

        for (int i=0; i<value; i++){
            expenseTMS.add(tableData.get(i));
        }

        table.setItems(expenseTMS);
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        setTableColumns();
        loadTable();
        setRowCmbValues();
        setSortCmbValues();
        setExpenseNoCmbValues();
        setRequestNoCmbValues();
        tableSearch();
    }


    public void tableSearch(){

        FilteredList<ExpenseTM> filteredData = new FilteredList<>(tableData, b -> true);

        searchTxt.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(expense -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                String lowerCaseFilter = newValue.toLowerCase();

                if (expense.getExpenseNo().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else if (expense.getDescription().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else if (String.valueOf(expense.getAmount()).contains(lowerCaseFilter)) {
                    return true;
                } else if (expense.getRequestNo() != null && expense.getRequestNo().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else if (String.valueOf(expense.getDate()).contains(lowerCaseFilter)) {
                    return true;
                }
                return false;
            });
        });

        SortedList<ExpenseTM> sortedData = new SortedList<>(filteredData);

        sortedData.comparatorProperty().bind(table.comparatorProperty());

        table.setItems(sortedData);

    }


    public void setRequestNoCmbValues(){

        try {
            ObservableList<String> requestNos = expenseBO.getAllDistinctMaintenanceRequestNos();
            requestNoCmb.setItems(requestNos);
            requestNoCmb.getSelectionModel().selectFirst();
        }
        catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            System.err.println("Error while setting request number combo box values: " + e.getMessage());
            notification("An error occurred while setting request number combo box values. Please try again or contact support.");
        }

    }


    public void setExpenseNoCmbValues(){

        ObservableList<String> expenseNos = FXCollections.observableArrayList();
        expenseNos.add("Select");

        for (ExpenseTM x : tableData){
            expenseNos.add(x.getExpenseNo());

        }

        expenseNoCmb.setItems(expenseNos);
        expenseNoCmb.getSelectionModel().selectFirst();

    }


    public void setRowCmbValues(){

        ObservableList<Integer> rows = FXCollections.observableArrayList();
        int count = 0;

        for (ExpenseTM x : tableData){
            count++;
            rows.add(count);

        }

        tableRowsCmb.setItems(rows);
        tableRowsCmb.getSelectionModel().selectLast();

    }

    public void setSortCmbValues(){

        ObservableList<String> sortTypes = FXCollections.observableArrayList("Sort By","Expense No (Ascending)","Expense No (Descending)","Amount (Ascending)","Amount (Descending)","Date (Ascending)","Date (Descending)");
        sortCmb.setItems(sortTypes);
        sortCmb.getSelectionModel().selectFirst();

    }

    public void setTableColumns(){

        expenseNoColumn.setCellValueFactory(new PropertyValueFactory<>("expenseNo"));
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
        amountColumn.setCellValueFactory(new PropertyValueFactory<>("amount"));
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
        requestNoColumn.setCellValueFactory(new PropertyValueFactory<>("requestNo"));

    }


    public void loadTable(){

        try {
            ObservableList<ExpenseDTO> expenseDTOS = expenseBO.getAll();
            ObservableList<ExpenseTM> expenseTMS = FXCollections.observableArrayList();

            for(ExpenseDTO x : expenseDTOS){
                expenseTMS.add(new ExpenseTM().toTM(x));
            }
            tableData = expenseTMS;
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
        setSortCmbValues();
        setExpenseNoCmbValues();
        setRequestNoCmbValues();
        table.getSelectionModel().clearSelection();
        searchTxt.clear();
        sortCmb.getSelectionModel().selectFirst();
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
