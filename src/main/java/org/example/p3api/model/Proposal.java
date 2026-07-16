package org.example.p3api.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Proposal {
    private Long proposalId;
    private Long customerId;
    private Integer policyTerm;
    private Double sumAssured;
    private Double annualPremium;
    private String nomineeName;
    private String paymentFrequency;
    private ProposalStatus status;
    private String policyNumber;

}
