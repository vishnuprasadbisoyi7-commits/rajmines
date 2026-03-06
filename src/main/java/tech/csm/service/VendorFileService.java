package tech.csm.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

public interface VendorFileService {

	void uploadFiles(String employeeId, List<MultipartFile> files);
	
	 void uploadSingleFile(String vendorId, MultipartFile file);
	

}
