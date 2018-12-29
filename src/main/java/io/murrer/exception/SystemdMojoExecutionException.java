package io.murrer.exception;

import org.apache.maven.plugin.MojoExecutionException;

public class SystemdMojoExecutionException extends MojoExecutionException {
    public SystemdMojoExecutionException(Object source, String shortMessage, String longMessage) {
        super(source, shortMessage, longMessage);
    }

    public SystemdMojoExecutionException(String message, Exception cause) {
        super(message, cause);
    }

    public SystemdMojoExecutionException(String message, Throwable cause) {
        super(message, cause);
    }

    public SystemdMojoExecutionException(String message) {
        super(message);
    }
}
