package com.pw.eiti.wedt.conditions;

import com.pw.eiti.wedt.model.Document;
import com.pw.eiti.wedt.model.DocSentence;

public interface ConditionChecker {
    boolean checkCondition(DocSentence sentence, Document document);
}
