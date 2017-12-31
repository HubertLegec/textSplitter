package com.pw.eiti.wedt.conditions

import com.pw.eiti.wedt.model.DocSentence
import com.pw.eiti.wedt.model.Document
import spock.lang.Specification

import java.nio.file.Path
import java.nio.file.Paths

class NotFirstSentenceInLineCheckerSpec extends Specification {
    ConditionChecker checker = new NotFirstSentenceInLineChecker()
    Document document

    def setup() {
        Path file = Paths.get(getClass().getResource("/notFirst.txt").file)
        document = new Document(file)
    }

    def "sentence is not first in line"() {
        given: "sentence"
            DocSentence sentence = document.sentences[2]
        when: "condition is checked"
            boolean result = checker.checkCondition(sentence)
        then: "result should be positive"
            result == true
    }

    def "sentence is first in line"() {
        given: "sentence"
            DocSentence sentence = document.sentences[1]
        when: "condition is checked"
            boolean result = checker.checkCondition(sentence)
        then: "result should be positive"
            result == false
    }
}
