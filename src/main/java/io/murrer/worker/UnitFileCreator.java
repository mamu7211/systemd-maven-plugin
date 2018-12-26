package io.murrer.worker;

import io.murrer.mojo.Install;
import io.murrer.mojo.Unit;
import io.murrer.utils.ResourceUtils;
import io.murrer.utils.TemplateProcessor;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.logging.Log;
import org.apache.maven.project.MavenProject;

import java.io.File;
import java.io.IOException;

public class UnitFileCreator extends AbstractWorker {

    public UnitFileCreator(MavenProject project, Log log) {
        super(project, log);
    }

    public File write(MavenProject project, Unit unit, Install install) throws MojoExecutionException, IOException {

        File unitFile = createFile(unit.getName() + ".service");

        try {
            TemplateProcessor.process(
                    ResourceUtils.textOf("template.service"),
                    project,
                    unit
            );

            getLog().info(String.format("Created systemd service unit '%s'.", unitFile.getAbsolutePath()));

        } catch (ClassNotFoundException e) {
            throw new MojoExecutionException("Failed to create unit file.", e);
        }
        return unitFile;
    }

}
