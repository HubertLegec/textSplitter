package com.pw.eiti.wedt;

import com.pw.eiti.wedt.model.DocSentence;

public interface ParagraphDetector {
    boolean startsNewParagraph(DocSentence sentence);
}
