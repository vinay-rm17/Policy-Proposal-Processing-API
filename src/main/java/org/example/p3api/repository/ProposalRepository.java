package org.example.p3api.repository;

import org.example.p3api.model.Proposal;
import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
@Repository
public class ProposalRepository {
    private final Map<Long,Proposal> proposalMap=new ConcurrentHashMap<>();
    public void save(Proposal proposal) {
        proposalMap.put(proposal.getProposalId(),proposal);
    }

    public Proposal findById(Long id) {
        return proposalMap.get(id);
    }
}
