package io.murrer.mojo;

import io.murrer.utils.FileConstants;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@ToString
public class RunProperties extends AbstractMojoProperties {

    public static final String DEFAULT_RUN_FILENAME = "${project.artifactId}" + FileConstants.EXTENSION_SCRIPT;

    private String fileName = DEFAULT_RUN_FILENAME;

    @Override
    protected String getTemplateFileName() {
        return "templates/run.sh";
    }
}
