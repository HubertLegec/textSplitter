package com.pw.eiti.wedt.conditions

import com.pw.eiti.wedt.model.DocSentence
import com.pw.eiti.wedt.model.SentencePredecessor
import spock.lang.Specification

class StartsNewLineCheckerSpec extends Specification {
    ConditionChecker checker = new StartsNewLineChecker()

    def "sentence starts new line"() {
        given: "sentence that starts new line"
            SentencePredecessor predecessor = new SentencePredecessor("\n")
            DocSentence sentence = new DocSentence(text: "Sentence text", predecessor: predecessor)
        when: "condition is checked"
            boolean result = checker.checkCondition(sentence, null)
        then: "result should be positive"
            result == true
    }

    def "sentence does not start new line"() {
        given: "sentence that doesn't start new line"
            SentencePredecessor predecessor = new SentencePredecessor(" ")
            DocSentence sentence = new DocSentence(text: "Sentence text", predecessor: predecessor)
        when: "condition is checked"
            boolean result = checker.checkCondition(sentence, null)
        then: "result should be negative"
            result == false
    }
}
