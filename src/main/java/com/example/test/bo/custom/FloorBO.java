package com.example.test.bo.custom;

import com.example.test.bo.SuperBO;
import com.example.test.dto.FloorDTO;
import com.example.test.dto.UnitDTO;
import javafx.collections.ObservableList;

import java.sql.SQLException;

public interface FloorBO extends SuperBO {

    public ObservableList<FloorDTO> getAll() throws SQLException, ClassNotFoundException;

    public String delete(FloorDTO floorDto) throws SQLException, ClassNotFoundException;

    public String add(FloorDTO floorDto) throws SQLException, ClassNotFoundException;

    public ObservableList<String> getIds() throws SQLException, ClassNotFoundException;

    public boolean checkFloorIsUsed(UnitDTO unitDTO) throws SQLException, ClassNotFoundException;

    public String update(FloorDTO floorDto) throws SQLException, ClassNotFoundException;
}
