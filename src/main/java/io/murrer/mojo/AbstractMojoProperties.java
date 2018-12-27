package io.murrer.mojo;

import io.murrer.utils.ResourceUtils;
import lombok.Data;
import org.apache.commons.io.FileUtils;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.project.MavenProject;

import java.io.File;
import java.io.IOException;

@Data
public abstract class AbstractMojoProperties {

    private String templateFileLocation;

    private String fileName;

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

    private String getTemplateContent() throws MojoExecutionException {
        if (templateFileLocation == null) {
            try {
                return ResourceUtils.textOf(getTemplateFileName());
            } catch (IOException e) {
                throw new MojoExecutionException(String.format("Failed to read default template '%s' from plugin resources.", getTemplateFileName()), e);
            }
        } else {
            File file = new File(templateFileLocation);
            if (file.exists()) {
                try {
                    return FileUtils.readFileToString(file);
                } catch (IOException e) {
                    throw new MojoExecutionException(String.format("Failed to read template file '%s'.", templateFileLocation), e);
                }
            } else {
                throw new MojoExecutionException(String.format("File '%s' not found.", templateFileLocation));
            }
        }
    }

    public String getTemplate() throws MojoExecutionException {
        return getTemplateContent();
    }

    protected abstract String getTemplateFileName();
}
