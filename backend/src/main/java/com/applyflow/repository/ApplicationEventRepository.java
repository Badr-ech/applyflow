package com.applyflow.repository;

import com.applyflow.domain.ApplicationEvent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ApplicationEventRepository extends JpaRepository<ApplicationEvent, Long> {

    List<ApplicationEvent> findByApplicationIdOrderByTimestampDesc(Long applicationId);

    List<ApplicationEvent> findByApplicationId(Long applicationId);
}
