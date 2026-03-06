package tech.csm.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class UserDto {

	 private Integer id;
	 
	 private String groupname;
	    private String fullname;
	    private String username;
	    private String mobileno;
	    
	    private String email;
	    private String role;
	    private String isActive;
}
