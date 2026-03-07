package tech.csm.service;

import java.util.List;

import tech.csm.model.FunctionMaster;

public interface FunctionMasterService {
	
	 FunctionMaster save(FunctionMaster functionMaster);
	 
	    List<FunctionMaster> findAll();

		List<FunctionMaster> findByStatus(String string);

	    List<FunctionMaster> getActiveFunctions();

}
