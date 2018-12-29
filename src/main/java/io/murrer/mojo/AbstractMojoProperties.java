package io.murrer.mojo;

import io.murrer.exception.SystemdMojoExecutionException;
import io.murrer.exception.TemplateException;
import io.murrer.utils.ResourceUtils;
import lombok.Data;
import org.apache.commons.io.FileUtils;
import org.apache.maven.plugin.MojoExecutionException;

import java.io.File;
import java.io.IOException;

@Data
public abstract class AbstractMojoProperties {

    private String templateFileLocation;

    private String fileName;

    private String getTemplateContent() throws SystemdMojoExecutionException {
        if (templateFileLocation == null) {
            return ResourceUtils.textOf(getTemplateFileName());
        } else {
            File file = new File(templateFileLocation);
            if (file.exists()) {
                try {
                    return FileUtils.readFileToString(file);
                } catch (IOException e) {
                    throw new TemplateException(String.format("Failed to read template file '%s'.", templateFileLocation), e);
                }
            } else {
                throw new TemplateException(String.format("File '%s' not found.", templateFileLocation));
            }
        }
    }

    public String getTemplate() throws MojoExecutionException {
        return getTemplateContent();
    }

    protected abstract String getTemplateFileName();
}
