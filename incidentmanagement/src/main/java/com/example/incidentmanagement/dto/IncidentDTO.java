package com.example.incidentmanagement.dto;


import com.example.incidentmanagement.entities.enums.IncidentStatus;
import com.example.incidentmanagement.entities.enums.Priority;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class IncidentDTO {
    private Long id;
    private String enterpriseOrGovernment;
    private String reporterName;
    private String incidentDetails;
    private LocalDateTime reportedDateTime;
    private Priority priority;
    private IncidentStatus status;
}
