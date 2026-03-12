package tech.csm.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import tech.csm.dto.MenuLinkDTO;
import tech.csm.dto.SaveMappingRequest;
import tech.csm.service.UserMenuMappingService;

@RestController
@RequestMapping("/user-menu-mapping")
@CrossOrigin(origins = "http://localhost:4200")
public class UserMenuMappingController {

	 	@Autowired
	    private UserMenuMappingService service;

	    /**
	     * GET /api/user-menu-mapping/links/{userId}
	     * All menu links with checked state for given user (for the mapping form).
	     */
	    @GetMapping("/links/{userId}")
	    public ResponseEntity<List<MenuLinkDTO>> getLinksForUser(@PathVariable Long userId) {
	        return ResponseEntity.ok(service.getMenuLinksForUser(userId));
	    }

	    /**
	     * GET /api/user-menu-mapping/sidebar/{userId}
	     * Only the assigned/checked links — used by the Sidebar component.
	     */
	    @GetMapping("/sidebar/{userId}")
	    public ResponseEntity<List<MenuLinkDTO>> getSidebarLinks(@PathVariable Long userId) {
	        return ResponseEntity.ok(service.getAssignedLinksForUser(userId));
	    }

	    /**
	     * POST /api/user-menu-mapping/save
	     * Save checkbox selections for a user.
	     */
	    @PostMapping("/save")
	    public ResponseEntity<Void> saveMappings(@RequestBody SaveMappingRequest request) {
	        service.saveMappings(request);
	        return ResponseEntity.ok().build();
	    }

	    /**
	     * POST /api/user-menu-mapping/copy
	     * Copy menu mappings from one user to another.
	     */
	    @PostMapping("/copy")
	    public ResponseEntity<Void> copyMappings(
	            @RequestParam Long fromUserId,
	            @RequestParam Long toUserId) {
	        service.copyMappings(fromUserId, toUserId);
	        return ResponseEntity.ok().build();
	    }

	    /**
	     * GET /api/user-menu-mapping/users/search?q=john
	     * Search users for the "Copy From" typeahead.
	     */
	    @GetMapping("/users/search")
	    public ResponseEntity<List<Map<String, Object>>> searchUsers(@RequestParam String q) {
	        return ResponseEntity.ok(service.searchUsers(q));
	    }
	
}
