package com.example.test.bo.custom.impl;

import com.example.test.bo.custom.UnitBO;
import com.example.test.dao.DAOFactory;
import com.example.test.dao.custom.FloorDAO;
import com.example.test.dao.custom.HouseTypeDAO;
import com.example.test.dao.custom.UnitDAO;
import com.example.test.dto.*;
import com.example.test.entity.Floor;
import com.example.test.entity.HouseType;
import com.example.test.entity.Unit;
import com.example.test.view.tdm.RequestTM;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.SQLException;

public class UnitBOImpl implements UnitBO {

    UnitDAO unitDAO = (UnitDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.UNIT);
    FloorDAO floorDAO = (FloorDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.FLOOR);
    HouseTypeDAO houseTypeDAO = (HouseTypeDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.HOUSETYPE);


    public ObservableList<com.example.test.dto.UnitDTO> getAll() throws SQLException, ClassNotFoundException {

        ObservableList<Unit> units = unitDAO.getAll();
        ObservableList<com.example.test.dto.UnitDTO> unitDTODTOS = FXCollections.observableArrayList();

        for(Unit x : units){
            unitDTODTOS.add(new com.example.test.dto.UnitDTO().toDTO(x));
        }
        return unitDTODTOS;
    }


    public String delete(com.example.test.dto.UnitDTO selectedRow) throws SQLException, ClassNotFoundException {

        return unitDAO.delete(new Unit().toEntity(selectedRow));
    }


    public com.example.test.dto.UnitDTO search(String houseId) throws SQLException, ClassNotFoundException {

        Unit unit = unitDAO.search(houseId);
        return new com.example.test.dto.UnitDTO().toDTO(unit);
    }


    public boolean setHouseAvailable(HouseReturnDTO houseReturnDto) throws SQLException, ClassNotFoundException {

        Unit unit = new Unit();
        unit.setHouseId(houseReturnDto.getHouseId());
        unit.setStatus("Available");
        return unitDAO.updateHouseStatus(unit);
    }


    public ObservableList<UnitDTO> getOnlyAvailableUnits() throws SQLException, ClassNotFoundException {

        ObservableList<Unit> units = unitDAO.getOnlyAvailableUnits();
        ObservableList<UnitDTO> unitDTOS = FXCollections.observableArrayList();

        for(Unit x : units){
            unitDTOS.add(new UnitDTO().toDTO(x));
        }
        return unitDTOS;
    }


    public String generateNewId() throws SQLException, ClassNotFoundException {

        return unitDAO.generateNewId();
    }


    public String add(UnitDTO unitDTO) throws SQLException, ClassNotFoundException {

        return unitDAO.add(new Unit().toEntity(unitDTO));
    }


    public String update(UnitDTO unitDTO) throws SQLException, ClassNotFoundException {

        return unitDAO.update(new Unit().toEntity(unitDTO));

    }


    public ObservableList<HouseTypeDTO> getAllHouseTypes()throws SQLException, ClassNotFoundException{

        ObservableList<HouseType> houseTypes = houseTypeDAO.getAll();
        ObservableList<HouseTypeDTO> houseTypeDTOS = FXCollections.observableArrayList();

        for(HouseType x : houseTypes){
            houseTypeDTOS.add(new HouseTypeDTO().toDTO(x));
        }
        return houseTypeDTOS;
    }


    public ObservableList<FloorDTO> getAllFloors()throws SQLException, ClassNotFoundException{

        ObservableList<Floor> floors = floorDAO.getAll();
        ObservableList<FloorDTO> floorDTOS = FXCollections.observableArrayList();

        for(Floor x : floors){
            floorDTOS.add(new FloorDTO().toDTO(x));
        }

        return floorDTOS;
    }
}
