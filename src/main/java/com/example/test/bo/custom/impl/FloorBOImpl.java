package com.example.test.bo.custom.impl;

import com.example.test.bo.custom.FloorBO;
import com.example.test.dao.DAOFactory;
import com.example.test.dao.custom.FloorDAO;
import com.example.test.dao.custom.UnitDAO;
import com.example.test.dto.FloorDTO;
import com.example.test.dto.UnitDTO;
import com.example.test.entity.Floor;
import com.example.test.entity.Unit;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.SQLException;

public class FloorBOImpl implements FloorBO {

    private FloorDAO floorDAO = (FloorDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.FLOOR);
    private UnitDAO unitDAO = (UnitDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.UNIT);


    public ObservableList<FloorDTO> getAll() throws SQLException, ClassNotFoundException {

       ObservableList<Floor> floors = floorDAO.getAll();
       ObservableList<FloorDTO> floorDTOS = FXCollections.observableArrayList();

       for(Floor x : floors){
           floorDTOS.add(new FloorDTO().toDTO(x));
       }

       return floorDTOS;
    }


    public String delete(FloorDTO floorDto) throws SQLException, ClassNotFoundException {

       return floorDAO.delete(new Floor().toEntity(floorDto));

    }


    public String add(FloorDTO floorDto) throws SQLException, ClassNotFoundException {

        return floorDAO.add(new Floor().toEntity(floorDto));

    }


    public ObservableList<String> getIds() throws SQLException, ClassNotFoundException {

        return floorDAO.getAllIds();
    }


    public boolean checkFloorIsUsed(UnitDTO unitDTO) throws SQLException, ClassNotFoundException {

        return unitDAO.checkFloorIsUsed(new Unit().toEntity(unitDTO));
    }


    public String update(FloorDTO floorDto) throws SQLException, ClassNotFoundException {

        return floorDAO.update(new Floor().toEntity(floorDto));
    }
}
