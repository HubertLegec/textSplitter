package com.pw.eiti.wedt;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.ParseException;

import java.io.IOException;

public class ValidatorApp {
    private static final ValidatorArgumentsParser argumentParser = new ValidatorArgumentsParser();

    public static void main(String[] args) {
        try {
            CommandLine cmd = argumentParser.parse(args);
            String modelFile = cmd.getOptionValue("model");
            String testDir = cmd.getOptionValue("test");
            Validator validator = new Validator(testDir, modelFile);
            validator.validate();
            if (modelFile != null) {
                System.out.println(validator.getNetworkResult());
            }
            System.out.println(validator.getCustomResult());
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
