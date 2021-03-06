package com.pw.eiti.wedt;

import com.pw.eiti.wedt.network.NetworkProvider;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.ParseException;
import org.encog.neural.networks.BasicNetwork;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class NetworkTrainer {
    private static final Logger log = LoggerFactory.getLogger(NetworkTrainer.class);
    private static final TrainerArgumentsParser argumentParser = new TrainerArgumentsParser();

    public static void main(String[] args) {
        try {
            CommandLine cmd = argumentParser.parse(args);
            String inputDir = cmd.getOptionValue("train");
            Double error = Double.parseDouble(cmd.getOptionValue("error"));
            String modelFileName = cmd.getOptionValue("output");
            log.info("Create trainer...");
            ModelTrainer trainer = new ModelTrainer(inputDir, error);
            log.info("Train model...");
            BasicNetwork networkModel = trainer.train();
            log.info("Training time: " + trainer.getTrainingTime());
            log.info("Save model to file: " + modelFileName);
            NetworkProvider.saveToFile(modelFileName, networkModel);
            log.info("Model saved.");
            System.exit(0);
        } catch (ParseException e) {
            log.error(e.getMessage());
            argumentParser.printHelp();
            System.exit(1);
        } catch (IOException e) {
            log.error(e.getMessage());
            System.exit(1);
        }
    }
}
