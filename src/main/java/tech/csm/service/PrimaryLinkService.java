package tech.csm.service;

import java.util.List;

import tech.csm.model.PrimaryLink;

public interface PrimaryLinkService {
	
	PrimaryLink save(PrimaryLink primaryLink);
	
    List<PrimaryLink> findAll();

}
