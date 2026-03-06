package tech.csm.entity;

import java.io.Serializable;
import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class SystemUser implements Serializable {

	 private String userId;
	    private String username;
	    private String email;
	    private String passwordHash;
	    private String userRole;
	    private String vendorId;
	    private String status;
	    private LocalDateTime lastLoginDate;
	    private LocalDateTime createdDate;
	    private String createdBy;
	    private LocalDateTime updatedDate;
	
}
