package com.pw.eiti.wedt;

import java.util.List;

public class TestResultStatistics {
    private final List<TestResultEntry> entries;

    public TestResultStatistics(List<TestResultEntry> entries) {
        this.entries = entries;
    }

    public int getDataSize() {
        return entries.size();
    }

    public long getFalsePositives() {
        return entries.stream()
                .filter(e -> e.isActual() && !e.isIdeal())
                .count();
    }

    public long getTruePositives() {
        return entries.stream()
                .filter(e -> e.isActual() && e.isIdeal())
                .count();
    }

    public long getFalseNegatives() {
        return entries.stream()
                .filter(e -> !e.isActual() && e.isIdeal())
                .count();
    }

    public long getTrueNegatives() {
        return entries.stream()
                .filter(e -> !e.isActual() && !e.isIdeal())
                .count();
    }

    public double getPrecision() {
        long tp = getTruePositives();
        long fp = getFalsePositives();
        return ((double) tp) / (tp + fp);
    }

    public double getRecall() {
        long tp = getTruePositives();
        long fn = getFalseNegatives();
        return ((double) tp) / (tp + fn);
    }

    @Override
    public String toString() {
        return String.format(
                "Data size: %d\nPrecision: %s\nRecall: %s\n",
                getDataSize(), getPrecision(), getRecall()
        );
    }
}
