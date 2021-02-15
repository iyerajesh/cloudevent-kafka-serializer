package com.xylia.platform.events.exception;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class SerdesExceptionTest {

    @Test
    public void testIfSerdesExceptionIsInstanceOfRuntimeException() {
        assertThat(new SerdesException("serialization exception")).isInstanceOf(RuntimeException.class);
    }

}