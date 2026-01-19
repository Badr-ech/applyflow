-- Create notifications table
-- Stores notification messages generated from application events

CREATE TABLE notifications (
    id BIGSERIAL PRIMARY KEY,
    application_id BIGINT NOT NULL,
    message VARCHAR(500) NOT NULL,
    type VARCHAR(50) NOT NULL,
    read BOOLEAN NOT NULL DEFAULT FALSE,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    
    CONSTRAINT chk_notification_type CHECK (type IN ('STATUS_CHANGE', 'REMINDER', 'SYSTEM'))
);

-- Index for fetching unread notifications
CREATE INDEX idx_notifications_read ON notifications(read, created_at DESC);

-- Index for fetching notifications by application
CREATE INDEX idx_notifications_application_id ON notifications(application_id);

COMMENT ON TABLE notifications IS 'Stores notifications generated from system events';
COMMENT ON COLUMN notifications.type IS 'Type of notification: status change, reminder, or system message';
COMMENT ON COLUMN notifications.read IS 'Whether the notification has been marked as read';
