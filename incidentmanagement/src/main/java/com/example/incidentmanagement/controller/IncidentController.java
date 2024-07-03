package com.example.incidentmanagement.controller;


import com.example.incidentmanagement.dto.IncidentDTO;
import com.example.incidentmanagement.entities.Incident;
import com.example.incidentmanagement.service.IncidentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/incidents")
public class IncidentController {

    @Autowired
    private IncidentService incidentService;

    public ResponseEntity<Incident> createIncident( @RequestBody IncidentDTO incidentDTO,
                                                   @RequestHeader(name = "username") String username) {
        Incident createdIncident = incidentService.createIncident(incidentDTO, username);
        return new ResponseEntity<>(createdIncident, HttpStatus.CREATED);
    }

    @GetMapping("/{incidentId}")
    public ResponseEntity<Incident> getIncidentById(@PathVariable Long incidentId) {
        Incident incident = incidentService.getIncidentById(incidentId);
        return ResponseEntity.ok(incident);
    }

    @PutMapping("/{incidentId}")
    public ResponseEntity<Incident> updateIncident(@PathVariable Long incidentId,
                                                   @Validated @RequestBody IncidentDTO incidentDTO) {
        Incident updatedIncident = incidentService.updateIncident(incidentId, incidentDTO);
        return ResponseEntity.ok(updatedIncident);
    }
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Incident>> getIncidentsByUserId(@PathVariable Long userId) {
        List<Incident> incidents = incidentService.getIncidentsByUserId(userId);
        return ResponseEntity.ok(incidents);
    }

    public void deleteIncident(@PathVariable Long id) {
        Incident incident = incidentService.getIncidentById(id);
        incidentService.delete(incident);
    }
}
