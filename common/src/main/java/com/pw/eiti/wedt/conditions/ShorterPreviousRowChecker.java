package com.pw.eiti.wedt.conditions;

import com.pw.eiti.wedt.model.DocSentence;
import com.pw.eiti.wedt.model.Document;

class ShorterPreviousRowChecker implements ConditionChecker {
    @Override
    public boolean checkCondition(DocSentence sentence) {
        int startRow = sentence.getStartRow();
        if (startRow < 2) {
            return false;
        }
        Document document = sentence.getDocument();
        String prevRow = document.getLine(startRow - 1);
        String rowAbove = document.getLine(startRow - 2);
        return prevRow.length() < rowAbove.length();
    }
}
