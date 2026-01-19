package com.applyflow.service;

import com.applyflow.domain.Notification;
import com.applyflow.repository.NotificationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NotificationService {

    private final NotificationRepository notificationRepository;

    @Transactional(readOnly = true)
    public List<Notification> getAllNotifications() {
        return notificationRepository.findAllByOrderByCreatedAtDesc();
    }

    @Transactional(readOnly = true)
    public List<Notification> getUnreadNotifications() {
        return notificationRepository.findByReadFalseOrderByCreatedAtDesc();
    }

    @Transactional(readOnly = true)
    public long getUnreadCount() {
        return notificationRepository.countByReadFalse();
    }

    @Transactional
    public Notification markAsRead(Long id) {
        Notification notification = notificationRepository.findById(id)
            .orElseThrow(() -> new jakarta.persistence.EntityNotFoundException(
                "Notification not found with id: " + id));
        
        notification.setRead(true);
        return notificationRepository.save(notification);
    }

    @Transactional
    public void markAllAsRead() {
        List<Notification> unread = notificationRepository.findByReadFalseOrderByCreatedAtDesc();
        unread.forEach(n -> n.setRead(true));
        notificationRepository.saveAll(unread);
    }
}
