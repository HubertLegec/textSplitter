package com.pw.eiti.wedt.conditions

import com.pw.eiti.wedt.model.DocSentence
import spock.lang.Specification

class EmptyPreviousRowCheckerSpec extends Specification {
    ConditionChecker checker = new EmptyPreviousRowChecker()

    def 'previous row is empty'() {
        given: 'sentence with empty previous row'
            DocSentence sentence = new DocSentence(text: "Sentence", predecessor: "\n\n\t")
        when: 'condition is checked'
            boolean result = checker.checkCondition(sentence)
        then: 'result should be positive'
            result == true
    }

    def 'previous row is not empty'() {
        given: 'sentence with not empty previous row'
            DocSentence sentence = new DocSentence(text: "Sentence", predecessor: "\n\t")
        when: 'condition is checked'
            boolean result = checker.checkCondition(sentence)
        then: 'result should be negative'
            result == false
    }

    def 'first sentence in document'() {
        given: 'first sentence in document'
            DocSentence sentence = new DocSentence(text: "Sentence", predecessor: "")
        when: 'condition is checked'
            boolean result = checker.checkCondition(sentence)
        then: 'result should be negative'
            result == false
    }
}
