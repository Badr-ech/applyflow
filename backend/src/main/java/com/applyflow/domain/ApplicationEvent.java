package com.applyflow.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "application_events")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApplicationEvent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "application_id", nullable = false)
    private JobApplication application;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ApplicationStatus previousStatus;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ApplicationStatus newStatus;

    @NotNull
    @Column(nullable = false)
    private LocalDateTime timestamp;

    @Column(length = 500)
    private String comment;

    public ApplicationEvent(JobApplication application, ApplicationStatus previousStatus,
                           ApplicationStatus newStatus, LocalDateTime timestamp) {
        this.application = application;
        this.previousStatus = previousStatus;
        this.newStatus = newStatus;
        this.timestamp = timestamp;
    }
}
