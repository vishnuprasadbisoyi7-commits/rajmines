package tech.csm.dto;

import java.time.Instant;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor // Essential for JSON
public class ApiResponse<T> {
	
    private boolean success; // Changed from String to boolean
    private String message;
    private T data;
    private Instant timestamp = Instant.now();

    // Add this specific constructor to match your Controller calls
    public ApiResponse(boolean success, String message, T data) {
        this.success = success;
        this.message = message;
        this.data = data;
    }
}