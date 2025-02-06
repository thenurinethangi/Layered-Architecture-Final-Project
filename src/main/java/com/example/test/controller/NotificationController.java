package com.example.test.controller;

import com.example.test.bo.BOFactory;
import com.example.test.bo.custom.NotificationBO;
import com.example.test.dto.TenantDTO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.ListView;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class NotificationController implements Initializable {

    @FXML
    private ListView<String> notificationList;

    private final NotificationBO notificationBO = (NotificationBO) BOFactory.getInstance().getBO(BOFactory.BOType.NOTIFICATION);


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        try {
            ObservableList<TenantDTO> tenants = notificationBO.getTenantWhoNotDonePayment();

            ObservableList<String> notifications = FXCollections.observableArrayList();

            for(TenantDTO x : tenants){
                notifications.add("Tenant ID: " + x.getTenantId() + ",   Name: " + x.getName()+",   Last Paid Month Is: "+x.getLastPaidMonth()+"\n->Rent For This Month Has Not Yet Been Paid.");
            }

            //notificationList.setItems(notPaidTenants);

            ObservableList<TenantDTO> notPaidForEarlierMonthsTenants =  notificationBO.checkTenantsWhoHaveNotPaidForEarlierMonths();

            for(TenantDTO x : notPaidForEarlierMonthsTenants){
                notifications.add("Tenant ID: " + x.getTenantId() + ",   Name: " + x.getName()+",   Last Paid Month Is: "+x.getLastPaidMonth()+"\n->Not Paid For Early Month.");
            }

            //notificationList.setItems(notPaidTenants);

            ObservableList<String> expiredAgreements = notificationBO.getExpiredAgreements();

            for(String  x : expiredAgreements){
                notifications.add(x);
            }

            //notificationList.setItems(notifications);

            ObservableList<String> expiredSoonAgreements = notificationBO.getSoonExpiredAgreements();

            for(String  x : expiredSoonAgreements){
                notifications.add(x);
            }

            //notificationList.setItems(notifications);

            ObservableList<String> notCompletedRequests = notificationBO.getInProcessMaintenanceRequest();

            for(String  x : notCompletedRequests){
                notifications.add(x);
            }

            ObservableList<String> notDamageCostPaidTenants = notificationBO.getWhoNotPaidDamageCost();

            for(String  x : notDamageCostPaidTenants){
                notifications.add(x);
            }

            notificationList.setItems(notifications);
        }

        catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            System.err.println("Error while calculating total expense: " + e.getMessage());
            notification("An error occurred while calculating total expense. Please try again or contact support.");
        }

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





