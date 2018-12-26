package io.murrer.mojo;

import io.murrer.utils.AbstractComplexMojoProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.project.MavenProject;

@Data
@NoArgsConstructor
@ToString
public class Unit extends AbstractComplexMojoProperty {

    private String description;

    private String workingDirectory;

    private String user;

    private String name;

    public void updateDefaults(MavenProject project) throws MojoExecutionException {

        workingDirectory = defaultValue(workingDirectory, String.format("/opt/%s/%s/", project.getArtifactId(), project.getVersion()));
        description = defaultValue(description, project.getDescription());
        name = defaultValue(description,project.getArtifactId());

        mandatoryValue(user, "user");
    }
}
