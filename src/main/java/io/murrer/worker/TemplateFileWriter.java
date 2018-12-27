package io.murrer.worker;

import io.murrer.templating.MojoContext;
import io.murrer.templating.TemplateProcessor;
import io.murrer.utils.FileConstants;
import org.apache.commons.io.FileUtils;
import org.apache.maven.plugin.MojoExecutionException;

import java.io.File;
import java.io.IOException;

public class TemplateFileWriter extends AbstractWorker {

    public TemplateFileWriter(MojoContext context) {
        super(context);
    }

    public File writeFile(String targetFileName, String template) throws MojoExecutionException {

        try {

            File file = createFile(targetFileName);

            String content = TemplateProcessor.process(
                    template,
                    getContext()
            );
            FileUtils.write(file, content);

            getContext().getLog().info(String.format("Created '%s'.", file.getAbsolutePath()));

            return file;

        } catch (Exception e) {
            throw new MojoExecutionException(String.format("Failed to create %s file.", template), e);
        }
    }
}
