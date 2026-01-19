package com.applyflow.domain;

public enum ApplicationStatus {
    APPLIED("Application Submitted"),
    INTERVIEW("Interview Scheduled"),
    OFFER("Offer Received"),
    HIRED("Hired"),
    REJECTED("Application Rejected");

    private final String displayName;

    ApplicationStatus(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }

    /**
     * Validates if a transition from current status to target status is allowed.
     * Business rules:
     * - Can move forward in the pipeline (APPLIED → INTERVIEW → OFFER → HIRED)
     * - Can reject from any status except HIRED
     * - Cannot reverse a transition once made
     */
    public boolean canTransitionTo(ApplicationStatus target) {
        if (this == target) {
            return false;
        }

        // Can't change status once hired
        if (this == HIRED) {
            return false;
        }

        // Can't unhire or unreject someone
        if (this == REJECTED) {
            return false;
        }

        // Can reject from any active status
        if (target == REJECTED) {
            return true;
        }

        // Forward transitions only
        return switch (this) {
            case APPLIED -> target == INTERVIEW || target == OFFER;
            case INTERVIEW -> target == OFFER || target == HIRED;
            case OFFER -> target == HIRED;
            default -> false;
        };
    }
}
