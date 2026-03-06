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
public class TripMaster implements Serializable {

	private Long tripId;
    private String tripNumber;
    private String erwanaTransitPassNo;
    private String vehicleRegNo;
    private Long deviceId;
    private Long originLeaseId;
    private Long destinationDealerId;
    private String commodityType;
    private BigDecimal commodityQuantity;
    private String driverName;
    private String driverContact;
    private LocalDateTime expectedDepartureTime;
    private LocalDateTime actualDepartureTime;
    private LocalDateTime expectedArrivalTime;
    private LocalDateTime actualArrivalTime;
    private Long weighbridgeId;
    private String routeDetails;
    private String tripStatus;
    private LocalDateTime createdDate;
    private String createdBy;
    private LocalDateTime updatedDate;
    private String remarks;
	
}
