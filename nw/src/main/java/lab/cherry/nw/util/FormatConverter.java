package lab.cherry.nw.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class FormatConverter {

    public static String toJson(Object object) {
        ObjectMapper mapper = new ObjectMapper();
        String json = null;

        try {
            json = mapper.writeValueAsString(object);
        }
        catch (JsonProcessingException e) {
            log.error(e.getMessage());
        }

        return json;
    }
}