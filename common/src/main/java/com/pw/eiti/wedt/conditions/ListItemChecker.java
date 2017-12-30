package com.pw.eiti.wedt.conditions;

import com.pw.eiti.wedt.model.DocSentence;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

class ListItemChecker implements ConditionChecker {
    private static final String patternString = "(\\s)*((\\d+\\.)|[\\-*])(\\s)*[A-Za-z].*";
    private static final Pattern pattern = Pattern.compile(patternString);

    @Override
    public boolean checkCondition(DocSentence sentence) {
        String text = sentence.getText();
        Matcher matcher = pattern.matcher(text);
        return matcher.matches();
    }
}
