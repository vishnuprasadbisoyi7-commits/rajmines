package tech.csm.service;

import java.util.Map;


import tools.jackson.databind.JsonNode;

public interface GroupService {

	Map<String,Object> saveGroup(JsonNode entity);

	Map<String,Object> getAllGroup();

}
