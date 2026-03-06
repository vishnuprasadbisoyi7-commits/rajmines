package tech.csm.exception;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

import jakarta.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import tools.jackson.databind.exc.JsonNodeException;
import org.springframework.web.bind.MethodArgumentNotValidException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    private Map<String, Object> errorBody(String status, String message, HttpServletRequest request) {
        Map<String, Object> body = new HashMap<>();
        body.put("status", status);
        body.put("message", message == null ? "" : message);
        body.put("path", request == null ? "" : request.getRequestURI());
        body.put("timestamp", Instant.now().toString());
        return body;
    }

    @ExceptionHandler(JsonNodeException.class)
    public ResponseEntity<Map<String, Object>> handleJsonNodeException(JsonNodeException ex, HttpServletRequest request) {
        logger.warn("JsonNodeException: {}", ex.getMessage());
        Map<String, Object> body = errorBody("FAIL", "Invalid or missing JSON fields: " + ex.getMessage(), request);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<Map<String, Object>> handleBadCredentials(BadCredentialsException ex, HttpServletRequest request) {
        logger.info("Bad credentials for request {}", request == null ? "" : request.getRequestURI());
        Map<String, Object> body = errorBody("FAIL", "Invalid credentials", request);
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(body);
    }

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<Map<String, Object>> handleValidation(ValidationException ex, HttpServletRequest request) {
        logger.info("Validation failed: {}", ex.getMessage());
        Map<String, Object> body = errorBody("FAIL", ex.getMessage(), request);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body);
    }

    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity<Map<String, Object>> handleNpe(NullPointerException ex, HttpServletRequest request) {
        logger.error("NullPointerException: {}", ex.getMessage(), ex);
        Map<String, Object> body = errorBody("FAIL", "Required request data missing or invalid", request);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body);
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<Map<String, Object>> handleRuntime(RuntimeException ex, HttpServletRequest request) {
        logger.error("RuntimeException: {}", ex.getMessage(), ex);
        Map<String, Object> body = errorBody("FAIL", ex.getMessage(), request);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(body);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> handleException(Exception ex, HttpServletRequest request) {
        logger.error("Unhandled exception: {}", ex.getMessage(), ex);
        Map<String, Object> body = errorBody("FAIL", "An unexpected error occurred", request);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(body);
    }
}