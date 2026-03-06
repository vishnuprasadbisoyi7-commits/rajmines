package tech.csm.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import tech.csm.service.DeviceService;
import tools.jackson.databind.JsonNode;


@RestController
@RequestMapping("/device")
public class DeviceController {

	private final DeviceService deviceService;
	
	public DeviceController(DeviceService deviceService) {
		this.deviceService = deviceService;
	}
	
	@PostMapping("/register")
	public ResponseEntity<?> registerDevice(@RequestBody JsonNode body) {
		return ResponseEntity.ok(deviceService.registerDevice(body));
	}
	
	@GetMapping("/list")
	public ResponseEntity<?> listDevices() {
		return ResponseEntity.ok(deviceService.listDevices());
	}
	
}
