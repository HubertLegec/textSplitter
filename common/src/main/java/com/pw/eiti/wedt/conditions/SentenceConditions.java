package com.pw.eiti.wedt.conditions;

import com.pw.eiti.wedt.model.SentenceRepresentation;
import com.pw.eiti.wedt.utils.ConversionUtils;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class SentenceConditions implements SentenceRepresentation {
    public boolean isEmptyPreviousRow;
    public boolean isFirstSentence;
    //public boolean hasLinkingWords;
    //public boolean isListItem;
    public boolean hasPrecedingTab;
    public boolean hasShorterPreviousRow;
    public boolean startsNewLine;
    public boolean notFirstSentenceInLine;

    @Override
    public double[] toMLData() {
        List<Double> conditionsList = Stream.of(
                isEmptyPreviousRow, isFirstSentence, hasPrecedingTab,
                hasShorterPreviousRow, startsNewLine, notFirstSentenceInLine
        ).map(ConversionUtils::booleanToDouble)
                .collect(Collectors.toList());
        return ConversionUtils.doubleListToArray(conditionsList);
    }
}
