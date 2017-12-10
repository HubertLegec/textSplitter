package com.pw.eiti.wedt.conditions;

import com.pw.eiti.wedt.model.DocSentence;
import com.pw.eiti.wedt.model.Document;

public class ShorterPreviousRowChecker implements ConditionChecker {
    @Override
    public boolean checkCondition(DocSentence sentence, Document document) {
        int startRow = sentence.getStartRow();
        if (startRow < 2) {
            return false;
        }
        String prevRow = document.getLine(startRow - 1);
        String rowAbove = document.getLine(startRow - 2);
        return prevRow.length() < rowAbove.length();
    }
}
