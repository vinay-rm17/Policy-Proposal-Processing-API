package org.example.p3api.service;

import org.example.p3api.model.Customer;
import org.example.p3api.model.Proposal;
import org.example.p3api.model.ProposalStatus;
import org.example.p3api.repository.AuditRepository;
import org.example.p3api.repository.CustomerRepository;
import org.example.p3api.repository.ProposalRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ProposalServiceTest {

    private ProposalRepository proposalRepository;
    private CustomerRepository customerRepository;
    private AuditRepository auditRepository;

    private ProposalService proposalService;

    @BeforeEach
    void setup() {

        proposalRepository = new ProposalRepository();
        customerRepository = new CustomerRepository();
        auditRepository = new AuditRepository();

        // Correct order
        proposalService = new ProposalService(
                customerRepository,
                proposalRepository,
                auditRepository
        );

        Customer customer = new Customer();
        customer.setCustomerId(1001L);
        customer.setFirstName("Vinay");
        customer.setLastName("RM");
        customer.setAge(23);
        customer.setPanNumber("ABCDE1234F");

        customerRepository.save(customer);
    }

    @Test
    void shouldCreateProposalSuccessfully() {

        Proposal proposal = new Proposal();

        proposal.setCustomerId(1001L);
        proposal.setPolicyTerm(20);
        proposal.setSumAssured(500000.0);
        proposal.setAnnualPremium(25000.0);
        proposal.setNomineeName("Ramesh");
        proposal.setPaymentFrequency("YEARLY");

        Proposal saved = proposalService.createProposal(proposal);

        assertNotNull(saved.getProposalId());
        assertEquals(ProposalStatus.CREATED, saved.getStatus());
    }

    @Test
    void shouldSubmitProposalSuccessfully() {

        Proposal proposal = new Proposal();

        proposal.setCustomerId(1001L);
        proposal.setPolicyTerm(20);
        proposal.setSumAssured(500000.0);
        proposal.setAnnualPremium(25000.0);
        proposal.setNomineeName("Ramesh");
        proposal.setPaymentFrequency("YEARLY");

        Proposal saved = proposalService.createProposal(proposal);

        Proposal submitted =
                proposalService.submitProposal(saved.getProposalId());

        assertEquals(ProposalStatus.SUBMITTED, submitted.getStatus());

        assertNotNull(submitted.getPolicyNumber());
    }
}