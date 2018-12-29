package io.murrer.exception;

public class TemplateException extends SystemdMojoExecutionException {
    public TemplateException(String message) {
        super(message);
    }

    public TemplateException(String message, Exception cause) {
        super(message, cause);
    }
}
