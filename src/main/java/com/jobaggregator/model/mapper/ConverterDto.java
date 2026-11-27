package com.jobaggregator.model.mapper;

import java.util.Collection;
import java.util.stream.Collectors;

public final class ConverterDto {

    private ConverterDto() {
        throw new UnsupportedOperationException("Utility class");
    }

    public static String toString(Object rawDto) {
        return switch (rawDto) {
            case OfferSource container -> toString(container.toOffers());
            case Collection<?> coll -> coll.isEmpty()
                    ? "Empty list"
                    : coll.stream().map(ConverterDto::toString).collect(Collectors.joining("\n"));
            case Formattable form -> form.format();
            case null, default -> "null";
        };
    }
}
