package com.pw.eiti.wedt.conditions

import com.pw.eiti.wedt.model.DocSentence
import spock.lang.Specification

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

    def 'sentence is not preceded by tab'() {
        given: 'sentence not preceded by tab'
            DocSentence sentence = new DocSentence(text: "Some sentence", predecessor: "    ")
        when: 'condition is checked'
            boolean result = checker.checkCondition(sentence)
        then: 'result should be negative'
            result == false
    }

}
