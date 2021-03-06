package io.murrer.mojo;

import io.murrer.templating.MojoContext;
import io.murrer.templating.TemplateProcessor;
import io.murrer.utils.SystemUtils;
import io.murrer.worker.TemplateFileWriter;
import io.murrer.worker.ZipCreator;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.project.MavenProject;

import java.io.File;
import java.io.IOException;

@Mojo(
        name = "bundle",
        defaultPhase = LifecyclePhase.PACKAGE,
        threadSafe = true
)
public class SystemdBundlerMojo extends AbstractMojo {

    @Parameter(defaultValue = "true")
    private boolean zipFile = true;

    @Parameter(defaultValue = "${project}", readonly = true)
    private MavenProject project;

    @Parameter(defaultValue = "${unitProperties}")
    private UnitProperties unit = new UnitProperties();

    @Parameter(defaultValue = "${installProperties}")
    private InstallProperties install;

    @Parameter(defaultValue = "${run}")
    private RunProperties run;

    @Parameter(defaultValue = "${environment}")
    private EnvironmentProperties environment;

    private ZipCreator zipCreator;
    private MojoContext mojoContext;
    private TemplateFileWriter templateFileWriter;

    @Override
    public void execute() throws MojoExecutionException, MojoFailureException {
        if (project.getPackaging().matches("jar")) {
            try {
                setup();

                //TODO - check which artifact gets created
                File jarFile = new File(project.getBuild().getDirectory(), project.getBuild().getFinalName() + ".jar");

                File unitFile = getUnitFile();
                File installFile = getInstallFile();
                File serviceFile = getServiceFile();
                File environmentFile = getEnvironmentFile();

                if (zipFile) {
                    zipCreator.zip(jarFile, serviceFile, unitFile, installFile, environmentFile);
                }
            } catch (IOException e) {
                throw new MojoExecutionException("Failed to create zip file.");
            }
        } else {
            getLog().warn(String.format("Ignoring project '%s:%s:%s' with packaging 's'.", project.getGroupId(), project.getArtifactId(), project.getVersion(), project.getPackaging()));
        }
    }

    private File getEnvironmentFile() throws MojoExecutionException {
        return templateFileWriter.writeFile(
                TemplateProcessor.process(mojoContext.getEnvironment().getFileName(), mojoContext),
                mojoContext.getEnvironment().getTemplate()
        );
    }

    private File getServiceFile() throws MojoExecutionException {
        File serviceFile = templateFileWriter.writeFile(
                TemplateProcessor.process(mojoContext.getRun().getFileName(), mojoContext),
                mojoContext.getRun().getTemplate()
        );
        serviceFile.setExecutable(true);
        return serviceFile;
    }

    private File getInstallFile() throws MojoExecutionException {
        File installFile = templateFileWriter.writeFile(
                TemplateProcessor.process(mojoContext.getInstall().getFileName(), mojoContext),
                mojoContext.getInstall().getTemplate()
        );
        installFile.setExecutable(true);
        return installFile;
    }

    private File getUnitFile() throws MojoExecutionException {
        return templateFileWriter.writeFile(
                TemplateProcessor.process(mojoContext.getUnit().getFileName(), mojoContext),
                mojoContext.getUnit().getTemplate()
        );
    }

    private MojoContext setup() throws MojoExecutionException {

        mojoContext = new MojoContext(getLog(), project, new SystemUtils(), unit, run, install, environment);

        zipCreator = new ZipCreator(mojoContext);
        templateFileWriter = new TemplateFileWriter(mojoContext);

        return null;
    }
}

