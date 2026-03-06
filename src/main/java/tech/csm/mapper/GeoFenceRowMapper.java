package tech.csm.mapper;


import org.springframework.jdbc.core.RowMapper;

import tech.csm.entity.GeoFence;

import java.sql.ResultSet;
import java.sql.SQLException;

public class GeoFenceRowMapper implements RowMapper<GeoFence> {
    @Override
    public GeoFence mapRow(ResultSet rs, int rowNum) throws SQLException {
        GeoFence g = new GeoFence();
        g.setGeofenceId(rs.getLong("GEOFENCE_ID"));
        g.setGeofenceName(rs.getString("GEOFENCE_NAME"));
        g.setGeofenceType(rs.getString("GEOFENCE_TYPE"));
        g.setLatitude(rs.getBigDecimal("LATITUDE"));
        g.setLongitude(rs.getBigDecimal("LONGITUDE"));
        g.setRadiusMeters(rs.getBigDecimal("RADIUS_METERS"));
        g.setToleranceRadiusMeters(rs.getBigDecimal("TOLERANCE_RADIUS_METERS"));
        g.setAddress(rs.getString("ADDRESS"));
        g.setStatus(rs.getString("STATUS"));
        g.setCreatedDate(rs.getTimestamp("CREATED_DATE").toLocalDateTime());
        g.setCreatedBy(rs.getString("CREATED_BY"));
        g.setUpdatedDate(rs.getTimestamp("UPDATED_DATE") != null ? rs.getTimestamp("UPDATED_DATE").toLocalDateTime() : null);
        g.setUpdatedBy(rs.getString("UPDATED_BY"));
        g.setDescription(rs.getString("DESCRIPTION"));
        return g;
    }
}
