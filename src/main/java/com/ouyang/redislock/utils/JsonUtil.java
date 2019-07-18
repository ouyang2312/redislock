package com.ouyang.redislock.utils;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.*;

import java.io.IOException;
import java.util.List;

/**
 * @author oy
 * @description
 * @date 2019/7/18
 */
public class JsonUtil {
    private static final ObjectMapper mapper = new ObjectMapper();

    public JsonUtil() {
    }

    public static String beanToJson(Object bean) throws JsonProcessingException {
        return mapper.writeValueAsString(bean);
    }

    public static String beanToJsonPretty(Object bean) throws JsonProcessingException {
        return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(bean);
    }

    public static <T> T jsonToBean(String json, Class<T> type) throws IOException, JsonParseException, JsonMappingException {
        return mapper.readValue(json, type);
    }

    public static <T> List<T> jsonToBeans(String json, Class<T> type) throws IOException, JsonParseException, JsonMappingException {
        JavaType javaType = getCollectionType(List.class, type);
        JsonNode node = mapper.readTree(json);
        return (List) mapper.readValue(json, javaType);
    }

    public static JavaType getCollectionType(Class<?> collectionClass, Class... elementClasses) {
        return mapper.getTypeFactory().constructParametricType(collectionClass, elementClasses);
    }

    static {
        mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
    }
}
