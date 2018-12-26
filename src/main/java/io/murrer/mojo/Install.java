package io.murrer.mojo;

import io.murrer.utils.AbstractComplexMojoProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.project.MavenProject;

@Getter
@NoArgsConstructor
@ToString
public class Install extends AbstractComplexMojoProperty {

    private String installationDirectory;


    @Override
    public void updateDefaults(MavenProject project) throws MojoExecutionException {
        installationDirectory = defaultValue(
                installationDirectory,
                String.format("/opt/%s/%s/", project.getArtifactId(), project.getVersion())
        );
    }
}
