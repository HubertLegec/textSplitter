package com.pw.eiti.wedt;

import org.apache.commons.cli.*;

class TrainerArgumentsParser {
    private final Options options;

    TrainerArgumentsParser() {
        this.options = new Options();
        Option trainDir = new Option("t", "train", true, "Train data directory");
        trainDir.setRequired(true);
        options.addOption(trainDir);
        Option modelPath = new Option("o", "output", true, "Trained model path");
        modelPath.setRequired(true);
        options.addOption(modelPath);
        Option error = new Option("e", "error", true, "Training error threshold");
        modelPath.setRequired(true);
        options.addOption(error);
    }

    CommandLine parse(String[] args) throws ParseException {
        CommandLineParser parser = new DefaultParser();
        return parser.parse(options, args);
    }

    void printHelp() {
        HelpFormatter formatter = new HelpFormatter();
        formatter.printHelp("utility-name", options);
    }
}
