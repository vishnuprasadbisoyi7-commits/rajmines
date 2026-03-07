package tech.csm.service;

import java.util.List;

import tech.csm.model.GlobalLink;

public interface GlobalLinkService {
	
	 GlobalLink save(GlobalLink globalLink);
	 
	    List<GlobalLink> findAll();
	    
		List<GlobalLink> findByStatus(String string);
		

	    List<GlobalLink> getActiveGlobals();

}
