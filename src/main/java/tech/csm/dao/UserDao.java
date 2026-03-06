package tech.csm.dao;

import java.util.Map;

public interface UserDao {
    Map<String, Object> login(String userId);
    Map<String, Object> resetPassword(String userId, String newHash);
}

