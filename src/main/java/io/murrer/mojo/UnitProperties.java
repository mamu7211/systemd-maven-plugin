package io.murrer.mojo;

import io.murrer.utils.FileConstants;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.apache.maven.plugins.annotations.Parameter;

import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@ToString
public class UnitProperties extends AbstractMojoProperties {

    public static final String RESTART_DIRECTIVE_VALUES = "always|on-success|on-failure|on-abnormal|on-abort|on-watchdog";
    public static final String TYPE_DIRECTIVE_VALUES = "simple|forking|oneshot|dbus|notify";

    public static final String REFERENCE_USER = "${user.name}";
    public static final String REFERENCE_DESCRIPTION = "${project.description}";
    public static final String REFERENCE_FILENAME = "${project.artifactId}";

    public static final String DEFAULT_TYPE = "simple";
    public static final String DEFAULT_WANTED_BY = "multi-user.target";
    public static final String DEFAULT_AFTER = "network.target";
    public static final String DEFAULT_RESTART = "always";
    public static final String DEFAULT_EXEC_START = "${run.javaPath}/java -jar ${project.build.finalName}.jar";

    @NotBlank(message = "Unit fileName must not be empty. This will be the unit file generated.")
    private String fileName = REFERENCE_FILENAME + FileConstants.EXTENSION_UNIT_FILE;

    @Parameter(readonly = true)
    private String templateFileLocation;

    /**
     * Configuration option for [Unit] Description
     */
    @NotBlank(message = "Units [Unit] directive 'Description' must not be empty. " +
            "Default will be '\\${project.artifactId}'.")
    private String description = REFERENCE_DESCRIPTION;

    /**
     * Configuration option for [Unit] After
     */
    @NotBlank(message = "Units [Unit] directive 'After' must not be empty. " +
            "Default will be 'network.target'.")
    private String after = DEFAULT_AFTER;

    /**
     * Configuration option for [Unit] User
     */
    @NotBlank(message = "Units [Service] directive 'User' must not be empty. ")
    private String user = REFERENCE_USER;

    /**
     * Configuration option for [Unit] WantedBy=${unit.wantedBy}
     */
    @NotBlank(message = "Units [Install] directive 'WantedBy' must not be empty. " +
            "Default will be '" + DEFAULT_WANTED_BY + "'.")
    private String wantedBy = DEFAULT_WANTED_BY;

    /**
     * Configuration option for [Unit] Type
     */
    @NotBlank(message = "Unit [Service] directive 'Type' must not be empty. " +
            "Default will be '" + DEFAULT_TYPE + "'.")
    private String type = DEFAULT_TYPE;

    /**
     * Configuration option for [Unit] Restart
     */
    @NotBlank(message = "Units [Service] directive 'Restart' must not be empty. " +
            "Default will be '" + DEFAULT_RESTART + "'.")
    private String restart = DEFAULT_RESTART;

    @NotBlank(message = "Units [Service] directive 'ExecStart' must not be empty. " +
            "Default will be 'always'")
    private String execStart = DEFAULT_EXEC_START;

    @Override
    protected String getTemplateFileName() {
        return "templates/unit" + FileConstants.EXTENSION_UNIT_FILE;
    }
}
