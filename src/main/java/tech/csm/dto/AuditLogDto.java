package tech.csm.dto;

import java.time.Instant;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class AuditLogDto {

    private Long id;
    private String actionType;
    private String entityName;
    private Integer entityId;
    private Integer performedBy;
    private String description;
    private Instant createdAt;
	
}
