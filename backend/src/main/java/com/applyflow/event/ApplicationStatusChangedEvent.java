package com.applyflow.event;

import com.applyflow.domain.ApplicationStatus;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

/**
 * Domain event published when a job application changes status.
 * This event is published after the transaction commits successfully,
 * ensuring data consistency before listeners react to the change.
 */
@Getter
public class ApplicationStatusChangedEvent extends ApplicationEvent {

    private final Long applicationId;
    private final String company;
    private final String position;
    private final ApplicationStatus previousStatus;
    private final ApplicationStatus newStatus;

    public ApplicationStatusChangedEvent(Object source, Long applicationId, String company,
                                        String position, ApplicationStatus previousStatus,
                                        ApplicationStatus newStatus) {
        super(source);
        this.applicationId = applicationId;
        this.company = company;
        this.position = position;
        this.previousStatus = previousStatus;
        this.newStatus = newStatus;
    }
}
