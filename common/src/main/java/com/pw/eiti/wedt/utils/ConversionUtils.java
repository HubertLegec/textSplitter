package com.pw.eiti.wedt.utils;

import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

public class ConversionUtils {
    public static double booleanToDouble(boolean val) {
        return !val ? 0 : 1;
    }

    public static List<Integer> stringToListOfInt(String input, String separator) {
        String[] elements = StringUtils.split(input, separator);
        return Stream.of(elements)
                .map(Integer::parseInt)
                .collect(toList());
    }
}
