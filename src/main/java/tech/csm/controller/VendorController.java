package tech.csm.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import jakarta.annotation.security.PermitAll;

import org.springframework.security.access.prepost.PreAuthorize;

import tech.csm.service.VendorFileService;
import tech.csm.service.VendorService;
import tools.jackson.databind.JsonNode;
import org.springframework.web.bind.annotation.RequestParam;




@RestController
@RequestMapping("/vendor")
@CrossOrigin("*")
//@PreAuthorize("hasAnyRole('ADMIN','GPS_VENDOR')") // allow ADMIN (can access vendor endpoints) and VENDOR; other roles like gps_vendor will be denied
public class VendorController {

    @Autowired
    private VendorService vendorService;
    
    @Autowired
    private VendorFileService fileService;

    @PostMapping("/send-otp")
    public ResponseEntity<?> sendOtp(@RequestBody JsonNode body) {
        return ResponseEntity.ok(vendorService.sendOtp(body));
    }

    @PostMapping("/verify-otp")
    public ResponseEntity<?> verifyOtp(@RequestBody JsonNode body) {
        return ResponseEntity.ok(vendorService.verifyOtp(body));
    }

	/*
	 * @PostMapping("/register") public ResponseEntity<?>
	 * registerVendor(@RequestBody JsonNode body) { return
	 * ResponseEntity.ok(vendorService.registerVendor(body)); }
	 */
    

    @PostMapping(
    	    value = "/register",
    	    consumes = MediaType.MULTIPART_FORM_DATA_VALUE
    	)
    	public ResponseEntity<?> registerVendor(
    	        @RequestPart("vendorData") JsonNode body,
    	        @RequestPart("gstFile") MultipartFile gstFile,
    	        @RequestPart("panFile") MultipartFile panFile,
    	        @RequestPart(value = "vendorDoc", required = false) MultipartFile vendorDoc
    	) {
    	    return ResponseEntity.ok(vendorService.registerVendor(body, gstFile, panFile, vendorDoc));
    	}

    

    @PermitAll
    @GetMapping("/prefill/{vendorId}")
    public ResponseEntity<?> prefill(@PathVariable String vendorId) {
        return ResponseEntity.ok(vendorService.getVendorPrefillData(vendorId));
    }
    
    @PermitAll
    @GetMapping("/list")
    public ResponseEntity<?> getAllVendors() {
        return  ResponseEntity.ok(vendorService.getAllVendors());
    }
    


    @PostMapping(
            value = "/upload/{vendorId}",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE
        )
        public ResponseEntity<Void> uploadFiles(
                @PathVariable String vendorId,
                @RequestPart("files") List<MultipartFile> files) {

            fileService.uploadFiles(vendorId, files);
            return ResponseEntity.ok().build();
        }
}