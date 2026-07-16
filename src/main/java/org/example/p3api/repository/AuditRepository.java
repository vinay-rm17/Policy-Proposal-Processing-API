package org.example.p3api.repository;

import org.example.p3api.model.Audit;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Repository
public class AuditRepository {
   private final List<Audit> auditList= Collections.synchronizedList(new ArrayList<>());
    public void save(Audit audit) {
        auditList.add(audit);
    }

    public List<Audit> findAll() {
        return auditList;
    }
}
