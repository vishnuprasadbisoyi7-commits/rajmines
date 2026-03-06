package tech.csm.dao;

import java.util.Map;

import tech.csm.entity.Group;

public interface GroupDao {



	Map<String, Object> saveGroup(Integer groupId, String groupName);

	Map<String, Object> getAllGroup();

}
