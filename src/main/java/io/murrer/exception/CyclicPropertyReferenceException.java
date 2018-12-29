package io.murrer.exception;

public class CyclicPropertyReferenceException extends SystemdMojoExecutionException {
    public CyclicPropertyReferenceException(String message) {
        super(message);
    }
}
