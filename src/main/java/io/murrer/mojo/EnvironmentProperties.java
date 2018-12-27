package io.murrer.mojo;

import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.project.MavenProject;

public class EnvironmentProperties extends AbstractMojoProperties {

    @Override
    public void updateDefaults(MavenProject project) throws MojoExecutionException {

    }

    @Override
    protected String getTemplateFileName() {
        return "templates/environment.cfg";
    }
}
