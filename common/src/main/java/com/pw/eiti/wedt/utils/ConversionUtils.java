package com.pw.eiti.wedt.utils;

import org.apache.commons.lang3.ArrayUtils;
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

    public static double[] doubleListToArray(List<Double> input) {
        return ArrayUtils.toPrimitive(input.stream().toArray(Double[]::new));
    }

    public static double[][] doubleListOfArrayToArray(List<double[]> input) {
        double[][] array = new double[input.size()][];
        for(int i=0; i < input.size(); i++) {
            array[i] = input.get(i);
        }
        return array;
    }
}
