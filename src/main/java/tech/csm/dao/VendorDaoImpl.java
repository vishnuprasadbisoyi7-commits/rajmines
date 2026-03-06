package tech.csm.dao;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;

import jakarta.annotation.PostConstruct;

@Repository
public class VendorDaoImpl implements VendorDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private SimpleJdbcCall saveOtpSp;
    private SimpleJdbcCall verifyOtpSp;
    private SimpleJdbcCall registerSp;
    private SimpleJdbcCall prefillSp;
    private SimpleJdbcCall getVendorDataSp;

    @PostConstruct
    public void init() {
        saveOtpSp = new SimpleJdbcCall(jdbcTemplate)
                .withProcedureName("SP_SAVE_VENDOR_OTP");

        verifyOtpSp = new SimpleJdbcCall(jdbcTemplate)
                .withProcedureName("SP_VERIFY_VENDOR_OTP");

        registerSp = new SimpleJdbcCall(jdbcTemplate)
                .withProcedureName("SP_REGISTER_VENDOR");
        
        prefillSp = new SimpleJdbcCall(jdbcTemplate)
                .withProcedureName("SP_GET_VENDOR_PREFILL");
        
        getVendorDataSp = new SimpleJdbcCall(jdbcTemplate)
        						.withProcedureName("SP_GET_VENDOR_DATA");
    }

    @Override
    public Map<String, Object> saveOtp(String vendorId, String otp, Timestamp expiry) {
        SqlParameterSource in = new MapSqlParameterSource()
                .addValue("p_vendor_id", vendorId)
                .addValue("p_otp", otp)
                .addValue("p_expiry", expiry);

        return saveOtpSp.execute(in);
    }

    @Override
    public Map<String, Object> verifyOtp(String vendorId, String otp) {
        SqlParameterSource in = new MapSqlParameterSource()
                .addValue("p_vendor_id", vendorId)
                .addValue("p_otp", otp);

        return verifyOtpSp.execute(in);
    }

    @Override
    public Map<String, Object> registerVendor(String vendorId, String vendorName, String email,
                                             String contactNo, String address, String pincode,
                                             String gst, String pan,String aadhar, String contactPersonName,
                                             String contactPersonNo, String createdBy, String passwordHash) {

        SqlParameterSource in = new MapSqlParameterSource()
                .addValue("p_vendor_id", vendorId)
                .addValue("p_vendor_name", vendorName)
                .addValue("p_email", email)
                .addValue("p_contact_no", contactNo)
                .addValue("p_address", address)
                .addValue("p_pincode", pincode)
                .addValue("p_gst", gst)
                .addValue("p_pan", pan)
                .addValue("p_aadhar_number", aadhar)
                .addValue("p_contact_person_name", contactPersonName)
                .addValue("p_contact_person_no", contactPersonNo)
                .addValue("p_created_by", createdBy)
                .addValue("p_password_hash", passwordHash);

        return registerSp.execute(in);
    }

    @Override
    public Map<String, Object> getVendorPrefillData(String vendorId) {

        SqlParameterSource in = new MapSqlParameterSource()
                .addValue("p_vendor_id", vendorId);

        Map<String, Object> result = prefillSp.execute(in);

        // MySQL returns result set as #result-set-1
        if (result.containsKey("#result-set-1")) {
            return (Map<String, Object>) ((List) result.get("#result-set-1")).get(0);
        }

        return Map.of("status", "FAIL", "message", "Vendor not found");
    }

	@Override
	public Map<String, Object> getVendorData() {
		// TODO Auto-generated method stub
		 Map<String, Object> result = getVendorDataSp.execute();

	        // MySQL returns result set as #result-set-1
	        if (result.containsKey("#result-set-1")) {
	            return Map.of("status", "SUCCESS", "data", result.get("#result-set-1"));
	        }
	        return Map.of("status", "FAIL", "message", "Vendor not found");
	}
}
