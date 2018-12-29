package io.murrer.utils;

import io.murrer.exception.ResourceProcessingException;
import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

public class ResourceUtils {

    public static InputStream resourceStreamOf(String fileName) {
        return ResourceUtils.class.getClassLoader().getResourceAsStream(fileName);
    }

    public static String textOf(String fileName) throws ResourceProcessingException {
        InputStream input = resourceStreamOf(fileName);
        if (input == null) {
            throw new ResourceProcessingException(fileName);
        }
        try {
            return IOUtils.toString(input, StandardCharsets.UTF_8.name());
        } catch (IOException e) {
            throw new ResourceProcessingException(fileName,e);
        }
    }

}

