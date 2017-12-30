package com.pw.eiti.wedt.conditions

import com.pw.eiti.wedt.model.DocSentence
import spock.lang.Specification

class StartsNewLineCheckerSpec extends Specification {
    ConditionChecker checker = new StartsNewLineChecker()

    def "sentence starts new line"() {
        given: "sentence that starts new line"
            DocSentence sentence = new DocSentence(text: "Sentence text", predecessor: "\n")
        when: "condition is checked"
            boolean result = checker.checkCondition(sentence)
        then: "result should be positive"
            result == true
    }

    def "sentence does not start new line"() {
        given: "sentence that doesn't start new line"
            DocSentence sentence = new DocSentence(id: 2, text: "Sentence text", predecessor: " ")
        when: "condition is checked"
            boolean result = checker.checkCondition(sentence)
        then: "result should be negative"
            result == false
    }
}
