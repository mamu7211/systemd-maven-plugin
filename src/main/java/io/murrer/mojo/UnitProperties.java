package io.murrer.mojo;

import io.murrer.utils.FileConstants;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

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

    @NotBlank(message = "Unit fileName must not be empty. This will be the unit file generated.")
    private String fileName = REFERENCE_FILENAME + FileConstants.EXTENSION_UNIT_FILE;

    private String templateFileLocation;

    /**
     * Configuration option for [Unit] Description
     */
    @NotBlank(message = "Units [Unit] directive 'description' must not be empty. " +
            "Default will be '\\${project.artifactId}'.")
    private String description = REFERENCE_DESCRIPTION;

    /**
     * Configuration option for [Unit] After
     */
    @NotBlank(message = "Unit directive 'after' must not be empty. " +
            "Default will be 'network.target'.")
    private String after = DEFAULT_AFTER;

    /**
     * Configuration option for [Unit] User
     */
    @NotBlank(message = "Units [Service] directive 'user' must not be empty. ")
    private String user = REFERENCE_USER;

    /**
     * Configuration option for [Unit] WantedBy=${unit.wantedBy}
     */
    @NotBlank(message = "Units [Install] directive 'wantedBy' must not be empty. " +
            "Default will be 'multi-user.target'.")
    private String wantedBy = DEFAULT_WANTED_BY;

    /**
     * Configuration option for [Unit] Type
     */
    @NotBlank(message = "Unit [Service] directive 'type' must not be empty. " +
            "Default will be 'simple'.")
    private String type = DEFAULT_TYPE;

    /**
     * Configuration option for [Unit] Restart
     */
    @NotBlank(message = "Units [Service] directive 'restart' must not be empty. " +
            "Default will be 'always'")
    private String restart = DEFAULT_RESTART;

    @Override
    protected String getTemplateFileName() {
        return "templates/unit" + FileConstants.EXTENSION_UNIT_FILE;
    }
}
