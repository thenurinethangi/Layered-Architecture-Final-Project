package com.example.test.bo.custom;

import com.example.test.bo.SuperBO;
import com.example.test.dto.CustomerDTO;
import com.example.test.dto.HouseTypeDTO;
import com.example.test.dto.RequestDTO;
import com.example.test.dto.UnitDTO;
import com.example.test.entity.Unit;
import javafx.collections.ObservableList;

import java.sql.SQLException;

public interface RequestBO extends SuperBO {

    public ObservableList<RequestDTO> getAll() throws SQLException, ClassNotFoundException;

    public String delete(RequestDTO selectedRequest) throws SQLException, ClassNotFoundException;

    public String addNewTenant(RequestDTO requestDTO) throws SQLException, ClassNotFoundException;

    public String addNewOwner(RequestDTO requestDTO) throws SQLException, ClassNotFoundException;

    public ObservableList<RequestDTO> getOnlyClosedRequests() throws SQLException, ClassNotFoundException;

    public ObservableList<String> getDistinctCustomers() throws SQLException, ClassNotFoundException;

    public String generateNewId() throws SQLException, ClassNotFoundException;

    public ObservableList<HouseTypeDTO> getAllHouseTypes() throws SQLException, ClassNotFoundException;

    public String add(RequestDTO request) throws SQLException, ClassNotFoundException;

    public String editRequest(RequestDTO request1, RequestDTO request2) throws SQLException, ClassNotFoundException;

    public RequestDTO search(String requestId) throws SQLException, ClassNotFoundException;

    public boolean update(RequestDTO requestDTO) throws SQLException, ClassNotFoundException;

    public String updateAssignedHouse(RequestDTO requestDTO) throws SQLException, ClassNotFoundException;

    public ObservableList<String> getAvailableRentHouses(RequestDTO requestDTO) throws SQLException, ClassNotFoundException;

    public ObservableList<String> getRecommendedRentHouses(RequestDTO requestDTO) throws SQLException, ClassNotFoundException;

    public ObservableList<String> getAvailableSellHouses(RequestDTO requestDTO) throws SQLException, ClassNotFoundException;

    public ObservableList<Unit> getAvailableRentHousesAsUnitObject(RequestDTO requestDTO) throws SQLException, ClassNotFoundException;

    public ObservableList<Unit> getRecommendedRentHousesAsUnitObject(RequestDTO requestDTO) throws SQLException, ClassNotFoundException;

    public ObservableList<Unit> getAvailableSellHousesAsUnitObject(RequestDTO requestDTO) throws SQLException, ClassNotFoundException;

    public ObservableList<String> getRecommendedSellHouses(RequestDTO requestDTO) throws SQLException, ClassNotFoundException;

    public ObservableList<Unit> getRecommendedSellHousesAsUnitObject(RequestDTO requestDTO) throws SQLException, ClassNotFoundException;

    public boolean setHouseAvailable(UnitDTO unitDTO) throws SQLException, ClassNotFoundException;

    public CustomerDTO getCustomerDetails(String id)throws SQLException, ClassNotFoundException;

    public String checkCustomerExistByNic(String nic)throws SQLException, ClassNotFoundException;
}
