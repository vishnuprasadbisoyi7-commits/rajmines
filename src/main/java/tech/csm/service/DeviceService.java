package tech.csm.service;

import java.util.Map;

import org.jspecify.annotations.Nullable;

import tools.jackson.databind.JsonNode;

public interface DeviceService {

	Map<String,Object> listDevices();

	Map<String,Object> registerDevice(JsonNode body);

}
