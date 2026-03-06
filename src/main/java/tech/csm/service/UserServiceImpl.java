package tech.csm.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import tech.csm.dao.UserListDao;
import tech.csm.dto.UserDto;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserListDao userDao;
	
	@Override
	public List<UserDto> getAllUsers() {
		// TODO Auto-generated method stub
		return userDao.getAllUsers();
	}

}
