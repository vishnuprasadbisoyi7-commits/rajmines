package tech.csm.mapper;


import org.springframework.jdbc.core.RowMapper;

import tech.csm.entity.AlertThreshold;

import java.sql.ResultSet;
import java.sql.SQLException;

public class AlertThresholdRowMapper implements RowMapper<AlertThreshold> {
    @Override
    public AlertThreshold mapRow(ResultSet rs, int rowNum) throws SQLException {
        AlertThreshold a = new AlertThreshold();
        a.setThresholdId(rs.getLong("THRESHOLD_ID"));
        a.setAlertType(rs.getString("ALERT_TYPE"));
        a.setThresholdValue(rs.getBigDecimal("THRESHOLD_VALUE"));
        a.setUnit(rs.getString("UNIT"));
        a.setDescription(rs.getString("DESCRIPTION"));
        a.setIsActive(rs.getString("IS_ACTIVE"));
        a.setCreatedDate(rs.getTimestamp("CREATED_DATE").toLocalDateTime());
        a.setUpdatedDate(rs.getTimestamp("UPDATED_DATE") != null ? rs.getTimestamp("UPDATED_DATE").toLocalDateTime() : null);
        return a;
    }
}

