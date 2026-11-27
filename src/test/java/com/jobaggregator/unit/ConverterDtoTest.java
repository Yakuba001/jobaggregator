package com.jobaggregator.unit;

import com.jobaggregator.model.JobOffer;
import com.jobaggregator.model.mapper.ConverterDto;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class ConverterDtoTest {

    private static final String TEST_URL = "url";
    private static final String TEST_TITLE = "title";
    private static final LocalDateTime TEST_DATE =
            LocalDateTime.of(2026, 11, 27, 12, 0);
    private static final String TEST_FORMATTED_DATE = "Date: 27.11.2026 12:00";
    private static final String TEST_LOCATION = "location";
    private static final String TEST_DESCRIPTION = "description";

    @Test
    void toString_shouldReturnStringWithNullText_whenArgIsNullOrUnknown() {
        String result = ConverterDto.toString(null);

        assertThat(result).isEqualTo("null");
    }

    @Test
    void toString_shouldReturnCorrectString_whenArgIsSingleObject() {
        String source = "Single object";
        JobOffer offer = new JobOffer(source, TEST_URL, TEST_TITLE, TEST_DATE, TEST_LOCATION, TEST_DESCRIPTION);

        String result = ConverterDto.toString(offer);

        assertThat(result).contains(source);
        assertThat(result).contains(TEST_URL);
        assertThat(result).contains(TEST_TITLE);
        assertThat(result).contains(TEST_FORMATTED_DATE);
        assertThat(result).contains(TEST_LOCATION);
        assertThat(result).contains(TEST_DESCRIPTION);
    }

    @Test
    void toString_shouldReturnCorrectString_whenArgIsCollection() {
        String firstSource = "First object";
        String secondSource = "Second object";
        JobOffer first = new JobOffer(firstSource, TEST_URL, TEST_TITLE, TEST_DATE, TEST_LOCATION, TEST_DESCRIPTION);
        JobOffer second = new JobOffer(secondSource, TEST_URL, TEST_TITLE, TEST_DATE, TEST_LOCATION, TEST_DESCRIPTION);
        List<JobOffer> offers = List.of(first, second);

        String result = ConverterDto.toString(offers);

        assertThat(result).contains(firstSource);
        assertThat(result).contains(secondSource);
        assertThat(result).contains(TEST_URL);
        assertThat(result).contains(TEST_TITLE);
        assertThat(result).contains(TEST_FORMATTED_DATE);
        assertThat(result).contains(TEST_LOCATION);
        assertThat(result).contains(TEST_DESCRIPTION);
    }
}
