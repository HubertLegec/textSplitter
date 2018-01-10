package com.pw.eiti.wedt.detector;

import com.pw.eiti.wedt.model.DocSentence;
import com.pw.eiti.wedt.model.SentenceRepresentation;

public interface ParagraphDetector {
    boolean startNewParagraph(SentenceRepresentation sentenceRepresentation);
    boolean startsNewParagraph(DocSentence sentence);
}
