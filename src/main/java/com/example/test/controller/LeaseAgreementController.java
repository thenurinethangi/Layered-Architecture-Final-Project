package com.example.test.controller;

import com.example.test.bo.BOFactory;
import com.example.test.bo.custom.LeaseAgreementBO;
import com.example.test.dao.custom.impl.PaymentDAOImpl;
import com.example.test.dao.custom.impl.TenantDAOImpl;
import com.example.test.dao.custom.impl.UnitDAOImpl;
import com.example.test.dto.LeaseAgreementDTO;
import com.example.test.entity.Tenant;
import com.example.test.view.tdm.LeaseAgreementTM;
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
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Comparator;
import java.util.ResourceBundle;
import java.util.Optional;

public class LeaseAgreementController implements Initializable {

    @FXML
    private TableView<LeaseAgreementTM> table;

    @FXML
    private TableColumn<LeaseAgreementTM, String> leaseIdColumn;

    @FXML
    private TableColumn<LeaseAgreementTM, String> tenantIdColumn;

    @FXML
    private TableColumn<LeaseAgreementTM, String> houseIdColumn;

    @FXML
    private TableColumn<LeaseAgreementTM, String> leaseTurnColumn;

    @FXML
    private TableColumn<LeaseAgreementTM, String> startDateColumn;

    @FXML
    private TableColumn<LeaseAgreementTM, String> endDateColumn;

    @FXML
    private TableColumn<LeaseAgreementTM, String> statusColumn;

    @FXML
    private TableColumn<LeaseAgreementTM, String> actionColumn;

    @FXML
    private ComboBox<Integer> tableRowsCmb;

    @FXML
    private ComboBox<String> sortCmb;

    @FXML
    private Button searchbtn;

    @FXML
    private Button refreshbtn;

    @FXML
    private ComboBox<String> leaseTurnCmb;

    @FXML
    private ComboBox<String> leaseIdCmb;

    @FXML
    private ComboBox<String> statusCmb;

    @FXML
    private ComboBox<String> tenantIdCmb;

    @FXML
    private ComboBox<String> houseIdCmb;

    @FXML
    private TextField searchTxt;


    private boolean isOnlyActiveAgreements = false;
    private ObservableList<LeaseAgreementTM> tableData;
    private final LeaseAgreementBO leaseAgreementBO = (LeaseAgreementBO) BOFactory.getInstance().getBO(BOFactory.BOType.LEASEAGREEMENT);


    @FXML
    void refreshOnAction(ActionEvent event) {

        if (!isOnlyActiveAgreements) {
            clean();
        } else {
            setOnlyActiveAgreement();
        }
    }


    @FXML
    void searchOnAction(ActionEvent event) {


        ObservableList<LeaseAgreementTM> searchedLeases = FXCollections.observableArrayList();

        String selectedLeaseId = leaseIdCmb.getValue();
        String selectedTenantId = tenantIdCmb.getValue();
        String selectedHouseId = houseIdCmb.getValue();
        String selectedLeaseTurn = leaseTurnCmb.getValue();
        String selectedStatus = statusCmb.getValue();

        boolean leaseIdSelected = selectedLeaseId != null && !selectedLeaseId.equals("Select");
        boolean tenantIdSelected = selectedTenantId != null && !selectedTenantId.equals("Select");
        boolean houseIdSelected = selectedHouseId != null && !selectedHouseId.equals("Select");
        boolean leaseTurnSelected = selectedLeaseTurn != null && !selectedLeaseTurn.equals("Select");
        boolean statusSelected = selectedStatus != null && !selectedStatus.equals("Select");

        if (leaseIdSelected) {

            ObservableList<LeaseAgreementTM> leasesById = getLeaseById(selectedLeaseId);

            if (leasesById.isEmpty()) {
                table.setItems(leasesById);
            } else {
                searchedLeases.addAll(leasesById);

                if (tenantIdSelected) {
                    searchedLeases = filterLeasesByTenantId(searchedLeases, selectedTenantId);
                }

                if (houseIdSelected) {
                    searchedLeases = filterLeasesByHouseId(searchedLeases, selectedHouseId);
                }

                if (leaseTurnSelected) {
                    searchedLeases = filterLeasesByLeaseTurn(searchedLeases, selectedLeaseTurn);
                }

                if (statusSelected) {
                    searchedLeases = filterLeasesByStatus(searchedLeases, selectedStatus);
                }

                table.setItems(searchedLeases);
            }

        } else if (tenantIdSelected || houseIdSelected || leaseTurnSelected || statusSelected) {

            ObservableList<LeaseAgreementTM> allLeases = tableData;
            searchedLeases.addAll(allLeases);

            if (tenantIdSelected) {
                searchedLeases = filterLeasesByTenantId(searchedLeases, selectedTenantId);
            }

            if (houseIdSelected) {
                searchedLeases = filterLeasesByHouseId(searchedLeases, selectedHouseId);
            }

            if (leaseTurnSelected) {
                searchedLeases = filterLeasesByLeaseTurn(searchedLeases, selectedLeaseTurn);
            }

            if (statusSelected) {
                searchedLeases = filterLeasesByStatus(searchedLeases, selectedStatus);
            }

            table.setItems(searchedLeases);

        } else {
            ObservableList<LeaseAgreementTM> allLeases = tableData;
            table.setItems(allLeases);
        }

    }


