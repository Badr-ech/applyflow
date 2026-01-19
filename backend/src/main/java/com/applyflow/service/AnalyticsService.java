package com.applyflow.service;

import com.applyflow.domain.ApplicationStatus;
import com.applyflow.event.listener.AnalyticsEventListener;
import com.applyflow.repository.JobApplicationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AnalyticsService {

    private final JobApplicationRepository applicationRepository;
    private final AnalyticsEventListener analyticsEventListener;

    @Transactional(readOnly = true)
    public Map<String, Object> getSummary() {
        Map<String, Object> summary = new HashMap<>();
        
        // Total applications count
        long total = applicationRepository.count();
        summary.put("totalApplications", total);
        
        // Count by status
        Map<String, Long> statusCounts = new HashMap<>();
        for (ApplicationStatus status : ApplicationStatus.values()) {
            long count = applicationRepository.countByStatus(status);
            statusCounts.put(status.name(), count);
        }
        summary.put("applicationsByStatus", statusCounts);
        
        // Transition metrics from event listener
        summary.put("totalTransitions", analyticsEventListener.getTotalTransitions());
        
        // Calculate success metrics
        long hired = applicationRepository.countByStatus(ApplicationStatus.HIRED);
        long rejected = applicationRepository.countByStatus(ApplicationStatus.REJECTED);
        
        double successRate = total > 0 ? (hired * 100.0 / total) : 0.0;
        double rejectionRate = total > 0 ? (rejected * 100.0 / total) : 0.0;
        
        summary.put("successRate", Math.round(successRate * 100.0) / 100.0);
        summary.put("rejectionRate", Math.round(rejectionRate * 100.0) / 100.0);
        
        return summary;
    }
}
