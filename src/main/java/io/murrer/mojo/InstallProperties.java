package io.murrer.mojo;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import static io.murrer.utils.FileConstants.EXTENSION_SCRIPT;

@Data
@NoArgsConstructor
@ToString
public class InstallProperties extends AbstractMojoProperties {

    public static final String DEFAULT_BASE_DIRECTORY = "/var";
    public static final String DEFAULT_DIRECTORY = DEFAULT_BASE_DIRECTORY + "/${project.artifactId}/${project.version}";
    public static final boolean DEFAULT_OVERWRITE_INSTALLED = false;
    public static final boolean DEFAULT_START_SERVICE = true;
    public static final String DEFAULT_INSTALL_FILE_NAME = "install" + EXTENSION_SCRIPT;

    private String fileName = DEFAULT_INSTALL_FILE_NAME;

    private String directory = DEFAULT_DIRECTORY;

    private boolean overwriteInstalled = DEFAULT_OVERWRITE_INSTALLED;

    private boolean startService = DEFAULT_START_SERVICE;

    @Override
    protected String getTemplateFileName() {
        return "templates/install.sh";
    }
}
