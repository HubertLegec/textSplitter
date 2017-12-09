package com.pw.eiti.wedt.conditions

import com.pw.eiti.wedt.model.DocSentence
import com.pw.eiti.wedt.model.SentencePredecessor
import spock.lang.Specification

class EmptyPreviousRowCheckerSpec extends Specification {
    ConditionChecker checker = new EmptyPreviousRowChecker()

    def 'previous row is empty'() {
        given: 'sentence with empty previous row'
            SentencePredecessor predecessor = new SentencePredecessor("\n\n\t")
            DocSentence sentence = new DocSentence(text: "Sentence", predecessor: predecessor)
        when: 'condition is checked'
            boolean result = checker.checkCondition(sentence, null)
        then: 'result should be positive'
            result == true
    }

    def 'previous row is not empty'() {
        given: 'sentence with not empty previous row'
            SentencePredecessor predecessor = new SentencePredecessor("'\n\t")
            DocSentence sentence = new DocSentence(text: "Sentence", predecessor: predecessor)
        when: 'condition is checked'
            boolean result = checker.checkCondition(sentence, null)
        then: 'result should be negative'
            result == false
    }

    def 'first sentence in document'() {
        given: 'first sentence in document'
            SentencePredecessor predecessor = new SentencePredecessor("")
            DocSentence sentence = new DocSentence(text: "Sentence", predecessor: predecessor)
        when: 'condition is checked'
            boolean result = checker.checkCondition(sentence, null)
        then: 'result should be negative'
            result == false
    }
}
