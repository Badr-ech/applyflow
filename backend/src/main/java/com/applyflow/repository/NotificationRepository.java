package com.applyflow.repository;

import com.applyflow.domain.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {

    List<Notification> findByReadFalseOrderByCreatedAtDesc();

    List<Notification> findByApplicationId(Long applicationId);

    List<Notification> findAllByOrderByCreatedAtDesc();

    long countByReadFalse();
}
