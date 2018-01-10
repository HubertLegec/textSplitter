package com.pw.eiti.wedt.conditions

import com.pw.eiti.wedt.model.DocSentence
import com.pw.eiti.wedt.model.Document
import spock.lang.Specification

import java.nio.file.Path
import java.nio.file.Paths

class PrecedingTabCheckerSpec extends Specification {
    ConditionChecker checker = new PrecedingTabChecker()

    def 'sentence is preceded by tab'() {
        given: 'sentence preceded by tab'
            DocSentence sentence = new DocSentence(text: "Some sentence", predecessor: "\t")
        when: 'condition is checked'
            boolean result = checker.checkCondition(sentence)
        then: 'result should be positive'
            result == true
    }

    def 'sentence is preceded by four spaces'() {
        given: 'sentence preceded by tab'
        DocSentence sentence = new DocSentence(text: "Some sentence", predecessor: "    ")
        when: 'condition is checked'
        boolean result = checker.checkCondition(sentence)
        then: 'result should be positive'
        result == true
    }

    def 'sentence is not preceded by tab'() {
        given: 'sentence not preceded by tab'
            DocSentence sentence = new DocSentence(text: "Some sentence", predecessor: "  e  ")
        when: 'condition is checked'
            boolean result = checker.checkCondition(sentence)
        then: 'result should be negative'
            result == false
    }

    def 'advanced example'() {
        given: 'document with one empyty line'
            Path file = Paths.get(getClass().getResource("/preceding_tab.txt").file)
            Document document = new Document(file)
        when: 'condition is checked'
            boolean result0 = checker.checkCondition(document.getSentences()[0])
            boolean result1 = checker.checkCondition(document.getSentences()[1])
            boolean result2 = checker.checkCondition(document.getSentences()[2])
        then:
            result0 == false
            result1 == true
            result2 == true
    }

}
