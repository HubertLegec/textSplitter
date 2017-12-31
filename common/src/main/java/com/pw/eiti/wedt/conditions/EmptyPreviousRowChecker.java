package com.pw.eiti.wedt.conditions;

import com.pw.eiti.wedt.model.DocSentence;
import org.apache.commons.lang3.StringUtils;

/**
 * Checks if row above given sentence is empty
 */
class EmptyPreviousRowChecker implements ConditionChecker {
    @Override
    public boolean checkCondition(DocSentence sentence) {
        if (sentence.getId() == 0) {
            return true;
        }
        String text = sentence.getPredecessor();
        return StringUtils.contains(text, "\n\n");
    }
}
