-- Create application_events table for audit trail
-- Tracks every status transition with timestamp

CREATE TABLE application_events (
    id BIGSERIAL PRIMARY KEY,
    application_id BIGINT NOT NULL,
    previous_status VARCHAR(50) NOT NULL,
    new_status VARCHAR(50) NOT NULL,
    timestamp TIMESTAMP NOT NULL,
    comment VARCHAR(500),
    
    CONSTRAINT fk_event_application FOREIGN KEY (application_id) 
        REFERENCES job_applications(id) ON DELETE CASCADE,
    CONSTRAINT chk_previous_status CHECK (previous_status IN ('APPLIED', 'INTERVIEW', 'OFFER', 'HIRED', 'REJECTED')),
    CONSTRAINT chk_new_status CHECK (new_status IN ('APPLIED', 'INTERVIEW', 'OFFER', 'HIRED', 'REJECTED'))
);

-- Index for fetching timeline for a specific application
CREATE INDEX idx_events_application_id ON application_events(application_id);

-- Composite index for timeline queries with ordering
CREATE INDEX idx_events_app_timestamp ON application_events(application_id, timestamp DESC);

COMMENT ON TABLE application_events IS 'Immutable audit log of all status transitions';
COMMENT ON COLUMN application_events.timestamp IS 'When the status transition occurred';
