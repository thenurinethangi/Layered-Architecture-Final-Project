package com.example.test.controller;

import com.example.test.bo.BOFactory;
import com.example.test.bo.custom.TenantBO;
import com.example.test.dao.custom.impl.UnitDAOImpl;
import com.example.test.dto.TenantDTO;
import com.example.test.dto.UnitDTO;
import com.example.test.entity.Unit;
import com.example.test.view.tdm.TenantTM;
import com.example.test.dao.custom.impl.TenantDAOImpl;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

import java.sql.SQLException;

public class TenantDetailsController {

    @FXML
    private Label lastPaidMonth;

    @FXML
    private Label houseId;

    @FXML
    private Label houseType;

    @FXML
    private Label rentForMonth;

    @FXML
    private Label rentStartDate;

    @FXML
    private Label membersCount;

    @FXML
    private Label email;

    @FXML
    private Label phoneNo;

    @FXML
    private Label name;

    @FXML
    private Label tenantId;

    @FXML
    private Label remainingSecurityPayment;

    private final TenantBO tenantBO = (TenantBO) BOFactory.getInstance().getBO(BOFactory.BOType.TENANT);
    private TenantTM selectedTenant;


    public void setSelectedTenantDetails(TenantTM tenant){

        selectedTenant = tenant;

        tenantId.setText(selectedTenant.getTenantId());
        name.setText(selectedTenant.getName());
        phoneNo.setText(tenant.getPhoneNo());
        membersCount.setText(String.valueOf(selectedTenant.getMembersCount()));
        rentStartDate.setText(selectedTenant.getRentStartDate());
        rentForMonth.setText(String.valueOf(selectedTenant.getMonthlyRent()));
        lastPaidMonth.setText(selectedTenant.getLastPaidMonth());
        houseId.setText(selectedTenant.getHouseId());

        setAdditionalDetails();

    }

    public void setAdditionalDetails(){

        try {
            TenantDTO tenantDto = tenantBO.search(selectedTenant.getTenantId());
            remainingSecurityPayment.setText(String.valueOf(tenantDto.getSecurityPaymentRemain()));
            email.setText(tenantDto.getEmail());

            UnitDTO unitDTO = tenantBO.getUnitDetails(selectedTenant.getHouseId());
            houseType.setText(unitDTO.getHouseType());

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

    }
}





