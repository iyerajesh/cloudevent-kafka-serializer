package com.xylia.platform.events.serialization;

import com.xylia.platform.events.exception.SerdesException;
import io.cloudevents.json.Json;
import io.cloudevents.v1.CloudEventImpl;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.header.Headers;
import org.apache.kafka.common.serialization.Serializer;

import java.util.Map;

@Slf4j
public class CloudEventKafkaSerializer implements Serializer<CloudEventImpl> {

    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {

    }

    @Override
    public byte[] serialize(String topic, CloudEventImpl cloudEvent) {
        return encodeCloudEvent(cloudEvent);
    }

    @Override
    public byte[] serialize(String topic, Headers headers, CloudEventImpl data) {
        return new byte[0];
    }

    @Override
    public void close() {

    }

    public static final byte[] encodeCloudEvent(CloudEventImpl cloudEvent) {

        byte[] serializedBytes = null;

        try {
            serializedBytes = Json.binaryEncode(cloudEvent);

        } catch (Exception e) {
            log.error("Kafka CloudEvent serialization error: {}", e);
            throw new SerdesException("Kafka CloudEvent serialization Error: {}", e);
        }
        return serializedBytes;
    }
}
