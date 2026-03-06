package tech.csm.dao;

import java.util.Map;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;

import tech.csm.entity.Group;

@Repository
public class GroupDaoImpl implements GroupDao {

	private final SimpleJdbcCall saveGroupJdbcCall;
	
	private final SimpleJdbcCall getAllGroupJdbcCall;
	
	public GroupDaoImpl(DataSource dataSource) {
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		this.saveGroupJdbcCall = new SimpleJdbcCall(jdbcTemplate)
				.withProcedureName("save_group");
		
		this.getAllGroupJdbcCall = new SimpleJdbcCall(jdbcTemplate)
				.withProcedureName("get_all_groups");
	}
	


	@Override
	public Map<String, Object> saveGroup(Integer groupId, String groupName) {
		// TODO Auto-generated method stub
		 Map<String, Object> in = Map.of(
	                "p_group_id", groupId,
	                "p_group_name", groupName
	        );
	        return saveGroupJdbcCall.execute(in);
		
	
	}



	@Override
	public Map<String, Object> getAllGroup() {
		// TODO Auto-generated method stub
		
		Map<String, Object> result = getAllGroupJdbcCall.execute();
		
		return result;
	}



}
