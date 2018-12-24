package io.murrer.creator;

import io.murrer.tool.AbstractWorker;
import io.murrer.utils.ResourceUtils;
import org.apache.commons.io.FileUtils;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.logging.Log;
import org.apache.maven.project.MavenProject;

import java.io.File;
import java.io.IOException;

public class InstallFileCreator extends AbstractWorker {

    public InstallFileCreator(MavenProject project, Log log) {
        super(project, log);
    }

    public File write() throws MojoExecutionException, IOException {
        File installFile = createFile("install.sh");
        FileUtils.copyInputStreamToFile(
                ResourceUtils.resourceStreamOf("install.sh"),
                installFile
        );
        getLog().info(String.format("Created install script '%s'.", installFile.getAbsolutePath()));
        return installFile;
    }
}
