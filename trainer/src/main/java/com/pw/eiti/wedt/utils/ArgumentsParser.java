package com.pw.eiti.wedt.utils;

import org.apache.commons.cli.*;

public class ArgumentsParser {
    private final Options options;

    public ArgumentsParser() {
        this.options = new Options();
        Option trainDir = new Option("t", "train", true, "Train data directory");
        trainDir.setRequired(true);
        options.addOption(trainDir);
        Option testDir = new Option("v", "validation", true, "Test data directory");
        testDir.setRequired(true);
        options.addOption(testDir);
        Option modelPath = new Option("o", "output", true, "Trained model path");
        modelPath.setRequired(true);
        options.addOption(modelPath);
        Option error = new Option("e", "error", true, "Training error threshold");
        modelPath.setRequired(true);
        options.addOption(error);
    }

    public CommandLine parse(String[] args) throws ParseException {
        CommandLineParser parser = new DefaultParser();
        return parser.parse(options, args);
    }

    public void printHelp() {
        HelpFormatter formatter = new HelpFormatter();
        formatter.printHelp("utility-name", options);
    }
}
