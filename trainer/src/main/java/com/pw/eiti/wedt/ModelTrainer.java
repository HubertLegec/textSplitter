package com.pw.eiti.wedt;

import com.pw.eiti.wedt.data.DataSetProvider;
import com.pw.eiti.wedt.network.NetworkProvider;
import org.encog.Encog;
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

public class ModelTrainer {
    private static final Logger log = LoggerFactory.getLogger(ModelTrainer.class);
    private final double errorThreshold;
    private final DataSetProvider dataSetProvider;
    private final BasicNetwork network;
    private Duration trainingTime = null;

    public ModelTrainer(String inputDir, double errorThreshold) {
        this(inputDir, 7, errorThreshold);
    }

    public ModelTrainer(String inputDir, int inputSize, double errorThreshold) {
        /*Path inputDirPath = Paths.get(inputDir);*/
        Path inputDirPath = Paths.get(getClass().getResource("/training").getPath());
        checkIsDirectory(inputDirPath);
        this.dataSetProvider = new DataSetProvider(inputDirPath);
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

    public Duration getTrainingTime() {
        return trainingTime;
    }

    private void checkIsDirectory(Path dirPath) {
        if (!Files.isDirectory(dirPath)) {
            throw new RuntimeException(dirPath + " is not a directory");
        }
    }

}
