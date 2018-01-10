package com.pw.eiti.wedt;


import org.apache.commons.cli.*;

class ValidatorArgumentsParser {
    private final Options options;

    ValidatorArgumentsParser() {
        this.options = new Options();
        Option testDir = new Option("t", "test", true, "Test data directory");
        testDir.setRequired(true);
        options.addOption(testDir);
        Option modelPath = new Option("m", "model", true, "Model path");
        modelPath.setRequired(true);
        options.addOption(modelPath);
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
