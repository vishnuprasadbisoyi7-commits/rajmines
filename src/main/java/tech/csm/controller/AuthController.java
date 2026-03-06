package tech.csm.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

import jakarta.validation.Valid;

import tech.csm.service.AuthService;
import tools.jackson.databind.JsonNode;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody tools.jackson.databind.JsonNode body) {
        Map<String, Object> result = authService.login(body);

        Object token = result.get("token");
        if (token != null) {
            // Set Authorization header so client can read it (CORS must expose this header)
            return ResponseEntity.ok()
                    .header("Authorization", "Bearer " + token.toString())
                    .body(result);
        }

        return ResponseEntity.ok(result);
    }

    @PostMapping("/admin/login")
    public ResponseEntity<?> adminlogin(@RequestBody tools.jackson.databind.JsonNode body) {
        Map<String, Object> result = authService.adminlogin(body);

        Object token = result.get("token");
        if (token != null) {
            // Set Authorization header so client can read it (CORS must expose this header)
            return ResponseEntity.ok()
                    .header("Authorization", "Bearer " + token.toString())
                    .body(result);
        }

        return ResponseEntity.ok(result);
    }
    
    @PostMapping("/first-login-reset")
    public ResponseEntity<?> firstLoginReset(@RequestBody JsonNode body) {
        return ResponseEntity.ok(authService.firstLoginReset(body));
    }
}