package com.example.test.dao.custom.impl;

import com.example.test.dao.SQLUtil;
import com.example.test.dao.custom.TenantDAO;
import com.example.test.dto.TenantDTO;
import com.example.test.entity.Tenant;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.List;
import java.util.stream.Collectors;

public class TenantDAOImpl implements TenantDAO {


    public String generateNewId() throws SQLException, ClassNotFoundException {

        String sql = "select tenantId from tenant order by tenantId desc limit 1";
        ResultSet result = SQLUtil.execute(sql);

        if(result.next()){

            String lastId = result.getString("tenantId");
            String subStr = lastId.substring(1);
            int id = Integer.parseInt(subStr);
            id+=1;
            return String.format("T%04d", id);

        }

        return "T0001";

    }


    public String add(Tenant tenant) throws SQLException, ClassNotFoundException {

        String sql = "insert into tenant values(?,?,?,?,?,?,?,?,?,?,?)";

        LocalDate date = LocalDate.now();
        DateTimeFormatter formatter = new DateTimeFormatterBuilder().appendPattern("yyyy-MM-dd").toFormatter();

        String today = date.format(formatter);
        System.out.println("today: "+today);

        DateTimeFormatter monthFormatter = DateTimeFormatter.ofPattern("MMMM");
        String currentMonth = date.format(monthFormatter);
        System.out.println("Current month: " + currentMonth);

        double monthlyPayment = tenant.getMonthlyRent();
        double securityPayment = tenant.getSecurityPaymentRemain();

        boolean result = SQLUtil.execute(sql,tenant.getTenantId(),tenant.getHeadOfHouseholdName(),tenant.getPhoneNo(),tenant.getMembersCount(),today,monthlyPayment,currentMonth,tenant.getHouseId(),tenant.getEmail(),securityPayment,1);

        return result ? "Success" : "Failed";
    }


    public ObservableList<Tenant> getAll() throws SQLException, ClassNotFoundException {

        String sql = "select * from tenant where isActiveTenant!=0";
        ResultSet result = SQLUtil.execute(sql);

        ObservableList<Tenant> allTenants = FXCollections.observableArrayList();

        while(result.next()){

            Tenant tenant = new Tenant();

            String id = result.getString(1);
            String name = result.getString(2);
            String phoneNo = result.getString(3);
            int membersCount = result.getInt(4);
            String startDate = result.getString(5);
            double monthlyRent = result.getDouble(6);
            String lastPaidMonth = result.getString(7);
            String houseId = result.getString(8);

            tenant.setTenantId(id);
            tenant.setHeadOfHouseholdName(name);
            tenant.setPhoneNo(phoneNo);
            tenant.setMembersCount(membersCount);
            tenant.setRentStartDate(startDate);
            tenant.setMonthlyRent(monthlyRent);
            tenant.setLastPaidMonth(lastPaidMonth);
            tenant.setHouseId(houseId);

            allTenants.add(tenant);
        }

        return allTenants;
    }


    public ObservableList<String> getHouseIds() throws SQLException, ClassNotFoundException {

        String sql = "select houseId from tenant where isActiveTenant = 1 order by houseId desc";
        ResultSet result = SQLUtil.execute(sql);

        ObservableList<String> houseIds = FXCollections.observableArrayList();
        houseIds.add("Select");

        while(result.next()){
            houseIds.add(result.getString("houseId"));
        }

        return houseIds;
    }


    public ObservableList<String> getNameSuggestions(String input) throws SQLException, ClassNotFoundException {

        String sql = "SELECT headOfHouseholdName FROM tenant WHERE headOfHouseholdName LIKE ?";
        ResultSet result = SQLUtil.execute(sql, input + "%");

        ObservableList<String> names = FXCollections.observableArrayList();

        while (result.next()) {
            names.add(result.getString("headOfHouseholdName"));
        }

        return names;
    }


