package tech.csm.service;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import tech.csm.dao.VendorDao;
import tech.csm.util.JwtUtil;
import tools.jackson.databind.JsonNode;


@Service
public class VendorServiceImpl implements VendorService {

    @Autowired
    private VendorDao vendorDao;

    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @Autowired
    private JwtUtil jwtUtil;
    
    @Autowired
    private  VendorFileService vendorFileService;
    
    @Value("${upload.dir}")
    private String uploadDir;


    @Override
    public Map<String, Object> sendOtp(JsonNode body) {

        // use path(...).asText(default) to avoid JsonNode MissingNode coercion exceptions
        String vendorId = body.path("vendorId").asText("");
        String otp = String.valueOf(100000 + new Random().nextInt(900000));
        Timestamp expiry = Timestamp.valueOf(LocalDateTime.now().plusMinutes(5));

        return vendorDao.saveOtp(vendorId, otp, expiry);
    }

    @Override
    public Map<String, Object> verifyOtp(JsonNode body) {
        String vendorId = body.path("vendorId").asText("");
        String otp = body.path("otp").asText("");

        return vendorDao.verifyOtp(vendorId, otp);
    }

	/*
	 * @Override public Map<String, Object> registerVendor(JsonNode body) {
	 * 
	 * String defaultPassword = "Gps@" + (1000 + new Random().nextInt(9000)); String
	 * passwordHash = passwordEncoder.encode(defaultPassword);
	 * 
	 * Map<String, Object> result = vendorDao.registerVendor(
	 * body.get("vendorId").asText(), body.get("vendorName").asText(),
	 * body.get("email").asText(), body.get("contactNo").asText(),
	 * body.get("address").asText(), body.get("pincode").asText(),
	 * body.get("gstNumber").asText(), body.get("panNumber").asText(),
	 * body.get("contactPersonName").asText(), body.get("contactPersonNo").asText(),
	 * body.get("createdBy").asText(), passwordHash );
	 * 
	 * result.put("defaultPassword", defaultPassword); // show once return result; }
	 */
    
	/*
	 * @Override public Map<String, Object> registerVendor(JsonNode body) {
	 * 
	 * String defaultPassword = "Gps@" + (1000 + new Random().nextInt(9000)); String
	 * passwordHash = passwordEncoder.encode(defaultPassword);
	 * 
	 * Map<String, Object> result = vendorDao.registerVendor(
	 * body.get("vendorId").asText(), body.get("vendorName").asText(),
	 * body.get("email").asText(), body.get("contactNo").asText(),
	 * body.get("address").asText(), body.get("pincode").asText(),
	 * body.get("gstNumber").asText(), body.get("panNumber").asText(),
	 * body.get("aadharNumber").asText(), body.get("contactPersonName").asText(),
	 * body.get("contactPersonNo").asText(), body.get("createdBy").asText(),
	 * passwordHash );
	 * 
	 * result.put("defaultPassword", defaultPassword); // show once
	 * 
	 * // ✅ Generate JWT only if success if
	 * ("SUCCESS".equals(result.get("o_status"))) { String userId =
	 * body.get("email").asText(); // USER_ID = email String username =
	 * body.get("vendorName").asText(); String role = "GPS_VENDOR";
	 * 
	 * String token = jwtUtil.generateToken(userId, username, role);
	 * result.put("token", token); }
	 * 
	 * return result; }
	 */

    @Override
    public Map<String, Object> getVendorPrefillData(String vendorId) {

        try {
            return vendorDao.getVendorPrefillData(vendorId);
        } catch (EmptyResultDataAccessException e) {

            Map<String, Object> error = new HashMap<>();
            error.put("status", "FAIL");
            error.put("message", "Vendor not found for vendorId: " + vendorId);

            return error;
        }
    }

    @Override
    public Map<String, Object> registerVendor(
            JsonNode body,
            MultipartFile gstFile,
            MultipartFile panFile, 
            MultipartFile vendorDoc
    ) {

        String defaultPassword = "Gps@" + (1000 + new Random().nextInt(9000));
        String passwordHash = passwordEncoder.encode(defaultPassword);

        // defensively read input values with default empty string to avoid MissingNode -> coercion error
        String vendorId = body.path("vendorId").asText("");
        String vendorName = body.path("vendorName").asText("");
        String email = body.path("email").asText("");
        String contactNo = body.path("contactNo").asText("");
        String address = body.path("address").asText("");
        String pincode = body.path("pincode").asText("");
        String gstNumber = body.path("gstNumber").asText("");
        String panNumber = body.path("panNumber").asText("");
        String aadharNumber = body.path("aadharNumber").asText("");
        String contactPersonName = body.path("contactPersonName").asText("");
        String contactPersonNo = body.path("contactPersonNo").asText("");
        String createdBy = body.path("createdBy").asText("");

// Basic validation - return a clear error if required fields missing
        if (vendorId.isEmpty() || email.isEmpty()) {
            Map<String, Object> error = new HashMap<>();
            error.put("o_status", "FAIL");
            error.put("o_message", "Required fields missing: vendorId or email");
            return error;
        }

        Map<String, Object> result = vendorDao.registerVendor(
                vendorId,
                vendorName,
                email,
                contactNo,
                address,
                pincode,
                gstNumber,
                panNumber,
                aadharNumber,
                contactPersonName,
                contactPersonNo,
                createdBy,
                passwordHash
        );

        // save files only if vendor created
        if ("SUCCESS".equalsIgnoreCase(String.valueOf(result.get("o_status")))) {

            vendorFileService.uploadSingleFile(vendorId, gstFile);
            vendorFileService.uploadSingleFile(vendorId, panFile);

            if (vendorDoc != null && !vendorDoc.isEmpty()) {
                vendorFileService.uploadSingleFile(vendorId, vendorDoc);
            }

            result.put("defaultPassword", defaultPassword);

            String token = jwtUtil.generateToken(
                    email,
                    vendorName,
                     "GPS_VENDOR"
             );

             result.put("token", token);
         }

         return result;
     }

	@Override
	public Map<String, Object> getAllVendors() {
		// TODO Auto-generated method stub
		 try {
	            return vendorDao.getVendorData();
	        } catch (EmptyResultDataAccessException e) {

	            Map<String, Object> error = new HashMap<>();
	            error.put("status", "FAIL");
	            error.put("message", "Vendor not found  " );

	            return error;
	        }
	} 

} 