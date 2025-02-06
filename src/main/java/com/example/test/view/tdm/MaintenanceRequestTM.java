package com.example.test.view.tdm;

import com.example.test.dto.MaintenanceRequestDTO;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class MaintenanceRequestTM {

    private String maintenanceRequestNo;
    private String description;
    private double estimatedCost;
    private String actualCost;
    private String date;
    private String assignedTechnician;
    private String tenantId;
    private String status;

    public MaintenanceRequestTM toTM(MaintenanceRequestDTO maintenanceRequestDTO){
        this.maintenanceRequestNo = maintenanceRequestDTO.getMaintenanceRequestNo();
        this.description = maintenanceRequestDTO.getDescription();
        this.estimatedCost = maintenanceRequestDTO.getEstimatedCost();
        this.actualCost = maintenanceRequestDTO.getActualCost();
        this.date = maintenanceRequestDTO.getDate();
        this.assignedTechnician = maintenanceRequestDTO.getAssignedTechnician();
        this.tenantId = maintenanceRequestDTO.getTenantId();
        this.status = maintenanceRequestDTO.getStatus();
        return this;

    }

}
