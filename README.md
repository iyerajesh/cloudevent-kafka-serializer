# cloudevent-kafka-serializer
Kafka serializer/deserializer Java library, which is responsible for the serialization and deserialization of CloudEvent messages.

## Usage
In your gradle projects, add the following dependency in your build.gradle.
```groovy
implementation 'com.xylia.platform.events:cloudevent-kafka-serializer:1.0.0'
```

In your Spring Kafka producer configuration properties:
```
props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, CloudEventKafkaSerializer.class)
```

In your Spring Kafka consumer properties:
```
props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, CloudEventKafkaDeserializer.class);
```

The serializer/deserializer module currently does not support encryption/decryption of the data payload.
To create a CloudEvent message, please do the following:

```
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

```

The serializer configuration also relies on the:
- Environment variable `EPASS_ENV` this is used to get the environment specific profile configurations.
- System property `PROFILE_TYPE` this is used to change the topic types.
