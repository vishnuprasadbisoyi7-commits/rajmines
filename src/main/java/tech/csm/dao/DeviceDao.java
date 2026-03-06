package tech.csm.dao;

import java.sql.Timestamp;
import java.util.Map;

public interface DeviceDao {

    Map<String, Object> registerDevice(
            String deviceName,
            String imeiSerial,
            String model,
            String firmwareVersion,
            String simIccid1,
            String simMsisdn1,
            String simIccid2,
            String simMsisdn2,
            String healthStatus,
            String deviceStatus,
            String vendorId,
            String registeredBy,
            String remarks,
            Timestamp registrationDate
    );

    Map<String, Object> listDevices();
}