package com.pw.eiti.wedt.conditions;

import com.pw.eiti.wedt.model.DocSentence;
import com.pw.eiti.wedt.model.Document;
import org.apache.commons.lang3.StringUtils;

public class StartsNewLineChecker implements ConditionChecker {
    @Override
    public boolean checkCondition(DocSentence sentence, Document document) {
        String text = sentence.getPredecessor().getContent();
        return StringUtils.endsWith(StringUtils.replace(text, " ", ""), "\n");
    }
}
