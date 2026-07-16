package org.example.p3api.model;

public enum PolicyTerm {
    TEN(10),

    FIFTEEN(15),

    TWENTY(20),

    TWENTY_FIVE(25),

    THIRTY(30);

    private final int years;

    PolicyTerm(int years) {
        this.years = years;
    }

    public int getYears() {
        return years;
    }
}
