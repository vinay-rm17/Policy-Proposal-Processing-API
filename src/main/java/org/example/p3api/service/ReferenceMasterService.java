package org.example.p3api.service;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class ReferenceMasterService {
    private final Map<String, List<String>> referenceData = Map.of(
            "POLICY_TERM", List.of("10", "15", "20", "25", "30"),
            "PAYMENT_FREQUENCY", List.of("MONTHLY", "QUARTERLY", "HALF_YEARLY", "YEARLY")
    );
    public List<String> getReferenceData(String category) {
        List<String> data=referenceData.get(category.toUpperCase());
        if(data==null)
        {
            throw new RuntimeException("Invalid reference category");

        }
        return data;
    }
}
