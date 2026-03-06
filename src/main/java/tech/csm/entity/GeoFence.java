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
public class GeoFence implements Serializable {

	private Long geofenceId;
    private String geofenceName;
    private String geofenceType;
    private BigDecimal latitude;
    private BigDecimal longitude;
    private BigDecimal radiusMeters;
    private BigDecimal toleranceRadiusMeters;
    private String address;
    private String status;
    private LocalDateTime createdDate;
    private String createdBy;
    private LocalDateTime updatedDate;
    private String updatedBy;
    private String description;
}
