package com.example.test.dao.custom;

import com.example.test.dao.CrudDAO;
import com.example.test.entity.Expense;
import javafx.collections.ObservableList;

import java.sql.SQLException;

public interface ExpenseDAO extends CrudDAO<Expense> {

    public Expense getLastExpenseDetails() throws SQLException, ClassNotFoundException;

    public ObservableList<String> getAllDistinctMaintenanceRequestNos() throws SQLException, ClassNotFoundException;

    public boolean addNewExpenseForMaintenanceRequest(Expense expense) throws SQLException, ClassNotFoundException;

    public double getTotalExpense() throws SQLException, ClassNotFoundException;
}
