package com.xylia.platform.events.model;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

import static com.xylia.platform.events.util.Util.getActiveRuntimeProfile;

@Slf4j
public class EventTypes {

    public static final String APPLY_EVENT_TYPES_YML = "apply-event-types.yml";
    private static ObjectMapper om = new ObjectMapper(new YAMLFactory());
    private static EventTypes ourInstance = new EventTypes();

    private Map<String, Map> events;

    private EventTypes() {
        try {
            Map<String, Map> events = Maps.newHashMap();
            InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream(APPLY_EVENT_TYPES_YML);

            if (inputStream != null)
                events = om.readValue(inputStream, Map.class);

            this.events = events;
        } catch (JsonParseException e) {
            log.error("Error while creating the EventTypes object!", e);
        } catch (JsonMappingException e) {
            log.error("Error while creating the EventTypes object!", e);
        } catch (IOException e) {
            log.error("Error while creating the EventTypes object!", e);
        }
    }

    public static ApplyEvent getEventType(String type) {
        ApplyEvent applyEvent = new ApplyEvent();

        applyEvent.setType((String) ourInstance.events.get(type).get("type"));
        applyEvent.setTopic((String) ourInstance.events.get(type).get("topic"));

        return applyEvent;
    }

    private static final String mapActiveRuntimeProfile() {

        String runtimeProfile = getActiveRuntimeProfile();
        String profileType = System.getProperty("PROFILE_TYPE");

        log.info("The active profile in the serializer is: {}, and the profile type is: {}", runtimeProfile, profileType);

        if (profileType != null)
            return "-" + profileType + ".";

        return ".";
    }
}
