package com.pw.eiti.wedt.conditions;

import com.pw.eiti.wedt.model.Document;
import com.pw.eiti.wedt.model.Sentence;

import java.util.Arrays;
import java.util.List;

public class LinkingWordsConditionChecker implements ConditionChecker {
    private static final List<String> LINKING_WORDS = Arrays.asList(
            "Although",
            "As a consequence",
            "As a result",
            "As we have seen",
            "At the same time",
            "Accordingly",
            "By the same token",
            "By the same token",
            "Consequently",
            "Correspondingly",
            "Correspondingly",
            "Despite this",
            "Evidently",
            "Evidently",
            "However",
            "Indeed",
            "Indeed",
            "Subsequently",
            "Similarly",
            "Therefore",
            "Thus",
            "Undoubtedly"
    );

    @Override
    public boolean checkCondition(Sentence sentence, Document document) {
        return LINKING_WORDS.stream()
                .anyMatch(lw -> sentence.getText().startsWith(lw));
    }
}
