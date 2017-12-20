package com.pw.eiti.wedt.conditions;

import com.pw.eiti.wedt.model.DocSentence;
import com.pw.eiti.wedt.model.Document;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ListItemChecker implements ConditionChecker {
    private static final String patternString = "(\\s)*((\\d+\\.)|[\\-*])(\\s)*[A-Za-z].*";
    private static final Pattern pattern = Pattern.compile(patternString);

    @Override
    public boolean checkCondition(DocSentence sentence, Document document) {
        String text = sentence.getText();
        Matcher matcher = pattern.matcher(text);
        return matcher.matches();
    }
}
