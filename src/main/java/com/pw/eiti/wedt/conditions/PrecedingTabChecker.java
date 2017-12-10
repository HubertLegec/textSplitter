package com.pw.eiti.wedt.conditions;

import com.pw.eiti.wedt.model.DocSentence;
import com.pw.eiti.wedt.model.Document;

/**
 * Checks if given sentence is preceded by tab
 */
public class PrecedingTabChecker implements ConditionChecker {
    private static String TAB_CHARACTER = "\t";
    @Override
    public boolean checkCondition(DocSentence sentence, Document document) {
        String predecessor = sentence.getPredecessor();
        return predecessor.endsWith(TAB_CHARACTER);
    }
}
