package io.murrer.mojo;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import static io.murrer.utils.FileConstants.EXTENSION_CONFIGURATION;


@Data
@NoArgsConstructor
@ToString
public class EnvironmentProperties extends AbstractMojoProperties {


    public static final String DEFAULT_ENVIRONMENT_FILENAME = "environment" + EXTENSION_CONFIGURATION;
    private static final String DEFAULT_ENVIRONMENT_DIRECTORY = "${install.directory}";
    public static final boolean DEFAULT_OVERWRITE_INSTALLED = false;

    private boolean generate = true;

    private String fileName = DEFAULT_ENVIRONMENT_FILENAME;

    private String directory = DEFAULT_ENVIRONMENT_DIRECTORY;

    private boolean overwriteInstalled = DEFAULT_OVERWRITE_INSTALLED;

    @Override
    protected String getTemplateFileName() {
        return "templates/environment" + EXTENSION_CONFIGURATION;
    }
}
