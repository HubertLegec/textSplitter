package com.pw.eiti.wedt;

import com.pw.eiti.wedt.conditions.SentenceConditionsMapper;
import com.pw.eiti.wedt.data.DataSetProvider;
import com.pw.eiti.wedt.detector.CustomParagraphDetector;
import com.pw.eiti.wedt.detector.ParagraphDetector;
import com.pw.eiti.wedt.detector.PerceptronParagraphDetector;
import com.pw.eiti.wedt.model.SentenceRepresentation;
import com.pw.eiti.wedt.network.NetworkProvider;
import org.apache.commons.lang3.tuple.Pair;
import org.encog.neural.networks.BasicNetwork;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import static java.util.stream.Collectors.toList;

/**
 * Paragraph detection methods validator.
 * It can check results of neural network and custom detection method.
 * Methods are validated on given test set.
 */
public class Validator {
    private static final Logger log = LoggerFactory.getLogger(Validator.class);
    private final DataSetProvider dataSetProvider;
    private final ParagraphDetector networkParagraphDetector;
    private final ParagraphDetector customParagraphDetector;
    private TestResultStatistics networkResult;
    private TestResultStatistics customResult;

    /**
     * Main constructor
     * @param testDir directory with test files
     * @param modelFile optional argument. Should be provided when network model is validated.
     */
    public Validator(String testDir, String modelFile) {
        Path testDirPath = Paths.get(testDir);
        dataSetProvider = new DataSetProvider(testDirPath);
        if (modelFile != null) {
            Path modelPath = Paths.get(modelFile);
            BasicNetwork network = NetworkProvider.restoreSavedNetwork(modelPath.toFile().getAbsolutePath());
            networkParagraphDetector = new PerceptronParagraphDetector(network, new SentenceConditionsMapper());
        } else {
            networkParagraphDetector = null;
        }
        customParagraphDetector = new CustomParagraphDetector(new SentenceConditionsMapper());
    }

    /**
     * Validates network model and custom method on given test set
     * @throws IOException when given data set is not valid
     */
    public void validate() throws IOException {
        log.info("Validation start...");
        List<Pair<SentenceRepresentation, Boolean>> dataSet = dataSetProvider.prepareTestDataSet();
        if (networkParagraphDetector != null) {
            List<TestResultEntry> nResult = dataSet.stream()
                    .map(p -> dataPairToResultEntry(p, networkParagraphDetector))
                    .collect(toList());
            networkResult = new TestResultStatistics(nResult);
        }
        List<TestResultEntry> cResult = dataSet.stream()
                .map(p -> dataPairToResultEntry(p, customParagraphDetector))
                .collect(toList());
        customResult = new TestResultStatistics(cResult);
    }

    private TestResultEntry dataPairToResultEntry(Pair<SentenceRepresentation, Boolean> p, ParagraphDetector detector) {
        boolean startsNew = detector.startNewParagraph(p.getKey());
        return new TestResultEntry(
                p.getValue(),
                startsNew
        );
    }

    public String getNetworkResult() {
        return "---- Trained model statistics ---\n" + networkResult + "---------------------------------\n";
    }

    public String getCustomResult() {
        return "------- Custom statistics -------\n" + customResult + "---------------------------------\n";
    }
}
