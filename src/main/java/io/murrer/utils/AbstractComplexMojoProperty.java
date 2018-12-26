package io.murrer.utils;

import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.project.MavenProject;

public abstract class AbstractComplexMojoProperty {

    public abstract void updateDefaults(MavenProject project) throws MojoExecutionException;

    protected void mandatoryValue(Object o, String propertyName) throws MojoExecutionException {
        if (o == null) {

            throw new MojoExecutionException(
                    String.format("Property '%s' of %s is mandatory but no value was set.", propertyName, getClass().getSimpleName()
                    ));
        }
    }

    protected <T> T defaultValue(T value, T defaultValue) {
        return value == null ? defaultValue : value;
    }
}
