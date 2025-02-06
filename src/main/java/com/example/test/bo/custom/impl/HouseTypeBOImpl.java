package com.example.test.bo.custom.impl;

import com.example.test.bo.custom.HouseTypeBO;
import com.example.test.dao.DAOFactory;
import com.example.test.dao.custom.HouseTypeDAO;
import com.example.test.dao.custom.UnitDAO;
import com.example.test.dto.HouseTypeDTO;
import com.example.test.entity.HouseType;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.SQLException;

public class HouseTypeBOImpl implements HouseTypeBO {

    private HouseTypeDAO houseTypeDAO = (HouseTypeDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.HOUSETYPE);
    private UnitDAO unitDAO = (UnitDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.UNIT);


    public ObservableList<HouseTypeDTO> getAll() throws SQLException, ClassNotFoundException {

        ObservableList<HouseType> houseTypes = houseTypeDAO.getAll();
        ObservableList<HouseTypeDTO> houseTypeDTOs = FXCollections.observableArrayList();

        for(HouseType x : houseTypes){
            houseTypeDTOs.add(new HouseTypeDTO().toDTO(x));
        }
        return houseTypeDTOs;
    }


    public String add(HouseTypeDTO houseTypeDto) throws SQLException, ClassNotFoundException {

        return houseTypeDAO.add(new HouseType().toEntity(houseTypeDto));
    }


    public String delete(HouseTypeDTO houseTypeDto) throws SQLException, ClassNotFoundException {

        return houseTypeDAO.delete(new HouseType().toEntity(houseTypeDto));
    }


    public boolean isHouseTypeUsing(String houseType) throws SQLException, ClassNotFoundException {

        return unitDAO.isHouseTypeUsing(houseType);
    }


    public String Update(HouseTypeDTO houseTypeDTO, HouseTypeDTO oldHouseTypeDetailsDTO) throws SQLException, ClassNotFoundException{

        return houseTypeDAO.Update(new HouseType().toEntity(houseTypeDTO),new HouseType().toEntity(oldHouseTypeDetailsDTO));
    }
}
