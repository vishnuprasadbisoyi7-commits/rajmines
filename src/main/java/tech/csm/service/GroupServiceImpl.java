package tech.csm.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tech.csm.dao.GroupDao;
import tech.csm.entity.Group;
import tools.jackson.databind.JsonNode;

@Service
public class GroupServiceImpl implements GroupService {

	@Autowired
	private GroupDao groupDao;
	
	@Override
	public Map<String, Object> saveGroup(JsonNode entity) {
		// TODO Auto-generated method stub
		Integer groupId = entity.path("groupId").asInt(0);
		String groupName = entity.path("groupName").asText("").trim();
		
		return groupDao.saveGroup(groupId, groupName);
	}

	@Override
	public Map<String, Object> getAllGroup() {
		// TODO Auto-generated method stub
		return groupDao.getAllGroup();
	}

}
