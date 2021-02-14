package com.xylia.platform.events.model;

import lombok.Getter;

import java.util.Arrays;
import java.util.Optional;

@Getter
public enum ApplyEventTypes {

    APPLICATION_SUBMITTED_EVENT,
    APPLICATION_CAPTURED_EVENT,
    APPLICATION_FAILED_EVENT,
    APPLICATION_APPROVED_EVENT,
    APPLICATION_CANCELLED_EVENT,
    APPLICATION_PENDED_EVENT,
    APPLICATION_DECLINED_EVENT;


    private String topic;
    private String type;

    ApplyEventTypes() {

        this.topic = EventTypes.getEventType(this.name()).getTopic();
        this.type = EventTypes.getEventType(this.name()).getType();
    }

    public static String getTopicByType(String type) {
        Optional<ApplyEventTypes> applyEventTypes = Arrays.stream(ApplyEventTypes.values())
                .filter(applyEventType -> applyEventType.getType().equals(type))
                .findFirst();

        if (applyEventTypes.isPresent())
            return applyEventTypes.get().getTopic();
        return null;
    }

    public static boolean exists(String type) {
        Optional<ApplyEventTypes> applyEventTypes = Arrays.stream(ApplyEventTypes.values())
                .filter(applyEventType -> applyEventType.getType().equals(type))
                .findFirst();

        if (applyEventTypes.isPresent())
            return true;

        return false;
    }
}

