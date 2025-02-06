package com.example.test.dao.custom.impl;

import com.example.test.dao.SQLUtil;
import com.example.test.dao.custom.ExpenseDAO;
import com.example.test.db.DBConnection;
import com.example.test.dto.ExpenseDTO;
import com.example.test.dto.TenantDTO;
import com.example.test.entity.Expense;
import com.example.test.entity.MaintainRequest;
import com.example.test.view.tdm.ExpenseTM;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public class ExpenseDAOImpl implements ExpenseDAO {


    public String add(Expense expense) throws SQLException, ClassNotFoundException {

        String newExpenseNo = generateNewId();

        String sql = "INSERT INTO expence (expenceNo, description, Amount, date, isActiveExpense) VALUES (?, ?, ?, ?, ?)";
        boolean result = SQLUtil.execute(sql,newExpenseNo,"Security deposit refunded expense",expense.getAmount(), String.valueOf(LocalDate.now()),1);

        return result ? "Success" : "Failed";
    }


    public String generateNewId() throws SQLException, ClassNotFoundException {

        String sql = "select expenceNo from expence order by expenceNo desc limit 1";
        ResultSet result = SQLUtil.execute(sql);

        if(result.next()){

            String lastId = result.getString("expenceNo");
            String subStr = lastId.substring(4);
            System.out.println("sub string: "+subStr);
            int id = Integer.parseInt(subStr);
            id+=1;
            return String.format("EXP-%05d", id);

        }
        else{
            return "EXP-00001";
        }

    }

    @Override
    public String update(Expense entity) throws SQLException, ClassNotFoundException {
        return "";
    }


    public Expense getLastExpenseDetails() throws SQLException, ClassNotFoundException {

        String sql = "select * from expence order by expenceNo desc limit 1";
        ResultSet result = SQLUtil.execute(sql);

        Expense expense = new Expense();

        if(result.next()){

            String expenseNo = result.getString("expenceNo");
            double amount = result.getDouble("Amount");

            expense.setExpenceNo(expenseNo);
            expense.setAmount(amount);
        }

        return expense;
    }


    public ObservableList<Expense> getAll() throws SQLException, ClassNotFoundException {

        String sql = "select * from expence where isActiveExpense = 1";
        ResultSet result = SQLUtil.execute(sql);

        ObservableList<Expense> allExpenses = FXCollections.observableArrayList();

        while(result.next()){

           String expenseNo = result.getString(1);
           String description = result.getString(2);
           double amount = result.getDouble(3);
           String requestId = result.getString(4);
           if(requestId==null){
              requestId = "N/A";
           }
           String date = result.getString(5);
           int isActive = result.getInt(6);

            Expense expense = new Expense(expenseNo,description,amount,requestId,date,isActive);
            allExpenses.add(expense);
        }
       return allExpenses;
    }


    public ObservableList<String> getAllDistinctMaintenanceRequestNos() throws SQLException, ClassNotFoundException {

        String sql = "SELECT DISTINCT requestNo FROM expence WHERE requestNo IS NOT NULL";
        ResultSet result = SQLUtil.execute(sql);

        ObservableList<String> requestNos = FXCollections.observableArrayList();
        requestNos.add("Select");

        while (result.next()){

            requestNos.add(result.getString("requestNo"));
        }

        return requestNos;
    }


    public String delete(Expense selectedExpense) throws SQLException, ClassNotFoundException {

        String sql = "UPDATE expence SET isActiveExpense = ? WHERE expenceNo = ?";
        boolean result =  SQLUtil.execute(sql,0,selectedExpense.getExpenceNo());

        return result ? "Success" : "Failed";

    }

    @Override
    public boolean isExist(Expense entity) throws SQLException, ClassNotFoundException {

        String sql = "select * from expence where expenceNo = ?";
        ResultSet rst = SQLUtil.execute(sql,entity.getExpenceNo());
        return rst.next();
    }


    @Override
    public Expense search(String id) throws SQLException, ClassNotFoundException {

        String sql = "select * from expence where expenceNo = ?";
        ResultSet rst = SQLUtil.execute(sql,id);

        Expense expense = new Expense();

        if(rst.next()){
            expense.setExpenceNo(rst.getString(1));
            expense.setDescription(rst.getString(2));
            expense.setAmount(rst.getDouble(3));
            expense.setRequestNo(rst.getString(4));
            expense.setDate(rst.getString(5));
            expense.setIsActiveExpense(rst.getInt(6));
        }

        return expense;
    }


    public boolean addNewExpenseForMaintenanceRequest(Expense expense) throws SQLException, ClassNotFoundException {

        String sql = "INSERT INTO expence (expenceNo, description, Amount, requestNo, date, isActiveExpense) VALUES (?, ?, ?, ?, ?, ?)";
        return SQLUtil.execute(sql, expense.getExpenceNo(), expense.getDescription(), expense.getAmount(), expense.getRequestNo(), expense.getDate(), 1);

    }


    public double getTotalExpense() throws SQLException, ClassNotFoundException {

        String sql = "select sum(Amount) from expence where isActiveExpense = 1";
        ResultSet result = SQLUtil.execute(sql);

        if(result.next()){
           return result.getDouble("sum(Amount)");
        }
        return 0;
    }
}






