package com.pw.eiti.wedt.conditions

import com.pw.eiti.wedt.model.Sentence
import spock.lang.Specification


class LinkingWordsCheckerSpec extends Specification {
    def checker = new LinkingWordsConditionChecker()

    def 'test contains linking phase'() {
        given: 'sentence that starts with linking phase'
            def sentence = new Sentence(text: "Undoubtedly, it must be true")
        when: 'condition is checked'
            def condition = checker.checkCondition(sentence, null)
        then: 'result should be positive'
            condition == true
    }

    def 'test does not contain linking phase'() {
        given: 'sentence not starting with linking phase'
            def sentence = new Sentence(text:  "Hello, it's me!")
        when: 'condition is checked'
            def condition = checker.checkCondition(sentence, null)
        then: 'result should be positive'
            condition == false
    }
}
