package com.pw.eiti.wedt.conditions;

import com.pw.eiti.wedt.model.Document;
import com.pw.eiti.wedt.model.Sentence;

public interface ConditionChecker {
    boolean checkCondition(Sentence sentence, Document document);
}
