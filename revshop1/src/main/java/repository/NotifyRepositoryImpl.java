package repository;

import dto.NotificationDTO;
import enumeration.ConnectionEnum;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class NotifyRepositoryImpl implements  NotifyRepository{

    @Override
    public void addNotification(int sellerId, String message) {

        String sql = """
            INSERT INTO notifications_seller (seller_id, notification_message)
            VALUES (?, ?)
        """;

        try (Connection con = DriverManager.getConnection(
                ConnectionEnum.URL.getValue(),
                ConnectionEnum.USERNAME.getValue(),
                ConnectionEnum.PASSWORD.getValue());
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, sellerId);
            ps.setString(2, message);
            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<NotificationDTO> getUnreadNotifications(int sellerId) {

        List<NotificationDTO> list = new ArrayList<>();

        String sql = """
        SELECT notification_id, notification_message, created_at
        FROM notifications_seller
        WHERE seller_id = ? AND is_read = 'N'
        ORDER BY created_at DESC
    """;

        try (Connection con = DriverManager.getConnection(
                ConnectionEnum.URL.getValue(),
                ConnectionEnum.USERNAME.getValue(),
                ConnectionEnum.PASSWORD.getValue());
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, sellerId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                NotificationDTO dto = new NotificationDTO();
                dto.setNotificationId(rs.getInt("notification_id"));
                dto.setMessage(rs.getString("notification_message"));
                dto.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
                list.add(dto);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public void markAsRead(int notificationId) {

        String sql =
                "UPDATE notifications_seller SET is_read = 'Y' WHERE notification_id = ?";

        try (Connection con = DriverManager.getConnection(
                ConnectionEnum.URL.getValue(),
                ConnectionEnum.USERNAME.getValue(),
                ConnectionEnum.PASSWORD.getValue());
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, notificationId);
            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


}
