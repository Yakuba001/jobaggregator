package com.jobaggregator;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class SmokeTest {

    @Test
    void context() {
        int a = 1;
        int b = 2;

        int result = a + b;

        assertThat(result).isEqualTo(a + b);
    }
}
