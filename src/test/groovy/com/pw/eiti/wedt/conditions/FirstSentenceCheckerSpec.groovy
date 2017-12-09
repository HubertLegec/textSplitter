package com.pw.eiti.wedt.conditions

import com.pw.eiti.wedt.model.Document
import com.pw.eiti.wedt.model.DocSentence
import spock.lang.Specification

class FirstSentenceCheckerSpec extends Specification {
    ConditionChecker checker = new FirstSentenceChecker();

    def 'sentence is the first one'() {
        given: 'document and the first sentence'
            DocSentence sentence = new DocSentence(text: "Some text", id: 1)
            Document document = new Document(sentences: [sentence, new DocSentence(text: 'Some another text', id: 2)])
        when: 'condition is checked'
            boolean result = checker.checkCondition(sentence, document)
        then: 'result should be positive'
            result == true
    }

    def 'sentence is not the first one'() {
        given: 'document and some sentence (not the first one)'
            DocSentence sentence = new DocSentence(text: "Some text", id: 5)
            Document document = new Document(sentences: [new DocSentence(text: 'Some another text', id: 2), sentence])
        when: 'condition is checked'
            boolean result = checker.checkCondition(sentence, document)
        then: 'result should be negative'
            result == false
    }
}
