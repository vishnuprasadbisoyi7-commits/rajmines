package tech.csm.mapper;


import org.springframework.jdbc.core.RowMapper;

import tech.csm.entity.AuditLog;

import java.sql.ResultSet;
import java.sql.SQLException;

public class AuditLogRowMapper implements RowMapper<AuditLog> {
    @Override
    public AuditLog mapRow(ResultSet rs, int rowNum) throws SQLException {
        AuditLog a = new AuditLog();
        a.setLogId(rs.getLong("LOG_ID"));
        a.setUserId(rs.getString("USER_ID"));
        a.setUserRole(rs.getString("USER_ROLE"));
        a.setActionType(rs.getString("ACTION_TYPE"));
        a.setEntityType(rs.getString("ENTITY_TYPE"));
        a.setEntityId(rs.getString("ENTITY_ID"));
        a.setOldValues(rs.getString("OLD_VALUES"));
        a.setNewValues(rs.getString("NEW_VALUES"));
        a.setIpAddress(rs.getString("IP_ADDRESS"));
        a.setSessionId(rs.getString("SESSION_ID"));
        a.setActionDateTime(rs.getTimestamp("ACTION_DATE_TIME").toLocalDateTime());
        a.setRemarks(rs.getString("REMARKS"));
        return a;
    }
}
