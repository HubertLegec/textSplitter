package com.pw.eiti.wedt;


/**
 * This class represents single test/validation entry.
 * It consist of ideal and actual value.
 */
public class TestResultEntry {
    private boolean ideal;
    private boolean actual;

    TestResultEntry(boolean ideal, boolean actual) {
        this.ideal = ideal;
        this.actual = actual;
    }

    public boolean isIdeal() {
        return ideal;
    }

    public boolean isActual() {
        return actual;
    }
}
