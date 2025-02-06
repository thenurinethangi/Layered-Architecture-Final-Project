package com.example.test.dao.custom;

import com.example.test.dao.CrudDAO;
import com.example.test.entity.Floor;
import javafx.collections.ObservableList;

import java.sql.SQLException;

public interface FloorDAO extends CrudDAO<Floor> {

    public ObservableList<String> getAllIds() throws SQLException, ClassNotFoundException;

}
