package com.pw.eiti.wedt;

import java.util.List;

/**
 * Representation of validation result
 */
class TestResultStatistics {
    private final List<TestResultEntry> entries;

    TestResultStatistics(List<TestResultEntry> entries) {
        this.entries = entries;
    }

    int getDataSize() {
        return entries.size();
    }

    long getFalsePositives() {
        return entries.stream()
                .filter(e -> e.isActual() && !e.isIdeal())
                .count();
    }

    long getTruePositives() {
        return entries.stream()
                .filter(e -> e.isActual() && e.isIdeal())
                .count();
    }

    long getFalseNegatives() {
        return entries.stream()
                .filter(e -> !e.isActual() && e.isIdeal())
                .count();
    }

    long getTrueNegatives() {
        return entries.stream()
                .filter(e -> !e.isActual() && !e.isIdeal())
                .count();
    }

    double getPrecision() {
        long tp = getTruePositives();
        long fp = getFalsePositives();
        return ((double) tp) / (tp + fp);
    }

    double getRecall() {
        long tp = getTruePositives();
        long fn = getFalseNegatives();
        return ((double) tp) / (tp + fn);
    }

    long getExpectedNumberOfParagraphs() {
        return entries.stream()
                .filter(TestResultEntry::isIdeal)
                .count();
    }

    long getFoundNumberOfParagraphs() {
        return entries.stream()
                .filter(TestResultEntry::isActual)
                .count();
    }

    @Override
    public String toString() {
        return String.format(
                "Data size: %d\nExpected number of paragraphs: %d\nFound number of paragraphs: %d\nPrecision: %s\nRecall: %s\n",
                getDataSize(), getExpectedNumberOfParagraphs(), getFoundNumberOfParagraphs(), getPrecision(), getRecall()
        );
    }
}
