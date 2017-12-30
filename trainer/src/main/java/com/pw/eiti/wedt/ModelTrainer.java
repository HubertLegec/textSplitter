package com.pw.eiti.wedt;

import com.pw.eiti.wedt.network.NetworkProvider;
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

public class ModelTrainer {
    private static final Logger log = LoggerFactory.getLogger(ModelTrainer.class);
    private static final double ERROR_THRESHOLD = 0.14;
    private final DataSetProvider dataSetProvider;
    private final Path inputDir;
    private final BasicNetwork network;

    public ModelTrainer(String inputDir) {
        assert Files.isDirectory(Paths.get(inputDir));
        // TODO - to be changed at the end of development
        // this.inputDir = Paths.get(inputDir);
        this.inputDir = Paths.get(getClass().getResource("/training").getPath());
        this.dataSetProvider = new DataSetProvider(this.inputDir);
        this.network = NetworkProvider.createNetwork(7);
    }

    public BasicNetwork train() throws IOException {
        final MLDataSet dataSet = dataSetProvider.prepareDataSet();
        final Train train = new ResilientPropagation(network, dataSet);
        int epoch = 1;
        do {
            train.iteration();
            log.info("Epoch #" + epoch + ", error: " + train.getError());
            epoch++;
        } while (train.getError() > ERROR_THRESHOLD);
        log.info("Training finished");
        return network;
    }

}
