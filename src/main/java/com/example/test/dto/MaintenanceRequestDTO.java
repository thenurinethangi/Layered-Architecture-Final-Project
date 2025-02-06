package com.example.test.dto;

import com.example.test.entity.MaintainRequest;
import com.example.test.view.tdm.MaintenanceRequestTM;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class MaintenanceRequestDTO {

    private String maintenanceRequestNo;
    private String description;
    private double estimatedCost;
    private String actualCost;
    private String date;
    private String assignedTechnician;
    private String tenantId;
    private String status;

    public MaintenanceRequestDTO toDTO(MaintainRequest maintainRequest){
        this.maintenanceRequestNo = maintainRequest.getRequestNo();
        this.description = maintainRequest.getDescription();
        this.estimatedCost = maintainRequest.getEstimatedCost();
        this.actualCost = maintainRequest.getActualCost();
        this.date = maintainRequest.getDate();
        this.assignedTechnician = maintainRequest.getAssignedTechnician();
        this.tenantId = maintainRequest.getTenantId();
        this.status = maintainRequest.getStatus();
        return this;

    }

    public MaintenanceRequestDTO toDTO(MaintenanceRequestTM maintenanceRequestTM){
        this.maintenanceRequestNo = maintenanceRequestTM.getMaintenanceRequestNo();
        this.description = maintenanceRequestTM.getDescription();
        this.estimatedCost = maintenanceRequestTM.getEstimatedCost();
        this.actualCost = maintenanceRequestTM.getActualCost();
        this.date = maintenanceRequestTM.getDate();
        this.assignedTechnician = maintenanceRequestTM.getAssignedTechnician();
        this.tenantId = maintenanceRequestTM.getTenantId();
        this.status = maintenanceRequestTM.getStatus();
        return this;

    }
}
