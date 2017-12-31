package com.pw.eiti.wedt.conditions;

import com.pw.eiti.wedt.model.DocSentence;
import com.pw.eiti.wedt.model.SentenceMapper;

public class SentenceConditionsMapper implements SentenceMapper {
    private final ConditionChecker emptyPreviousRowChecker = new EmptyPreviousRowChecker();
    private final ConditionChecker firstSentenceChecker = new FirstSentenceChecker();
    //private final ConditionChecker linkingWordsChecker = new LinkingWordsConditionChecker();
    //private final ConditionChecker listItemChecker = new ListItemChecker();
    private final ConditionChecker precedingTabChecker = new PrecedingTabChecker();
    private final ConditionChecker shorterPreviousRowChecker = new ShorterPreviousRowChecker();
    private final ConditionChecker startsNewLineChecker = new StartsNewLineChecker();
    private final ConditionChecker notFirstSentenceInLineChecker = new NotFirstSentenceInLineChecker();

    @Override
    public SentenceConditions getSentenceRepresentation(DocSentence sentence) {
        SentenceConditions conditions = new SentenceConditions();
        conditions.isEmptyPreviousRow = emptyPreviousRowChecker.checkCondition(sentence);
        conditions.isFirstSentence = firstSentenceChecker.checkCondition(sentence);
        //conditions.hasLinkingWords = linkingWordsChecker.checkCondition(sentence);
        //conditions.isListItem = listItemChecker.checkCondition(sentence);
        conditions.hasPrecedingTab = precedingTabChecker.checkCondition(sentence);
        conditions.hasShorterPreviousRow = shorterPreviousRowChecker.checkCondition(sentence);
        conditions.startsNewLine = startsNewLineChecker.checkCondition(sentence);
        conditions.notFirstSentenceInLine = notFirstSentenceInLineChecker.checkCondition(sentence);
        return conditions;
    }
}
