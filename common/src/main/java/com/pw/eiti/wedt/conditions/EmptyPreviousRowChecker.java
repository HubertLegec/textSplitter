package com.pw.eiti.wedt.conditions;

import com.pw.eiti.wedt.model.Document;
import com.pw.eiti.wedt.model.DocSentence;
import org.apache.commons.lang3.StringUtils;

/**
 * Checks if row above given sentence is empty
 */
public class EmptyPreviousRowChecker implements ConditionChecker {
    @Override
    public boolean checkCondition(DocSentence sentence, Document document) {
        String text = sentence.getPredecessor();
        return StringUtils.contains(text, "\n\n");
    }
}
