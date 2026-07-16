package org.example.p3api.controller;

import org.example.p3api.service.ReferenceMasterService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/reference-master")
public class ReferenceMasterController {
    private final ReferenceMasterService referenceMasterService;
    public ReferenceMasterController(ReferenceMasterService referenceMasterService)
    {
        this.referenceMasterService=referenceMasterService;
    }
    @GetMapping("/{category}")
    public List<String> getReferenceData(@PathVariable String category)
    {
        return referenceMasterService.getReferenceData(category);
    }
}
