package tech.csm.mapper;


import org.springframework.jdbc.core.RowMapper;

import tech.csm.entity.SystemUser;

import java.sql.ResultSet;
import java.sql.SQLException;

public class SystemUserRowMapper implements RowMapper<SystemUser> {
    @Override
    public SystemUser mapRow(ResultSet rs, int rowNum) throws SQLException {
        SystemUser u = new SystemUser();
        u.setUserId(rs.getString("USER_ID"));
        u.setUsername(rs.getString("USERNAME"));
        u.setEmail(rs.getString("EMAIL"));
        u.setPasswordHash(rs.getString("PASSWORD_HASH"));
        u.setUserRole(rs.getString("USER_ROLE"));
        u.setVendorId(rs.getString("VENDOR_ID"));
        u.setStatus(rs.getString("STATUS"));
        u.setLastLoginDate(rs.getTimestamp("LAST_LOGIN_DATE") != null ? rs.getTimestamp("LAST_LOGIN_DATE").toLocalDateTime() : null);
        u.setCreatedDate(rs.getTimestamp("CREATED_DATE").toLocalDateTime());
        u.setCreatedBy(rs.getString("CREATED_BY"));
        u.setUpdatedDate(rs.getTimestamp("UPDATED_DATE") != null ? rs.getTimestamp("UPDATED_DATE").toLocalDateTime() : null);
        return u;
    }
}
