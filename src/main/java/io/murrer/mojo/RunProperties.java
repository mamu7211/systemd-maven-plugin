package io.murrer.mojo;

import io.murrer.utils.FileConstants;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@ToString
public class RunProperties extends AbstractMojoProperties {

    public static final String DEFAULT_RUN_FILENAME = "${project.artifactId}" + FileConstants.EXTENSION_SCRIPT;
    public static final String DEFAULT_JAVA_PATH = "/usr/bin/java";

    private String fileName = DEFAULT_RUN_FILENAME;

    @NotBlank(message = "Run javaPath must not be empty, default is '" + DEFAULT_JAVA_PATH + "'.")
    private String javaPath = DEFAULT_JAVA_PATH;

    @Override
    protected String getTemplateFileName() {
        return "templates/service.sh";
    }
}
