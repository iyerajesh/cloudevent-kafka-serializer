package com.xylia.platform.events.model;

import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;

public class EventTypesTest {

    @Test
    public void getEventType() {
        Arrays.stream(ApplyEventTypes.values()).forEach(applyEventTypes -> {
            assertThat(EventTypes.getEventType(applyEventTypes.name())).isNotNull();
        });
    }
}
