package com.pw.eiti.wedt.model

import spock.lang.Specification

class DocumentSpec extends Specification {
    def "simple text is parsed correctly"() {
        given: "simple text"
            String text = "\tThis is first sentence. This is second sentence in first paragraph.\n\t" +
                    "This is first sentence in second paragraph"
            Document document = new Document()
        when: "text is parsed"
            List<DocSentence> result = document.generateSentencesFromString(text)
        then: "correct sentences are created"
            result.size() == 3
    }

    def "text with lists and empty lines is parsed correctly"() {
        given: "text to parse"
            String text = "This is first e.g. sentence. Isn't it simple?\n\n\t" +
                    "Start of second paragraph. It consists of following sentences:\n" +
                    " - hello to everyone" +
                    " - hi \"in quotations\""
            Document document = new Document()
        when: "text is parsed"
            List<DocSentence> result = document.generateSentencesFromString(text)
        then: "correct sentences are created"
            result.size() == 4
    }

    def "simple file model is created correctly"() {
        given: "sample file"
            File file = new File(getClass().getResource("/document1.txt").file)
        when: "model is created"
            Document document = new Document(file)
        then: "it has correct sentence and line division"
            document.lines.size() == 6
            document.sentences.size() == 5

            DocSentence sentence1 = document.sentences[0]
            sentence1.startRow == 0
            sentence1.endRow == 0

            DocSentence sentence2 = document.sentences[1]
            sentence2.startRow == 0
            sentence2.endRow == 0

            DocSentence sentence3 = document.sentences[2]
            sentence3.startRow == 2
            sentence3.endRow == 2

            DocSentence sentence4 = document.sentences[3]
            sentence4.startRow == 2
            sentence4.endRow == 4

            DocSentence sentence5 = document.sentences[4]
            sentence5.startRow == 5
            sentence5.endRow == 5
    }
}