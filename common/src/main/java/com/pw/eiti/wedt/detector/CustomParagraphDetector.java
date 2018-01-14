package com.pw.eiti.wedt.detector;

import com.pw.eiti.wedt.conditions.SentenceConditions;
import com.pw.eiti.wedt.conditions.SentenceConditionsMapper;
import com.pw.eiti.wedt.model.DocSentence;
import com.pw.eiti.wedt.model.SentenceRepresentation;

public class CustomParagraphDetector implements ParagraphDetector {
    private final SentenceConditionsMapper sentenceMapper;

    public CustomParagraphDetector(SentenceConditionsMapper sentenceMapper) {
        this.sentenceMapper = sentenceMapper;
    }

    @Override
    public boolean startsNewParagraph(DocSentence sentence) {
        SentenceConditions representation = sentenceMapper.getSentenceRepresentation(sentence);
        return startNewParagraph(representation);
    }

    @Override
    public boolean startNewParagraph(SentenceRepresentation sentenceRepresentation) {
        SentenceConditions representation = (SentenceConditions) sentenceRepresentation;
        if (representation.isFirstSentence) {
            return true;
        }
        if (representation.notFirstSentenceInLine) {
            return false;
        }
        if (representation.isEmptyPreviousRow) {
            return true;
        }
        if (representation.isListItem) {
            return false;
        }
        if (representation.hasPrecedingTab && representation.startsNewLine && representation.hasShorterPreviousRow) {
            return true;
        }
        return false;
    }
}
