package tech.csm.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;



import tech.csm.dao.UserDao;
import tech.csm.util.JwtUtil;
import tools.jackson.databind.JsonNode;

@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    @Override
    public Map<String, Object> login(tools.jackson.databind.JsonNode body) {

        // defensive access to avoid NPE when a field is missing
        String userId = body.path("userId").asText("");   // email id
        String password = body.path("password").asText("");

        // basic validation
        if (userId.isEmpty() || password.isEmpty()) {
            Map<String, Object> error = new java.util.HashMap<>();
            error.put("status", "FAIL");
            error.put("message", "Required fields missing: userId or password");
            return error;
        }

        Map<String, Object> dbResult = userDao.login(userId);

        if (dbResult == null || dbResult.isEmpty()) {
            Map<String, Object> error = new java.util.HashMap<>();
            error.put("status", "FAIL");
            error.put("message", "User not found");
            return error;
        }

        String dbHash = (String) dbResult.get("PASSWORD_HASH");

        if (!passwordEncoder.matches(password, dbHash)) {
            throw new RuntimeException("Invalid Credentials");
        }

        // generate JWT token and include it in response body
        String username = (String) dbResult.get("USER_ID");
        String role = (String) dbResult.get("USER_ROLE");
        String token = jwtUtil.generateToken(userId, username == null ? userId : username, role == null ? "" : role);

        // return a shallow copy with token and a success status
        dbResult.put("token", token);
        dbResult.put("status", "SUCCESS");

        return dbResult;
    }

    @Override
    public Map<String, Object> firstLoginReset(tools.jackson.databind.JsonNode body) {

        String userId = body.path("userId").asText("");
        String newPassword = body.path("newPassword").asText("");

        if (userId.isEmpty() || newPassword.isEmpty()) {
            Map<String, Object> error = new java.util.HashMap<>();
            error.put("status", "FAIL");
            error.put("message", "Required fields missing: userId or newPassword");
            return error;
        }

        return userDao.resetPassword(userId, passwordEncoder.encode(newPassword));
    }

    @Override
    public Map<String, Object> adminlogin(JsonNode body) {
        String userId = body.path("userId").asText("");
        String password = body.path("password").asText("");

        if (userId.isEmpty() || password.isEmpty()) {
            Map<String, Object> error = new java.util.HashMap<>();
            error.put("status", "FAIL");
            error.put("message", "Required fields missing: userId or password");
            return error;
        }

        Map<String, Object> dbResult = userDao.login(userId);
        if (dbResult == null || dbResult.isEmpty()) {
            Map<String, Object> error = new java.util.HashMap<>();
            error.put("status", "FAIL");
            error.put("message", "User not found");
            return error;
        }

        String dbHash = (String) dbResult.get("PASSWORD_HASH");
        if (!passwordEncoder.matches(password, dbHash)) {
            throw new RuntimeException("Invalid Credentials");
        }

        // ✅ Role check — only ADMIN can login to VTS admin panel
        String role = (String) dbResult.get("USER_ROLE");
        if (role == null || !role.equalsIgnoreCase("ADMIN")) {
            Map<String, Object> error = new java.util.HashMap<>();
            error.put("status", "FAIL");
            error.put("message", "Access denied. Only admin users can login here.");
            return error;
        }

        String username = (String) dbResult.get("USER_ID");
        String token = jwtUtil.generateToken(userId, username == null ? userId : username, role);

        dbResult.put("token", token);
        dbResult.put("status", "SUCCESS");
        return dbResult;
    }

	
}