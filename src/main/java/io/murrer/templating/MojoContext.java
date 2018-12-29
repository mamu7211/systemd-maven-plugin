package io.murrer.templating;

import io.murrer.mojo.EnvironmentProperties;
import io.murrer.mojo.InstallProperties;
import io.murrer.mojo.RunProperties;
import io.murrer.mojo.UnitProperties;
import io.murrer.utils.SystemUtils;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.apache.maven.plugin.logging.Log;
import org.apache.maven.project.MavenProject;

@Data
@AllArgsConstructor
public class MojoContext {
    private Log log;
    private MavenProject project;
    private SystemUtils system;
    private UnitProperties unit;
    private RunProperties run;
    private InstallProperties install;
    private EnvironmentProperties environment;
}
