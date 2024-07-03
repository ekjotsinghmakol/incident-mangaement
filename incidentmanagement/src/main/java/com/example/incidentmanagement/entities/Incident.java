package com.example.incidentmanagement.entities;

import com.example.incidentmanagement.entities.enums.IncidentStatus;
import com.example.incidentmanagement.entities.enums.Priority;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Data
@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "incidents")
public class Incident {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long incidentId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reporter_id")
    private User reporter;

    @Column(nullable = false)
    private String enterpriseOrGovernment;

    @Column(nullable = false)
    private String reporterName;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String incidentDetails;

    @Column(nullable = false)
    private LocalDateTime reportedDateTime;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Priority priority;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private IncidentStatus status;
}
