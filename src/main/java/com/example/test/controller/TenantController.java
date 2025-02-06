package com.example.test.controller;

import com.example.test.bo.BOFactory;
import com.example.test.bo.custom.TenantBO;
import com.example.test.db.DBConnection;
import com.example.test.dto.TenantDTO;
import com.example.test.view.tdm.TenantTM;
import com.example.test.dao.custom.impl.TenantDAOImpl;
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
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;
import org.controlsfx.control.Notifications;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

public class TenantController implements Initializable {

    @FXML
    private Button editbtn;

    @FXML
    private TableView<TenantTM> table;

    @FXML
    private TableColumn<TenantTM, String> tenantIdColumn;

    @FXML
    private TableColumn<TenantTM, String> nameColumn;

    @FXML
    private TableColumn<TenantTM, String> phoneNoColumn;

    @FXML
    private TableColumn<TenantTM, Integer> membersCountColumn;

    @FXML
    private TableColumn<TenantTM, String> startDateColumn;

    @FXML
    private TableColumn<TenantTM, Double> monthlyRentColumn;

    @FXML
    private TableColumn<TenantTM, String> lastPaidMonthColumn;

    @FXML
    private TableColumn<TenantTM, String> houseIdColumn;

    @FXML
    private TableColumn<TenantTM, String> actionColumn;

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
    private ComboBox<String> monthsCmb;

    @FXML
    private TextField nameTxt;

    @FXML
    private ListView<String> nameList;

    @FXML
    private TextField membersCountTxt;

    @FXML
    private ComboBox<String> tenantIdCmb;

    @FXML
    private ComboBox<String> houseIdCmb;

    @FXML
    private TextField searchTxt;

    private final TenantBO tenantBO = (TenantBO) BOFactory.getInstance().getBO(BOFactory.BOType.TENANT);
    private ObservableList<TenantTM> tableData;
    private ObservableList<String> names = FXCollections.observableArrayList();


    @FXML
    void nameTxtKeyReleased(KeyEvent event) {

        String input = nameTxt.getText();


        try {
            names = tenantBO.getNameSuggestions(input);
            nameList.setItems(names);
        }
        catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            System.err.println("Error while getting tenants names suggestions: " + e.getMessage());
            notification("An error occurred while getting tenants names suggestions. Please try again or contact support.");
        }

