package repository;

import dto.NotificationDTO;

import java.util.List;

public interface NotifyRepository {
    void addNotification(int sellerId, String message);

    List<NotificationDTO> getUnreadNotifications(int sellerId);

    void markAsRead(int notificationId);
}
