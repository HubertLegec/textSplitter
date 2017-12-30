package com.pw.eiti.wedt.conditions;

import com.pw.eiti.wedt.model.DocSentence;

/**
 * Checks if given sentence is preceded by tab
 */
class PrecedingTabChecker implements ConditionChecker {
    private static String TAB_CHARACTER = "\t";
    @Override
    public boolean checkCondition(DocSentence sentence) {
        String predecessor = sentence.getPredecessor();
        return predecessor.endsWith(TAB_CHARACTER);
    }
}
