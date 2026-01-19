package com.applyflow.api;

import com.applyflow.api.dto.CreateApplicationRequest;
import com.applyflow.api.dto.TransitionRequest;
import com.applyflow.domain.ApplicationEvent;
import com.applyflow.domain.ApplicationStatus;
import com.applyflow.domain.JobApplication;
import com.applyflow.service.JobApplicationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/applications")
@RequiredArgsConstructor
@Tag(name = "Job Applications", description = "Manage job applications and their lifecycle")
public class JobApplicationController {

    private final JobApplicationService applicationService;

    @GetMapping
    @Operation(summary = "Get all job applications", 
               description = "Retrieves all job applications ordered by application date")
    public ResponseEntity<List<JobApplication>> getAllApplications() {
        return ResponseEntity.ok(applicationService.getAllApplications());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get application by ID")
    @ApiResponse(responseCode = "200", description = "Application found")
    @ApiResponse(responseCode = "404", description = "Application not found")
    public ResponseEntity<JobApplication> getApplicationById(@PathVariable Long id) {
        return ResponseEntity.ok(applicationService.getApplicationById(id));
    }

    @PostMapping
    @Operation(summary = "Create new job application", 
               description = "Creates a new application in APPLIED status")
    public ResponseEntity<JobApplication> createApplication(
            @Valid @RequestBody CreateApplicationRequest request) {
        
        JobApplication application = new JobApplication();
        application.setCompany(request.getCompany());
        application.setPosition(request.getPosition());
        application.setNotes(request.getNotes());
        application.setLocation(request.getLocation());
        application.setSalaryExpectation(request.getSalaryExpectation());
        application.setJobUrl(request.getJobUrl());
        
        JobApplication created = applicationService.createApplication(application);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PostMapping("/{id}/transition")
    @Operation(summary = "Transition application status", 
               description = "Changes application status and triggers domain events")
    @ApiResponse(responseCode = "200", description = "Status transitioned successfully")
    @ApiResponse(responseCode = "400", description = "Invalid transition")
    @ApiResponse(responseCode = "404", description = "Application not found")
    public ResponseEntity<JobApplication> transitionStatus(
            @PathVariable Long id,
            @Valid @RequestBody TransitionRequest request) {
        
        JobApplication updated = applicationService.transitionStatus(
            id, 
            request.getNewStatus(), 
            request.getComment()
        );
        return ResponseEntity.ok(updated);
    }

    @GetMapping("/{id}/timeline")
    @Operation(summary = "Get application timeline", 
               description = "Retrieves all status change events for an application")
    public ResponseEntity<List<ApplicationEvent>> getTimeline(@PathVariable Long id) {
        return ResponseEntity.ok(applicationService.getApplicationTimeline(id));
    }

    @GetMapping("/status/{status}")
    @Operation(summary = "Get applications by status")
    public ResponseEntity<List<JobApplication>> getApplicationsByStatus(
            @PathVariable ApplicationStatus status) {
        return ResponseEntity.ok(applicationService.getApplicationsByStatus(status));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete application")
    @ApiResponse(responseCode = "204", description = "Application deleted")
    @ApiResponse(responseCode = "404", description = "Application not found")
    public ResponseEntity<Void> deleteApplication(@PathVariable Long id) {
        applicationService.deleteApplication(id);
        return ResponseEntity.noContent().build();
    }
}
