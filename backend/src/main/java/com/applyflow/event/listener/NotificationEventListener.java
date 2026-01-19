package com.applyflow.event.listener;

import com.applyflow.domain.Notification;
import com.applyflow.event.ApplicationStatusChangedEvent;
import com.applyflow.repository.NotificationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

/**
 * Listens to application status changes and creates notifications.
 * Runs after the transaction commits to ensure data consistency.
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class NotificationEventListener {

    private final NotificationRepository notificationRepository;

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void handleStatusChange(ApplicationStatusChangedEvent event) {
        try {
            String message = buildNotificationMessage(event);
            
            Notification notification = new Notification(
                event.getApplicationId(),
                message,
                Notification.NotificationType.STATUS_CHANGE
            );
            
            notificationRepository.save(notification);
            
            log.info("Notification created for application {} - Status: {} → {}",
                event.getApplicationId(), event.getPreviousStatus(), event.getNewStatus());
                
        } catch (Exception e) {
            log.error("Failed to create notification for application {}", 
                event.getApplicationId(), e);
        }
    }

    private String buildNotificationMessage(ApplicationStatusChangedEvent event) {
        String statusDisplay = event.getNewStatus().getDisplayName();
        
        return switch (event.getNewStatus()) {
            case INTERVIEW -> String.format("Interview scheduled for %s position at %s",
                event.getPosition(), event.getCompany());
            case OFFER -> String.format("Offer received from %s for %s position!",
                event.getCompany(), event.getPosition());
            case HIRED -> String.format("Congratulations! Hired at %s as %s",
                event.getCompany(), event.getPosition());
            case REJECTED -> String.format("Application for %s at %s was not successful",
                event.getPosition(), event.getCompany());
            default -> String.format("Application status updated to %s for %s at %s",
                statusDisplay, event.getPosition(), event.getCompany());
        };
    }
}
