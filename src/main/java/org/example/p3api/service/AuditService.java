package org.example.p3api.service;

import org.example.p3api.model.Audit;
import org.example.p3api.repository.AuditRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuditService {
    private final AuditRepository auditRepository;
    public AuditService(AuditRepository auditRepository)
    {
        this.auditRepository=auditRepository;
    }
    public List<Audit> getAllAudits()
    {
        return auditRepository.findAll();
    }
}
