package com.applyflow.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "job_applications")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class JobApplication {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Company name is required")
    @Column(nullable = false)
    private String company;

    @NotBlank(message = "Position is required")
    @Column(nullable = false)
    private String position;

    @NotNull(message = "Status is required")
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ApplicationStatus currentStatus;

    @NotNull(message = "Application date is required")
    @Column(nullable = false)
    private LocalDateTime appliedDate;

    @Column(length = 1000)
    private String notes;

    @Column
    private String location;

    @Column
    private Integer salaryExpectation;

    @Column
    private String jobUrl;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(nullable = false)
    private LocalDateTime updatedAt;

    // Bidirectional relationship - helps with fetching timeline
    @OneToMany(mappedBy = "application", cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderBy("timestamp DESC")
    private List<ApplicationEvent> events = new ArrayList<>();

    /**
     * Business method to validate state transitions before applying them.
     * Throws exception if transition is not allowed.
     */
    public void transitionTo(ApplicationStatus newStatus) {
        if (!currentStatus.canTransitionTo(newStatus)) {
            throw new IllegalStateException(
                String.format("Cannot transition from %s to %s for application at %s",
                    currentStatus, newStatus, company)
            );
        }
        this.currentStatus = newStatus;
    }

    /**
     * Helper to add event to the relationship while maintaining both sides.
     */
    public void addEvent(ApplicationEvent event) {
        events.add(event);
        event.setApplication(this);
    }
}
