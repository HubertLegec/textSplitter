package com.pw.eiti.wedt.conditions

import com.pw.eiti.wedt.model.DocSentence
import com.pw.eiti.wedt.model.Document
import spock.lang.Specification

class ShorterPreviousRowCheckerSpec extends Specification {
    ConditionChecker checker = new ShorterPreviousRowChecker()
    Document document

    def setup() {
        File file = new File(getClass().getResource("/shorterLine.txt").file)
        document = new Document(file)
    }

    def "previous line is shorter"() {
        given: "sentence and document"
            DocSentence sentence = document.sentences[2]
        when: "condition is checked"
            boolean result = checker.checkCondition(sentence)
        then: "result should be positive"
            result == true
    }

    def "previous line has the same length"() {
        given: "sentence and document"
            DocSentence sentence = document.sentences[4]
        when: "condition is checked"
            boolean result = checker.checkCondition(sentence)
        then: "result should be negative"
            result == false
    }

    def "previous line is longer"() {
        given: "sentence and document"
            DocSentence sentence = document.sentences[6]
        when: "condition is checked"
            boolean result = checker.checkCondition(sentence)
        then: "result should be negative"
            result == false
    }
}
