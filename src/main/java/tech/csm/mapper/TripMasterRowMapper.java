package tech.csm.mapper;


import org.springframework.jdbc.core.RowMapper;

import tech.csm.entity.TripMaster;

import java.sql.ResultSet;
import java.sql.SQLException;

public class TripMasterRowMapper implements RowMapper<TripMaster> {
    @Override
    public TripMaster mapRow(ResultSet rs, int rowNum) throws SQLException {
        TripMaster t = new TripMaster();
        t.setTripId(rs.getLong("TRIP_ID"));
        t.setTripNumber(rs.getString("TRIP_NUMBER"));
        t.setErwanaTransitPassNo(rs.getString("ERWANA_TRANSIT_PASS_NO"));
        t.setVehicleRegNo(rs.getString("VEHICLE_REG_NO"));
        t.setDeviceId(rs.getLong("DEVICE_ID"));
        t.setOriginLeaseId(rs.getLong("ORIGIN_LEASE_ID"));
        t.setDestinationDealerId(rs.getLong("DESTINATION_DEALER_ID"));
        t.setCommodityType(rs.getString("COMMODITY_TYPE"));
        t.setCommodityQuantity(rs.getBigDecimal("COMMODITY_QUANTITY"));
        t.setDriverName(rs.getString("DRIVER_NAME"));
        t.setDriverContact(rs.getString("DRIVER_CONTACT"));
        t.setExpectedDepartureTime(rs.getTimestamp("EXPECTED_DEPARTURE_TIME") != null ? rs.getTimestamp("EXPECTED_DEPARTURE_TIME").toLocalDateTime() : null);
        t.setActualDepartureTime(rs.getTimestamp("ACTUAL_DEPARTURE_TIME") != null ? rs.getTimestamp("ACTUAL_DEPARTURE_TIME").toLocalDateTime() : null);
        t.setExpectedArrivalTime(rs.getTimestamp("EXPECTED_ARRIVAL_TIME") != null ? rs.getTimestamp("EXPECTED_ARRIVAL_TIME").toLocalDateTime() : null);
        t.setActualArrivalTime(rs.getTimestamp("ACTUAL_ARRIVAL_TIME") != null ? rs.getTimestamp("ACTUAL_ARRIVAL_TIME").toLocalDateTime() : null);
        t.setWeighbridgeId(rs.getLong("WEIGHBRIDGE_ID"));
        t.setRouteDetails(rs.getString("ROUTE_DETAILS"));
        t.setTripStatus(rs.getString("TRIP_STATUS"));
        t.setCreatedDate(rs.getTimestamp("CREATED_DATE").toLocalDateTime());
        t.setCreatedBy(rs.getString("CREATED_BY"));
        t.setUpdatedDate(rs.getTimestamp("UPDATED_DATE") != null ? rs.getTimestamp("UPDATED_DATE").toLocalDateTime() : null);
        t.setRemarks(rs.getString("REMARKS"));
        return t;
    }
}
