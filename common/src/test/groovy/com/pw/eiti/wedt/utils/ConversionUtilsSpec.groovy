package com.pw.eiti.wedt.utils

import spock.lang.Specification

class ConversionUtilsSpec extends Specification {

    def 'boolean is converted to double 1.0 and 0.0'() {
        expect:
            ConversionUtils.booleanToDouble(a) == b
        where:
            a     | b
            true  | 1.0d
            false | 0.0d
    }

    def 'string with comma separator is converted to list of int'() {
        given:
            String input = "1,2,5,6"
        when:
            List<Integer> result = ConversionUtils.stringToListOfInt(input, ",")
        then:
            result == [1, 2, 5, 6]
    }
}
