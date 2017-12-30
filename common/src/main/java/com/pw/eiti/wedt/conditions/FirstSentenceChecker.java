package com.pw.eiti.wedt.conditions;

import com.pw.eiti.wedt.model.DocSentence;

/**
 * Checks if given sentence is the first sentence in document
 */
class FirstSentenceChecker implements ConditionChecker {
    @Override
    public boolean checkCondition(DocSentence sentence) {
        return sentence.getDocument().getMinSentenceId()
                .map(id -> sentence.getId() == id)
                .orElse(false);
    }
}
