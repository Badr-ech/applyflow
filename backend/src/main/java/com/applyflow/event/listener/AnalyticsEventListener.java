package com.applyflow.event.listener;

import com.applyflow.event.ApplicationStatusChangedEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Tracks analytics metrics for job applications.
 * In a production system, this would update a time-series database
 * or metrics collection system. For now, we keep in-memory counters.
 */
@Component
@Slf4j
public class AnalyticsEventListener {

    private final Map<String, AtomicInteger> statusCounts = new ConcurrentHashMap<>();
    private final AtomicInteger totalTransitions = new AtomicInteger(0);

    public AnalyticsEventListener() {
        // Initialize counters for all statuses
        for (com.applyflow.domain.ApplicationStatus status : 
             com.applyflow.domain.ApplicationStatus.values()) {
            statusCounts.put(status.name(), new AtomicInteger(0));
        }
    }

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void handleStatusChange(ApplicationStatusChangedEvent event) {
        try {
            // Decrement previous status count
            statusCounts.get(event.getPreviousStatus().name()).decrementAndGet();
            
            // Increment new status count
            statusCounts.get(event.getNewStatus().name()).incrementAndGet();
            
            // Track total transitions
            totalTransitions.incrementAndGet();
            
            log.debug("Analytics updated - Total transitions: {}, Current counts: {}",
                totalTransitions.get(), getStatusCounts());
                
        } catch (Exception e) {
            log.error("Failed to update analytics for application {}", 
                event.getApplicationId(), e);
        }
    }

    public Map<String, Integer> getStatusCounts() {
        Map<String, Integer> counts = new ConcurrentHashMap<>();
        statusCounts.forEach((status, count) -> counts.put(status, count.get()));
        return counts;
    }

    public int getTotalTransitions() {
        return totalTransitions.get();
    }

    public void incrementStatusCount(String status) {
        statusCounts.computeIfAbsent(status, k -> new AtomicInteger(0)).incrementAndGet();
    }
}
