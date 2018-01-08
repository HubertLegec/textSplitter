package com.pw.eiti.wedt;

import com.pw.eiti.wedt.network.NetworkProvider;
import com.pw.eiti.wedt.utils.ArgumentsParser;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.ParseException;
import org.encog.neural.networks.BasicNetwork;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class NetworkTrainer {
    private static final Logger log = LoggerFactory.getLogger(NetworkTrainer.class);
    private static final ArgumentsParser argumentParser = new ArgumentsParser();
    public static void main(String[] args) {
        try {
            CommandLine cmd = argumentParser.parse(args);
            String inputDir = cmd.getOptionValue("train");
            String testDir = cmd.getOptionValue("test");
            String modelFileName = cmd.getOptionValue("output");
            log.info("Create trainer...");
            ModelTrainer trainer = new ModelTrainer(inputDir, testDir);
            log.info("Train model...");
            BasicNetwork networkModel = trainer.train();
            log.info("Save model to file: " + modelFileName);
            NetworkProvider.saveToFile(modelFileName, networkModel);
            log.info("Model saved.");
            TestResultStatistics statistics = trainer.validate();
            System.out.println("---- Trained model statistics ---");
            System.out.println("Training time: " + trainer.getTrainingTime().toString());
            System.out.println(statistics.toString());
            System.out.println("---------------------------------");
            System.exit(0);
        } catch (ParseException e) {
            System.out.println(e.getMessage());
            argumentParser.printHelp();
            System.exit(1);
        } catch (IOException e) {
            System.out.println(e.getMessage());
            System.exit(1);
        }
    }
}
