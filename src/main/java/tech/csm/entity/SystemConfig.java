package tech.csm.entity;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class SystemConfig {

	private Long configId;
    private String configKey;
    private String configValue;
    private String configDescription;
    private String configType;
    private String isActive;
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;

	
}
