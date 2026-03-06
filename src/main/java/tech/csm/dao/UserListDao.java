package tech.csm.dao;

import tech.csm.dto.UserDto;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface UserListDao {

    List<UserDto> getAllUsers();

    Optional<UserDto> getUserById(Integer userId);

    boolean updateUserRole(Integer userId, String role);

    boolean updateUserStatus(Integer userId, String status);

	Map<String, Object> saveUser(UserDto userDto);
}