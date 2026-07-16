package org.example.p3api.service;

import org.example.p3api.model.Audit;
import org.example.p3api.model.Customer;
import org.example.p3api.model.Proposal;
import org.example.p3api.model.ProposalStatus;
import org.example.p3api.repository.AuditRepository;
import org.example.p3api.repository.CustomerRepository;
import org.example.p3api.repository.ProposalRepository;
import org.springframework.stereotype.Service;

import javax.management.RuntimeMBeanException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class ProposalService {
    private final CustomerRepository customerRepository;
    private final ProposalRepository proposalRepository;
    private final AuditRepository auditRepository;
    private final AtomicLong auditIdGenerator=new AtomicLong(1);
    private final AtomicLong proposalIdGenerator = new AtomicLong(1000);
    public ProposalService(CustomerRepository customerRepository,ProposalRepository proposalRepository,AuditRepository auditRepository)
    {
        this.customerRepository=customerRepository;
        this.proposalRepository=proposalRepository;
        this.auditRepository=auditRepository;


    }
    public Proposal createProposal(Proposal proposal) {
        Customer customer=customerRepository.findById(proposal.getCustomerId());
        if(customer==null)
        {
            throw new RuntimeException("Customer not found");
        }
        validateProposal(proposal,customer);
        proposal.setProposalId(proposalIdGenerator.incrementAndGet());
        proposal.setStatus(ProposalStatus.CREATED);
        proposalRepository.save(proposal);
        return proposal;

    }
    public void validateProposal(Proposal proposal,Customer customer)
    {
        List<Integer> validTerms=List.of(10,15,20,25,30);
        if(!validTerms.contains(proposal.getPolicyTerm()))
        {
            throw new RuntimeException("Invalid policy term");
        }
        if(proposal.getSumAssured()<100000 || proposal.getSumAssured()>500000)
        {
            throw new RuntimeException("Invalid sum assured");
        }
        if(proposal.getAnnualPremium()<5000)
        {
            throw new RuntimeException("Minimum premium is 500");

        }
        if(proposal.getAnnualPremium()>50000 && (customer.getPanNumber()==null || customer.getPanNumber().isBlank()))
        {
            throw new RuntimeException("PAN is mandatory");
        }
        if(proposal.getNomineeName().equalsIgnoreCase(customer.getFirstName()))
        {
            throw new RuntimeException("Nominee cannot be the customer");
        }
        List<String> frequencies=List.of("MONTHLY", "QUARTERLY", "HALF_YEARLY", "YEARLY");
        if(!frequencies.contains(proposal.getPaymentFrequency().toUpperCase()))
        {
            throw  new RuntimeException("Invalid payment frequency");
        }


    }

    public Proposal getProposalById(Long id) {
        Proposal proposal=proposalRepository.findById(id);
        if(proposal==null)
        {
            throw new RuntimeException("Proposal not found");

        }
        return proposal;
    }

    public Proposal submitProposal(Long proposalId) {
        Proposal proposal=proposalRepository.findById(proposalId);
        if(proposal==null)
        {
            throw  new RuntimeException("Proposal not found");
        }
        Customer customer=customerRepository.findById(proposal.getCustomerId());
        validateProposal(proposal,customer);
        proposal.setPolicyNumber("POL"+System.currentTimeMillis());
        proposal.setStatus(ProposalStatus.SUBMITTED);
        proposalRepository.save(proposal);
        Audit audit=new Audit();
        audit.setAuditId(auditIdGenerator.incrementAndGet());
        audit.setProposalId(proposal.getProposalId());
        audit.setAction("SUBMIT_PROPOSAL");
        audit.setDescription("Proposal submitted successfully");
        audit.setTimestamp(LocalDateTime.now());

        auditRepository.save(audit);

        return proposal;
    }
}