    public Tenant search(String tenantId) throws SQLException, ClassNotFoundException {

        String sql = "select tenantId,headOfHouseholdName,rentForMonth,lastPayementMonth,email,securityPaymentRemain,houseId from tenant where tenantId = ? and isActiveTenant = ?";
        ResultSet result = SQLUtil.execute(sql,tenantId,1);

        Tenant tenant = new Tenant();

        if(result.next()){
            tenant.setTenantId(result.getString("tenantId"));
            tenant.setHeadOfHouseholdName(result.getString("headOfHouseholdName"));
            tenant.setMonthlyRent(result.getDouble("rentForMonth"));
            tenant.setLastPaidMonth(result.getString("lastPayementMonth"));
            tenant.setSecurityPaymentRemain(result.getDouble("securityPaymentRemain"));
            tenant.setEmail(result.getString("email"));
            tenant.setHouseId(result.getString("houseId"));
        }

        return tenant;

    }


    public String update(Tenant tenant) throws SQLException, ClassNotFoundException {

        String sql = "UPDATE tenant SET headOfHouseholdName = ?, phoneNo = ?, membersCount = ?, email = ? WHERE tenantId = ?";
        boolean result = SQLUtil.execute(sql,tenant.getHeadOfHouseholdName(),tenant.getPhoneNo(),tenant.getMembersCount(),tenant.getEmail(),tenant.getTenantId());

        return result ? "Tenant Updated Successfully" : "Failed To Update Tenant, Please Try Again Later.";
    }


    public boolean updateLastPaymentMonth(Tenant tenant) throws SQLException, ClassNotFoundException {

        String sqlOne = "select lastPayementMonth from tenant where tenantId = ?";
        ResultSet resultOne = SQLUtil.execute(sqlOne,tenant.getTenantId());

        String lastPaidMonth = "January";

        if(resultOne.next()){

            lastPaidMonth = resultOne.getString("lastPayementMonth");
            System.out.println(lastPaidMonth);
        }

        Month month = Month.valueOf(lastPaidMonth.toUpperCase());
        Month previousMonth = month.minus(1);
        System.out.println("The previous month is: " + previousMonth);
        String updatedMonth = String.valueOf(previousMonth).toLowerCase();

        String sqlTwo = "UPDATE tenant SET lastPayementMonth = ? WHERE tenantId = ?";
        boolean resultTwo = SQLUtil.execute(sqlTwo,updatedMonth,tenant.getTenantId());

        return resultTwo;
    }


    public String delete(Tenant tenant) throws SQLException, ClassNotFoundException {

        String sql = "UPDATE tenant SET isActiveTenant = ? WHERE tenantId = ?";
        boolean result = SQLUtil.execute(sql,0,tenant.getTenantId());

        return result ? "Success" : "Failed";
    }


    @Override
    public boolean isExist(Tenant entity) throws SQLException, ClassNotFoundException {

        String sql = "select * from tenant where tenantId = ?";
        ResultSet rst = SQLUtil.execute(sql,entity.getTenantId());
        return rst.next();
    }


    public boolean checkRemainingSecurityFundEnoughOrNot(String tenantId, String costOfRepair) throws SQLException, ClassNotFoundException {

        String sql = "select securityPaymentRemain from tenant where tenantId = ?";
        ResultSet result = SQLUtil.execute(sql,tenantId);

        double remainingSecurityDeposit = 0.0;
        if(result.next()){

            remainingSecurityDeposit = result.getDouble("securityPaymentRemain");
        }

        if(remainingSecurityDeposit>Double.valueOf(costOfRepair)){
            return true;
        }
        return false;
    }


    public String reduceRepairCostFromSecurityCharge(String tenantId, String costOfRepair) throws SQLException, ClassNotFoundException {

        String sql = "UPDATE tenant SET securityPaymentRemain = securityPaymentRemain - ? WHERE tenantId = ?";
        boolean result = SQLUtil.execute(sql,Double.parseDouble(costOfRepair),tenantId);

        return result ? "Repair costs were successfully deducted from the security deposit." : "Failed to deduct repair cost from security deposit, try again later";
    }


