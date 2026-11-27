package com.jobaggregator.model;

import com.jobaggregator.model.mapper.Formattable;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public record JobOffer(
        String source,
        String url,
        String title,
        LocalDateTime publishedAt,
        String location,
        String description
) implements Formattable {

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");

    @Override
    public String format() {
        String dateStr = publishedAt == null ? "N/A" : DATE_FORMATTER.format(publishedAt);
        String descStr = description == null ? "" : description.substring(0, Math.min(300, description.length()));
        return """
                ==================================================
                Source: %s
                Url: %s
                Title: %s
                Date: %s
                Location: %s
                Description: %s
                ==================================================
                """.formatted(
                source == null ? "" : source,
                url == null ? "" : url,
                title == null ? "" : title,
                dateStr,
                location == null ? "" : location,
                descStr
        );
    }
}
