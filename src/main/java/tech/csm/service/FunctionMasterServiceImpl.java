package tech.csm.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import tech.csm.model.FunctionMaster;
import tech.csm.repository.FunctionMasterRepository;

@Service
@RequiredArgsConstructor
public class FunctionMasterServiceImpl implements FunctionMasterService {

	@Autowired
    private FunctionMasterRepository functionMasterRepository;

    @Override
    public FunctionMaster save(FunctionMaster functionMaster) {
        return functionMasterRepository.save(functionMaster);
    }

    @Override
    public List<FunctionMaster> findAll() {
        return functionMasterRepository.findAll();
    }

    @Override
    public List<FunctionMaster> findByStatus(String status) {
        return functionMasterRepository.findByStatus(status);
    }

    @Override
    public List<FunctionMaster> getActiveFunctions() {
        return functionMasterRepository.findByStatus("A");
    }

}