    public ObservableList<LeaseAgreementTM> getLeaseById(String leaseId) {

        return FXCollections.observableArrayList(
                tableData.stream()
                        .filter(lease -> lease.getLeaseId().equalsIgnoreCase(leaseId))
                        .toList()
        );
    }

    public ObservableList<LeaseAgreementTM> filterLeasesByTenantId(ObservableList<LeaseAgreementTM> leases, String tenantId) {

        return FXCollections.observableArrayList(
                leases.stream()
                        .filter(lease -> lease.getTenantId().equalsIgnoreCase(tenantId))
                        .toList()
        );
    }


    public ObservableList<LeaseAgreementTM> filterLeasesByHouseId(ObservableList<LeaseAgreementTM> leases, String houseId) {

        return FXCollections.observableArrayList(
                leases.stream()
                        .filter(lease -> lease.getHouseId().equalsIgnoreCase(houseId))
                        .toList()
        );
    }


    public ObservableList<LeaseAgreementTM> filterLeasesByLeaseTurn(ObservableList<LeaseAgreementTM> leases, String leaseTurn) {

        return FXCollections.observableArrayList(
                leases.stream()
                        .filter(lease -> lease.getLeaseTurn().equalsIgnoreCase(leaseTurn))
                        .toList()
        );
    }


    public ObservableList<LeaseAgreementTM> filterLeasesByStatus(ObservableList<LeaseAgreementTM> leases, String status) {

        return FXCollections.observableArrayList(
                leases.stream()
                        .filter(lease -> lease.getStatus().equalsIgnoreCase(status))
                        .toList()
        );
    }



    @FXML
    void sortCmbOnAction(ActionEvent event) {

        String sortType = sortCmb.getSelectionModel().getSelectedItem();
        ObservableList<LeaseAgreementTM> leaseAgreementTMS = FXCollections.observableArrayList(tableData);

        if (sortType == null) {
            return;
        }

        Comparator<LeaseAgreementTM> comparator = null;

        switch (sortType) {
            case "Lease ID (Ascending)":
                comparator = Comparator.comparing(LeaseAgreementTM::getLeaseId);
                break;

            case "Lease ID (Descending)":
                comparator = Comparator.comparing(LeaseAgreementTM::getLeaseId).reversed();
                break;

            case "Tenant ID (Ascending)":
                comparator = Comparator.comparing(LeaseAgreementTM::getTenantId);
                break;

            case "Tenant ID (Descending)":
                comparator = Comparator.comparing(LeaseAgreementTM::getTenantId).reversed();
                break;

            case "Start Date (Ascending)":
                comparator = Comparator.comparing(LeaseAgreementTM::getStartDate);
                break;

            case "Start Date (Descending)":
                comparator = Comparator.comparing(LeaseAgreementTM::getStartDate).reversed();
                break;

            case "End Date (Ascending)":
                comparator = Comparator.comparing(LeaseAgreementTM::getEndDate);
                break;

            case "End Date (Descending)":
                comparator = Comparator.comparing(LeaseAgreementTM::getEndDate).reversed();
                break;

            default:
                break;
        }

        if (comparator != null) {
            FXCollections.sort(leaseAgreementTMS, comparator);
            table.setItems(leaseAgreementTMS);
        }
    }


