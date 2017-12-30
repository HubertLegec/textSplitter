package com.pw.eiti.wedt.utils;

import org.apache.commons.cli.*;

public class ArgumentsParser {
    private final Options options;

    public ArgumentsParser() {
        this.options = new Options();
        Option inputDir = new Option("i", "input", true, "input directory");
        inputDir.setRequired(true);
        options.addOption(inputDir);
        Option modelPath = new Option("o", "output", true, "trained model path");
        modelPath.setRequired(true);
        options.addOption(modelPath);
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
