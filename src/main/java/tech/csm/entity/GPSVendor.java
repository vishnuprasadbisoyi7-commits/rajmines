package tech.csm.entity;

import java.io.Serializable;
import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class GPSVendor implements Serializable {

	private String vendorId;
    private String vendorName;
    private String email;
    private String contactNo;
    private String address;
    private String pincode;
    private String gstNumber;
    private String panNumber;
    private String contactPersonName;
    private String contactPersonNo;
    private String status;
    private String otp;
    private LocalDateTime otpExpiry;
    private String otpVerified;
    private LocalDateTime createdDate;
    private String createdBy;
    private LocalDateTime updatedDate;
    private String updatedBy;
    private String remarks;
	
}
