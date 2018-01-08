package com.pw.eiti.wedt;

import com.pw.eiti.wedt.network.NetworkProvider;
import org.encog.Encog;
import org.encog.ml.data.MLDataPair;
import org.encog.ml.data.MLDataSet;
import org.encog.neural.networks.BasicNetwork;
import org.encog.neural.networks.training.Train;
import org.encog.neural.networks.training.propagation.resilient.ResilientPropagation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.stream.StreamSupport;

import static java.util.stream.Collectors.toList;

public class ModelTrainer {
    private static final Logger log = LoggerFactory.getLogger(ModelTrainer.class);
    private final double errorThreshold;
    private final DataSetProvider dataSetProvider;
    private final DataSetProvider testDataSetProvider;
    private final BasicNetwork network;
    private Duration trainingTime = null;

    public ModelTrainer(String inputDir, String testDir, double errorThreshold) {
        this(inputDir, testDir, 8, errorThreshold);
    }

    public ModelTrainer(String inputDir, String testDir, int inputSize, double errorThreshold) {
        Path inputDirPath = Paths.get(inputDir);
        Path testDirPath = Paths.get(testDir);
        checkIsDirectory(inputDirPath);
        checkIsDirectory(testDirPath);
        this.dataSetProvider = new DataSetProvider(inputDirPath);
        this.testDataSetProvider = new DataSetProvider(testDirPath);
        this.network = NetworkProvider.createNetwork(inputSize);
        this.errorThreshold = errorThreshold;
    }

    public BasicNetwork train() throws IOException {
        final MLDataSet dataSet = dataSetProvider.prepareDataSet();
        final Train train = new ResilientPropagation(network, dataSet);
        int epoch = 1;
        Instant start = Instant.now();
        do {
            train.iteration();
            log.info("Epoch #" + epoch + ", error: " + train.getError());
            epoch++;
        } while (train.getError() > errorThreshold);
        train.finishTraining();
        Instant end = Instant.now();
        log.info("Training finished");
        trainingTime = Duration.between(start, end);
        Encog.getInstance().shutdown();
        return network;
    }

    public TestResultStatistics validate() throws IOException {
        MLDataSet dataSet = testDataSetProvider.prepareDataSet();
        List<TestResultEntry> result = StreamSupport.stream(dataSet.spliterator(), false)
                .map(this::mlDataPairToResultEntry)
                .collect(toList());
        return new TestResultStatistics(result);
    }

    public Duration getTrainingTime() {
        return trainingTime;
    }

    private TestResultEntry mlDataPairToResultEntry(MLDataPair p) {
        return new TestResultEntry(
                p.getIdeal().getData(0),
                network.compute(p.getInput()).getData(0)
        );
    }

    private void checkIsDirectory(Path dirPath) {
        if (!Files.isDirectory(dirPath)) {
            throw new RuntimeException(dirPath + " is not a directory");
        }
    }

}
