package tech.csm.entity;

import java.io.Serializable;
import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class GPSDevice implements Serializable {

	private Long deviceId;
    private String deviceName;
    private String imeiSerial;
    private String model;
    private String firmwareVersion;
    private String simIccid;
    private String simMsisdn;
    private String healthStatus;
    private String deviceStatus;
    private String vendorId;
    private LocalDateTime registrationDate;
    private String registeredBy;
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;
    private String remarks;
	
}
