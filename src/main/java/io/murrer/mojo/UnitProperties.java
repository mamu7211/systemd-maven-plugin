package io.murrer.mojo;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.project.MavenProject;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

@Data
@NoArgsConstructor
@ToString
public class UnitProperties extends AbstractMojoProperties {

    @NotBlank(message = "Unit fileName must not be empty. This will be the unit file generated.")
    private String fileName;

    private String templateFileLocation;

    /**
     * Configuration option for [Unit] Description
     */
    @NotBlank(message = "Units [Unit] directive 'description' must not be empty. " +
            "Default will be '\\${project.artifactId}'.")
    private String description;

    /**
     * Configuration option for [Unit] After
     */
    @NotBlank(message = "Unit directive 'after' must not be empty. " +
            "Default will be 'network.target'.")
    private String after = "network.target";

    /**
     * Configuration option for [Unit] WorkingDirectory
     */
    @NotBlank(message = "Units [Service] directive 'workingDirectory' must not be empty. " +
            "Default will be '/opt/\\${project.artifactID}/\\${project.version}")
    private String workingDirectory;

    /**
     * Configuration option for [Unit] User
     */
    @NotBlank(message = "Units [Service] directive 'user' must not be empty. ")
    private String user;

    /**
     * Configuration option for [Unit] WantedBy=${unit.wantedBy}
     */
    @NotBlank(message = "Units [Install] directive 'wantedBy' must not be empty. " +
            "Default will be 'multi-user.target'.")
    private String wantedBy;

    /**
     * Configuration option for [Unit] EnvironmentFile.
     */
    @NotBlank(message = "Units [Service] 'envitonmentFile' must not be empty. " +
            "Default will be 'environment.cfg'.")
    private String environmentFile = "environment.cfg";

    /**
     * Configuration option for [Unit] Type
     */
    @NotBlank(message = "Unit [Service] directive 'type' must not be empty. " +
            "Default will be 'simple'.")
    @Pattern(regexp = "^(simple|forking|oneshot|dbus|notify)$",
            message = "Units [Service] directive 'type' must be one of [simple|forking|oneshot|dbus|notify].")
    private String type = "simple";

    /**
     * Configuration option for [Unit] ExecStart
     */
    @NotBlank(message = "Unit [Service] execStart must not be empty. " +
            "Default will be set so that run.sh is executed.")
    private String execStart;

    /**
     * Configuration option for [Unit] Restart
     */
    @NotBlank(message = "Units [Service] directive 'restart' must not be empty. " +
            "Default will be 'always'")
    private String restart = "always";


    @Override
    public void updateDefaults(MavenProject project) throws MojoExecutionException {
        fileName = String.format("%s.service", defaultValue(fileName, project.getArtifactId()));

        description = defaultValue(description, project.getDescription());
        workingDirectory = defaultValue(workingDirectory, String.format("/opt/%s/%s/", project.getArtifactId(), project.getVersion()));
        user = defaultValue(user, "user");
        after = defaultValue(after, "network.target");
        wantedBy = defaultValue(wantedBy, "multi-user.target");
        environmentFile = defaultValue(environmentFile, workingDirectory + "environment.cfg");
        type = defaultValue(type, "simple");
        execStart = defaultValue(execStart, "run.sh");
    }

    @Override
    protected String getTemplateFileName() {
        return "templates/unit.service";
    }
}
