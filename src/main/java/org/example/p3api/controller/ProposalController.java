package org.example.p3api.controller;

import org.example.p3api.model.Proposal;
import org.example.p3api.service.ProposalService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/proposals")
public class ProposalController{
    private final ProposalService proposalService;
    public ProposalController(ProposalService proposalService)
    {
        this.proposalService=proposalService;
    }
    @PostMapping
    public Proposal createProposal(@RequestBody Proposal proposal)
    {
        return proposalService.createProposal(proposal);
    }
    @GetMapping("/{id}")
    public Proposal getProposal(@PathVariable Long id)
    {
        return proposalService.getProposalById(id);
    }
    @PostMapping("/{id}/submit")
    public Proposal submitProposal(@PathVariable Long id)
    {
        return proposalService.submitProposal(id);
    }

}