    public Tenant searchByPhoneNo(String phoneNo) throws SQLException, ClassNotFoundException {

        String sql = "select * from tenant where phoneNo = ? and isActiveTenant = ?";
        ResultSet result = SQLUtil.execute(sql,phoneNo,1);

        Tenant tenant = new Tenant();

        if(result.next()){

            String id = result.getString(1);
            String name = result.getString(2);
            String no = result.getString(3);
            int membersCount = result.getInt(4);
            String startDate = result.getString(5);
            double monthlyRent = result.getDouble(6);
            String lastPaidMonth = result.getString(7);
            String houseId = result.getString(8);
            String email = result.getString(9);

            tenant.setTenantId(id);
            tenant.setHeadOfHouseholdName(name);
            tenant.setPhoneNo(no);
            tenant.setMembersCount(membersCount);
            tenant.setRentStartDate(startDate);
            tenant.setMonthlyRent(monthlyRent);
            tenant.setLastPaidMonth(lastPaidMonth);
            tenant.setHouseId(houseId);
            tenant.setEmail(email);

        }

        return tenant;
    }

    public boolean setNewLastPaidMonth(Tenant tenant) throws SQLException, ClassNotFoundException {

        String sql = "UPDATE tenant SET lastPayementMonth = ? WHERE tenantId = ?";
        boolean result = SQLUtil.execute(sql,tenant.getLastPaidMonth(),tenant.getTenantId());

        return result;
    }


    public ObservableList<Tenant> getTenantWhoNotDonePayment() throws SQLException, ClassNotFoundException {

        String sql = "select * from tenant where isActiveTenant = 1";
        ResultSet result = SQLUtil.execute(sql);

        ObservableList<Tenant> tenants = FXCollections.observableArrayList();

        try {
            while (result.next()) {
                String tenantId = result.getString("tenantId");
                String name = result.getString("headOfHouseholdName");
                String lastPaymentMonth = result.getString("lastPayementMonth");

                Tenant tenant = new Tenant();
                tenant.setTenantId(tenantId);
                tenant.setHeadOfHouseholdName(name);
                tenant.setLastPaidMonth(lastPaymentMonth);
                tenants.add(tenant);
            }

            LocalDate currentDate = LocalDate.now();
            String currentMonth = currentDate.getMonth().toString().toLowerCase();
            int currentYear = currentDate.getYear();

            List<Tenant> notPaidTenants = tenants.stream()
                    .filter(tenant -> !tenant.getLastPaidMonth().toLowerCase().equals(currentMonth))
                    .collect(Collectors.toList());

            System.out.println("Tenants who haven't paid for " + currentMonth + " " + currentYear + ":");
            for (Tenant tenant : notPaidTenants) {
                System.out.println("Tenant ID: " + tenant.getTenantId() + ", Name: " + tenant.getHeadOfHouseholdName());
            }

            return FXCollections.observableArrayList(notPaidTenants);

        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Error while getting payment not done tenants: " + e.getMessage());
            return FXCollections.observableArrayList();
        }

    }


    public ObservableList<Tenant> checkTenantsWhoHaveNotPaidForEarlierMonths() throws SQLException, ClassNotFoundException {

        String sql = "select * from tenant where isActiveTenant = 1";
        ResultSet result = SQLUtil.execute(sql);

        ObservableList<Tenant> tenants = FXCollections.observableArrayList();

        try {
            while (result.next()) {
                String tenantId = result.getString("tenantId");
                String name = result.getString("headOfHouseholdName");
                String lastPaymentMonth = result.getString("lastPayementMonth");

                Tenant tenant = new Tenant();
                tenant.setTenantId(tenantId);
                tenant.setHeadOfHouseholdName(name);
                tenant.setLastPaidMonth(lastPaymentMonth);

                tenants.add(tenant);
            }

            LocalDate currentDate = LocalDate.now();
            Month currentMonth = currentDate.getMonth();
            int currentYear = currentDate.getYear();

            List<Tenant> notPaidTenants = tenants.stream()
                    .filter(tenant -> {

                        Month lastPaymentMonthEnum = Month.valueOf(tenant.getLastPaidMonth().toUpperCase());
                        return lastPaymentMonthEnum.compareTo(currentMonth) < 0;

                    })
                    .collect(Collectors.toList());

            System.out.println("Tenants who haven't paid for any earlier months:");
            for (Tenant tenant : notPaidTenants) {
                System.out.println("Tenant ID: " + tenant.getTenantId() + ", Name: " + tenant.getHeadOfHouseholdName());
            }

            return FXCollections.observableArrayList(notPaidTenants);

        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Error while getting tenants who haven't paid for earlier months: " + e.getMessage());
            return FXCollections.observableArrayList();

        }
    }
}





