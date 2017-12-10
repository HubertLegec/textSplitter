package com.pw.eiti.wedt;

import com.pw.eiti.wedt.model.DocSentence;

public class PerceptronParagraphDetector implements ParagraphDetector {
    @Override
    public boolean startsNewParagraph(DocSentence sentence) {
        return false;
    }
}
