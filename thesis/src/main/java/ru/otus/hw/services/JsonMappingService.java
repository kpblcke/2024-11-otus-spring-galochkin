package ru.otus.hw.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;
import ru.otus.hw.dto.EventShortDTO;
import ru.otus.hw.dto.EventJson;

@Service
public class JsonMappingService {

    private final ObjectMapper objectMapper;

    public JsonMappingService(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public EventShortDTO mapStringToRequest(String jsonString) {
        try {
            return objectMapper.readValue(jsonString, EventShortDTO.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Failed to parse JSON string", e);
        }
    }

    public EventJson testMapStringToRequest(String jsonString) {
        try {
            return objectMapper.readValue(jsonString, EventJson.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Failed to parse JSON string", e);
        }
    }
}