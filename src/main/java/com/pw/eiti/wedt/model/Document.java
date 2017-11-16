package com.pw.eiti.wedt.model;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class Document {
    private List<Sentence> sentences;
    private List<String> lines;

    public Document(Path file) throws IOException {
        lines = Files.readAllLines(file);

    }
}
