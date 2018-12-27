package io.murrer.mojo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.project.MavenProject;

@Getter
@NoArgsConstructor
@ToString
public class InstallProperties extends AbstractMojoProperties {

    private String installationDirectory;


    @Override
    public void updateDefaults(MavenProject project) throws MojoExecutionException {
        installationDirectory = defaultValue(
                installationDirectory,
                String.format("/opt/%s/%s/", project.getArtifactId(), project.getVersion())
        );
    }

    @Override
    protected String getTemplateFileName() {
        return "templates/install.sh";
    }
}
