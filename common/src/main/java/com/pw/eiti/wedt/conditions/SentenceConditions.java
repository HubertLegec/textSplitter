package com.pw.eiti.wedt.conditions;

import com.pw.eiti.wedt.model.SentenceRepresentation;
import com.pw.eiti.wedt.utils.ConversionUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.encog.ml.data.MLData;
import org.encog.ml.data.basic.BasicMLData;

import java.util.stream.Stream;

public class SentenceConditions implements SentenceRepresentation {
    public boolean isEmptyPreviousRow;
    public boolean isFirstSentence;
    public boolean hasLinkingWords;
    public boolean isListItem;
    public boolean hasPrecedingTab;
    public boolean hasShorterPreviousRow;
    public boolean startsNewLine;

    @Override
    public MLData toMLData() {
        Double[] conditionsList = Stream.of(
                isEmptyPreviousRow, isFirstSentence, hasLinkingWords, isListItem,
                hasPrecedingTab, hasShorterPreviousRow, startsNewLine
        ).map(ConversionUtils::booleanToDouble)
                .toArray(Double[]::new);
        return new BasicMLData(ArrayUtils.toPrimitive(conditionsList));
    }
}
