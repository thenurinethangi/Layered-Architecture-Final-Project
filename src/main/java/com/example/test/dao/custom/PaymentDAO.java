package com.example.test.dao.custom;

import com.example.test.dao.CrudDAO;
import com.example.test.entity.Payment;
import javafx.collections.ObservableList;

import java.sql.SQLException;

public interface PaymentDAO extends CrudDAO<Payment> {

    public ObservableList<String> getInvoiceNoSuggestions(String input) throws SQLException, ClassNotFoundException;

    public boolean checkIfThisPaymentIsFirstPaymentOrNot(Payment selectedPayment) throws SQLException, ClassNotFoundException;

    public String getLastPayment() throws SQLException, ClassNotFoundException;

    public double getTotalIncome() throws SQLException, ClassNotFoundException;
}
