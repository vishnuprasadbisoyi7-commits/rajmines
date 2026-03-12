package tech.csm.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import tech.csm.dto.ApiResponse;
import tech.csm.dto.UserDto;
import tech.csm.dto.UserRoleUpdateRequest;
import tech.csm.dto.UserStatusUpdateRequest;
import tech.csm.security.JwtUserDetails;
import tech.csm.service.AdminService;
import tech.csm.service.AuthService;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/admin")
@CrossOrigin("*")
@PreAuthorize("hasAuthority('ADMIN')") // ✅ applies to all endpoints
public class AdminController {

	@Autowired
	private AdminService adminService;
	
	

	// ✅ GET ALL USERS
	@GetMapping("/users")
	public ResponseEntity<List<UserDto>> getAllUsers() {
		return ResponseEntity.ok(adminService.getAllUsers());
	}

	// ✅ UPDATE USER ROLE
	@PutMapping("/users/{id}/role")
	public ResponseEntity<ApiResponse<Integer>> updateUserRole(@PathVariable Integer id,
	        @RequestBody @Valid UserRoleUpdateRequest request, 
	        @AuthenticationPrincipal JwtUserDetails userDetails) {

	    // Convert the String ID from userDetails to Integer
	    Integer adminId = Integer.valueOf(userDetails.getId()); 

	    adminService.updateUserRole(id, request.getRole(), adminId);

	    return ResponseEntity.ok(new ApiResponse<>(true, "User role updated successfully", id));
	}

	// ✅ UPDATE USER STATUS
	@PutMapping("/users/{id}/status")
	public ResponseEntity<ApiResponse<Integer>> updateUserStatus(@PathVariable Integer id,
	        @RequestBody @Valid UserStatusUpdateRequest request, 
	        @AuthenticationPrincipal JwtUserDetails userDetails) {

	    // Convert the String ID from userDetails to Integer
	    Integer adminId = Integer.valueOf(userDetails.getId());

	    adminService.updateUserStatus(id, request.getStatus(), adminId);

	    return ResponseEntity.ok(new ApiResponse<>(true, "User status updated successfully", id));
	}
	
	   @PostMapping("/createuser")
	    public ResponseEntity<Map<String, Object>> createUser(@RequestBody UserDto userDto) {

	        Map<String, Object> response = adminService.saveUser(userDto);

	        return ResponseEntity.ok(response);
	    }
	
	  

	   
}
