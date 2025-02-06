package com.example.test.bo.custom;

import com.example.test.bo.SuperBO;
import com.example.test.dto.ExpenseDTO;
import com.example.test.entity.Expense;
import javafx.collections.ObservableList;

import java.sql.SQLException;

public interface ExpenseBO extends SuperBO {

    public String add(ExpenseDTO expenseDTO) throws SQLException, ClassNotFoundException;

    public String generateNewId() throws SQLException, ClassNotFoundException;

    public ExpenseDTO getLastExpenseDetails() throws SQLException, ClassNotFoundException;

    public ObservableList<ExpenseDTO> getAll() throws SQLException, ClassNotFoundException;

    public ObservableList<String> getAllDistinctMaintenanceRequestNos() throws SQLException, ClassNotFoundException;

    public String delete(ExpenseDTO selectedExpense) throws SQLException, ClassNotFoundException;

    public String addNewExpenseForMaintenanceRequest(ExpenseDTO expenseDTO) throws SQLException, ClassNotFoundException;

    public double getTotalExpense() throws SQLException, ClassNotFoundException;

    public ObservableList<String> getRequestIdSuggestions(String input) throws SQLException, ClassNotFoundException;

    public boolean checkEnteredRequestIdValid(String maintenanceRequestNo) throws SQLException, ClassNotFoundException;

}
