package com.pw.eiti.wedt.detector;

import com.pw.eiti.wedt.model.DocSentence;
import com.pw.eiti.wedt.model.SentenceMapper;
import com.pw.eiti.wedt.model.SentenceRepresentation;
import org.encog.ml.data.MLData;
import org.encog.ml.data.basic.BasicMLData;
import org.encog.neural.networks.BasicNetwork;

public class PerceptronParagraphDetector implements ParagraphDetector {
    private BasicNetwork network;
    private SentenceMapper mapper;

    public PerceptronParagraphDetector(BasicNetwork network, SentenceMapper mapper) {
        this.mapper = mapper;
        this.network = network;
    }

    @Override
    public boolean startsNewParagraph(DocSentence sentence) {
        SentenceRepresentation sentenceRepresentation = mapper.getSentenceRepresentation(sentence);
        return startNewParagraph(sentenceRepresentation);
    }

    @Override
    public boolean startNewParagraph(SentenceRepresentation sentenceRepresentation) {
        MLData data = new BasicMLData(sentenceRepresentation.toMLData());
        double result = network.compute(data).getData(0);
        return result > 0.5;
    }
}
