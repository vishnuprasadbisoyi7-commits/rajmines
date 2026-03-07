package tech.csm.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import tech.csm.model.PrimaryLink;
import tech.csm.repository.PrimaryLinkRepository;

@Service
@RequiredArgsConstructor
public class PrimaryLinkServiceImpl implements PrimaryLinkService {

	@Autowired
    private PrimaryLinkRepository primaryLinkRepository;

    @Override
    public PrimaryLink save(PrimaryLink primaryLink) {
        return primaryLinkRepository.save(primaryLink);
    }

    @Override
    public List<PrimaryLink> findAll() {
        return primaryLinkRepository.findAll();
    }

}
