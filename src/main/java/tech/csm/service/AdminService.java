package tech.csm.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.csm.dao.UserListDao;
import tech.csm.dto.UserDto;

import java.util.List;
import java.util.Map;

@Service
public class AdminService {

    private final UserListDao userDao;
    

    public AdminService(UserListDao userDao) {
        this.userDao = userDao;
        
    }

    public List<UserDto> getAllUsers() {
        return userDao.getAllUsers();
    }

    @Transactional
    public UserDto updateUserRole(Integer userId, String role, Integer performedByUserId) {

        UserDto user = userDao.getUserById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        String oldRole = user.getRole();

        boolean updated = userDao.updateUserRole(userId, role);
        if (!updated) throw new RuntimeException("Role update failed");

       
        user.setRole(role.toUpperCase());
        return user;
    }

    @Transactional
    public UserDto updateUserStatus(Integer userId, String status, Integer performedByUserId) {

        UserDto user = userDao.getUserById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        String oldStatus = user.getIsActive();

        boolean updated = userDao.updateUserStatus(userId, status);
        if (!updated) throw new RuntimeException("Status update failed");

       

        user.setIsActive(status);
        return user;
    }

	public Map<String, Object> saveUser(UserDto userDto) {
		// TODO Auto-generated method stub
		return userDao.saveUser(userDto);
	}
}