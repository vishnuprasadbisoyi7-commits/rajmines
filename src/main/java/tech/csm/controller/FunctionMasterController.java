package tech.csm.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import tech.csm.model.FunctionMaster;
import tech.csm.service.FunctionMasterService;


@RestController
@RequestMapping("/function")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
public class FunctionMasterController {

	@Autowired
    private FunctionMasterService functionMasterService;

    @PostMapping("/save")
    public FunctionMaster save(@RequestBody FunctionMaster functionMaster) {
        return functionMasterService.save(functionMaster);
    }

    @GetMapping("/all")
    public List<FunctionMaster> getAll() {
        return functionMasterService.findAll();
    }
}

