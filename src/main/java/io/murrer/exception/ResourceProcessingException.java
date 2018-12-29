package io.murrer.exception;

import java.io.IOException;

public class ResourceProcessingException extends SystemdMojoExecutionException {

    public static final String RESOURCE_PROCESSING_FAILED = "Resource processing of '%s' failed.";

    public ResourceProcessingException(String resourceName) {
        super(getMessage(resourceName));
    }

    public ResourceProcessingException(String resourceName, IOException e) {
        super(getMessage(resourceName), e);
    }

    private static String getMessage(String resourceName) {
        return String.format(RESOURCE_PROCESSING_FAILED, resourceName);
    }
}
