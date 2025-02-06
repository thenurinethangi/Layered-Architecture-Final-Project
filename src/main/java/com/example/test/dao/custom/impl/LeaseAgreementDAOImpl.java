package com.example.test.dao.custom.impl;

import com.example.test.bo.custom.HouseInspectBO;
import com.example.test.bo.custom.impl.HouseInspectBOImpl;
import com.example.test.dao.SQLUtil;
import com.example.test.dao.custom.LeaseAgreementDAO;
import com.example.test.dto.HouseInspectDTO;
import com.example.test.entity.LeaseAgreement;
import com.example.test.entity.Tenant;
import com.example.test.view.tdm.LeaseAgreementTM;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class LeaseAgreementDAOImpl implements LeaseAgreementDAO {

    private final TenantDAOImpl tenantDAOImpl = new TenantDAOImpl();


    public String add(LeaseAgreement leaseAgreement) throws SQLException, ClassNotFoundException {

        String leaseId = generateNewId();

        LocalDate date = LocalDate.now();
        String today = String.valueOf(date);

        String endDate = "0";

        if(leaseAgreement.getLeaseTurn().equals("6 Months")){

            LocalDate dateAfterSixMonths = date.plusMonths(6);
            endDate = String.valueOf(dateAfterSixMonths);

        }
        else if(leaseAgreement.getLeaseTurn().equals("12 Months")){

            LocalDate dateAfterTowelMonths = date.plusMonths(12);
            endDate = String.valueOf(dateAfterTowelMonths);

        }
        else if(leaseAgreement.getLeaseTurn().equals("18 Months")){

            LocalDate dateAfterEighteenMonths = date.plusMonths(18);
            endDate = String.valueOf(dateAfterEighteenMonths);

        }
        else if(leaseAgreement.getLeaseTurn().equals("2 Year")){

            LocalDate dateAfterTwoYears = date.plusMonths(24);
            endDate = String.valueOf(dateAfterTwoYears);

        }

        String sql = "insert into leaseagreement values(?,?,?,?,?,?,?,?,?)";
        boolean result = SQLUtil.execute(sql,leaseId,leaseAgreement.getTenantId(),leaseAgreement.getHouseId(),leaseAgreement.getLeaseTurn(),today,endDate,leaseAgreement.getMonthlyRent(),leaseAgreement.getSecurityPayment(),"Active");

        return result ? "Success" : "Failed";

    }


    public String generateNewId() throws SQLException, ClassNotFoundException {

        String sql = "select leaseId from leaseagreement order by leaseId desc limit 1";
        ResultSet result = SQLUtil.execute(sql);

        if(result.next()){

            String lastId = result.getString("leaseId");
            String subStr = lastId.substring(1);
            int id = Integer.parseInt(subStr);
            id+=1;
            return String.format("L%04d", id);

        }

        return  "L0001";
    }


    public ObservableList<LeaseAgreement> getAll() throws SQLException, ClassNotFoundException {

        String sql = "SELECT * FROM leaseagreement WHERE status != 'Deleted'";
        ResultSet result = SQLUtil.execute(sql);

        ObservableList<LeaseAgreement> allAgreements = FXCollections.observableArrayList();

        while(result.next()){

            String leaseId = result.getString(1);
            String tenantId = result.getString(2);
            String houseId = result.getString(3);
            String leaseTurn = result.getString(4);
            String startDate = result.getString(5);
            String endDate = result.getString(6);
            String status = result.getString(9);

            LocalDate endingDate = LocalDate.parse(endDate);

            if (LocalDate.now().isAfter(endingDate) && !status.equals("Canceled") && !status.equals("Deleted")) {

                status = "Expired";

                String sqlState = "UPDATE leaseagreement SET status = ? WHERE leaseId = ?";
                boolean res = SQLUtil.execute(sqlState,"Expired",leaseId);
            }


            LeaseAgreement leaseAgreement = new LeaseAgreement(leaseId,tenantId,houseId,leaseTurn,startDate,endDate,status);
            allAgreements.add(leaseAgreement);
        }

        return allAgreements;
    }

    @Override
    public String delete(LeaseAgreement entity) throws SQLException, ClassNotFoundException {
        return "";
    }

    @Override
    public boolean isExist(LeaseAgreement entity) throws SQLException, ClassNotFoundException {

        String sql = "select * from leaseagreement where leaseId = ?";
        ResultSet rst = SQLUtil.execute(sql,entity.getLeaseId());
        return rst.next();
    }


    public LeaseAgreement search(String leaseId) throws SQLException, ClassNotFoundException {

        String sql = "select leaseId,tenantId,houseId,leaseTurn,startDate,endDate,rentmount,securityPayment,status from leaseagreement where leaseId = ?";
        ResultSet result = SQLUtil.execute(sql,leaseId);

        LeaseAgreement leaseAgreement = new LeaseAgreement();

        if(result.next()){
            leaseAgreement.setLeaseId(result.getString(1));
            leaseAgreement.setTenantId(result.getString(2));
            leaseAgreement.setHouseId(result.getString(3));
            leaseAgreement.setLeaseTurn(result.getString(4));
            leaseAgreement.setStartDate(result.getString(5));
            leaseAgreement.setEndDate(result.getString(6));
            leaseAgreement.setMonthlyRent(result.getDouble(7));
            leaseAgreement.setSecurityPayment(result.getDouble(8));
            leaseAgreement.setStatus(result.getString(9));
        }

        return  leaseAgreement;

    }


    public String update(LeaseAgreement selectedLeaseAgreement) throws SQLException, ClassNotFoundException {

        LocalDate date = LocalDate.now();
        String today = String.valueOf(date);

        String endDate = "0";

        if(selectedLeaseAgreement.getLeaseTurn().equals("6 Months")){

            LocalDate dateAfterSixMonths = date.plusMonths(6);
            endDate = String.valueOf(dateAfterSixMonths);

        }
        else if(selectedLeaseAgreement.getLeaseTurn().equals("12 Months")){

            LocalDate dateAfterTowelMonths = date.plusMonths(12);
            endDate = String.valueOf(dateAfterTowelMonths);

        }
        else if(selectedLeaseAgreement.getLeaseTurn().equals("18 Months")){

            LocalDate dateAfterEighteenMonths = date.plusMonths(18);
            endDate = String.valueOf(dateAfterEighteenMonths);

        }
        else if(selectedLeaseAgreement.getLeaseTurn().equals("2 Year")){

            LocalDate dateAfterTwoYears = date.plusMonths(24);
            endDate = String.valueOf(dateAfterTwoYears);

        }

        String sql = "UPDATE leaseagreement SET startDate = ?, endDate = ?, leaseTurn = ?,status = ?  WHERE leaseId = ?";
        boolean result = SQLUtil.execute(sql,today,endDate,selectedLeaseAgreement.getLeaseTurn(),"Active",selectedLeaseAgreement.getLeaseId());

        return result ? "The lease agreement has been successfully renewed." : "Failed to renew lease agreement, try again later";

    }


    public boolean updateLeaseAgreementStatus(LeaseAgreement leaseAgreement) throws SQLException, ClassNotFoundException {

        String sql = "UPDATE leaseagreement SET status = ? WHERE leaseId = ?";
        boolean result = SQLUtil.execute(sql,leaseAgreement.getStatus(),leaseAgreement.getLeaseId());

        return result;
    }


    public String getLeaseAgreementByTenantId(String tenantId) throws SQLException, ClassNotFoundException {

        String sql = "select leaseId from leaseagreement where tenantId = ?";
        ResultSet result = SQLUtil.execute(sql,tenantId);

        String leaseId = "";

        if(result.next()){

            leaseId = result.getString("leaseId");
        }

        return leaseId;
    }


    public ObservableList<LeaseAgreement> getOnlyActiveAgreements() throws SQLException, ClassNotFoundException {

        String sql = "select * from leaseagreement where status = 'Active' or 'Expired'";
        ResultSet result = SQLUtil.execute(sql);

        ObservableList<LeaseAgreement> allAgreements = FXCollections.observableArrayList();

        while(result.next()){

            String leaseId = result.getString(1);
            String tenantId = result.getString(2);
            String houseId = result.getString(3);
            String leaseTurn = result.getString(4);
            String startDate = result.getString(5);
            String endDate = result.getString(6);
            String status = result.getString(9);

            LeaseAgreement leaseAgreement = new LeaseAgreement(leaseId,tenantId,houseId,leaseTurn,startDate,endDate,status);
            allAgreements.add(leaseAgreement);
        }

        return allAgreements;
    }


    public ObservableList<String> getDistinctHouseIds() throws SQLException, ClassNotFoundException {


        String sql = "select distinct houseId from leaseagreement where status !='Deleted'  order by houseId asc";
        ResultSet result = SQLUtil.execute(sql);

        ObservableList<String> houseIds = FXCollections.observableArrayList();
        houseIds.add("Select");

        while(result.next()){

            houseIds.add(result.getString("houseId"));
        }

        return houseIds;
    }


    public ObservableList<String> getExpiredAgreements() throws SQLException, ClassNotFoundException {

        String sql = "select * from leaseagreement where status = 'Expired'";
        ResultSet result = SQLUtil.execute(sql);

        ObservableList<String> expiredAgreements = FXCollections.observableArrayList();

        while(result.next()){

            String id = result.getString("leaseId");
            String tenantId = result.getString("tenantId");
            String endDate = result.getString("endDate");
            String status = result.getString("status");
            expiredAgreements.add("Lease ID: "+id+"  ,Tenant ID: "+tenantId+"  ,End Date: "+endDate+"\n->This Lease Agreement Has Expired, Take Necessary Actions");

        }

        return expiredAgreements;
    }


    public ObservableList<String> getSoonExpiredAgreements() throws SQLException, ClassNotFoundException {

        String sql = "select * from leaseagreement where status = 'Active'";
        ResultSet result = SQLUtil.execute(sql);

        ObservableList<String> expiredSoonAgreements = FXCollections.observableArrayList();

        while(result.next()){

            String id = result.getString("leaseId");
            String tenantId = result.getString("tenantId");
            String endD = result.getString("endDate");
            String status = result.getString("status");

            boolean isNearToEndLease = false;

            String end = endD;
            LocalDate endDate = LocalDate.parse(end);

            LocalDate currentDate = LocalDate.now();

            long daysDifference = ChronoUnit.DAYS.between(currentDate, endDate);

            if (daysDifference >= 0 && daysDifference <= 30) {
                System.out.println("The dates are near (within one month).");
                isNearToEndLease = true;
            } else {
                System.out.println("The dates are not near (not within one month).");
                isNearToEndLease = false;
            }

            if(isNearToEndLease) {
                expiredSoonAgreements.add("Lease ID: " + id + "  ,Tenant ID: " + tenantId + "  ,End Date: " + endDate + "\n->This Lease Agreement Will Soon Expire, Take Necessary Actions");
            }

        }

        return expiredSoonAgreements;
    }

}






