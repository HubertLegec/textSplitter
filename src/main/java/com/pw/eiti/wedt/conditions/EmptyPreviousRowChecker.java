package com.pw.eiti.wedt.conditions;

import com.pw.eiti.wedt.model.Document;
import com.pw.eiti.wedt.model.DocSentence;

/**
 * Checks if row above given sentence is empty
 */
public class EmptyPreviousRowChecker implements ConditionChecker {
    @Override
    public boolean checkCondition(DocSentence sentence, Document document) {
        return sentence.getPredecessor().containsEmptyRow();
    }
}
