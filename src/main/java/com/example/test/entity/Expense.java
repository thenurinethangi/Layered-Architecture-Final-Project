package com.example.test.entity;

import com.example.test.dto.ExpenseDTO;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Expense {

    private String expenceNo;
    private String description;
    private double amount;
    private String requestNo;
    private String date;
    private int isActiveExpense;

    public Expense toEntity(ExpenseDTO expenseDto){
        this.expenceNo = expenseDto.getExpenseNo();
        this.description = expenseDto.getDescription();
        this.amount = expenseDto.getAmount();
        this.requestNo = expenseDto.getMaintenanceRequestNo();
        this.date = expenseDto.getDate();
        return this;
    }
}
