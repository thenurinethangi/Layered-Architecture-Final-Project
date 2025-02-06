package com.example.test.view.tdm;

import com.example.test.dto.ExpenseDTO;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class ExpenseTM {

    private String expenseNo;
    private String description;
    private double amount;
    private String requestNo;
    private String date;

    public ExpenseTM toTM(ExpenseDTO expenseDto){
        this.expenseNo = expenseDto.getExpenseNo();
        this.description = expenseDto.getDescription();
        this.amount = expenseDto.getAmount();
        this.requestNo = expenseDto.getMaintenanceRequestNo();
        this.date = expenseDto.getDate();
        return this;
    }

}
