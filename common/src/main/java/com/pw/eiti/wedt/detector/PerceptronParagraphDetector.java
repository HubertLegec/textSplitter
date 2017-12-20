package com.pw.eiti.wedt.network;

import com.pw.eiti.wedt.ParagraphDetector;
import com.pw.eiti.wedt.model.DocSentence;
import org.encog.neural.networks.BasicNetwork;
import org.encog.persist.EncogDirectoryPersistence;

import java.io.File;

public class PerceptronParagraphDetector implements ParagraphDetector {
    private BasicNetwork network;

    PerceptronParagraphDetector(BasicNetwork network) {
        this.network = network;
    }

    @Override
    public boolean startsNewParagraph(DocSentence sentence) {
        return false;
    }

    public void saveToFile(String modelPath) {
        EncogDirectoryPersistence.saveObject(new File(modelPath), this);
    }

    public static PerceptronParagraphDetector restoreFromSavedModel(String modelPath) {
        BasicNetwork savedNet = (BasicNetwork) EncogDirectoryPersistence.loadObject(new File(modelPath));
        return new PerceptronParagraphDetector(savedNet);
    }
}
