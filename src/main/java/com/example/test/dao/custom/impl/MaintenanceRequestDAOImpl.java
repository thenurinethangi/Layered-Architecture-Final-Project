package com.example.test.dao.custom.impl;

import com.example.test.dao.SQLUtil;
import com.example.test.dao.custom.MaintenanceRequestDAO;
import com.example.test.entity.MaintainRequest;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.ResultSet;
import java.sql.SQLException;

public class MaintenanceRequestDAOImpl implements MaintenanceRequestDAO {

    public ObservableList<MaintainRequest> getAll() throws SQLException, ClassNotFoundException {

        String sql = "select * from maintainrequest where isActiveRequest = 1";
        ResultSet result = SQLUtil.execute(sql);

        ObservableList<MaintainRequest> allRequests = FXCollections.observableArrayList();

        while(result.next()){

            String requestId = result.getString(1);
            String desc = result.getString(2);
            double estimatedCost = result.getDouble(3);
            String actualCost = result.getString(4);
            if(actualCost==null){
                actualCost = "-";
            }
            String date = result.getString(5);
            String technician = result.getString(6);
            String tenantId = result.getString(7);
            String status = result.getString(8);
            int isActive = result.getInt(9);

            MaintainRequest maintainRequest = new MaintainRequest(requestId,desc,estimatedCost,actualCost,date,technician,tenantId,status,isActive);
            allRequests.add(maintainRequest);

        }
        return allRequests;
    }


    public ObservableList<String> getDistinctTenantIds() throws SQLException, ClassNotFoundException {

        String sql = "select Distinct tenantId from maintainrequest where isActiveRequest = 1 order by tenantId asc";
        ResultSet result = SQLUtil.execute(sql);

        ObservableList<String> tenantIds = FXCollections.observableArrayList();

        while(result.next()){

            tenantIds.add(result.getString("tenantId"));
        }

        return tenantIds;
    }


    public String updateRequestStatus(MaintainRequest selectedRequest) throws SQLException, ClassNotFoundException {

        String sql = "UPDATE maintainrequest SET status = ? WHERE requestNo = ?";
        boolean result = SQLUtil.execute(sql,selectedRequest.getStatus(),selectedRequest.getRequestNo());

        return result ? "Successfully Completed The Maintenance Request No :"+selectedRequest.getRequestNo() : "Failed To Complete Maintenance Request No :"+selectedRequest.getRequestNo()+" Try Again Later";
    }


    public String generateNewId() throws SQLException, ClassNotFoundException {

        String sql = "select requestNo from maintainrequest order by requestNo desc limit 1";
        ResultSet result = SQLUtil.execute(sql);

        if(result.next()){

            String lastId = result.getString("requestNo");
            String subStr = lastId.substring(4);
            System.out.println(subStr);
            int id = Integer.parseInt(subStr);
            id+=1;
            return String.format("REQ-%05d", id);

        }
        else{
            return "REQ-00001";
        }
    }


    public String add(MaintainRequest maintenanceRequest) throws SQLException, ClassNotFoundException {

        String sql = "insert into maintainrequest values(?,?,?,?,?,?,?,?,?)";
        boolean result = SQLUtil.execute(sql,maintenanceRequest.getRequestNo(),maintenanceRequest.getDescription(),maintenanceRequest.getEstimatedCost(),
                maintenanceRequest.getActualCost(),maintenanceRequest.getDate(),maintenanceRequest.getAssignedTechnician(),maintenanceRequest.getTenantId(),maintenanceRequest.getStatus(),1);

        return result ? "Successfully Added New Maintenance Request By Tenant ID: "+maintenanceRequest.getTenantId() : "Failed To Add New Maintenance Request, Try Again Later";
    }


    public String delete(MaintainRequest selectedRequest) throws SQLException, ClassNotFoundException {

        String sql = "UPDATE maintainrequest SET isActiveRequest = ? WHERE requestNo = ?";
        boolean result = SQLUtil.execute(sql,0,selectedRequest.getRequestNo());

        return result ? "Successfully Deleted The Maintenance Request No :"+selectedRequest.getRequestNo() : "Failed To Delete The Maintenance Request, Try Again Later";
    }


    @Override
    public boolean isExist(MaintainRequest entity) throws SQLException, ClassNotFoundException {

        String sql = "select * from maintainrequest where requestNo = ?";
        ResultSet rst = SQLUtil.execute(sql,entity.getRequestNo());
        return rst.next();
    }


    @Override
    public MaintainRequest search(String id) throws SQLException, ClassNotFoundException {

        String sql = "select * from maintainrequest where requestNo = ?";
        ResultSet rst = SQLUtil.execute(sql,id);

        MaintainRequest maintainRequest = new MaintainRequest();

        if(rst.next()){
            maintainRequest.setRequestNo(rst.getString(1));
            maintainRequest.setDescription(rst.getString(2));
            maintainRequest.setEstimatedCost(rst.getDouble(3));
            maintainRequest.setActualCost(rst.getString(4));
            maintainRequest.setDate(rst.getString(5));
            maintainRequest.setAssignedTechnician(rst.getString(6));
            maintainRequest.setTenantId(rst.getString(7));
            maintainRequest.setStatus(rst.getString(8));
            maintainRequest.setIsActiveRequest(rst.getInt(9));
        }
        return null;
    }


