package tech.csm.service;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.Map;

import org.springframework.stereotype.Service;

import tech.csm.dao.DeviceDao;
import tech.csm.service.DeviceService;
import tools.jackson.databind.JsonNode;
import tech.csm.exception.ValidationException;

@Service
public class DeviceServiceImpl implements DeviceService {

    private final DeviceDao deviceDao;

    public DeviceServiceImpl(DeviceDao deviceDao) {
        this.deviceDao = deviceDao;
    }

    @Override
    public Map<String, Object> registerDevice(JsonNode body) {

        String deviceName     = body.path("deviceName").asText("").trim();
        String imeiSerial     = body.path("imeiSerial").asText("").trim();
        String model          = body.path("model").asText("").trim();
        String firmwareVersion= body.path("firmwareVersion").asText(null);

        String simIccid1      = body.path("simIccid").asText("").trim();      // ✅ fixed
        String simMsisdn1     = body.path("simMsisdn").asText("").trim();     // ✅ fixed
        String simIccid2      = body.path("simIccid2").asText(null);
        String simMsisdn2     = body.path("simMsisdn2").asText(null);

        String healthStatus   = body.path("healthStatus").asText("").trim();  // ✅ fixed
        String deviceStatus   = body.path("deviceStatus").asText("").trim();
        String vendorId       = body.path("vendorId").asText("").trim();
        String registeredBy   = body.path("registeredBy").asText("").trim();
        String remarks        = body.path("remarks").asText(null);

        if (deviceName.isEmpty() || imeiSerial.isEmpty() || model.isEmpty()
                || simIccid1.isEmpty() || simMsisdn1.isEmpty()
                || healthStatus.isEmpty() || deviceStatus.isEmpty()
                || vendorId.isEmpty() || registeredBy.isEmpty()) {

            throw new ValidationException("Missing required fields for device registration");
        }

        return deviceDao.registerDevice(
                deviceName,
                imeiSerial,
                model,
                firmwareVersion,
                simIccid1,
                simMsisdn1,
                simIccid2,
                simMsisdn2,
                healthStatus,
                deviceStatus,
                vendorId,
                registeredBy,
                remarks,
                Timestamp.from(Instant.now())
        );
    }

    @Override
    public Map<String, Object> listDevices() {
        return deviceDao.listDevices();
    }
}