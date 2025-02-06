package com.example.test.controller;

import com.example.test.bo.BOFactory;
import com.example.test.bo.custom.RequestBO;
import com.example.test.dto.RequestDTO;
import com.example.test.dto.UnitDTO;
import com.example.test.entity.Unit;
import com.example.test.view.tdm.RequestTM;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;

import java.sql.SQLException;

public class RecommendedHousesController {

    @FXML
    private ListView<String> availableHousesList;

    @FXML
    private ListView<String> recommendedHousesList;

    private RequestTM requestTm;
    private RequestDTO requestDTO;
    private String estimatedMonthlyBudgetForRent;
    private ObservableList<String> messageOne = FXCollections.observableArrayList("No Recommended Houses, As There Are Available Options.");
    private ObservableList<String> messageTwo = FXCollections.observableArrayList("No Available Houses For This Request");
    private ObservableList<String> messageThree = FXCollections.observableArrayList("No Recommended Houses For This Request");
    private final RequestBO requestBO = (RequestBO) BOFactory.getInstance().getBO(BOFactory.BOType.REQUEST);
    private ObservableList<Unit> availableUnitsDtos;
    private ObservableList<Unit> recommendedUnitsDto;
    private ObservableList<String> recommendedUnits;
    private ObservableList<String> availableUnits;


    @FXML
    void availableHousesListOnMouseClicked(MouseEvent event) {

        String selectedAvailableHouse = availableHousesList.getSelectionModel().getSelectedItem();

        if(selectedAvailableHouse==null || selectedAvailableHouse.equals("No Available Houses For This Request")){
            return;
        }
        else{

            if(!requestTm.getHouseId().equals("-")){
                try {
                    UnitDTO unitDTO = new UnitDTO();
                    unitDTO.setHouseId(requestTm.getHouseId());
                    unitDTO.setStatus("Available");
                    boolean result = requestBO.setHouseAvailable(unitDTO);
                    if(!result){
                        Notifications notifications = Notifications.create();
                        notifications.title("Notification");
                        notifications.text("Something went wrong, try again later");
                        notifications.hideCloseButton();
                        notifications.hideAfter(Duration.seconds(5));
                        notifications.position(Pos.CENTER);
                        notifications.darkStyle();
                        notifications.showInformation();
                        return;
                    }
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                } catch (ClassNotFoundException e) {
                    throw new RuntimeException(e);
                }
            }

            int index = 0;

            for (int i = 0; i < availableUnits.size(); i++) {
                if(availableUnits.get(i).equals(selectedAvailableHouse)){
                    index = i;
                    break;
                }
            }

            try {
                RequestDTO requestDTO = new RequestDTO();
                requestDTO.setRequestId(requestTm.getRequestId());
                requestDTO.setHouseId(availableUnitsDtos.get(index).getHouseId());

                String response = requestBO.updateAssignedHouse(requestDTO);
                Notifications notifications = Notifications.create();
                notifications.title("Notification");
                notifications.text(response);
                notifications.hideCloseButton();
                notifications.hideAfter(Duration.seconds(5));
                notifications.position(Pos.CENTER);
                notifications.darkStyle();
                notifications.showInformation();

            } catch (SQLException e) {
                throw new RuntimeException(e);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }

    }

    @FXML
    void recommendedHousesListOnMouseClicked(MouseEvent event) {

        String selectedRecommendedHouse = recommendedHousesList.getSelectionModel().getSelectedItem();


        if (selectedRecommendedHouse == null ||
                selectedRecommendedHouse.equals("No Recommended Houses, As There Are Available Options.") ||
                selectedRecommendedHouse.equals("No Recommended Houses For This Request")) {
            return;
        } else {

            if(!requestTm.getHouseId().equals("-")){
                try {
                    UnitDTO unitDTO = new UnitDTO();
                    unitDTO.setHouseId(requestTm.getHouseId());
                    unitDTO.setStatus("Available");
                    boolean result = requestBO.setHouseAvailable(unitDTO);
                    if(!result){
                        Notifications notifications = Notifications.create();
                        notifications.title("Notification");
                        notifications.text("Something went wrong, try again later");
                        notifications.hideCloseButton();
                        notifications.hideAfter(Duration.seconds(5));
                        notifications.position(Pos.CENTER);
                        notifications.darkStyle();
                        notifications.showInformation();
                        return;
                    }
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                } catch (ClassNotFoundException e) {
                    throw new RuntimeException(e);
                }
            }

            int index = 0;


            for (int i = 0; i < recommendedUnits.size(); i++) {
                if (recommendedUnits.get(i).equals(selectedRecommendedHouse)) {
                    index = i;
                    break;
                }
            }

            try {
                RequestDTO requestDTO = new RequestDTO();
                requestDTO.setRequestId(requestTm.getRequestId());
                requestDTO.setHouseId(recommendedUnitsDto.get(index).getHouseId());
                String response = requestBO.updateAssignedHouse(requestDTO);

                Notifications notifications = Notifications.create();
                notifications.title("Notification");
                notifications.text(response);
                notifications.hideCloseButton();
                notifications.hideAfter(Duration.seconds(5));
                notifications.position(Pos.CENTER);
                notifications.darkStyle();
                notifications.showInformation();

            } catch (SQLException e) {
                throw new RuntimeException(e);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
    }


    public void setSelectedRequestData(RequestTM request) {

        requestTm = request;

        if(requestTm.getRentOrBuy().equals("Rent")){
            getSelectedRequestData();
            getAvailableRentHouses();
        }
        else{
            getSelectedRequestData();
            getAvailableSellHouses();
        }
    }



    public void getSelectedRequestData(){

        try {
            requestDTO = requestBO.search(requestTm.getRequestId());
            estimatedMonthlyBudgetForRent = requestDTO.getEstimatedMonthlyBudgetForRent();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }


    public void getAvailableRentHouses(){

        try {
            availableUnits = requestBO.getAvailableRentHouses(requestDTO);
            availableUnitsDtos = requestBO.getAvailableRentHousesAsUnitObject(requestDTO);

            if(availableUnits.isEmpty()){
                availableHousesList.setItems(messageTwo);
                getRecommendedRentHouses();
            }
            else{
                availableHousesList.setItems(availableUnits);
                recommendedHousesList.setItems(messageOne);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

    }


    public void getAvailableSellHouses(){

        try {
            availableUnits = requestBO.getAvailableSellHouses(requestDTO);
            availableUnitsDtos = requestBO.getAvailableSellHousesAsUnitObject(requestDTO);

            if(availableUnits.isEmpty()){
                availableHousesList.setItems(messageTwo);
                getRecommendedSellHouses();
            }
            else{
                availableHousesList.setItems(availableUnits);
                recommendedHousesList.setItems(messageOne);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

    }


    public void getRecommendedRentHouses(){

        try {
            recommendedUnits = requestBO.getRecommendedRentHouses(requestDTO);
            recommendedUnitsDto = requestBO.getRecommendedRentHousesAsUnitObject(requestDTO);

            if(recommendedUnits.isEmpty()){
                recommendedHousesList.setItems(messageThree);
            }
            else {
                recommendedHousesList.setItems(recommendedUnits);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }


    }

    public void getRecommendedSellHouses(){

        try {

            recommendedUnits = requestBO.getRecommendedSellHouses(requestDTO);
            recommendedUnitsDto = requestBO.getRecommendedSellHousesAsUnitObject(requestDTO);

            if (recommendedUnits.isEmpty()) {
                recommendedHousesList.setItems(messageThree);
            } else {
                recommendedHousesList.setItems(recommendedUnits);
            }
        }  catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

    }

}