    public String update(MaintainRequest maintenanceRequest) throws SQLException, ClassNotFoundException {

        String sql = "UPDATE maintainrequest SET description = ?,estimatedCost = ?,actualCost = ?,assignedTechnician = ? WHERE requestNo = ?";
        boolean result = SQLUtil.execute(sql,maintenanceRequest.getDescription(),maintenanceRequest.getEstimatedCost(),maintenanceRequest.getActualCost(),maintenanceRequest.getAssignedTechnician(),maintenanceRequest.getRequestNo());

        return result ? "Successfully Updated The Request No: "+maintenanceRequest.getRequestNo() : "Failed To Update The Request No: "+maintenanceRequest.getRequestNo()+" Try Again Later";

    }


    public boolean setRequestNotComplete(MaintainRequest maintainRequest) throws SQLException, ClassNotFoundException {

        String sqlStatementOne = "select actualCost from maintainrequest where requestNo = ?";
        ResultSet resultSet = SQLUtil.execute(sqlStatementOne,maintainRequest.getRequestNo());

        if(resultSet.next()){

            double actualCost = Double.parseDouble(resultSet.getString("actualCost"));
            double deletedCost = Double.parseDouble(maintainRequest.getActualCost());

            if(actualCost>deletedCost){

                double newActualCost = actualCost-deletedCost;
                String newActualCostAsString = String.valueOf(newActualCost);

                String sql = "UPDATE maintainrequest SET actualCost = ?, status = ?,isActiveRequest = ? WHERE requestNo = ?";
                boolean result = SQLUtil.execute(sql,newActualCost,"In Progress",1,maintainRequest.getRequestNo());

                return result;
            }
            else{

                String sql = "UPDATE maintainrequest SET actualCost = ?, status = ?,isActiveRequest = ? WHERE requestNo = ?";
                boolean result = SQLUtil.execute(sql,"-","In Progress",1,maintainRequest.getRequestNo());

                return result;
            }
        }
        else{
            return false;
        }
    }


    public ObservableList<String> getRequestIdSuggestions(String input) throws SQLException, ClassNotFoundException {

        String sql = "SELECT requestNo FROM maintainrequest WHERE requestNo LIKE ? and isActiveRequest = ? and status = ?";
        ResultSet result = SQLUtil.execute(sql, input + "%",1,"In Progress");

        ObservableList<String> requestIds = FXCollections.observableArrayList();

        while (result.next()) {
            requestIds.add(result.getString("requestNo"));
        }

        return requestIds;
    }


    public boolean checkEnteredRequestIdValid(String maintenanceRequestNo) throws SQLException, ClassNotFoundException {

        String sql = "select * from maintainrequest where requestNo = ? and isActiveRequest = ? and status = ?";
        ResultSet result = SQLUtil.execute(sql,maintenanceRequestNo,1,"In Progress");

        if(result.next()){
            return true;
        }
        return false;
    }


    public boolean setActualCost(MaintainRequest maintainRequest) throws SQLException, ClassNotFoundException {

        String sql = "select actualCost from maintainrequest where requestNo = ?";
        ResultSet resultSet = SQLUtil.execute(sql,maintainRequest.getRequestNo());

        if(resultSet.next()){
            String actualCost = resultSet.getString("actualCost");

            if(actualCost.equals("-")){

                String sqlStatementOne = "UPDATE maintainrequest SET actualCost = ? WHERE requestNo = ?";
                return SQLUtil.execute(sqlStatementOne,maintainRequest.getActualCost(),maintainRequest.getRequestNo());
            }
            else {

                String sqlStatementTwo = "UPDATE maintainrequest SET actualCost = actualCost+ ? WHERE requestNo = ?";
                return SQLUtil.execute(sqlStatementTwo,maintainRequest.getActualCost(),maintainRequest.getRequestNo());
            }

        }
        else{
            return false;
        }
    }


    public ObservableList<MaintainRequest> getAllInProgressRequests() throws SQLException, ClassNotFoundException {

        String sql = "select * from maintainrequest where status = 'In Progress'";
        ResultSet result = SQLUtil.execute(sql);

        ObservableList<MaintainRequest> allRequests = FXCollections.observableArrayList();

        while(result.next()){

            String requestId = result.getString(1);
            String desc = result.getString(2);
            double estimatedCost = result.getDouble(3);
            String actualCost = result.getString(4);
            if(actualCost==null){
                actualCost = "-";
            }
            String date = result.getString(5);
            String technician = result.getString(6);
            String tenantId = result.getString(7);
            String status = result.getString(8);
            int isActive = result.getInt(9);

            MaintainRequest maintainRequest = new MaintainRequest(requestId,desc,estimatedCost,actualCost,date,technician,tenantId,status,isActive);
            allRequests.add(maintainRequest);

        }
        return allRequests;
    }


    public ObservableList<String> getInProcessMaintenanceRequest() throws SQLException, ClassNotFoundException {

        String sql = "select * from maintainrequest where status = 'In Progress'";
        ResultSet result = SQLUtil.execute(sql);

        ObservableList<String> notCompleteRequest = FXCollections.observableArrayList();

        while(result.next()){

            String id = result.getString("requestNo");
            String desc = result.getString("description");
            String tenantId = result.getString("tenantId");

            notCompleteRequest.add("Maintenance Request ID: "+id+",  Request Description: "+desc+",  tenant ID: "+tenantId+ "\n->Pending Maintenance Request from Tenant ID: "+tenantId+", Immediate Action Required.");
        }

        return notCompleteRequest;
    }
}





