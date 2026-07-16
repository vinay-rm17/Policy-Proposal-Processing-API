package org.example.p3api.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Audit {

    private Long auditId;

    private Long proposalId;

    private String action;

    private String description;

    private LocalDateTime timestamp;
}
