package tech.csm.dao;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;

import tech.csm.dao.DeviceDao;

@Repository
public class DeviceDaoImpl implements DeviceDao {

    private final SimpleJdbcCall registerDeviceCall;
    private final SimpleJdbcCall listDevicesCall;

    public DeviceDaoImpl(DataSource dataSource) {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

        this.registerDeviceCall = new SimpleJdbcCall(jdbcTemplate)
                .withProcedureName("sp_register_device");

        this.listDevicesCall = new SimpleJdbcCall(jdbcTemplate)
                .withProcedureName("sp_list_devices");
    }

    @Override
    public Map<String, Object> registerDevice(
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
    ) {

        Map<String, Object> in = new HashMap<>();
        in.put("p_device_name", deviceName);
        in.put("p_imei_serial", imeiSerial);
        in.put("p_model", model);
        in.put("p_firmware_version", firmwareVersion);
        in.put("p_sim_iccid1", simIccid1);
        in.put("p_sim_msisdn1", simMsisdn1);
        in.put("p_sim_iccid2", simIccid2);
        in.put("p_sim_msisdn2", simMsisdn2);
        in.put("p_health_status", healthStatus);
        in.put("p_device_status", deviceStatus);
        in.put("p_vendor_id", vendorId);
        in.put("p_registered_by", registeredBy);
        in.put("p_remarks", remarks);
        in.put("p_registration_date", registrationDate);

        return registerDeviceCall.execute(in);
    }

    @Override
    public Map<String, Object> listDevices() {
        return listDevicesCall.execute(new HashMap<>());
    }
}