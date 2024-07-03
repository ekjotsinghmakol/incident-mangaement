package com.example.incidentmanagement.service;


import com.example.incidentmanagement.dto.IncidentDTO;
import com.example.incidentmanagement.entities.Incident;
import com.example.incidentmanagement.entities.User;
import com.example.incidentmanagement.entities.enums.IncidentStatus;
import com.example.incidentmanagement.repository.IncidentRepository;
import com.example.incidentmanagement.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
public class IncidentService {

    @Autowired
    private IncidentRepository incidentRepository;

    @Autowired
    private UserRepository userRepository;

    @Transactional
    public Incident createIncident(IncidentDTO incidentDTO, String username) {
        User reporter = userRepository.findByUsername(username);
        if (reporter == null) {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }

        Incident newIncident = new Incident();
        newIncident.setReporter(reporter);
        newIncident.setEnterpriseOrGovernment(incidentDTO.getEnterpriseOrGovernment());
        newIncident.setReporterName(reporter.getUsername());
        newIncident.setIncidentDetails(incidentDTO.getIncidentDetails());
        newIncident.setReportedDateTime(LocalDateTime.now());
        newIncident.setPriority(incidentDTO.getPriority());
        newIncident.setStatus(IncidentStatus.OPEN);

        newIncident.setIncidentId(generateIncidentId());
        return incidentRepository.save(newIncident);
    }

    @org.springframework.transaction.annotation.Transactional(readOnly = true)
    public Incident getIncidentById(Long incidentId) {
        return incidentRepository.findById(incidentId)
                .orElseThrow(() -> new EntityNotFoundException("Incident not found with id: " + incidentId));
    }



    @Transactional
    public Incident updateIncident(Long incidentId, IncidentDTO incidentDTO) {
        Incident incident = getIncidentById(incidentId);
        if (incident.getStatus() == IncidentStatus.CLOSED) {
            throw new IllegalStateException("Cannot update a closed incident");
        }

        incident.setEnterpriseOrGovernment(incidentDTO.getEnterpriseOrGovernment());
        incident.setIncidentDetails(incidentDTO.getIncidentDetails());
        incident.setPriority(incidentDTO.getPriority());

        return incidentRepository.save(incident);
    }

    public void delete(Incident incident) {
        incidentRepository.delete(incident);
    }

    @org.springframework.transaction.annotation.Transactional(readOnly = true)
    public List<Incident> getIncidentsByUserId(Long userId) {
        return incidentRepository.findByReporterUserId(userId);
    }

private Long generateIncidentId() {
    Random random = new Random();
    long randomNumber = 10000 + random.nextInt(90000);
    int currentYear = LocalDateTime.now().getYear();
    return Long.parseLong("1" + String.valueOf(currentYear) + String.valueOf(randomNumber));
}


}