        if(input.isEmpty()){
            names.clear();
        }

    }

    @FXML
    void nameListOnMouseClicked(MouseEvent event) {

        nameTxt.setText(nameList.getSelectionModel().getSelectedItem());
        nameList.getItems().clear();
    }


    @FXML
    void refreshOnAction(ActionEvent event) {

       clean();
    }


    @FXML
    void searchOnAction(ActionEvent event) {

        ObservableList<TenantTM> searchedTenants = FXCollections.observableArrayList();

        String selectedTenantId = tenantIdCmb.getValue();
        String selectedName = nameTxt.getText();
        String selectedMembersCount = membersCountTxt.getText();
        String selectedLastPaymentMonth = monthsCmb.getValue();
        String selectedHouseId = houseIdCmb.getValue();

        boolean tenantIdSelected = selectedTenantId != null && !selectedTenantId.equals("Select");
        boolean nameSelected = selectedName != null && !selectedName.isEmpty();
        boolean membersCountSelected = selectedMembersCount != null && !selectedMembersCount.isEmpty();
        boolean lastPaymentMonthSelected = selectedLastPaymentMonth != null && !selectedLastPaymentMonth.equals("Select");
        boolean houseIdSelected = selectedHouseId != null && !selectedHouseId.equals("Select");


        if (tenantIdSelected) {
            ObservableList<TenantTM> tenantsById = getTenantById(selectedTenantId);

            if (tenantsById.isEmpty()) {
                table.setItems(tenantsById);
            } else {
                searchedTenants.addAll(tenantsById);

                if (nameSelected) {
                    ObservableList<TenantTM> filteredByName = filterTenantsByName(searchedTenants, selectedName);
                    searchedTenants.clear();
                    searchedTenants.addAll(filteredByName);
                }

                if (membersCountSelected) {
                    ObservableList<TenantTM> filteredByMembersCount = filterTenantsByMembersCount(searchedTenants, selectedMembersCount);
                    searchedTenants.clear();
                    searchedTenants.addAll(filteredByMembersCount);
                }

                if (lastPaymentMonthSelected) {
                    ObservableList<TenantTM> filteredByLastPaymentMonth = filterTenantsByLastPaymentMonth(searchedTenants, selectedLastPaymentMonth);
                    searchedTenants.clear();
                    searchedTenants.addAll(filteredByLastPaymentMonth);
                }

                if (houseIdSelected) {
                    ObservableList<TenantTM> filteredByHouseId = filterTenantsByHouseId(searchedTenants, selectedHouseId);
                    searchedTenants.clear();
                    searchedTenants.addAll(filteredByHouseId);
                }

                table.setItems(searchedTenants);
            }


        } else if (nameSelected || membersCountSelected || lastPaymentMonthSelected || houseIdSelected) {
            ObservableList<TenantTM> allTenants = tableData;
            searchedTenants.addAll(allTenants);

            if (nameSelected) {
                searchedTenants = filterTenantsByName(searchedTenants, selectedName);
            }

            if (membersCountSelected) {
                searchedTenants = filterTenantsByMembersCount(searchedTenants, selectedMembersCount);
            }

            if (lastPaymentMonthSelected) {
                searchedTenants = filterTenantsByLastPaymentMonth(searchedTenants, selectedLastPaymentMonth);
            }

            if (houseIdSelected) {
                searchedTenants = filterTenantsByHouseId(searchedTenants, selectedHouseId);
            }

            table.setItems(searchedTenants);

        } else {
            ObservableList<TenantTM> allTenants = tableData;
            table.setItems(allTenants);
        }
    }


    private ObservableList<TenantTM> getTenantById(String tenantId) {
        return FXCollections.observableArrayList(
                tableData.stream()
                        .filter(tenant -> tenant.getTenantId().equalsIgnoreCase(tenantId))
                        .toList()
        );
    }


    private ObservableList<TenantTM> filterTenantsByName(ObservableList<TenantTM> tenants, String name) {
        return FXCollections.observableArrayList(
                tenants.stream()
                        .filter(tenant -> tenant.getName().toLowerCase().contains(name.toLowerCase()))
                        .toList()
        );
    }

    private ObservableList<TenantTM> filterTenantsByMembersCount(ObservableList<TenantTM> tenants, String membersCount) {
        int count = Integer.parseInt(membersCount);
        return FXCollections.observableArrayList(
                tenants.stream()
                        .filter(tenant -> tenant.getMembersCount() == count)
                        .toList()
        );
    }

    private ObservableList<TenantTM> filterTenantsByLastPaymentMonth(ObservableList<TenantTM> tenants, String lastPaymentMonth) {
        return FXCollections.observableArrayList(
                tenants.stream()
                        .filter(tenant -> tenant.getLastPaidMonth().equalsIgnoreCase(lastPaymentMonth))
                        .toList()
        );
    }

    private ObservableList<TenantTM> filterTenantsByHouseId(ObservableList<TenantTM> tenants, String houseId) {
        return FXCollections.observableArrayList(
                tenants.stream()
                        .filter(tenant -> tenant.getHouseId().equalsIgnoreCase(houseId))
                        .toList()
        );
    }



    @FXML
    void sortCmbOnAction(ActionEvent event) {

        String sortType = sortCmb.getSelectionModel().getSelectedItem();
        ObservableList<TenantTM> tenantTMS = FXCollections.observableArrayList(tableData);

        if (sortType == null) {
            return;
        }

        Comparator<TenantTM> comparator = null;

        switch (sortType) {
            case "Tenant ID (Ascending)":
                comparator = Comparator.comparing(TenantTM::getTenantId);
                break;

            case "Tenant ID (Descending)":
                comparator = Comparator.comparing(TenantTM::getTenantId).reversed();
                break;

            case "Tenant Name (Ascending)":
                comparator = Comparator.comparing(TenantTM::getName);
                break;

            case "Tenant Name (Descending)":
                comparator = Comparator.comparing(TenantTM::getName).reversed();
                break;

            case "Resident Count (Ascending)":
                comparator = Comparator.comparing(TenantTM::getMembersCount);
                break;

            case "Resident Count (Descending)":
                comparator = Comparator.comparing(TenantTM::getMembersCount).reversed();
                break;

            case "Rent Start Date (Ascending)":
                comparator = Comparator.comparing(TenantTM::getRentStartDate);
                break;

            case "Rent Start Date (Descending)":
                comparator = Comparator.comparing(TenantTM::getRentStartDate).reversed();
                break;

            case "Rent For Month (Ascending)":
                comparator = Comparator.comparing(TenantTM::getMonthlyRent);
                break;

            case "Rent For Month (Descending)":
                comparator = Comparator.comparing(TenantTM::getMonthlyRent).reversed();
                break;

            default:
                break;
        }

        if (comparator != null) {
            FXCollections.sort(tenantTMS, comparator);
            table.setItems(tenantTMS);
        }

    }


    @FXML
    void tableRowsCmbOnAction(ActionEvent event) {

        Integer value = tableRowsCmb.getSelectionModel().getSelectedItem();

        if(value==null){
            return;
        }

        ObservableList<TenantTM> tenantTMS = FXCollections.observableArrayList();

        for (int i=0; i<value; i++){
            tenantTMS.add(tableData.get(i));
        }

        table.setItems(tenantTMS);

    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        setTableColumnsValue();
        setRowCmbValues();
        setTenantIdCmbValues();
        setMonthsCmbValues();
        setHouseIdCmbValues();
        setSortCmbValues();

        tableSearch();

    }


    public void tableSearch() {

        FilteredList<TenantTM> filteredData = new FilteredList<>(tableData, b -> true);

        searchTxt.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(tenant -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                String lowerCaseFilter = newValue.toLowerCase();


                if (tenant.getTenantId().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else if (tenant.getName().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else if (tenant.getPhoneNo().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else if (String.valueOf(tenant.getMembersCount()).contains(lowerCaseFilter)) {
                    return true;
                } else if (String.valueOf(tenant.getRentStartDate()).toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else if (String.valueOf(tenant.getMonthlyRent()).contains(lowerCaseFilter)) {
                    return true;
                } else if (tenant.getLastPaidMonth().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else if (tenant.getHouseId().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                }
                return false;
            });
        });

        SortedList<TenantTM> sortedData = new SortedList<>(filteredData);

        sortedData.comparatorProperty().bind(table.comparatorProperty());

        table.setItems(sortedData);
    }


    public void setSortCmbValues(){

        ObservableList<String> sortTypes = FXCollections.observableArrayList("Sort By","Tenant ID (Ascending)","Tenant ID (Descending)","Tenant Name (Ascending)","Tenant Name (Descending)","Resident Count (Ascending)","Resident Count (Descending)","Rent Start Date (Ascending)","Rent Start Date (Descending)","Rent For Month (Ascending)","Rent For Month (Descending)");
        sortCmb.setItems(sortTypes);
        sortCmb.getSelectionModel().selectFirst();

    }


    public void setHouseIdCmbValues(){

        try {
            ObservableList<String> houseIds = tenantBO.getHouseIds();
            houseIdCmb.setItems(houseIds);
            houseIdCmb.getSelectionModel().selectFirst();

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            System.err.println("Error while setting the house id combo box values: " + e.getMessage());
            notification("An error occurred while setting the house id combo box values. Please try again or contact support.");
        }

    }


    public void setMonthsCmbValues(){

        ObservableList<String> months = FXCollections.observableArrayList( "Select","January", "February", "March", "April", "May", "June",
                "July", "August", "September", "October", "November", "December");

        monthsCmb.setItems(months);
        monthsCmb.getSelectionModel().selectFirst();

    }

    public void setTenantIdCmbValues(){

        ObservableList<String> tenantIds = FXCollections.observableArrayList();
        tenantIds.add("Select");

        for(TenantTM x : tableData){
          tenantIds.add(x.getTenantId());
        }

        tenantIdCmb.setItems(tenantIds);
        tenantIdCmb.getSelectionModel().selectFirst();

    }

    public void setRowCmbValues(){

        ObservableList<Integer> rows = FXCollections.observableArrayList();
        int count = 0;

        for (TenantTM x : tableData){
            count++;
            rows.add(count);

        }
        tableRowsCmb.setItems(rows);
        tableRowsCmb.getSelectionModel().selectLast();

    }

    public void setTableColumnsValue(){

        tenantIdColumn.setCellValueFactory(new PropertyValueFactory<>("tenantId"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        phoneNoColumn.setCellValueFactory(new PropertyValueFactory<>("phoneNo"));
        membersCountColumn.setCellValueFactory(new PropertyValueFactory<>("membersCount"));
        startDateColumn.setCellValueFactory(new PropertyValueFactory<>("rentStartDate"));
        monthlyRentColumn.setCellValueFactory(new PropertyValueFactory<>("monthlyRent"));
        lastPaidMonthColumn.setCellValueFactory(new PropertyValueFactory<>("lastPaidMonth"));
        houseIdColumn.setCellValueFactory(new PropertyValueFactory<>("houseId"));


        Callback<TableColumn<TenantTM, String>, TableCell<TenantTM, String>> cellFoctory = (TableColumn<TenantTM, String> param) -> {

            final TableCell<TenantTM, String> cell = new TableCell<TenantTM, String>() {
                @Override
                public void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);

                    if (empty) {
                        setGraphic(null);
                        setText(null);

                    } else {
                        Image image1 = new Image("C:\\Users\\PCWORLD\\IdeaProjects\\Semester_final\\src\\main\\resources\\assets\\image\\visibility.png");
                        Image image2 = new Image("C:\\Users\\PCWORLD\\IdeaProjects\\Semester_final\\src\\main\\resources\\assets\\image\\editText.png");
                        Image image3 = new Image("C:\\Users\\PCWORLD\\IdeaProjects\\Semester_final\\src\\main\\resources\\assets\\image\\accounting (3).png");

                        ImageView viewDetails = new ImageView();
                        viewDetails.setImage(image1);
                        viewDetails.setFitHeight(20);
                        viewDetails.setFitWidth(20);

                        ImageView edit = new ImageView();
                        edit.setImage(image2);
                        edit.setFitHeight(20);
                        edit.setFitWidth(20);

                        ImageView paymentHistory = new ImageView();
                        paymentHistory.setImage(image3);
                        paymentHistory.setFitHeight(20);
                        paymentHistory.setFitWidth(20);


                        viewDetails.setStyle(" -fx-cursor: hand ;");
                        edit.setStyle(" -fx-cursor: hand ;");
                        paymentHistory.setStyle(" -fx-cursor: hand ;");


                        viewDetails.setOnMouseClicked((MouseEvent event) -> {

                            TenantTM SelectedTenant = table.getSelectionModel().getSelectedItem();

                            try {
                                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/TenantDetails.fxml"));
                                Parent root = fxmlLoader.load();
                                TenantDetailsController tenantDetailsController = fxmlLoader.getController();
                                tenantDetailsController.setSelectedTenantDetails(SelectedTenant);
                                Scene scene = new Scene(root);
                                Stage stage = new Stage();
                                stage.setScene(scene);
                                stage.show();

                            } catch (IOException e) {
                                e.printStackTrace();
                                System.err.println("Error while loading the details of tenant id: "+SelectedTenant.getTenantId()+ " + e.getMessage()");
                                notification("An error occurred while loading the details of tenant id: "+SelectedTenant.getTenantId()+", Please try again or contact support.");
                            }

                        });

                        edit.setOnMouseClicked((MouseEvent event) -> {

                            TenantTM SelectedTenant = table.getSelectionModel().getSelectedItem();

                            try {
                                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/EditTenant.fxml"));
                                Parent root = fxmlLoader.load();
                                TenantEditController tenantEditController = fxmlLoader.getController();
                                tenantEditController.setSelectedTenantDetails(SelectedTenant);
                                Scene scene = new Scene(root);
                                Stage stage = new Stage();
                                stage.setScene(scene);
                                stage.show();

                            } catch (IOException e) {
                                e.printStackTrace();
                                System.err.println("Error while loading the Edit Tenant page: " + e.getMessage());
                                notification("An error occurred while loading the Edit Tenant page. Please try again or contact support.");
                            }


                        });

                        paymentHistory.setOnMouseClicked((MouseEvent event) -> {

                            TenantTM tenant = table.getSelectionModel().getSelectedItem();

                            if (tenant == null) {
                                return;
                            }

                            try {
                                JasperReport jasperReport = (JasperReport) JRLoader.loadObject(
                                        getClass().getResourceAsStream("/assets/Report/Tenant_Payment_Report(1).jasper")
                                );

                                Connection connection = DBConnection.getInstance().getConnection();

                                Map<String, Object> parameters = new HashMap<>();

                                parameters.put("P_Tenant_Id", tenant.getTenantId());


                                JasperPrint jasperPrint = JasperFillManager.fillReport(
                                        jasperReport,
                                        parameters,
                                        connection
                                );

                                JasperViewer.viewReport(jasperPrint, false);

                            } catch (JRException e) {
                                e.printStackTrace();
                                notification("Fail to generate report...!");
                            } catch (SQLException e) {
                                e.printStackTrace();
                                notification("DB connection error...!");
                            } catch (ClassNotFoundException e) {
                                e.printStackTrace();
                                notification("Class Not Found Error...!");
                            }


                        });


                        HBox manageBtn = new HBox(viewDetails,edit,paymentHistory);


                        manageBtn.setAlignment(Pos.CENTER);
                        manageBtn.setSpacing(3);
                        manageBtn.setPadding(new Insets(2));

                        HBox.setMargin(viewDetails, new Insets(2, 2, 0, 3));
                        HBox.setMargin(edit, new Insets(2, 3, 0, 3));
                        HBox.setMargin(paymentHistory, new Insets(2, 3, 0, 3));
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
            ObservableList<TenantDTO> tenantDTOS = tenantBO.getAll();
            ObservableList<TenantTM> tenantTMS = FXCollections.observableArrayList();

            for(TenantDTO x : tenantDTOS){
                tenantTMS.add(new TenantTM().toTM(x));
            }
            tableData = tenantTMS;
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
        setHouseIdCmbValues();
        monthsCmb.getSelectionModel().selectFirst();
        sortCmb.getSelectionModel().selectFirst();
        nameTxt.setText("");
        membersCountTxt.setText("");
        searchTxt.clear();
        table.getSelectionModel().clearSelection();

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




