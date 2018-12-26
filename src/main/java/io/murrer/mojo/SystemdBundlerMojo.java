package io.murrer.mojo;

import io.murrer.worker.*;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.project.MavenProject;

import java.io.File;
import java.io.IOException;
import java.time.Instant;

@Mojo(
        name = "systemd-bundler",
        defaultPhase = LifecyclePhase.PACKAGE,
        threadSafe = true
)
public class SystemdBundlerMojo extends AbstractMojo {

    @Parameter(defaultValue = "${project}", readonly = true)
    private MavenProject project;

    @Parameter(defaultValue = "${unit}")
    private Unit unit;

    @Parameter(defaultValue = "${install}")
    private Install install;

    private ZipCreator zipCreator;
    private UnitFileCreator unitFileCreator;
    private InstallFileCreator installFileCreator;
    private RunFileCreator runFileCreator;
    private EnvironmentFileWriter environmentFileWriter;

    @Override
    public void execute() throws MojoExecutionException, MojoFailureException {
        if (project.getPackaging().matches("jar")) {
            try {
                setup();

                File unitFile = unitFileCreator.write(project, unit, install);
                File jarFile = new File(project.getBuild().getDirectory(), project.getBuild().getFinalName() + ".jar");
                File installFile = installFileCreator.write();
                File runFile = runFileCreator.write();
                File environmentFile = environmentFileWriter.write();
                zipCreator.zip(jarFile, unitFile, installFile, runFile, environmentFile);
            } catch (IOException e) {
                throw new MojoExecutionException("Failed to create zip file.");
            }
        } else {
            getLog().warn(String.format("Ignoring project '%s:%s:%s' with packaging 's'.", project.getGroupId(), project.getArtifactId(), project.getVersion(), project.getPackaging()));
        }
    }

    private void setup() throws MojoExecutionException {

        zipCreator = new ZipCreator(project, getLog());
        unitFileCreator = new UnitFileCreator(project, getLog());
        installFileCreator = new InstallFileCreator(project, getLog());
        runFileCreator = new RunFileCreator(project, getLog());
        environmentFileWriter = new EnvironmentFileWriter(project, getLog());

        unit.updateDefaults(project);
        install.updateDefaults(project);
    }
}

