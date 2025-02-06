package com.example.test.dao;

import javafx.collections.ObservableList;

import java.sql.SQLException;

public interface CrudDAO<T> extends SuperDAO{

    public ObservableList<T> getAll() throws SQLException, ClassNotFoundException;

    public String delete(T entity) throws SQLException, ClassNotFoundException;

    public boolean isExist(T entity) throws SQLException, ClassNotFoundException;

    public T search(String id) throws SQLException, ClassNotFoundException;

    public String generateNewId() throws SQLException, ClassNotFoundException;

    public String update(T entity) throws SQLException, ClassNotFoundException;

    public String add(T entity) throws SQLException, ClassNotFoundException;
}
