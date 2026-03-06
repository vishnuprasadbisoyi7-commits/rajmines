package tech.csm.service;

import java.util.Map;

import tools.jackson.databind.JsonNode;


public interface AuthService {
    Map<String, Object> login(JsonNode body);
    Map<String, Object> firstLoginReset(JsonNode body);
	Map<String, Object> adminlogin(JsonNode body);
	
}

