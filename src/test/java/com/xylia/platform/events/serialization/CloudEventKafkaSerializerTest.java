package com.xylia.platform.events.serialization;

import com.xylia.platform.events.builder.ApplyDataContentTypes;
import com.xylia.platform.events.model.ApplyEventTypes;
import io.cloudevents.extensions.ExtensionFormat;
import io.cloudevents.extensions.InMemoryFormat;
import io.cloudevents.v1.CloudEventBuilder;
import io.cloudevents.v1.CloudEventImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.net.URI;
import java.time.ZonedDateTime;

import static io.cloudevents.extensions.ExtensionFormat.of;
import static org.assertj.core.api.Assertions.assertThat;


public class CloudEventKafkaSerializerTest {

    private static final String TYPE = ApplyEventTypes.APPLICATION_SUBMITTED_EVENT.getType();
    private static final URI DATA_SCHEMA_URL = URI.create("url/dataschema");
    private static final String DATA_CONTENT_TYPE = "application/json";
    private static final String SUBJECT = "subject";
    private static final ZonedDateTime TIME = ZonedDateTime.parse("2007-12-03T10:15:30+01:00[Europe/Paris]");
    private static final String DATA = "{}";
    private static final URI SOURCE_URL = URI.create("url/source");
    private static final String ID = "e072fb68-e1e9-4ff3-a871-567901285f60";
    private static final String TOPIC = "topic";

    private CloudEventKafkaSerializer serializer;
    private CloudEventKafkaDeserializer deserializer;

    @BeforeEach
    public void setUp() {
        serializer = new CloudEventKafkaSerializer();
        deserializer = new CloudEventKafkaDeserializer();
    }

    @Test
    public void testAndVerifySerialization() {

        CloudEventImpl cloudEvent = CloudEventBuilder.builder()
                .withId(ID)
                .withTime(TIME)
                .withType(TYPE)
                .withSource(SOURCE_URL)
                .withSubject(SUBJECT)
                .withDataschema(DATA_SCHEMA_URL)
                .withDataContentType(DATA_CONTENT_TYPE)
                .withData(DATA)
                .build();

        assertThat(new String(serializer.serialize(TOPIC, cloudEvent)))
                .contains("{\"data\":\"{}\",\"id\":\"e072fb68-e1e9-4ff3-a871-567901285f60\",\"source\":\"url/source\",\"specversion\":\"1.0\",\"type\":\"com.xylia.platform.events.ApplicationSubmittedEvent\",\"datacontenttype\":\"application/json\",\"dataschema\":\"url/dataschema\",\"subject\":\"subject\",\"time\":\"2007-12-03T10:15:30+01:00\"}");
    }

    @Test
    public void serializeWithNoEncryptionAndValidatePayload() {

        CloudEventImpl cloudEvent = CloudEventBuilder.builder()
                .withId(ID)
                .withTime(TIME)
                .withType(TYPE)
                .withSource(SOURCE_URL)
                .withSubject(SUBJECT)
                .withDataschema(DATA_SCHEMA_URL)
                .withDataContentType(DATA_CONTENT_TYPE)
                .withData(DATA)
                .build();

        byte[] serializedPayload = serializer.serialize(TOPIC, cloudEvent);
        CloudEventImpl deserializedEvent = deserializer.deserialize(TOPIC, serializedPayload);

        assertThat(deserializedEvent.getAttributes().getType()).isEqualTo(TYPE);
        assertThat(deserializedEvent.getData().get()).isEqualTo(DATA);
        assertThat(deserializedEvent.getAttributes().getId()).isEqualTo(ID);
    }

    @Test
    public void shouldHaveCustomExtension() {

        String applicationId = "applicationId";
        String value = "SOME_APPLICATION_ID";

        ExtensionFormat extensionFormat = of(InMemoryFormat.of(applicationId, value, String.class),
                applicationId, value);

        CloudEventImpl cloudEvent = CloudEventBuilder.builder()
                .withId(ID)
                .withTime(TIME)
                .withType(TYPE)
                .withSource(SOURCE_URL)
                .withSubject(SUBJECT)
                .withDataschema(DATA_SCHEMA_URL)
                .withDataContentType(ApplyDataContentTypes.NO_ENCRYPTION.getContentType())
                .withData(DATA)
                .withExtension(extensionFormat)
                .build();

        byte[] serializedPayload = serializer.serialize(TOPIC, cloudEvent);
        CloudEventImpl deserializedPayload = deserializer.deserialize(TOPIC, serializedPayload);

        assertThat(deserializedPayload.getExtensions().get(applicationId)).isEqualTo(value);
    }
}