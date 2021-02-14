package com.xylia.platform.events.model;

import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

@Getter
@Setter
public class ApplyEvent {

    private String topic;
    private String type;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ApplyEvent that = (ApplyEvent) o;
        return Objects.equals(topic, that.topic) && Objects.equals(type, that.type);
    }

    @Override
    public int hashCode() {
        return Objects.hash(topic, type);
    }

    @Override
    public String toString() {
        return "ApplyEvent{" +
                "topic='" + topic + '\'' +
                ", type='" + type + '\'' +
                '}';
    }
}
