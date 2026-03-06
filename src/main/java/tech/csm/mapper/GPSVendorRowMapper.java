package tech.csm.mapper;




import org.springframework.jdbc.core.RowMapper;

import tech.csm.entity.GPSVendor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

public class GPSVendorRowMapper implements RowMapper<GPSVendor> {
    @Override
    public GPSVendor mapRow(ResultSet rs, int rowNum) throws SQLException {
        GPSVendor v = new GPSVendor();
        v.setVendorId(rs.getString("VENDOR_ID"));
        v.setVendorName(rs.getString("VENDOR_NAME"));
        v.setEmail(rs.getString("EMAIL"));
        v.setContactNo(rs.getString("CONTACT_NO"));
        v.setAddress(rs.getString("ADDRESS"));
        v.setPincode(rs.getString("PINCODE"));
        v.setGstNumber(rs.getString("GST_NUMBER"));
        v.setPanNumber(rs.getString("PAN_NUMBER"));
        v.setContactPersonName(rs.getString("CONTACT_PERSON_NAME"));
        v.setContactPersonNo(rs.getString("CONTACT_PERSON_NO"));
        v.setStatus(rs.getString("STATUS"));
        v.setOtp(rs.getString("OTP"));
        v.setOtpExpiry(rs.getTimestamp("OTP_EXPIRY") != null ? rs.getTimestamp("OTP_EXPIRY").toLocalDateTime() : null);
        v.setOtpVerified(rs.getString("OTP_VERIFIED"));
        v.setCreatedDate(rs.getTimestamp("CREATED_DATE").toLocalDateTime());
        v.setCreatedBy(rs.getString("CREATED_BY"));
        v.setUpdatedDate(rs.getTimestamp("UPDATED_DATE") != null ? rs.getTimestamp("UPDATED_DATE").toLocalDateTime() : null);
        v.setUpdatedBy(rs.getString("UPDATED_BY"));
        v.setRemarks(rs.getString("REMARKS"));
        return v;
    }
}
