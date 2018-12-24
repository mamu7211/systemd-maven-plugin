package io.murrer.utils;

import java.io.InputStream;

public class ResourceUtils {

    public static InputStream resourceStreamOf(String fileName) {
        return ResourceUtils.class.getClassLoader().getResourceAsStream(fileName);
    }

}

