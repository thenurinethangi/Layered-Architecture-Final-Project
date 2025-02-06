package com.example.test.bo.custom;

import com.example.test.bo.SuperBO;
import com.example.test.dto.HouseTypeDTO;
import javafx.collections.ObservableList;

import java.sql.SQLException;

public interface HouseTypeBO extends SuperBO {

    ObservableList<HouseTypeDTO> getAll() throws SQLException, ClassNotFoundException;

    String add(HouseTypeDTO houseTypeDto) throws SQLException, ClassNotFoundException;

    String delete(HouseTypeDTO houseTypeDto) throws SQLException, ClassNotFoundException;

    boolean isHouseTypeUsing(String houseType) throws SQLException, ClassNotFoundException;

    String Update(HouseTypeDTO houseTypeDTO, HouseTypeDTO oldHouseTypeDetailsDTO) throws SQLException, ClassNotFoundException;
}
