package io.murrer.worker;

import io.murrer.utils.FileConstants;
import io.murrer.utils.ProjectPropertyFileScanner;
import org.apache.commons.io.FileUtils;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.logging.Log;
import org.apache.maven.project.MavenProject;

import java.io.File;
import java.io.IOException;

public class EnvironmentFileWriter extends AbstractWorker {

    private final ProjectPropertyFileScanner scanner;

    public EnvironmentFileWriter(MavenProject project, Log log) {
        super(project, log);
        scanner = new ProjectPropertyFileScanner(project, log);
    }

    public File write() throws MojoExecutionException {
        File environmentFile = createFile("environment.cfg");

        getLog().debug("Writing '" + environmentFile.getAbsolutePath() + "'.");
        try {
            FileUtils.writeLines(environmentFile, scanner.getUnresolvedProperties(), FileConstants.LINUX_LINE_ENDING);
        } catch (IOException e) {
            throw new MojoExecutionException("Failed to scan properties.", e);
        }

        getLog().info(String.format("Created environment configuration '%s'.", environmentFile.getAbsolutePath()));

        return environmentFile;
    }
}
