package com.example.test.entity;

import com.example.test.dto.MaintenanceRequestDTO;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class MaintainRequest {

    private String requestNo ;
    private String description;
    private double estimatedCost;
    private String actualCost;
    private String date;
    private String assignedTechnician;
    private String tenantId;
    private String status;
    private int isActiveRequest;

    public MaintainRequest toEntity(MaintenanceRequestDTO maintenanceRequestDto){
        this.requestNo = maintenanceRequestDto.getMaintenanceRequestNo();
        this.description = maintenanceRequestDto.getDescription();
        this.estimatedCost = maintenanceRequestDto.getEstimatedCost();
        this.actualCost = maintenanceRequestDto.getActualCost();
        this.date = maintenanceRequestDto.getDate();
        this.assignedTechnician = maintenanceRequestDto.getAssignedTechnician();
        this.tenantId = maintenanceRequestDto.getTenantId();
        this.status = maintenanceRequestDto.getStatus();
        return this;

    }
}
