package com.pw.eiti.wedt.conditions;

import com.pw.eiti.wedt.model.DocSentence;

interface ConditionChecker {
    boolean checkCondition(DocSentence sentence);
}
