package io.murrer.worker;

import io.murrer.utils.ResourceUtils;
import org.apache.commons.io.FileUtils;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.logging.Log;
import org.apache.maven.project.MavenProject;

import java.io.File;
import java.io.IOException;

public class RunFileCreator extends AbstractWorker {

    public RunFileCreator(MavenProject project, Log log) {
        super(project, log);
    }

    public File write() throws MojoExecutionException, IOException {
        File installFile = createFile("run.sh");
        FileUtils.copyInputStreamToFile(
                ResourceUtils.resourceStreamOf("run.sh"),
                installFile
        );
        getLog().info(String.format("Created run script '%s'.", installFile.getAbsolutePath()));
        return installFile;
    }

}
