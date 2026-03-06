package tech.csm.dao;

import java.sql.Timestamp;
import java.util.Map;

public interface VendorDao {
    Map<String, Object> saveOtp(String vendorId, String otp, Timestamp expiry);
    Map<String, Object> verifyOtp(String vendorId, String otp);
    Map<String, Object> registerVendor(
            String vendorId,
            String vendorName,
            String email,
            String contactNo,
            String address,
            String pincode,
            String gst,
            String pan,
            String aadhar,
            String contactPersonName,
            String contactPersonNo,
            String createdBy,
            String passwordHash
    );
	Map<String, Object> getVendorPrefillData(String vendorId);
	Map<String, Object> getVendorData();
}
