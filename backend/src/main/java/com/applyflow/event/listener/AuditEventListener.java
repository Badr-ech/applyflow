package com.applyflow.event.listener;

import com.applyflow.event.ApplicationStatusChangedEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

/**
 * Logs all status changes for audit purposes.
 * In production, this could integrate with centralized logging,
 * compliance systems, or monitoring tools.
 */
@Component
@Slf4j
public class AuditEventListener {

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void handleStatusChange(ApplicationStatusChangedEvent event) {
        log.info("AUDIT: Application {} ({} at {}) transitioned from {} to {}",
            event.getApplicationId(),
            event.getPosition(),
            event.getCompany(),
            event.getPreviousStatus(),
            event.getNewStatus()
        );
    }
}
