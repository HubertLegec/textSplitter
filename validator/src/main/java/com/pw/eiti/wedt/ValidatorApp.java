package com.pw.eiti.wedt;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class ValidatorApp {
    private static final Logger log = LoggerFactory.getLogger(ValidatorApp.class);
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
            log.error(e.getMessage());
            argumentParser.printHelp();
            System.exit(1);
        } catch (IOException e) {
            log.error(e.getMessage());
            System.exit(1);
        }
    }
}
