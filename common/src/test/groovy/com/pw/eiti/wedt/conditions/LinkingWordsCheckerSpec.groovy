package com.pw.eiti.wedt.conditions

import com.pw.eiti.wedt.model.DocSentence
import spock.lang.Specification


class LinkingWordsCheckerSpec extends Specification {
    ConditionChecker checker = new LinkingWordsConditionChecker()

    def 'sentence contains linking phase'() {
        given: 'sentence that starts with linking phase'
            def sentence = new DocSentence(text: "Undoubtedly, it must be true")
        when: 'condition is checked'
            def condition = checker.checkCondition(sentence, null)
        then: 'result should be positive'
            condition == true
    }

    def 'sentence does not contain linking phase'() {
        given: 'sentence not starting with linking phase'
            def sentence = new DocSentence(text:  "Hello, it's me!")
        when: 'condition is checked'
            def condition = checker.checkCondition(sentence, null)
        then: 'result should be positive'
            condition == false
    }
}
