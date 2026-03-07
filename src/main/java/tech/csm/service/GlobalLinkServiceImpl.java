package tech.csm.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import tech.csm.model.GlobalLink;
import tech.csm.repository.GlobalLinkRepository;

@Service
@RequiredArgsConstructor
public class GlobalLinkServiceImpl implements GlobalLinkService {

	@Autowired
    private GlobalLinkRepository globalLinkRepository;

    @Override
    public GlobalLink save(GlobalLink globalLink) {
        return globalLinkRepository.save(globalLink);
    }

    @Override
    public List<GlobalLink> findAll() {
        return globalLinkRepository.findAll();
    }

    @Override
    public List<GlobalLink> findByStatus(String status) {
        return globalLinkRepository.findByStatus(status);
    }

    public List<GlobalLink> getActiveGlobals() {
        return globalLinkRepository.findByStatusOrderByOrderNo("A");
    }


	

}
