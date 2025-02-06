package com.example.test.bo.custom.impl;

import com.example.test.bo.custom.ExpenseBO;
import com.example.test.dao.DAOFactory;
import com.example.test.dao.SQLUtil;
import com.example.test.dao.custom.ExpenseDAO;
import com.example.test.dao.custom.MaintenanceRequestDAO;
import com.example.test.db.DBConnection;
import com.example.test.dto.ExpenseDTO;
import com.example.test.entity.Expense;
import com.example.test.entity.MaintainRequest;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.SQLException;

public class ExpenseBOImpl implements ExpenseBO {

    ExpenseDAO expenseDAO = (ExpenseDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.EXPENSE);
    MaintenanceRequestDAO maintenanceRequestDAO = (MaintenanceRequestDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.MAINTENANCEREQUEST);


    public String add(ExpenseDTO expenseDTO) throws SQLException, ClassNotFoundException{

        return expenseDAO.add(new Expense().toEntity(expenseDTO));
    }


    public String generateNewId() throws SQLException, ClassNotFoundException{

        return expenseDAO.generateNewId();
    }


    public ExpenseDTO getLastExpenseDetails() throws SQLException, ClassNotFoundException{

        return new ExpenseDTO().toDTO(expenseDAO.getLastExpenseDetails());
    }


    public ObservableList<ExpenseDTO> getAll() throws SQLException, ClassNotFoundException{

        ObservableList<Expense> expenses = expenseDAO.getAll();
        ObservableList<ExpenseDTO> expenseDTOS = FXCollections.observableArrayList();

        for(Expense x : expenses){
            expenseDTOS.add(new ExpenseDTO().toDTO(x));
        }
        return expenseDTOS;
    }


    public ObservableList<String> getAllDistinctMaintenanceRequestNos() throws SQLException, ClassNotFoundException{

        return expenseDAO.getAllDistinctMaintenanceRequestNos();
    }


    public String delete(ExpenseDTO selectedExpense) throws SQLException, ClassNotFoundException{

        Connection connection = DBConnection.getInstance().getConnection();
        connection.setAutoCommit(false);

        try{

            MaintainRequest maintainRequest = new MaintainRequest();
            maintainRequest.setRequestNo(selectedExpense.getMaintenanceRequestNo());
            maintainRequest.setActualCost(String.valueOf(selectedExpense.getAmount()));

            boolean isMakeNotComplete = maintenanceRequestDAO.setRequestNotComplete(maintainRequest);

            if(!isMakeNotComplete){
                connection.rollback();
                return "Something Went Wrong With Deleting The Selected Expense, Try Again Later";
            }

            String result = expenseDAO.delete(new Expense().toEntity(selectedExpense));

            if(!result.equals("Success")){
                connection.rollback();
                return "Something Went Wrong With Deleting The Selected Expense, Try Again Later";
            }

            connection.commit();
            return "Successfully Deleted The Expense No: "+selectedExpense.getExpenseNo();

        }
        catch (Exception e){
            connection.rollback();
            e.printStackTrace();
        }
        finally {
            connection.setAutoCommit(true);
        }

        return "0";

    }


    public String addNewExpenseForMaintenanceRequest(ExpenseDTO expenseDTO) throws SQLException, ClassNotFoundException{

        Connection connection = DBConnection.getInstance().getConnection();
        connection.setAutoCommit(false);

        try {
            boolean result = expenseDAO.addNewExpenseForMaintenanceRequest(new Expense().toEntity(expenseDTO));

            if(!result){
                connection.rollback();
                return "Something Went Wrong With Adding A New Expense, Please Try Again Later";
            }

            MaintainRequest maintainRequest = new MaintainRequest();
            maintainRequest.setRequestNo(expenseDTO.getMaintenanceRequestNo());
            maintainRequest.setActualCost(String.valueOf(expenseDTO.getAmount()));

            boolean isUpdateTheCost = maintenanceRequestDAO.setActualCost(maintainRequest);

            if(!isUpdateTheCost){
                connection.rollback();
                return "Something Went Wrong With Adding A New Expense, Please Try Again Later";
            }

            connection.commit();
            return "Successfully Added New Expense";
        }
        catch (Exception e){
            connection.rollback();
            e.printStackTrace();
        }
        finally {
            connection.setAutoCommit(true);
        }

        return "0";

    }


    public double getTotalExpense() throws SQLException, ClassNotFoundException{

        return expenseDAO.getTotalExpense();
    }


    public ObservableList<String> getRequestIdSuggestions(String input) throws SQLException, ClassNotFoundException{

        return maintenanceRequestDAO.getRequestIdSuggestions(input);
    }


    public boolean checkEnteredRequestIdValid(String maintenanceRequestNo) throws SQLException, ClassNotFoundException{

        return maintenanceRequestDAO.checkEnteredRequestIdValid(maintenanceRequestNo);
    }
}



