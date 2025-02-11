package com.example.test.controller;

import com.example.test.bo.BOFactory;
import com.example.test.bo.custom.*;
import com.example.test.dao.custom.impl.*;
import com.example.test.dto.*;
import com.example.test.entity.*;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class DashboardController implements Initializable {

    @FXML
    private AnchorPane bodyPane;

    @FXML
    private Pane p1;

    @FXML
    private ImageView image;

    @FXML
    private Label nameLabel;

    @FXML
    private Label positionLabel;

    @FXML
    private Button forwardbtn;

    @FXML
    private Button backbtn;

    @FXML
    private Pane p2;

    @FXML
    private Label incometxt;

    @FXML
    private Button incomebtn;

    @FXML
    private Pane p3;

    @FXML
    private Label expencetxt;

    @FXML
    private Button expencesbtn;

    @FXML
    private Pane p4;

    @FXML
    private Label profittxt;

    @FXML
    private Button profitbtn;

    @FXML
    private Pane p5;

    @FXML
    private Label salestxt;

    @FXML
    private Button salesbtn;

    @FXML
    private Pane p6;

    @FXML
    private Label customertxt;

    @FXML
    private Label leaseAgreementTxt;

    @FXML
    private Label purchaseAgreementTxt;

    @FXML
    private Button totalCustomerbtn;

    @FXML
    private Pane p7;

    @FXML
    private Label unitstxt;

    @FXML
    private Button unitsbtn;

    @FXML
    private Pane p8;

    @FXML
    private Label availableUnittxt;

    @FXML
    private Button availableUnitsbtn;

    @FXML
    private Pane p9;

    @FXML
    private Label tenantstxt;

    @FXML
    private Button tenantsbtn;

    @FXML
    private Pane p10;

    @FXML
    private Label ownerstxt;

    @FXML
    private Button ownersbtn;

    @FXML
    private Pane p11;

    @FXML
    private Label employeetxt;

    @FXML
    private Button employeebtn;

    @FXML
    private Pane p12;

    @FXML
    private Label maintancetxt;

    @FXML
    private Button maintancebtn;

    @FXML
    private Pane p13;

    @FXML
    private Label floortxt;

    @FXML
    private Button noOfFloorbtn;

    @FXML
    private Pane p14;

    @FXML
    private Label houseTypestxt;

    @FXML
    private Button houseTypesbtn;

    @FXML
    private Pane p15;

    @FXML
    private Label complainstxt;

    @FXML
    private Button complainsbtn;

    @FXML
    private Pane p16;

    @FXML
    private Button moreInfobtn15;

    private ArrayList<Image> images = new ArrayList<>();
    private int count = 0;
    private final RequestBO requestBO = (RequestBO) BOFactory.getInstance().getBO(BOFactory.BOType.REQUEST);
    private final CustomerBO customerBO = (CustomerBO) BOFactory.getInstance().getBO(BOFactory.BOType.CUSTOMER);
    private final UnitBO unitBO = (UnitBO) BOFactory.getInstance().getBO(BOFactory.BOType.UNIT);
    private final TenantBO tenantBO = (TenantBO) BOFactory.getInstance().getBO(BOFactory.BOType.TENANT);
    private final OwnerBO ownerBO = (OwnerBO) BOFactory.getInstance().getBO(BOFactory.BOType.OWNER);
    private final EmployeeBO employeeBO = (EmployeeBO) BOFactory.getInstance().getBO(BOFactory.BOType.EMPLOYEE);
    private final MaintenanceRequestBO maintenanceRequestBO = (MaintenanceRequestBO) BOFactory.getInstance().getBO(BOFactory.BOType.MAINTENANCEREQUEST);
    private final FloorBO floorBO = (FloorBO) BOFactory.getInstance().getBO(BOFactory.BOType.FLOOR);
    private final HouseTypeBO houseTypeBO = (HouseTypeBO) BOFactory.getInstance().getBO(BOFactory.BOType.HOUSETYPE);
    private final LeaseAgreementBO leaseAgreementBO = (LeaseAgreementBO) BOFactory.getInstance().getBO(BOFactory.BOType.LEASEAGREEMENT);
    private final PurchaseAgreementBO purchaseAgreementBO = (PurchaseAgreementBO) BOFactory.getInstance().getBO(BOFactory.BOType.PURCHASEAGREEMENT);
    private final PaymentBO paymentBO = (PaymentBO) BOFactory.getInstance().getBO(BOFactory.BOType.PAYMENT);
    private final ExpenseBO expenseBO = (ExpenseBO) BOFactory.getInstance().getBO(BOFactory.BOType.EXPENSE);



    @FXML
    void availableOnAction(ActionEvent event) {

        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Unit.fxml"));
            AnchorPane anchorPane = fxmlLoader.load();
            UnitController unitController = fxmlLoader.getController();
            unitController.setTableToOnlyAvailableUnits();
            bodyPane.getChildren().clear();
            bodyPane.getChildren().add(anchorPane);
        }
        catch (IOException e) {
            e.printStackTrace();
            System.err.println("Error while loading the Available Units: " + e.getMessage());
            notification("An error occurred while loading the Available Units. Please try again or contact support.");
        }
    }

    @FXML
    void backOnAction(ActionEvent event) {

        if(count<=4 && count>=0){
            if((count-1)>=0) {
                int x = count--;
                image.setImage(images.get(x));

                if(x==0){
                    nameLabel.setText("Mr.Damith Silva");
                    positionLabel.setText("Owner");
                }
                else if(x==1){
                    nameLabel.setText("Mr.Malith Perera");
                    positionLabel.setText("Property Manager");
                }
                else if(x==2){
                    nameLabel.setText("Mr.Krishantha Senanayake");
                    positionLabel.setText("Leasing Agent");
                }
                else if(x==3){
                    nameLabel.setText("Mr.Sadun Mendis");
                    positionLabel.setText("Marketing Specialist");
                }
                else if(x==4){
                    nameLabel.setText("Mr.Roshan Rathnayake");
                    positionLabel.setText("Assistant Property Manager");
                }
            }
        }

    }


    @FXML
    void forwardOnAction(ActionEvent event) {

        if(count<=4 && count>=0){
            if((count+1)<=4) {
                int x = count++;
                image.setImage(images.get(x));

                if(x==0){
                    nameLabel.setText("Mr.Damith Silva");
                    positionLabel.setText("Owner");
                }
                else if(x==1){
                    nameLabel.setText("Mr.Malith Perera");
                    positionLabel.setText("Property Manager");
                }
                else if(x==2){
                    nameLabel.setText("Mr.Krishantha Senanayake");
                    positionLabel.setText("Leasing Agent");
                }
                else if(x==3){
                    nameLabel.setText("Mr.Sadun Mendis");
                    positionLabel.setText("Marketing Specialist");
                }
                else if(x==4){
                    nameLabel.setText("Mr.Roshan Rathnayake");
                    positionLabel.setText("Assistant Property Manager");
                }
            }
        }
    }


    @FXML
    void employeeOnAction(ActionEvent event) {

        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Employee.fxml"));
            AnchorPane anchorPane = fxmlLoader.load();
            bodyPane.getChildren().clear();
            bodyPane.getChildren().add(anchorPane);
        }
        catch (IOException e) {
            e.printStackTrace();
            System.err.println("Error while loading the Employee Page: " + e.getMessage());
            notification("An error occurred while loading the Employee Page. Please try again or contact support.");
        }

    }

    @FXML
    void expenceOnAction(ActionEvent event) {

        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Expense.fxml"));
            AnchorPane anchorPane = fxmlLoader.load();
            bodyPane.getChildren().clear();
            bodyPane.getChildren().add(anchorPane);
        }
        catch (IOException e) {
            e.printStackTrace();
            System.err.println("Error while loading the Expense Page: " + e.getMessage());
            notification("An error occurred while loading the Expense Page. Please try again or contact support.");
        }
    }

    @FXML
    void floorOnAction(ActionEvent event) {

        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Floor.fxml"));
            AnchorPane anchorPane = fxmlLoader.load();
            FloorController floorController = fxmlLoader.getController();
            floorController.setFloorTextInvisible();
            bodyPane.getChildren().clear();
            bodyPane.getChildren().add(anchorPane);
        }
        catch (IOException e) {
            e.printStackTrace();
            System.err.println("Error while loading the Floor Page: " + e.getMessage());
            notification("An error occurred while loading the Floor Page. Please try again or contact support.");
        }
    }


    @FXML
    void houseTypeOnAction(ActionEvent event) {

        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/HouseType.fxml"));
            AnchorPane anchorPane = fxmlLoader.load();
            HouseTypeController houseTypeController = fxmlLoader.getController();
            houseTypeController.setHouseTypeTextInvisible();
            bodyPane.getChildren().clear();
            bodyPane.getChildren().add(anchorPane);
        }
        catch (IOException e) {
            e.printStackTrace();
            System.err.println("Error while loading the House Type Page: " + e.getMessage());
            notification("An error occurred while loading the House Type Page. Please try again or contact support.");
        }
    }

    @FXML
    void incomeOnAction(ActionEvent event) {

        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Payment.fxml"));
            AnchorPane anchorPane = fxmlLoader.load();
            bodyPane.getChildren().clear();
            bodyPane.getChildren().add(anchorPane);
        }
        catch (IOException e) {
            e.printStackTrace();
            System.err.println("Error while loading the Income Page: " + e.getMessage());
            notification("An error occurred while loading the Income Page. Please try again or contact support.");
        }

    }

    @FXML
    void maintanceOnAction(ActionEvent event) {

        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/MaintenanceRequest.fxml"));
            AnchorPane anchorPane = fxmlLoader.load();
            MaintenanceRequestController maintenanceRequestController = fxmlLoader.getController();
            maintenanceRequestController.setOnlyProgressRequest();
            bodyPane.getChildren().clear();
            bodyPane.getChildren().add(anchorPane);
        }
        catch (IOException e) {
            e.printStackTrace();
            System.err.println("Error while loading the Maintenance Page: " + e.getMessage());
            notification("An error occurred while loading the Maintenance Page. Please try again or contact support.");
        }
    }

    @FXML
    void ownerOnAction(ActionEvent event) {

        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Owner.fxml"));
            AnchorPane anchorPane = fxmlLoader.load();
            bodyPane.getChildren().clear();
            bodyPane.getChildren().add(anchorPane);
        }
        catch (IOException e) {
            e.printStackTrace();
            System.err.println("Error while loading the Owner Page: " + e.getMessage());
            notification("An error occurred while loading the Owner Page. Please try again or contact support.");
        }
    }

    @FXML
    void profitOnAction(ActionEvent event) {

    }

    @FXML
    void salesOnAction(ActionEvent event) {

        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Request.fxml"));
            AnchorPane anchorPane = fxmlLoader.load();
            RequestController requestController = fxmlLoader.getController();
            requestController.getOnlyClosedRequest();
            bodyPane.getChildren().clear();
            bodyPane.getChildren().add(anchorPane);
        }
        catch (IOException e) {
            e.printStackTrace();
            System.err.println("Error while loading the Sales Page: " + e.getMessage());
            notification("An error occurred while loading the Sales Page. Please try again or contact support.");
        }
    }

    @FXML
    void tenantsOnAction(ActionEvent event) {

        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Tenant.fxml"));
            AnchorPane anchorPane = fxmlLoader.load();
            bodyPane.getChildren().clear();
            bodyPane.getChildren().add(anchorPane);
        }
        catch (IOException e) {
            e.printStackTrace();
            System.err.println("Error while loading the Tenant Page: " + e.getMessage());
            notification("An error occurred while loading the Tenant Page. Please try again or contact support.");
        }
    }

    @FXML
    void totalCustomerOnAction(ActionEvent event) {

        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Customer.fxml"));
            AnchorPane anchorPane = fxmlLoader.load();
            bodyPane.getChildren().clear();
            bodyPane.getChildren().add(anchorPane);
        }
        catch (IOException e) {
            e.printStackTrace();
            System.err.println("Error while loading the Customer Page: " + e.getMessage());
            notification("An error occurred while loading the Customer Page. Please try again or contact support.");
        }
    }

    @FXML
    void unitOnAction(ActionEvent event) {

        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Unit.fxml"));
            AnchorPane anchorPane = fxmlLoader.load();
            bodyPane.getChildren().clear();
            bodyPane.getChildren().add(anchorPane);
        }
        catch (IOException e) {
            e.printStackTrace();
            System.err.println("Error while loading the Unit Page: " + e.getMessage());
            notification("An error occurred while loading the Unit Page. Please try again or contact support.");
        }
    }


    @FXML
    public void leaseAgreementOnAction(ActionEvent event) {

        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/LeaseAgreement.fxml"));
            AnchorPane anchorPane = fxmlLoader.load();
            LeaseAgreementController leaseAgreementController = fxmlLoader.getController();
            leaseAgreementController.setOnlyActiveAgreement();
            bodyPane.getChildren().clear();
            bodyPane.getChildren().add(anchorPane);
        }
        catch (IOException e) {
            e.printStackTrace();
            System.err.println("Error while loading the Lease Agreement Page: " + e.getMessage());
            notification("An error occurred while loading the Lease Agreement Page. Please try again or contact support.");
        }
    }


    @FXML
    public void purchaseAgreemntOnAction(ActionEvent event) {

        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/PurchaseAgreement.fxml"));
            AnchorPane anchorPane = fxmlLoader.load();
            bodyPane.getChildren().clear();
            bodyPane.getChildren().add(anchorPane);
        }
        catch (IOException e) {
            e.printStackTrace();
            System.err.println("Error while loading the Purchase Agreement Page: " + e.getMessage());
            notification("An error occurred while loading the Purchase Agreement Page. Please try again or contact support.");
        }
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        Image image1 = new Image("C:\\Users\\PCWORLD\\IdeaProjects\\Semester_final\\src\\main\\resources\\assets\\image\\image (1).png");
        Image image2 = new Image("C:\\Users\\PCWORLD\\IdeaProjects\\Semester_final\\src\\main\\resources\\assets\\image\\pngtree-smart-business-man-png-image_10275552-removebg-preview.png");
        Image image3 = new Image("C:\\Users\\PCWORLD\\IdeaProjects\\Semester_final\\src\\main\\resources\\assets\\image\\image (3).png");
        Image image4 = new Image("C:\\Users\\PCWORLD\\IdeaProjects\\Semester_final\\src\\main\\resources\\assets\\image\\png-transparent-business-man-young-men-executive-thumbnail-removebg-preview.png");
        Image image5 = new Image("C:\\Users\\PCWORLD\\IdeaProjects\\Semester_final\\src\\main\\resources\\assets\\image\\pngtreee (1).png");

        images.add(image4);
        images.add(image1);
        images.add(image3);
        images.add(image2);
        images.add(image5);

        image.setImage(images.get(count));

        calculateTotalSales();
        calculateTotalCustomers();
        calculateTotalUnits();
        calculateAvailableUnits();
        calculateTotalTenants();
        calculateTotalOwners();
        calculateTotalEmployees();
        calculateTotalFloors();
        calculateTotalHouseTypes();
        calculateTotalLeaseAgreement();
        calculateTotalMaintenance();
        calculateTotalPurchaseAgreement();
        int income = calculateTotalIncome();
        int expense = calculateTotalExpense();
        calculateTotalProfit(income,expense);

    }


    public void calculateTotalSales(){

        try{
            ObservableList<RequestDTO> totalSales = requestBO.getOnlyClosedRequests();
            salestxt.setText(String.valueOf(totalSales.size()));

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            System.err.println("Error while calculating the total sales: " + e.getMessage());
            notification("An error occurred while calculating the total sales. Please try again or contact support.");
        }

    }


    public void calculateTotalCustomers(){

        try{
            ObservableList<CustomerDTO> customers = customerBO.getAll();
            customertxt.setText(String.valueOf(customers.size()));

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            System.err.println("Error while calculating the total customers: " + e.getMessage());
            notification("An error occurred while calculating the total customers. Please try again or contact support.");
        }

    }

    public void calculateTotalUnits(){

        try{
            ObservableList<UnitDTO> unitTMS = unitBO.getAll();
            unitstxt.setText(String.valueOf(unitTMS.size()));

        } catch (SQLException |ClassNotFoundException e) {
            e.printStackTrace();
            System.err.println("Error while calculating the total units: " + e.getMessage());
            notification("An error occurred while calculating the total units. Please try again or contact support.");
        }

    }

    public void calculateAvailableUnits(){

        try{
            ObservableList<UnitDTO> unitTMS = unitBO.getOnlyAvailableUnits();
            availableUnittxt.setText(String.valueOf(unitTMS.size()));

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            System.err.println("Error while calculating the available units: " + e.getMessage());
            notification("An error occurred while calculating the available units. Please try again or contact support.");
        }

    }


    public void calculateTotalTenants(){

        try{
            ObservableList<TenantDTO> tenants = tenantBO.getAll();
            tenantstxt.setText(String.valueOf(tenants.size()));

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            System.err.println("Error while calculating the total tenants: " + e.getMessage());
            notification("An error occurred while calculating the total tenants. Please try again or contact support.");
        }

    }


    public void calculateTotalOwners(){

        try{
            ObservableList<OwnerDTO> owners = ownerBO.getAll();
            ownerstxt.setText(String.valueOf(owners.size()));
        }
        catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            System.err.println("Error while calculating the total owners: " + e.getMessage());
            notification("An error occurred while calculating the total owners. Please try again or contact support.");
        }
    }


    public void calculateTotalEmployees(){

        try{
            ObservableList<EmployeeDTO> employees = employeeBO.getAll();
            employeetxt.setText(String.valueOf(employees.size()));
        }
        catch (SQLException |ClassNotFoundException e) {
            e.printStackTrace();
            System.err.println("Error while calculating the total employees: " + e.getMessage());
            notification("An error occurred while calculating the total employees. Please try again or contact support.");
        }
    }


    public void calculateTotalMaintenance(){

        try{
            ObservableList<MaintenanceRequestDTO> requests = maintenanceRequestBO.getAllInProgressRequests();
            maintancetxt.setText(String.valueOf(requests.size()));
        }
        catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            System.err.println("Error while calculating the total maintenance requests: " + e.getMessage());
            notification("An error occurred while calculating the total maintenance requests. Please try again or contact support.");
        }
    }


    public void calculateTotalFloors(){

        try{
            ObservableList<FloorDTO> floors = floorBO.getAll();
            floortxt.setText(String.valueOf(floors.size()));
        }
        catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            System.err.println("Error while calculating the total floors: " + e.getMessage());
            notification("An error occurred while calculating the total floors. Please try again or contact support.");
        }
    }


    public void calculateTotalHouseTypes(){

        try{
            ObservableList<HouseTypeDTO> houseTypes = houseTypeBO.getAll();
            houseTypestxt.setText(String.valueOf(houseTypes.size()));
        }
        catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            System.err.println("Error while calculating the total house types: " + e.getMessage());
            notification("An error occurred while calculating the total house types. Please try again or contact support.");
        }
    }

    public void calculateTotalLeaseAgreement(){

        try{
            ObservableList<LeaseAgreementDTO> leaseAgreements = leaseAgreementBO.getOnlyActiveAgreements();
            leaseAgreementTxt.setText(String.valueOf(leaseAgreements.size()));
        }
        catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            System.err.println("Error while calculating the active lease agreements: " + e.getMessage());
            notification("An error occurred while calculating the active lease agreements. Please try again or contact support.");
        }
    }

    public void calculateTotalPurchaseAgreement(){

        try{
            ObservableList<PurchaseAgreementDTO> agreements = purchaseAgreementBO.getAll();
            purchaseAgreementTxt.setText(String.valueOf(agreements.size()));
        }
        catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            System.err.println("Error while calculating the active purchase agreements: " + e.getMessage());
            notification("An error occurred while calculating the active purchase agreements. Please try again or contact support.");
        }
    }


    public int calculateTotalIncome(){

        int tot = 0;

        try {
            double total = paymentBO.getTotalIncome();
            tot = (int) total;
            NumberFormat numberFormat = NumberFormat.getInstance();
            incometxt.setText(numberFormat.format(tot) + " LKR");
        }
        catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            System.err.println("Error while calculating total income: " + e.getMessage());
            notification("An error occurred while calculating total income. Please try again or contact support.");
        }

        return tot;
    }


    public int calculateTotalExpense(){

        int tot = 0;

        try {
            double total = expenseBO.getTotalExpense();
            tot = (int) total;
            NumberFormat numberFormat = NumberFormat.getInstance();
            expencetxt.setText(numberFormat.format(tot) + " LKR");
        }
        catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            System.err.println("Error while calculating total expense: " + e.getMessage());
            notification("An error occurred while calculating total expense. Please try again or contact support.");
        }

        return tot;

    }


    public void  calculateTotalProfit(int income,int expense){

        int profit = income - expense;
        NumberFormat numberFormat = NumberFormat.getInstance();
        profittxt.setText(numberFormat.format(profit) + " LKR");
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


