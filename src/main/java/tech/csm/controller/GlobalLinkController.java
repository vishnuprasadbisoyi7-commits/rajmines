package tech.csm.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import tech.csm.model.GlobalLink;
import tech.csm.service.GlobalLinkService;

@RestController
@RequestMapping("/global")
@RequiredArgsConstructor
public class GlobalLinkController {

	@Autowired
    private GlobalLinkService globalLinkService;

    @PostMapping("/save")
    public GlobalLink save(@RequestBody GlobalLink globalLink) {
        return globalLinkService.save(globalLink);
    }

    @GetMapping("/list")
    public List<GlobalLink> getAll() {
        return globalLinkService.findAll();
    }
}
