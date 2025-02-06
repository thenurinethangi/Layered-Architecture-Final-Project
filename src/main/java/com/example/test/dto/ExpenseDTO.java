package com.example.test.dto;

import com.example.test.entity.Expense;
import com.example.test.view.tdm.ExpenseTM;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class ExpenseDTO {

    private String expenseNo;
    private String description;
    private double amount;
    private String maintenanceRequestNo;
    private String date;

    public ExpenseDTO toDTO(Expense expense){
        this.expenseNo = expense.getExpenceNo();
        this.description = expense.getDescription();
        this.amount = expense.getAmount();
        this.maintenanceRequestNo = expense.getRequestNo();
        this.date = expense.getDate();
        return this;
    }

    public ExpenseDTO toDTO(ExpenseTM expenseTM){
        this.expenseNo = expenseTM.getExpenseNo();
        this.description = expenseTM.getDescription();
        this.amount = expenseTM.getAmount();
        this.maintenanceRequestNo = expenseTM.getRequestNo();
        this.date = expenseTM.getDate();
        return this;
    }

}
