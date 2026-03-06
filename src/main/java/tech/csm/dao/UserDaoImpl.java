package tech.csm.dao;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;

import jakarta.annotation.PostConstruct;

@Repository
public class UserDaoImpl implements UserDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private SimpleJdbcCall loginSp;
    private SimpleJdbcCall resetSp;

    @PostConstruct
    public void init() {
        loginSp = new SimpleJdbcCall(jdbcTemplate)
                .withProcedureName("SP_LOGIN_USER")
                .returningResultSet("result", (rs, rowNum) -> {
                    Map<String, Object> map = new HashMap<>();
                    map.put("USER_ID", rs.getString("USER_ID"));
                    map.put("PASSWORD_HASH", rs.getString("PASSWORD_HASH"));
                    map.put("USER_ROLE", rs.getString("USER_ROLE"));
                    map.put("STATUS", rs.getString("STATUS"));
                    map.put("VENDOR_ID", rs.getString("VENDOR_ID"));
                    return map;
                });

        resetSp = new SimpleJdbcCall(jdbcTemplate)
                .withProcedureName("SP_RESET_PASSWORD");
    }

    @Override
    public Map<String, Object> login(String userId) {
        Map<String, Object> out = loginSp.execute(
                new MapSqlParameterSource().addValue("p_user_id", userId));

        @SuppressWarnings("unchecked")
		List<Map<String, Object>> list = (List<Map<String, Object>>) out.get("result");
        return list.isEmpty() ? Collections.emptyMap() : list.get(0);
    }

    @Override
    public Map<String, Object> resetPassword(String userId, String newHash) {
        return resetSp.execute(new MapSqlParameterSource()
                .addValue("p_user_id", userId)
                .addValue("p_new_hash", newHash));
    }
}

