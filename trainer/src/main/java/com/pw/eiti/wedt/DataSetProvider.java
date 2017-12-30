package com.pw.eiti.wedt;

import com.pw.eiti.wedt.conditions.SentenceConditionsMapper;
import com.pw.eiti.wedt.model.DocSentence;
import com.pw.eiti.wedt.model.Document;
import com.pw.eiti.wedt.model.SentenceMapper;
import com.pw.eiti.wedt.model.SentenceRepresentation;
import com.pw.eiti.wedt.utils.ConversionUtils;
import com.pw.eiti.wedt.utils.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.encog.ml.data.MLData;
import org.encog.ml.data.MLDataPair;
import org.encog.ml.data.MLDataSet;
import org.encog.ml.data.basic.BasicMLData;
import org.encog.ml.data.basic.BasicMLDataPair;
import org.encog.ml.data.basic.BasicMLDataSet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collection;
import java.util.List;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

public class DataSetProvider {
    private static final Logger log = LoggerFactory.getLogger(DataSetProvider.class);
    private final SentenceMapper mapper = new SentenceConditionsMapper();
    private final Path inputDir;

    DataSetProvider(Path inputDir) {
        this.inputDir = inputDir;
    }

    MLDataSet prepareDataSet() throws IOException {
        log.info("Prepare data set...");
        try (Stream<Path> stream = Files.list(inputDir)) {
            List<Pair<SentenceRepresentation, Boolean>> sentences = stream
                    .filter(Files::isRegularFile)
                    .filter(p -> StringUtils.endsWith(p.getFileName().toString(), "_raw.txt"))
                    .map(this::getFileSentences)
                    .flatMap(Collection::stream)
                    .collect(toList());
            return new BasicMLDataSet(
                    sentences.stream()
                            .map(DataSetProvider::createMLDataPair)
                            .collect(toList())
            );
        }
    }

    private List<Pair<SentenceRepresentation, Boolean>> getFileSentences(Path inputFile) {
        log.info("Get sentences from file: " + inputFile.getFileName());
        Document document = new Document(inputFile);
        List<Integer> startParagraphIds = getStartParagraphIds(inputFile.getFileName().toString());
        return document.getSentences().stream()
                .map(s -> mapSentenceToPair(s, startParagraphIds))
                .collect(toList());
    }

    private Pair<SentenceRepresentation, Boolean> mapSentenceToPair(DocSentence s, List<Integer> ids) {
        return Pair.of(mapper.getSentenceRepresentation(s), ids.contains(s.getId()));
    }

    private List<Integer> getStartParagraphIds(String rawFileName) {
        String filename = rawFileName.replace("_raw.txt", "_paras.txt");
        return FileUtils.findFileInDirectory(inputDir, filename)
                .map(FileUtils::readFileAsString)
                .map(s -> s.orElse(""))
                .map(s -> ConversionUtils.stringToListOfInt(s, ","))
                .orElseThrow(() -> new RuntimeException("Can't find file: " + filename));
    }

    private static MLDataPair createMLDataPair(Pair<SentenceRepresentation, Boolean> pair) {
        MLData sentenceData = pair.getKey().toMLData();
        double resultDoubleRepresentation = ConversionUtils.booleanToDouble(pair.getValue());
        MLData resultData = new BasicMLData(new double[]{resultDoubleRepresentation});
        return new BasicMLDataPair(sentenceData, resultData);
    }
}
