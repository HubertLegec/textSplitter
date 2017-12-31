package com.pw.eiti.wedt.conditions;

import com.pw.eiti.wedt.model.DocSentence;
import com.pw.eiti.wedt.model.Document;

import java.util.Comparator;

public class NotFirstSentenceInLineChecker implements ConditionChecker {
    @Override
    public boolean checkCondition(DocSentence sentence) {
        String predecessor = sentence.getPredecessor();
        if (predecessor.contains("\n") || sentence.getId() == 0) {
            return false;
        }
        Document document = sentence.getDocument();
        DocSentence prevSentence = document.getSentences().stream()
                .filter(s -> s.getId() < sentence.getId())
                .max(Comparator.comparingInt(DocSentence::getId))
                .orElseThrow(() -> new RuntimeException("Unexpected error - can't find previous sentence"));
        return sentence.getStartRow() == prevSentence.getEndRow();
    }
}
