package tech.csm.mapper;


import org.springframework.jdbc.core.RowMapper;

import tech.csm.entity.SystemConfig;

import java.sql.ResultSet;
import java.sql.SQLException;

public class SystemConfigRowMapper implements RowMapper<SystemConfig> {
    @Override
    public SystemConfig mapRow(ResultSet rs, int rowNum) throws SQLException {
        SystemConfig c = new SystemConfig();
        c.setConfigId(rs.getLong("CONFIG_ID"));
        c.setConfigKey(rs.getString("CONFIG_KEY"));
        c.setConfigValue(rs.getString("CONFIG_VALUE"));
        c.setConfigDescription(rs.getString("CONFIG_DESCRIPTION"));
        c.setConfigType(rs.getString("CONFIG_TYPE"));
        c.setIsActive(rs.getString("IS_ACTIVE"));
        c.setCreatedDate(rs.getTimestamp("CREATED_DATE").toLocalDateTime());
        c.setUpdatedDate(rs.getTimestamp("UPDATED_DATE") != null ? rs.getTimestamp("UPDATED_DATE").toLocalDateTime() : null);
        return c;
    }
}
