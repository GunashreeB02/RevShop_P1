package dto;

import java.time.LocalDateTime;

public class NotificationDTO {

    private int notificationId;
    private int sellerId;
    private String message;
    private boolean read;
    private LocalDateTime createdAt;

    public NotificationDTO()
    {

    }

    public NotificationDTO(int notificationId, int sellerId, String message, boolean read, LocalDateTime createdAt) {
        this.notificationId = notificationId;
        this.sellerId = sellerId;
        this.message = message;
        this.read = read;
        this.createdAt = createdAt;
    }

    public int getNotificationId() {
        return notificationId;
    }

    public void setNotificationId(int notificationId) {
        this.notificationId = notificationId;
    }

    public int getSellerId() {
        return sellerId;
    }

    public void setSellerId(int sellerId) {
        this.sellerId = sellerId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isRead() {
        return read;
    }

    public void setRead(boolean read) {
        this.read = read;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
