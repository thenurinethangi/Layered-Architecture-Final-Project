package com.example.test.bo.custom;

import com.example.test.bo.SuperBO;
import com.example.test.dto.HouseInspectDTO;
import com.example.test.dto.PaymentDTO;
import com.example.test.dto.TenantDTO;
import javafx.collections.ObservableList;

import java.sql.SQLException;

public interface PaymentBO extends SuperBO {

    public String add(PaymentDTO paymentDTO) throws SQLException, ClassNotFoundException;

    public String generateNewId() throws SQLException, ClassNotFoundException;

    public ObservableList<PaymentDTO> getAll() throws SQLException, ClassNotFoundException;

    public ObservableList<TenantDTO> getAllTenantIds() throws SQLException, ClassNotFoundException;

    public ObservableList<String> getInvoiceNoSuggestions(String input) throws SQLException, ClassNotFoundException;

    public boolean checkIfThisPaymentIsFirstPaymentOrNot(PaymentDTO selectedPayment) throws SQLException, ClassNotFoundException;

    public String delete(PaymentDTO selectedPayment) throws SQLException, ClassNotFoundException;

    public String addNewMonthlyPayment(PaymentDTO paymentDTO, TenantDTO tenantDTO) throws SQLException, ClassNotFoundException;

    public String addNewPropertyDamagePayment(PaymentDTO paymentDTO,HouseInspectDTO houseInspectDTO) throws SQLException, ClassNotFoundException;

    public String getLastPayment() throws SQLException, ClassNotFoundException;

    public double getTotalIncome() throws SQLException, ClassNotFoundException;

    public ObservableList<String> getHouseCheckNumbersSuggestions(String input)throws SQLException, ClassNotFoundException;

    public HouseInspectDTO getHouseInspectionDetails(String inspectionId) throws SQLException, ClassNotFoundException;

    public TenantDTO getTenantDetailsUsingId(String tenantId) throws SQLException, ClassNotFoundException;

    public TenantDTO getTenantDetailsUsingPhoneNo(String phoneNo)throws SQLException, ClassNotFoundException;
}
