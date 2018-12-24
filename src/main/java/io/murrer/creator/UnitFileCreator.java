package io.murrer.creator;

import io.murrer.tool.AbstractWorker;
import io.murrer.utils.FileConstants;
import org.apache.commons.io.FileUtils;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.logging.Log;
import org.apache.maven.project.MavenProject;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class UnitFileCreator extends AbstractWorker {

    public UnitFileCreator(MavenProject project, Log log) {
        super(project, log);
    }

    public File write() throws MojoExecutionException, IOException {
        File unitFile = createFile(getArtifactId() + ".service");
        List<String> lines = createUnitFileLines();

        FileUtils.writeLines(unitFile, lines, FileConstants.LINUX_LINE_ENDING);
        getLog().info(String.format("Created systemd service unit '%s'.", unitFile.getAbsolutePath()));

        return unitFile;
    }

    private List<String> createUnitFileLines() {
        ArrayList<String> lines = new ArrayList<>();

        lines.add("[Unit]");
        lines.add("Description=" + getProject().getDescription());
        lines.add("After=network.target");
        lines.add("[Service]");
        lines.add("EnvironmentFile=environment.cfg");
        lines.add("WorkingDirectory=");
        lines.add("Type=simple");
        lines.add("ExecStart=run.sh");
        lines.add("Restart=always");
        lines.add("User=");
        lines.add("[Install]");
        lines.add("WantedBy=multi-user.target");

        return lines;
    }

}
