package com.pw.eiti.wedt

import com.pw.eiti.wedt.model.DocSentence
import spock.lang.Specification

class SentenceGeneratorSpec extends Specification {

    def "simple text is parsed correctly"() {
        given: "simple text"
            String text = "\tThis is first sentence. This is second sentence in first paragraph.\n\t" +
                    "This is first sentence in second paragraph"
        when: "text is parsed"
            List<DocSentence> result = SentenceGenerator.generateSentencesFromString(text)
        then: "correct sentences are created"
            result.size() == 3
    }

    def "text with lists and empty lines is parsed correctly"() {
        given: "text to parse"
            String text = "This is first e.g. sentence. Isn't it simple?\n\n\t" +
                    "Start of second paragraph. It consists of following sentences:\n" +
                    " - hello to everyone" +
                    " - hi \"in quotations\""
        when: "text is parsed"
            List<DocSentence> result = SentenceGenerator.generateSentencesFromString(text)
        then: "correct sentences are created"
            result.size() == 4
    }
}
