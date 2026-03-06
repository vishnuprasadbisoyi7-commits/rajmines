package tech.csm.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class AlertThreshold implements Serializable {

	private Long thresholdId;
    private String alertType;
    private BigDecimal thresholdValue;
    private String unit;
    private String description;
    private String isActive;
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;
	
}
