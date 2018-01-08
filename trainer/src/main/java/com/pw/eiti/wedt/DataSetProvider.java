package com.pw.eiti.wedt;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import com.pw.eiti.wedt.conditions.SentenceConditionsMapper;
import com.pw.eiti.wedt.model.DocSentence;
import com.pw.eiti.wedt.model.Document;
import com.pw.eiti.wedt.model.SentenceMapper;
import com.pw.eiti.wedt.model.SentenceRepresentation;
import com.pw.eiti.wedt.utils.ConversionUtils;
import com.pw.eiti.wedt.utils.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.encog.ml.data.MLDataSet;
import org.encog.ml.data.basic.BasicMLDataSet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

public class DataSetProvider {
    private static final String TXT_FILE_EXTENSION = ".txt";
    private static final String JSON_FILE_EXTENSION = ".json";
    private static final Logger log = LoggerFactory.getLogger(DataSetProvider.class);
    private final SentenceMapper mapper = new SentenceConditionsMapper();
    private final Path inputDir;

    DataSetProvider(Path inputDir) {
        this.inputDir = inputDir;
    }

    MLDataSet prepareDataSet() throws IOException {
        log.info("Prepare data set...");
        try (Stream<Path> stream = Files.list(inputDir)) {
            List<Pair<double[], double[]>> dataList = stream
                    .filter(Files::isRegularFile)
                    .filter(p -> StringUtils.endsWith(p.getFileName().toString(), TXT_FILE_EXTENSION))
                    .map(this::getFileSentences)
                    .flatMap(Collection::stream)
                    .map(DataSetProvider::createMLDataPair)
                    .collect(toList());
            double[][] inputs = dataListToArray(dataList, Pair::getKey);
            double[][] ideals = dataListToArray(dataList, Pair::getValue);
            return new BasicMLDataSet(inputs, ideals);
        }
    }

    List<Pair<SentenceRepresentation, Boolean>> getFileSentences(Path inputFile) {
        log.info("Get sentences from file: " + inputFile.getFileName());
        Document document = new Document(inputFile);
        String filename = inputFile.getFileName().toString();
        DocumentDescription documentDescription = getDocumentDescription(filename);
        int expectedSentSize = documentDescription.getSentencesCount();
        int sentSize = document.getSentences().size();
        if (sentSize != expectedSentSize) {
            throw new RuntimeException("Number of sentences doesn't match for file: " + filename + ", expected: " + expectedSentSize + ", but was: " + sentSize);
        }
        return document.getSentences().stream()
                .map(s -> mapSentenceToPair(s, documentDescription.getParagraphsStarts()))
                .collect(toList());
    }

    private Pair<SentenceRepresentation, Boolean> mapSentenceToPair(DocSentence s, List<Integer> ids) {
        return Pair.of(mapper.getSentenceRepresentation(s), ids.contains(s.getId()));
    }

    private DocumentDescription getDocumentDescription(String rawFileName) {
        String filename = rawFileName.replace(TXT_FILE_EXTENSION, JSON_FILE_EXTENSION);
        log.info("Get start paragraph sentence ids from file: " + filename);
        return FileUtils.findFileInDirectory(inputDir, filename)
                .map(DataSetProvider::mapDocDescriptionFileToObject)
                .map(s -> s.orElseThrow(() -> new RuntimeException("Can't read file: " + filename)))
                .orElseThrow(() -> new RuntimeException("Can't find file: " + filename));
    }

    private static double[][] dataListToArray(List<Pair<double[], double[]>> dataList,
                                              Function<? super Pair<double[], double[]>, ? extends double[]> mapper) {
        return ConversionUtils.doubleListOfArrayToArray(
                dataList.stream()
                        .map(mapper)
                        .collect(toList())
        );
    }

    private static Optional<DocumentDescription> mapDocDescriptionFileToObject(Path path) {
        Gson gson = new Gson();
        try {
            JsonReader reader = new JsonReader(new FileReader(path.toAbsolutePath().toString()));
            DocumentDescription description = gson.fromJson(reader, DocumentDescription.class);
            return Optional.of(description);
        } catch (FileNotFoundException e) {
            log.error(e.getMessage());
            return Optional.empty();
        }

    }

    private static Pair<double[], double[]> createMLDataPair(Pair<SentenceRepresentation, Boolean> pair) {
        double[] sentenceData = pair.getKey().toMLData();
        double resultDoubleRepresentation = ConversionUtils.booleanToDouble(pair.getValue());
        double[] resultData = new double[]{resultDoubleRepresentation};
        return Pair.of(sentenceData, resultData);
    }
}
