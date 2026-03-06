package tech.csm.service;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import tech.csm.dao.VendorFileDao;
import tech.csm.util.FileUtil;

@Service
public class VendorFileServiceImpl implements VendorFileService {

	
	
	 	@Autowired
	    private VendorFileDao fileDao;

	    @Autowired
	    private FileUtil fileUtil;
	    
	    @Value("${upload.dir}")
	    private String uploadDir;

	    @Override
	    public void uploadFiles(String vendorId, List<MultipartFile> files) {

	        for (MultipartFile file : files) {
	            String storedFileName = fileUtil.uploadFile(file);
	            fileDao.saveFile(vendorId, storedFileName);
	        }
	    }
	    @Override
	    public void uploadSingleFile(String vendorId, MultipartFile file) {

	        if (file == null || file.isEmpty()) return;

	        try {
	            String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
	            Path path = Paths.get(uploadDir + vendorId + "/" + fileName);
	            Files.createDirectories(path.getParent());
	            Files.write(path, file.getBytes());

	            fileDao.saveFile(vendorId, fileName);

	        } catch (Exception e) {
	            throw new RuntimeException("File upload failed", e);
	        }
	    }

}
