package tech.csm.service;

import java.util.List;
import java.util.Map;

import org.jspecify.annotations.Nullable;

import tech.csm.dto.MenuLinkDTO;
import tech.csm.dto.SaveMappingRequest;

public interface UserMenuMappingService {

	List<MenuLinkDTO> getMenuLinksForUser(Long userId);
	
	 List<MenuLinkDTO> getAssignedLinksForUser(Long userId);
	 
	 void saveMappings(SaveMappingRequest request);
	 
	 void copyMappings(Long fromUserId, Long toUserId);
	 
	 List<Map<String, Object>> searchUsers(String query);

}
