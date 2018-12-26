package io.murrer.utils;

import org.apache.commons.io.IOUtils;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

public class ResourceUtils {

    public static InputStream resourceStreamOf(String fileName) {
        return ResourceUtils.class.getClassLoader().getResourceAsStream(fileName);
    }

    public static String textOf(String fileName) throws IOException {
        InputStream input = resourceStreamOf(fileName);
        if (input==null){
            throw new FileNotFoundException(fileName);
        }
        return IOUtils.toString(input, StandardCharsets.UTF_8.name());
    }

}

