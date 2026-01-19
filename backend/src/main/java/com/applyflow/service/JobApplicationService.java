package com.applyflow.service;

import com.applyflow.domain.ApplicationEvent;
import com.applyflow.domain.ApplicationStatus;
import com.applyflow.domain.JobApplication;
import com.applyflow.event.ApplicationStatusChangedEvent;
import com.applyflow.repository.ApplicationEventRepository;
import com.applyflow.repository.JobApplicationRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class JobApplicationService {

    private final JobApplicationRepository applicationRepository;
    private final ApplicationEventRepository eventRepository;
    private final ApplicationEventPublisher eventPublisher;

    @Transactional(readOnly = true)
    public List<JobApplication> getAllApplications() {
        return applicationRepository.findAllOrderByAppliedDateDesc();
    }

    @Transactional(readOnly = true)
    public JobApplication getApplicationById(Long id) {
        return applicationRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Application not found with id: " + id));
    }

    @Transactional
    public JobApplication createApplication(JobApplication application) {
        // New applications always start in APPLIED status
        application.setCurrentStatus(ApplicationStatus.APPLIED);
        application.setAppliedDate(LocalDateTime.now());
        
        JobApplication saved = applicationRepository.save(application);
        log.info("Created new application for {} at {}", saved.getPosition(), saved.getCompany());
        
        return saved;
    }

    /**
     * Transitions an application to a new status.
     * This method validates the transition, creates an audit event,
     * and publishes a domain event that triggers listeners.
     */
    @Transactional
    public JobApplication transitionStatus(Long applicationId, ApplicationStatus newStatus, String comment) {
        JobApplication application = getApplicationById(applicationId);
        ApplicationStatus previousStatus = application.getCurrentStatus();

        // Validate transition is allowed
        application.transitionTo(newStatus);
        
        // Create audit event record
        ApplicationEvent event = new ApplicationEvent(
            application,
            previousStatus,
            newStatus,
            LocalDateTime.now()
        );
        event.setComment(comment);
        application.addEvent(event);
        
        // Save changes
        JobApplication updated = applicationRepository.save(application);
        
        // Publish domain event after successful save
        // The @TransactionalEventListener will only execute after commit
        eventPublisher.publishEvent(new ApplicationStatusChangedEvent(
            this,
            updated.getId(),
            updated.getCompany(),
            updated.getPosition(),
            previousStatus,
            newStatus
        ));
        
        log.info("Transitioned application {} from {} to {}", 
            applicationId, previousStatus, newStatus);
        
        return updated;
    }

    @Transactional(readOnly = true)
    public List<ApplicationEvent> getApplicationTimeline(Long applicationId) {
        // Verify application exists
        getApplicationById(applicationId);
        return eventRepository.findByApplicationIdOrderByTimestampDesc(applicationId);
    }

    @Transactional(readOnly = true)
    public List<JobApplication> getApplicationsByStatus(ApplicationStatus status) {
        return applicationRepository.findByCurrentStatus(status);
    }

    @Transactional
    public void deleteApplication(Long id) {
        if (!applicationRepository.existsById(id)) {
            throw new EntityNotFoundException("Application not found with id: " + id);
        }
        applicationRepository.deleteById(id);
        log.info("Deleted application {}", id);
    }
}
