package tech.csm.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UserStatusUpdateRequest {
    @NotBlank(message = "Status is required")
    private String status; // "Y" for active, "N" for inactive
}

