package com.pw.eiti.wedt.conditions

import com.pw.eiti.wedt.model.DocSentence
import com.pw.eiti.wedt.model.Document
import spock.lang.Specification

import java.nio.file.Path
import java.nio.file.Paths

class EmptyPreviousRowCheckerSpec extends Specification {
    ConditionChecker checker = new EmptyPreviousRowChecker()

    def 'previous row is empty'() {
        given: 'sentence with empty previous row'
            DocSentence sentence = new DocSentence(id: 3, text: "Sentence", predecessor: "\n\n\t")
        when: 'condition is checked'
            boolean result = checker.checkCondition(sentence)
        then: 'result should be positive'
            result == true
    }

    def 'previous row is not empty'() {
        given: 'sentence with not empty previous row'
            DocSentence sentence = new DocSentence(id: 3, text: "Sentence", predecessor: "\n\t")
        when: 'condition is checked'
            boolean result = checker.checkCondition(sentence)
        then: 'result should be negative'
            result == false
    }

    def 'first sentence in document'() {
        given: 'first sentence in document'
            DocSentence sentence = new DocSentence(id: 0, text: "Sentence", predecessor: "")
        when: 'condition is checked'
            boolean result = checker.checkCondition(sentence)
        then: 'result should be negative'
            result == true
    }

    def 'advanced example'() {
        given: 'document with one empyty line'
            Path file = Paths.get(getClass().getResource("/empty_previous.txt").file)
            Document document = new Document(file)
        when: 'conditions are checked'
            boolean result0 = checker.checkCondition(document.getSentences()[0])
            boolean result1 = checker.checkCondition(document.getSentences()[1])
            boolean result3 = checker.checkCondition(document.getSentences()[3])
        then:
            result0 == true
            result1 == false
            result3 == true
    }
}
