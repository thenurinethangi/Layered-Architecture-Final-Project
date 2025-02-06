package com.example.test.bo.custom;

import com.example.test.bo.SuperBO;
import com.example.test.dto.FloorDTO;
import com.example.test.dto.HouseReturnDTO;
import com.example.test.dto.HouseTypeDTO;
import com.example.test.dto.UnitDTO;
import com.example.test.entity.Floor;
import javafx.collections.ObservableList;

import java.sql.SQLException;

public interface UnitBO extends SuperBO {

    public ObservableList<UnitDTO> getAll() throws SQLException, ClassNotFoundException;

    public String delete(UnitDTO selectedRow) throws SQLException, ClassNotFoundException;

    public UnitDTO search(String houseId) throws SQLException, ClassNotFoundException;

    public boolean setHouseAvailable(HouseReturnDTO houseReturnDto) throws SQLException, ClassNotFoundException;

    public ObservableList<UnitDTO> getOnlyAvailableUnits() throws SQLException, ClassNotFoundException;

    public String generateNewId() throws SQLException, ClassNotFoundException;

    public String add(UnitDTO unitDTO) throws SQLException, ClassNotFoundException;

    public String update(UnitDTO unitDTO) throws SQLException, ClassNotFoundException;

    public ObservableList<HouseTypeDTO> getAllHouseTypes()throws SQLException, ClassNotFoundException;

    public ObservableList<FloorDTO> getAllFloors()throws SQLException, ClassNotFoundException;
}
