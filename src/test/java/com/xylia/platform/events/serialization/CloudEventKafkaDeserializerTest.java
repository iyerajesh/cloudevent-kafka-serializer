package com.xylia.platform.events.serialization;

import com.google.common.collect.Maps;
import com.xylia.platform.events.model.ApplyEventTypes;
import io.cloudevents.v1.CloudEventBuilder;
import io.cloudevents.v1.CloudEventImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.time.ZonedDateTime;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class CloudEventKafkaDeserializerTest {

    private static final String TYPE = ApplyEventTypes.APPLICATION_SUBMITTED_EVENT.getType();
    private static final URI DATA_SCHEMA_URL = URI.create("url/dataschema");
    private static final String DATA_CONTENT_TYPE = "application/json";
    private static final String SUBJECT = "subject";
    private static final ZonedDateTime TIME = ZonedDateTime.parse("2007-12-03T10:15:30+01:00[Europe/Paris]");
    private static final String DATA = "{}";
    private static final URI SOURCE_URL = URI.create("url/source");
    private static final String ID = "e072fb68-e1e9-4ff3-a871-567901285f60";
    private static final String TOPIC = "topic";

    private static final Map<String, String> dataMap = Maps.newHashMap();

    private CloudEventKafkaSerializer serializer;
    private CloudEventKafkaDeserializer deserializer;

    static byte[] resourceOf(String name) throws IOException {
        InputStream stream = Thread.currentThread().getContextClassLoader().getResourceAsStream(name);
        return stream.readAllBytes();
    }

    @BeforeEach
    public void setUp() {
        serializer = new CloudEventKafkaSerializer();
        deserializer = new CloudEventKafkaDeserializer();

        dataMap.put("key1", "value1");
        dataMap.put("key2", "value2");
        dataMap.put("key3", "value3");
        dataMap.put("key4", "value4");
    }


    @Test
    public void deserilizeWithoutEncryptedDataPayload() throws IOException {

        CloudEventImpl cloudEvent = deserializer.deserialize(TOPIC, resourceOf("data-payload-integrity.json"));

        assertThat(cloudEvent.getData().get()).isInstanceOf(String.class);
        assertThat(cloudEvent.getAttributes().getId()).isEqualTo(ID);
        assertThat(cloudEvent.getAttributes().getType()).isEqualTo(ApplyEventTypes.APPLICATION_SUBMITTED_EVENT.getType());
        assertThat(cloudEvent.getAttributes().getSpecversion()).isEqualTo("1.0");
    }

    @Test
    public void createDeserializeAndValidatePayloadIntegrity() {

        CloudEventImpl cloudEvent = CloudEventBuilder.builder()
                .withId(ID)
                .withTime(TIME)
                .withType(TYPE)
                .withSource(SOURCE_URL)
                .withSubject(SUBJECT)
                .withDataschema(DATA_SCHEMA_URL)
                .withDataContentType(DATA_CONTENT_TYPE)
                .withData(dataMap)
                .build();

        byte[] serializedPayload = serializer.serialize(TOPIC, cloudEvent);
        CloudEventImpl deserializedCloudEvent = deserializer.deserialize(TOPIC, serializedPayload);

        String serializedString = new String(serializedPayload);
        assertThat(deserializedCloudEvent.getData().get()).isInstanceOf(Map.class);

        assertThat(((Map) deserializedCloudEvent.getData().get()).get("key1")).isEqualTo("value1");
        assertThat(((Map) deserializedCloudEvent.getData().get()).get("key2")).isEqualTo("value2");
        assertThat(((Map) deserializedCloudEvent.getData().get()).get("key3")).isEqualTo("value3");
        assertThat(((Map) deserializedCloudEvent.getData().get()).get("key4")).isEqualTo("value4");

        assertThat(deserializedCloudEvent.getAttributes().getType()).isEqualTo("com.xylia.platform.events.ApplicationSubmittedEvent");
        assertThat(deserializedCloudEvent.getAttributes().getSpecversion()).isEqualTo("1.0");
        assertThat(deserializedCloudEvent.getAttributes().getDatacontenttype().get()).isEqualTo("application/json");
        assertThat(deserializedCloudEvent.getAttributes().getSubject().get()).isEqualTo(SUBJECT);
    }




}