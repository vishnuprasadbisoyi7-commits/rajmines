package tech.csm.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import tech.csm.entity.Group;
import tech.csm.service.GroupService;
import tools.jackson.databind.JsonNode;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;



@RestController
@RequestMapping("/group")
@CrossOrigin("*")
@PreAuthorize("hasAuthority('ADMIN')")
public class GroupController {
	
	@Autowired
	private GroupService groupService;

	@PostMapping("/save")
	public ResponseEntity<?> saveGroupName(@RequestBody JsonNode entity) {
		//TODO: process POST request
		
		return ResponseEntity.ok(groupService.saveGroup(entity));
	}
	
	@GetMapping("/get")
	public ResponseEntity<?> getAllGroup() {
		
		return ResponseEntity.ok(groupService.getAllGroup());
		
	}
	
	
}
