package tech.csm.mapper;


import org.springframework.jdbc.core.RowMapper;

import tech.csm.entity.Notification;

import java.sql.ResultSet;
import java.sql.SQLException;

public class NotificationRowMapper implements RowMapper<Notification> {
    @Override
    public Notification mapRow(ResultSet rs, int rowNum) throws SQLException {
        Notification n = new Notification();
        n.setNotificationId(rs.getLong("NOTIFICATION_ID"));
        n.setNotificationType(rs.getString("NOTIFICATION_TYPE"));
        n.setTitle(rs.getString("TITLE"));
        n.setMessage(rs.getString("MESSAGE"));
        n.setPriority(rs.getString("PRIORITY"));
        n.setRecipientType(rs.getString("RECIPIENT_TYPE"));
        n.setRecipientId(rs.getString("RECIPIENT_ID"));
        n.setRelatedEntityType(rs.getString("RELATED_ENTITY_TYPE"));
        n.setRelatedEntityId(rs.getObject("RELATED_ENTITY_ID") != null ? rs.getLong("RELATED_ENTITY_ID") : null);
        n.setStatus(rs.getString("STATUS"));
        n.setEmailSent(rs.getString("EMAIL_SENT"));
        n.setInAppSent(rs.getString("IN_APP_SENT"));
        n.setCreatedDate(rs.getTimestamp("CREATED_DATE").toLocalDateTime());
        n.setReadDate(rs.getTimestamp("READ_DATE") != null ? rs.getTimestamp("READ_DATE").toLocalDateTime() : null);
        n.setReadBy(rs.getString("READ_BY"));
        return n;
    }
}
