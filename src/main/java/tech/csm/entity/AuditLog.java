package tech.csm.entity;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class AuditLog {

	private Long logId;
    private String userId;
    private String userRole;
    private String actionType;
    private String entityType;
    private String entityId;
    private String oldValues;
    private String newValues;
    private String ipAddress;
    private String sessionId;
    private LocalDateTime actionDateTime;
    private String remarks;

	
}
