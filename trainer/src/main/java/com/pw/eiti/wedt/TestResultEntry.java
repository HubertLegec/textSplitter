package com.pw.eiti.wedt;

import com.pw.eiti.wedt.utils.ConversionUtils;

public class TestResultEntry {
    private boolean ideal;
    private boolean actual;

    public TestResultEntry(double ideal, double actual) {
        this.ideal = ConversionUtils.doubleToBoolean(ideal);
        this.actual = actual > 0.5;
    }

    public boolean isIdeal() {
        return ideal;
    }

    public boolean isActual() {
        return actual;
    }
}
