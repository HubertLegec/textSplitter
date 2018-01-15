package com.pw.eiti.wedt.detector;

import com.pw.eiti.wedt.model.DocSentence;
import com.pw.eiti.wedt.model.SentenceRepresentation;

/**
 * Interface that all implementations of paragraph detector should implements
 */
public interface ParagraphDetector {
    /**
     * Checks if sentence starts new paragraph based on sentence representation.
     * @param sentenceRepresentation representation of a sentence
     * @return {@code true} if sentence starts new paragraph, {@code false} otherwise
     */
    boolean startNewParagraph(SentenceRepresentation sentenceRepresentation);

    /**
     * Checks if sentence starts new paragraph based on sentence representation.
     * @param sentence single document sentence
     * @return {@code true} if sentence starts new paragraph, {@code false} otherwise
     */
    boolean startsNewParagraph(DocSentence sentence);
}
