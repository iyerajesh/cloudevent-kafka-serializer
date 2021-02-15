package com.xylia.platform.events.serialization;

import io.cloudevents.json.Json;
import io.cloudevents.v1.CloudEventImpl;
import org.apache.kafka.common.header.Headers;
import org.apache.kafka.common.serialization.Deserializer;

import java.util.Map;

public class CloudEventKafkaDeserializer implements Deserializer<CloudEventImpl> {

    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {

    }

    @Override
    public CloudEventImpl deserialize(String topic, byte[] data) {
        return Json.binaryDecodeValue(data, CloudEventImpl.class, Object.class);
    }

    @Override
    public CloudEventImpl deserialize(String topic, Headers headers, byte[] data) {
        return Json.binaryDecodeValue(data, CloudEventImpl.class, Object.class);
    }

    @Override
    public void close() {

    }
}
