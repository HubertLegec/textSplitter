package com.pw.eiti.wedt.conditions;

import com.pw.eiti.wedt.model.DocSentence;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Checks if given sentence is preceded by tab
 */
class PrecedingTabChecker implements ConditionChecker {
    private static String PATTERN_STRING = "((^)|([\\S\n]*))((\\x09)|( {4}))";
    private static final Pattern pattern = Pattern.compile(PATTERN_STRING);

    @Override
    public boolean checkCondition(DocSentence sentence) {
        String predecessor = sentence.getPredecessor();
        Matcher matcher = pattern.matcher(predecessor);
        return matcher.matches();
    }
}
