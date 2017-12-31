package com.pw.eiti.wedt

import com.pw.eiti.wedt.conditions.SentenceConditions
import spock.lang.Specification

import java.nio.file.Path
import java.nio.file.Paths

class DataSetProviderSpec extends Specification {

    def "file sentence pairs are created correctly"() {
        given: "input file and paragraph sentence indexes"
            Path dirPath = Paths.get(getClass().getResource("/dataSet").path)
            Path filePath = Paths.get(getClass().getResource("/dataSet/reut_raw.txt").path)
            DataSetProvider provider = new DataSetProvider(dirPath)
        when: "sentences are generated"
            def result = provider.getFileSentences(filePath)
        then: "sentences are mapped correctly"
            result.size() == 17
            def p1 = result[0]
            p1.value == true
            SentenceConditions c1 = p1.key
            c1.isEmptyPreviousRow == true
            c1.isFirstSentence == true
            //c1.hasLinkingWords == false
            //c1.isListItem == false
            c1.hasPrecedingTab == false
            c1.hasShorterPreviousRow == false
            c1.startsNewLine == true

            result[1].value == false
            result[2].value == false
            result[3].value == false
            result[4].value == false
            result[5].value == false
            result[6].value == false

            def p7 = result[7]
            p7.value == true
            SentenceConditions c7 = p7.key
            c7.isEmptyPreviousRow == true
            c7.isFirstSentence == false
            //c7.hasLinkingWords == false
            //c7.isListItem == false
            c7.hasPrecedingTab == false
            c7.hasShorterPreviousRow == true
            c7.startsNewLine == true
    }
}
