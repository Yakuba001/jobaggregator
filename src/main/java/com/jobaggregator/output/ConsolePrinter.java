package com.jobaggregator.output;

import com.jobaggregator.model.mapper.ConverterDto;

public final class ConsolePrinter {

    private ConsolePrinter() {
        throw new UnsupportedOperationException("Utility class");
    }

    public static void print(Object data) {
        System.out.println(ConverterDto.toString(data));
    }
}
