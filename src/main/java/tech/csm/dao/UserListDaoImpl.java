package tech.csm.dao;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;

import jakarta.annotation.PostConstruct;
import tech.csm.dto.UserDto;

@Repository
public class UserListDaoImpl implements UserListDao {

	private final JdbcTemplate jdbcTemplate;

    private SimpleJdbcCall getAllUsersSp;
    private SimpleJdbcCall getUserByIdSp;
    private SimpleJdbcCall updateUserRoleSp;
    private SimpleJdbcCall updateUserStatusSp;
    private SimpleJdbcCall saveUserSp;

    public UserListDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @PostConstruct
    public void init() {
        this.getAllUsersSp = new SimpleJdbcCall(jdbcTemplate)
                .withProcedureName("SP_GET_ALL_USERS");

        this.getUserByIdSp = new SimpleJdbcCall(jdbcTemplate)
                .withProcedureName("SP_GET_USER_BY_ID");

        this.updateUserRoleSp = new SimpleJdbcCall(jdbcTemplate)
                .withProcedureName("SP_UPDATE_USER_ROLE");

        this.updateUserStatusSp = new SimpleJdbcCall(jdbcTemplate)
                .withProcedureName("SP_UPDATE_USER_STATUS");
        
         this.saveUserSp = new SimpleJdbcCall(jdbcTemplate)
				 .withProcedureName("SP_SAVE_USER");
    }

    @Override
    public List<UserDto> getAllUsers() {
        Map<String, Object> result = getAllUsersSp.execute();
        return (List<UserDto>) result.get("#result-set-1");
    }

    @Override
    public Optional<UserDto> getUserById(Integer userId) {
        Map<String, Object> result = getUserByIdSp.execute(
                Collections.singletonMap("p_user_id", userId)
        );

        List<Map<String, Object>> rs = (List<Map<String, Object>>) result.get("#result-set-1");

        if (rs == null || rs.isEmpty()) return Optional.empty();

        Map<String, Object> row = rs.get(0);
        UserDto dto = new UserDto();
        dto.setId((Integer) row.get("id"));
        dto.setFullname((String) row.get("fullname"));
        dto.setEmail((String) row.get("email"));
        dto.setRole((String) row.get("role"));
        dto.setIsActive((String) row.get("is_active"));

        return Optional.of(dto);
    }

    @Override
    public boolean updateUserRole(Integer userId, String role) {
        Map<String, Object> result = updateUserRoleSp.execute(
                Map.of("p_user_id", userId, "p_new_role", role)
        );
        List<Map<String, Object>> rs = (List<Map<String, Object>>) result.get("#result-set-1");
        return rs != null && !rs.isEmpty() && ((Number) rs.get(0).get("rows_affected")).intValue() > 0;
    }

    @Override
    public boolean updateUserStatus(Integer userId, String status) {
        Map<String, Object> result = updateUserStatusSp.execute(
                Map.of("p_user_id", userId, "p_status", status)
        );
        List<Map<String, Object>> rs = (List<Map<String, Object>>) result.get("#result-set-1");
        return rs != null && !rs.isEmpty() && ((Number) rs.get(0).get("rows_affected")).intValue() > 0;
    }

    @Override
    public Map<String, Object> saveUser(UserDto userDto) {

        Map<String, Object> params = Map.of(
                "p_fullname", userDto.getFullname(),
                "p_email", userDto.getEmail(),
                "p_role", userDto.getRole(),
                "p_is_active", userDto.getIsActive(),
                "p_groupname", userDto.getGroupname(),
                "p_username", userDto.getUsername(),
                "p_mobileno", userDto.getMobileno()
        );

        return saveUserSp.execute(params);
    }

}
