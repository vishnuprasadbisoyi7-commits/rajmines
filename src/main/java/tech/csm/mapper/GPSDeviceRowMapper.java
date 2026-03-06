package tech.csm.mapper;


import org.springframework.jdbc.core.RowMapper;

import tech.csm.entity.GPSDevice;

import java.sql.ResultSet;
import java.sql.SQLException;

public class GPSDeviceRowMapper implements RowMapper<GPSDevice> {
    @Override
    public GPSDevice mapRow(ResultSet rs, int rowNum) throws SQLException {
        GPSDevice d = new GPSDevice();
        d.setDeviceId(rs.getLong("DEVICE_ID"));
        d.setDeviceName(rs.getString("DEVICE_NAME"));
        d.setImeiSerial(rs.getString("IMEI_SERIAL"));
        d.setModel(rs.getString("MODEL"));
        d.setFirmwareVersion(rs.getString("FIRMWARE_VERSION"));
        d.setSimIccid(rs.getString("SIM_ICCID"));
        d.setSimMsisdn(rs.getString("SIM_MSISDN"));
        d.setHealthStatus(rs.getString("HEALTH_STATUS"));
        d.setDeviceStatus(rs.getString("DEVICE_STATUS"));
        d.setVendorId(rs.getString("VENDOR_ID"));
        d.setRegistrationDate(rs.getTimestamp("REGISTRATION_DATE").toLocalDateTime());
        d.setRegisteredBy(rs.getString("REGISTERED_BY"));
        d.setCreatedDate(rs.getTimestamp("CREATED_DATE").toLocalDateTime());
        d.setUpdatedDate(rs.getTimestamp("UPDATED_DATE") != null ? rs.getTimestamp("UPDATED_DATE").toLocalDateTime() : null);
        d.setRemarks(rs.getString("REMARKS"));
        return d;
    }
}
