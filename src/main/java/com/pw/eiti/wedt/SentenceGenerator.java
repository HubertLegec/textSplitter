package com.pw.eiti.wedt;

import com.pw.eiti.wedt.model.DocSentence;
import edu.stanford.nlp.simple.Document;
import edu.stanford.nlp.simple.Sentence;

import java.util.List;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.toList;

public class SentenceGenerator {

    public static List<DocSentence> generateSentencesFromString(String fileContent) {
        Document doc = new Document(fileContent);
        List<Sentence>  sentences = doc.sentences();
        return IntStream.range(0, sentences.size()).boxed()
                .map(idx -> new DocSentence(idx, sentences.get(idx)))
                .collect(toList());
    }
}
