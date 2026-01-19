-- Initial schema for ApplyFlow
-- Creates job_applications table with proper constraints and indexes

CREATE TABLE job_applications (
    id BIGSERIAL PRIMARY KEY,
    company VARCHAR(255) NOT NULL,
    position VARCHAR(255) NOT NULL,
    current_status VARCHAR(50) NOT NULL,
    applied_date TIMESTAMP NOT NULL,
    notes VARCHAR(1000),
    location VARCHAR(255),
    salary_expectation INTEGER,
    job_url VARCHAR(500),
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    
    CONSTRAINT chk_status CHECK (current_status IN ('APPLIED', 'INTERVIEW', 'OFFER', 'HIRED', 'REJECTED'))
);

-- Index for querying by status (common filter in UI)
CREATE INDEX idx_applications_status ON job_applications(current_status);

-- Index for date-based queries and sorting
CREATE INDEX idx_applications_applied_date ON job_applications(applied_date DESC);

-- Index for searching by company
CREATE INDEX idx_applications_company ON job_applications(company);

COMMENT ON TABLE job_applications IS 'Stores all job applications with their current status';
COMMENT ON COLUMN job_applications.current_status IS 'Current status in the application pipeline';
COMMENT ON COLUMN job_applications.applied_date IS 'Date when application was submitted';
