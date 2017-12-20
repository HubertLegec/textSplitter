package com.pw.eiti.wedt.conditions;

import com.pw.eiti.wedt.model.Document;
import com.pw.eiti.wedt.model.DocSentence;

/**
 * Checks if given sentence is the first sentence in document
 */
public class FirstSentenceChecker implements ConditionChecker {
    @Override
    public boolean checkCondition(DocSentence sentence, Document document) {
        return document.getMinSentenceId()
                .map(id -> sentence.getId() == id)
                .orElse(false);
    }
}
