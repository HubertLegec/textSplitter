package com.pw.eiti.wedt.conditions

import com.pw.eiti.wedt.model.Document
import com.pw.eiti.wedt.model.DocSentence
import spock.lang.Specification

class FirstSentenceCheckerSpec extends Specification {
    ConditionChecker checker = new FirstSentenceChecker();

    def 'sentence is the first one'() {
        given: 'document and the first sentence'
            Document document = new Document()
            DocSentence sentence = new DocSentence(text: "Some text", id: 1, document: document)
            document.sentences = [sentence, new DocSentence(text: 'Some another text', id: 2)]

        when: 'condition is checked'
            boolean result = checker.checkCondition(sentence)
        then: 'result should be positive'
            result == true
    }

    def 'sentence is not the first one'() {
        given: 'document and some sentence (not the first one)'
            Document document = new Document()
            DocSentence sentence = new DocSentence(text: "Some text", id: 5, document: document)
            document.sentences = [new DocSentence(text: 'Some another text', id: 2), sentence]
        when: 'condition is checked'
            boolean result = checker.checkCondition(sentence)
        then: 'result should be negative'
            result == false
    }
}
