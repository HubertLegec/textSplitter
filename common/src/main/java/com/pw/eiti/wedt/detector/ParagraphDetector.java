package com.pw.eiti.wedt.detector;

import com.pw.eiti.wedt.model.DocSentence;

public interface ParagraphDetector {
    boolean startsNewParagraph(DocSentence sentence);
}
