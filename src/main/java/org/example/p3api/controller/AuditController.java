package org.example.p3api.controller;

import org.example.p3api.model.Audit;
import org.example.p3api.service.AuditService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/audits")
public class AuditController {
    private final AuditService auditService;
    public AuditController(AuditService auditService)
    {
        this.auditService=auditService;
    }
    @GetMapping
    public List<Audit> getAllAudits()
    {
        return auditService.getAllAudits();
    }
}
