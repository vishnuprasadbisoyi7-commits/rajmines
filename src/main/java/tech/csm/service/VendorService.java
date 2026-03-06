package tech.csm.service;

import java.util.Map;

import org.jspecify.annotations.Nullable;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.multipart.MultipartFile;

import tools.jackson.databind.JsonNode;



public interface VendorService {

	    Map<String, Object> sendOtp(JsonNode body);
	    Map<String, Object> verifyOtp(JsonNode body);
	    Map<String, Object> registerVendor(JsonNode body, MultipartFile gstFile, MultipartFile panFile, MultipartFile vendorDoc);
		Map<String,Object> getVendorPrefillData(String vendorId);
		Map<String,Object> getAllVendors();
	

}