    @FXML
    void tableRowsCmbOnAction(ActionEvent event) {

        Integer value = tableRowsCmb.getSelectionModel().getSelectedItem();

        if(value==null){
            return;
        }

        ObservableList<LeaseAgreementTM> leaseAgreementTMS = FXCollections.observableArrayList();

        for (int i=0; i<value; i++){
            leaseAgreementTMS.add(tableData.get(i));
        }

        table.setItems(leaseAgreementTMS);
    }



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        setTableColumns();
        setTableColumnsValue();
        setRowCmbValues();
        setTenantIdCmbValues();
        setHouseIdCmbValues();
        setLeaseTurnCmbValues();
        setStatusCmbValues();
        setLeaseIdCmbValues();
        setSortCmbValues();
        tableSearch();
    }


    public void tableSearch() {

        FilteredList<LeaseAgreementTM> filteredData = new FilteredList<>(tableData, b -> true);

        searchTxt.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(lease -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                String lowerCaseFilter = newValue.toLowerCase();

                if (lease.getLeaseId().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else if (lease.getTenantId().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else if (lease.getHouseId().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else if (lease.getLeaseTurn().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else if (String.valueOf(lease.getStartDate()).toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else if (String.valueOf(lease.getEndDate()).toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else if (lease.getStatus().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                }
                return false;
            });
        });

        SortedList<LeaseAgreementTM> sortedData = new SortedList<>(filteredData);

        sortedData.comparatorProperty().bind(table.comparatorProperty());

        table.setItems(sortedData);
    }



    public void setSortCmbValues(){

        ObservableList<String> statuses = FXCollections.observableArrayList("Sort By","Lease ID (Ascending)","Lease ID (Descending)","Tenant ID (Ascending)","Tenant ID (Descending)","Start Date (Ascending)","Start Date (Descending)","End Date (Ascending)","End Date (Descending)");
        sortCmb.setItems(statuses);
        sortCmb.getSelectionModel().selectFirst();

    }


    public void setStatusCmbValues(){

        ObservableList<String> statuses = FXCollections.observableArrayList("Select","Active","Expired","Canceled");
        statusCmb.setItems(statuses);
        statusCmb.getSelectionModel().selectFirst();

    }


    public void setLeaseTurnCmbValues(){

        ObservableList<String> leaseTurns = FXCollections.observableArrayList("Select","6 Months","12 Months","18 Months","2 Year");
        leaseTurnCmb.setItems(leaseTurns);
        leaseTurnCmb.getSelectionModel().selectFirst();

    }

    public void setHouseIdCmbValues(){

        try {
            ObservableList<String> houseIds = leaseAgreementBO.getDistinctHouseIds();
            houseIdCmb.setItems(houseIds);
        }catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            System.err.println("Error while setting values to house id combo box: " + e.getMessage());
            notification("An error occurred while setting values to house id combo box. Please try again or contact support.");
        }

        houseIdCmb.getSelectionModel().selectFirst();
    }


    public void setTenantIdCmbValues(){

        ObservableList<String> tenantIds = FXCollections.observableArrayList();
        tenantIds.add("Select");
        try {
            TenantDAOImpl tenantDAO = new TenantDAOImpl();
            ObservableList<Tenant> allTenants = tenantDAO.getAll();

            for (Tenant x: allTenants){
                tenantIds.add(x.getTenantId());
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            System.err.println("Error while setting values to tenant id combo box: " + e.getMessage());
            notification("An error occurred while setting values to tenant id combo box. Please try again or contact support.");
        }

        tenantIdCmb.setItems(tenantIds);
        tenantIdCmb.getSelectionModel().selectFirst();

    }


    public void setLeaseIdCmbValues(){

        ObservableList<String> leaseIds = FXCollections.observableArrayList();
        leaseIds.add("Select");

        for (LeaseAgreementTM x : tableData){
            leaseIds.add(x.getLeaseId());
        }

        leaseIdCmb.setItems(leaseIds);
        leaseIdCmb.getSelectionModel().selectFirst();

    }


    public void setRowCmbValues(){

        ObservableList<Integer> rows = FXCollections.observableArrayList();
        int count = 0;

        for (LeaseAgreementTM x : tableData){
            count++;
            rows.add(count);

        }

        tableRowsCmb.setItems(rows);
        tableRowsCmb.getSelectionModel().selectLast();
    }


    public void setTableColumns(){

        leaseIdColumn.setCellValueFactory(new PropertyValueFactory<>("leaseId"));
        tenantIdColumn.setCellValueFactory(new PropertyValueFactory<>("tenantId"));
        houseIdColumn.setCellValueFactory(new PropertyValueFactory<>("houseId"));
        leaseTurnColumn.setCellValueFactory(new PropertyValueFactory<>("leaseTurn"));
        startDateColumn.setCellValueFactory(new PropertyValueFactory<>("startDate"));
        endDateColumn.setCellValueFactory(new PropertyValueFactory<>("endDate"));
        statusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));

    }


    public void setTableColumnsValue(){

        Callback<TableColumn<LeaseAgreementTM, String>, TableCell<LeaseAgreementTM, String>> cellFoctory = (TableColumn<LeaseAgreementTM, String> param) -> {

            final TableCell<LeaseAgreementTM, String> cell = new TableCell<LeaseAgreementTM, String>() {
                @Override
                public void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);

                    if (empty) {
                        setGraphic(null);
                        setText(null);

                    } else {

                        Button status = new Button();
                        status.setStyle("-fx-background-radius: 100; -fx-text-fill: #FFFFFF;");

                        LeaseAgreementTM currentAgreement = getTableView().getItems().get(getIndex());

                        boolean isNearToEndLease = false;

                        String end = currentAgreement.getEndDate();
                        LocalDate endDate = LocalDate.parse(end);
                        System.out.println("end: "+endDate);

                        LocalDate currentDate = LocalDate.now();
                        System.out.println("Current date: "+currentDate);

                        long daysDifference = ChronoUnit.DAYS.between(currentDate, endDate);

                        if (daysDifference >= 0 && daysDifference <= 30) {
                            System.out.println("The dates are near (within one month).");
                            isNearToEndLease = true;
                        } else {
                            System.out.println("The dates are not near (not within one month).");
                            isNearToEndLease = false;
                        }


                        if (currentAgreement.getStatus().equalsIgnoreCase("Canceled")) {
                            status.setStyle("-fx-background-color: #2f3542; -fx-background-radius: 100; -fx-text-fill: #FFFFFF;");

                        }else if (currentAgreement.getStatus().equalsIgnoreCase("Expired") || isNearToEndLease) {
                            status.setStyle("-fx-background-color: #E53935; -fx-background-radius: 100; -fx-text-fill: #FFFFFF;");

                        }
                        else if (currentAgreement.getStatus().equalsIgnoreCase("Active")) {
                            status.setStyle("-fx-background-color: #4CAF50; -fx-background-radius: 100; -fx-text-fill: #FFFFFF;");

                        } else {
                            status.setStyle("-fx-background-color: #FFC107; -fx-background-radius: 100; -fx-text-fill: #FFFFFF;");
                        }


                        Image image1 = new Image("C:\\Users\\PCWORLD\\IdeaProjects\\Semester_final\\src\\main\\resources\\assets\\image\\email (2).png");
                        Image image2 = new Image("C:\\Users\\PCWORLD\\IdeaProjects\\Semester_final\\src\\main\\resources\\assets\\image\\contract(1).png");
                        Image image3 = new Image("C:\\Users\\PCWORLD\\IdeaProjects\\Semester_final\\src\\main\\resources\\assets\\image\\reply(1).png");


                        ImageView mail = new ImageView();
                        mail.setImage(image1);
                        mail.setFitHeight(19);
                        mail.setFitWidth(19);

                        ImageView reSign = new ImageView();
                        reSign.setImage(image2);
                        reSign.setFitHeight(20);
                        reSign.setFitWidth(20);

                        ImageView returnHouse = new ImageView();
                        returnHouse.setImage(image3);
                        returnHouse.setFitHeight(20);
                        returnHouse.setFitWidth(20);

                        mail.setStyle(" -fx-cursor: hand ;");
                        reSign.setStyle(" -fx-cursor: hand ;");
                        returnHouse.setStyle(" -fx-cursor: hand ;");


                        mail.setOnMouseClicked((MouseEvent event) -> {

                            LeaseAgreementTM selectedLeaseAgreement = table.getSelectionModel().getSelectedItem();

                            if(selectedLeaseAgreement.getStatus().equals("Canceled") || selectedLeaseAgreement.getStatus().equals("Deleted")) {

                                return;
                            }

                            String subject = "";
                            String message = "";
                            if(selectedLeaseAgreement.getStatus().equals("Expired")){

                                subject = "Regarding the expiration of the lease turn. \n";

                                message = "We would like to formally inform you that your lease has expired.\nThe lease term commenced on "+ selectedLeaseAgreement.getStartDate()+ " , and concluded on " + selectedLeaseAgreement.getEndDate()+".\nWe kindly request that you either sign the lease again or vacate the premises.\nPlease take the necessary actions promptly.\nThank you for your attention to this matter!\n\n\n" +
                                        "The GrandView Residences,  \n" +
                                        "Colombo 08.";
                            }

                            try{
                                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/MailSendFormInLeaseAgreement.fxml"));
                                Parent root = fxmlLoader.load();
                                MailSendFormInLeaseAgreementController mailSendFormInLeaseAgreementController = fxmlLoader.getController();
                                mailSendFormInLeaseAgreementController.setSelectedTenantDetailsToSendMail(selectedLeaseAgreement,subject,message);
                                Scene scene = new Scene(root);
                                Stage stage = new Stage();
                                stage.setScene(scene);
                                stage.initStyle(StageStyle.UNDECORATED);
                                stage.setX(70);
                                stage.setY(50);
                                stage.show();

                            }
                            catch (IOException e) {
                                e.printStackTrace();
                                System.err.println("Error while loading Send Mail form: " + e.getMessage());
                                notification("An error occurred while loading Send Mail form. Please try again or contact support.");
                            }

                        });

                        reSign.setOnMouseClicked((MouseEvent event) -> {

                            LeaseAgreementTM selectedLeaseAgreement = table.getSelectionModel().getSelectedItem();

                            if(selectedLeaseAgreement.getStatus().equals("Expired")){

                                try{
                                    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/ReSignLeaseAgreement.fxml"));
                                    Parent root = fxmlLoader.load();
                                    ReSignLeaseAgreementController reSignLeaseAgreementController = fxmlLoader.getController();
                                    reSignLeaseAgreementController.setSelectedAgreementDetails(selectedLeaseAgreement);
                                    Scene scene = new Scene(root);
                                    Stage stage = new Stage();
                                    stage.setScene(scene);
                                    stage.show();

                                }
                                catch (IOException e) {
                                    e.printStackTrace();
                                    System.err.println("Error while loading Re-Sign Agreement form: " + e.getMessage());
                                    notification("An error occurred while loading Re-Sign Agreement form. Please try again or contact support.");
                                }

                            }

                        });

                        returnHouse.setOnMouseClicked((MouseEvent event) -> {

                            LeaseAgreementTM selectedLeaseAgreement = table.getSelectionModel().getSelectedItem();

                            if(selectedLeaseAgreement.getStatus().equals("Expired")){

                                try{
                                    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/HouseReturnConformation.fxml"));
                                    Parent root = fxmlLoader.load();
                                    HouseReturnConformationController houseReturnConformationController = fxmlLoader.getController();
                                    houseReturnConformationController.setSelectedAgreementDetailsToReturn(selectedLeaseAgreement);
                                    Scene scene = new Scene(root);
                                    Stage stage = new Stage();
                                    stage.setScene(scene);
                                    stage.show();

                                }
                                catch (IOException e) {
                                    e.printStackTrace();
                                    System.err.println("Error while loading House Return form: " + e.getMessage());
                                    notification("An error occurred while loading House Return form. Please try again or contact support.");
                                }

                            }

                        });

                        HBox manageBtn = new HBox(status,mail,reSign,returnHouse);

                        manageBtn.setAlignment(Pos.CENTER);
                        manageBtn.setSpacing(3);
                        manageBtn.setPadding(new Insets(2));

                        HBox.setMargin(status, new Insets(2, 3, 0, 3));
                        HBox.setMargin(mail, new Insets(2, 2, 0, 3));
                        HBox.setMargin(reSign, new Insets(2, 3, 0, 3));
                        HBox.setMargin(returnHouse, new Insets(2, 3, 0, 3));
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
            ObservableList<LeaseAgreementDTO> leaseAgreementDTOS = leaseAgreementBO.getAll();
            ObservableList<LeaseAgreementTM> leaseAgreementTMS = FXCollections.observableArrayList();

            for(LeaseAgreementDTO x : leaseAgreementDTOS){
                leaseAgreementTMS.add(new LeaseAgreementTM().toTM(x));
            }
            tableData = leaseAgreementTMS;
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
        setTenantIdCmbValues();
        setHouseIdCmbValues();
        setLeaseIdCmbValues();
        sortCmb.getSelectionModel().selectFirst();
        statusCmb.getSelectionModel().selectFirst();
        leaseTurnCmb.getSelectionModel().selectFirst();
        table.getSelectionModel().clearSelection();

    }


    public void deleteOnAction(ActionEvent event) {

        LeaseAgreementTM selectedLeaseAgreement = table.getSelectionModel().getSelectedItem();

        if(selectedLeaseAgreement.getStatus().equals("Canceled")){

            ButtonType yesButton = new ButtonType("Yes", ButtonBar.ButtonData.YES);
            ButtonType cancelButton = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation");
            alert.setHeaderText("Please Confirm First");
            alert.setContentText("Are you sure you want to delete selected lease agreement?");

            alert.getButtonTypes().setAll(yesButton, cancelButton);

            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == yesButton) {

                try {
                    LeaseAgreementDTO leaseAgreementDTO = new LeaseAgreementDTO();
                    leaseAgreementDTO.setLeaseId(selectedLeaseAgreement.getLeaseId());
                    leaseAgreementDTO.setStatus("Deleted");
                    boolean response = leaseAgreementBO.updateLeaseAgreementStatus(leaseAgreementDTO);
                    if(response){
                        notification("The Lease Agreement Has Been Successfully Deleted");
                    }
                    else{
                        notification("Unable To Delete The Lease Agreement, Please Try Again Later.");
                    }
                }
                catch (SQLException | ClassNotFoundException e) {
                    e.printStackTrace();
                    System.err.println("Error while deleting the canceled lease Agreement: " + e.getMessage());
                    notification("An error occurred while deleting the \"canceled\" lease Agreement. Please try again or contact support.");
                }

            } else {
                return;
            }
        }
    }


    public void setOnlyActiveAgreement() {

        isOnlyActiveAgreements = true;

        try {
            ObservableList<LeaseAgreementDTO> leaseAgreementDTOS = leaseAgreementBO.getOnlyActiveAgreements();
            ObservableList<LeaseAgreementTM> leaseAgreementTMS = FXCollections.observableArrayList();

            for(LeaseAgreementDTO x : leaseAgreementDTOS){
                leaseAgreementTMS.add(new LeaseAgreementTM().toTM(x));
            }
            tableData = leaseAgreementTMS;
            table.setItems(tableData);

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            System.err.println("Error while loading table data: " + e.getMessage());
            notification("An error occurred while loading table data. Please try again or contact support.");
        }

        setRowCmbValues();
        setTenantIdCmbValues();
        setHouseIdCmbValues();
        setLeaseIdCmbValues();
        sortCmb.getSelectionModel().selectFirst();
        statusCmb.getSelectionModel().selectFirst();
        leaseTurnCmb.getSelectionModel().selectFirst();
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
