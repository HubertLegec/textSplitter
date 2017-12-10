package com.pw.eiti.wedt.conditions

import com.pw.eiti.wedt.model.DocSentence
import spock.lang.Specification

class ListItemCheckerSpec extends Specification {
    ConditionChecker checker = new ListItemChecker()

    def "sentence is a numbered list item"() {
        given: ""
            DocSentence sentence = new DocSentence(text: "11. List sentence.")
        when: "condition is checked"
            boolean result = checker.checkCondition(sentence, null)
        then: "result should be positive"
            result == true
    }

    def "sentence is a bulleted list item"() {
        given: ""
            DocSentence sentence = new DocSentence(text: "    * List sentence")
        when: "condition is checked"
            boolean result = checker.checkCondition(sentence, null)
        then: "result should be positive"
            result == true
    }

    def "sentence is not a list item"() {
        given: ""
            DocSentence sentence = new DocSentence(text: "Not a list sentence.")
        when: "condition is checked"
            boolean result = checker.checkCondition(sentence, null)
        then: "result should be negative"
            result == false
    }
}
