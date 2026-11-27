package com.jobaggregator.http.mapper;

import org.jsoup.Jsoup;
import tools.jackson.core.JacksonException;
import tools.jackson.core.JsonParser;
import tools.jackson.databind.DeserializationContext;
import tools.jackson.databind.ValueDeserializer;

public final class HtmlToTextDeserializer extends ValueDeserializer<String> {

    @Override
    public String deserialize(JsonParser p, DeserializationContext ctxt) throws JacksonException {
        String html = p.getString();
        if (html == null || html.isBlank()) {
            return html;
        }
        return Jsoup.parse(html).text();
    }
}
