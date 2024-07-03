package com.example.incidentmanagement.repository;

import com.example.incidentmanagement.entities.Incident;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IncidentRepository extends JpaRepository<Incident, Long> {
    List<Incident> findByReporterUserId(Long reporterId); // Updated method to fetch incidents by reporter's ID
    Optional<Incident> findByIncidentId(String incidentId);
    boolean existsByIncidentId(String incidentId);
}