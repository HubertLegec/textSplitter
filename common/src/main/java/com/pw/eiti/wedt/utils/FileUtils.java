package com.pw.eiti.wedt.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Optional;

import static org.apache.commons.io.FileUtils.readFileToString;

public class FileUtils {
    private static Logger log = LoggerFactory.getLogger(FileUtils.class);

    /**
     * Reads content of a file with given path. Content is returned as {@code Optional<String>}.
     * If error occurred when file was read, {@code Optional.empty()} is returned.
     * @param inputFile path of the file to read from
     * @return file content as {@code Optional<String>} or empty Optional if error occurred
     */
    public static Optional<String> readFileAsString(Path inputFile) {
        try {
            return Optional.of(readFileToString(inputFile.toFile(), "UTF-8"));
        } catch (IOException e) {
            log.error(e.getMessage());
            return Optional.empty();
        }
    }

    /**
     * Reads content of a file with given path. Content is returned as {@code Optional<List<String>>}.
     * If error occurred when file was read, {@code Optional.empty()} is returned.
     * @param inputFile path of the file to read from
     * @return file content as {@code Optional<List<String>>} or empty Optional if error occurred
     */
    public static Optional<List<String>> readFileAsLines(Path inputFile) {
        try {
            return Optional.of(Files.readAllLines(inputFile));
        } catch (IOException e) {
            log.error(e.getMessage());
            return Optional.empty();
        }
    }

    /**
     * Searches for file with name {@param filename} in directory {@param inputDir}.
     * If matching file exists, its {@code Path} is returned.
     * If such a file doesn't exist or some exception occurred {@code Optional.empty()} is returned.
     * @param inputDir path to directory to search
     * @param filename name of file to search
     * @return {@code Path} to matching file or empty Optional if such a file doesn't exist
     */
    public static Optional<Path> findFileInDirectory(Path inputDir, String filename) {
        try {
            return Files.find(
                    inputDir,
                    1,
                    (path, basicFileAttributes) -> path.getFileName().toString().equals(filename)
            ).findAny();
        } catch (IOException e) {
            log.error(e.getMessage());
            return Optional.empty();
        }
    }
}
