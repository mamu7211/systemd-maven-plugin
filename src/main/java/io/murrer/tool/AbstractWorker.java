package io.murrer.tool;

import lombok.Getter;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.logging.Log;
import org.apache.maven.project.MavenProject;

import java.io.File;
import java.io.IOException;

@Getter
public abstract class AbstractWorker {

    private MavenProject project;
    private Log log;

    public AbstractWorker(MavenProject project, Log log) {
        this.project = project;
        this.log = log;
    }

    protected File createFile(String name) throws MojoExecutionException {
        File file = new File(project.getBuild().getDirectory(), name);

        if (file.exists()) {
            if (!file.delete()) {
                getLog().warn(String.format("Delete of '%s' might not have succeeded, trying to create it anyways.", file.getAbsolutePath()));
            }
        }

        try {
            file.createNewFile();
        } catch (IOException e) {
            throw new MojoExecutionException(String.format("Failed to create file '%s'.", file.getAbsolutePath()));
        }

        return file;
    }

    protected String getArtifactId() {
        return project.getArtifactId();
    }
}
