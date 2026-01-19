package com.applyflow.repository;

import com.applyflow.domain.ApplicationStatus;
import com.applyflow.domain.JobApplication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface JobApplicationRepository extends JpaRepository<JobApplication, Long> {

    List<JobApplication> findByCurrentStatus(ApplicationStatus status);

    List<JobApplication> findByCompanyContainingIgnoreCase(String company);

    @Query("SELECT ja FROM JobApplication ja WHERE ja.appliedDate BETWEEN :startDate AND :endDate")
    List<JobApplication> findByAppliedDateBetween(LocalDateTime startDate, LocalDateTime endDate);

    @Query("SELECT COUNT(ja) FROM JobApplication ja WHERE ja.currentStatus = :status")
    long countByStatus(ApplicationStatus status);

    @Query("SELECT ja FROM JobApplication ja ORDER BY ja.appliedDate DESC")
    List<JobApplication> findAllOrderByAppliedDateDesc();
}
