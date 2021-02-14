package com.xylia.platform.events.model;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class ApplyEventTest {

    public static final String TOPIC = "test-topic";
    public static final String TYPE = "com.xylia.platform.events.ApplicationCapturedEvent";


    @Test
    public void testContructionOfApplyEvent() {
        ApplyEvent applyEvent = new ApplyEvent();
        applyEvent.setTopic(TOPIC);
        applyEvent.setType(TYPE);

        assertThat(applyEvent.getTopic().equals(TOPIC));
        assertThat(applyEvent.getType().equals(TYPE));
    }

    @Test
    public void testApplyEventEquals() {
        ApplyEvent applyEvent = new ApplyEvent();
        applyEvent.setType(TYPE);
        applyEvent.setTopic(TOPIC);

        ApplyEvent applyEvent1 = new ApplyEvent();
        applyEvent1.setType(TYPE);
        applyEvent1.setTopic(TOPIC);

        assertThat(applyEvent).isEqualTo(applyEvent1);
    }

}