package tech.csm.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import tech.csm.model.FunctionMaster;
import tech.csm.model.GlobalLink;
import tech.csm.model.PrimaryLink;
import tech.csm.service.FunctionMasterService;
import tech.csm.service.GlobalLinkService;
import tech.csm.service.PrimaryLinkService;

@RestController
@RequestMapping("/primary")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")

public class PrimaryLinkController {

	@Autowired
    private PrimaryLinkService primaryLinkService;
	
	@Autowired
	private GlobalLinkService globalLinkService;
	
	@Autowired
	private FunctionMasterService functionMasterService;

    @PostMapping("/save")
    public PrimaryLink save(@RequestBody PrimaryLink primaryLink) {
        return primaryLinkService.save(primaryLink);
    }

    @GetMapping("/all")
    public List<PrimaryLink> getAll() {
        return primaryLinkService.findAll();
    }
    
 
//    @GetMapping("/dropdown/globals")
//    public List<GlobalLink> getGlobalDropdown() {
//        return globalLinkService.findByStatus("A");
//    }
//    
    
    @GetMapping("/dropdown/globals")
    public ResponseEntity<List<GlobalLink>> getActiveGlobals() {
        return ResponseEntity.ok(globalLinkService.getActiveGlobals());
    }

    

   
    @GetMapping("/dropdown/functions")
    public List<FunctionMaster> getFunctionDropdown() {
        return functionMasterService.findByStatus("A");
    }
}
