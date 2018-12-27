package io.murrer.utils;

import io.murrer.mojo.EnvironmentProperties;
import io.murrer.mojo.InstallProperties;
import io.murrer.mojo.RunProperties;
import io.murrer.mojo.UnitProperties;
import io.murrer.templating.MojoContext;
import io.murrer.templating.TemplateProcessor;
import org.apache.maven.plugin.logging.Log;
import org.apache.maven.plugin.testing.AbstractMojoTestCase;
import org.apache.maven.project.MavenProject;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.io.IOException;

import static org.mockito.Mockito.mock;

public class TemplateProcessorTest extends AbstractMojoTestCase {

    private MavenProject project;
    private UnitProperties unitProperties;
    private MojoContext mojoContext;
    private Log log;
    private RunProperties runProperties;
    private InstallProperties installProperties;
    private EnvironmentProperties environmentProperties;

    @Before
    public void setUp() {

        log = mock(Log.class);

        project = new MavenProject();
        project.setArtifactId("PROJECT-ARTIFACT-ID");
        project.setGroupId("PROJECT-GROUP-ID");
        project.setDescription("PROJECT-DESCRIPTION");
        project.setVersion("PROJECT-VERSION");

        unitProperties = new UnitProperties();
        unitProperties.setDescription("UNIT-DESCRIPTION");
        unitProperties.setUser("UNIT-USER");
        unitProperties.setWorkingDirectory("WORKING-DIRECTORY");

        mojoContext = new MojoContext(log, project, unitProperties, runProperties, installProperties, environmentProperties);
    }

    @Test
    public void testProjectReplacements() throws IOException, ClassNotFoundException {
        String template = "${project.groupId}" +
                ":${project.artifactId}" +
                ":${project.version}" +
                ":${project.description}";
        String expected = String.format("%s:%s:%s:%s",
                project.getGroupId(),
                project.getArtifactId(),
                project.getVersion(),
                project.getDescription()
        );

        String actual = TemplateProcessor.process(template, mojoContext);

        assertEquals(expected, actual);
    }

    @Test
    public void testUnitReplacements() throws IOException, ClassNotFoundException {
        String template = "${unit.description}\n" +
                "${unit.after}\n" +
                "${unit.workingDirectory}\n" +
                "${unit.user}\n" +
                "${unit.wantedBy}\n";

        String expected = String.format("%s\n%s\n%s\n%s\n%s\n",
                unitProperties.getDescription(),
                unitProperties.getAfter(),
                unitProperties.getWorkingDirectory(),
                unitProperties.getUser(),
                unitProperties.getWantedBy()
        );

        String actual = TemplateProcessor.process(template, mojoContext);

        assertEquals(expected, actual);
    }

    @Test
    public void testUnitResourceTemplate() throws IOException, ClassNotFoundException {
        TemplateProcessor.process(ResourceUtils.textOf("templates/unit.service"), mojoContext);
    }

    @Test
    public void testEnvironmentResourceTemplate() throws IOException, ClassNotFoundException {
        TemplateProcessor.process(ResourceUtils.textOf("templates/environment.cfg"), mojoContext);
    }

    @Test
    public void testInstallResourceTemplate() throws IOException, ClassNotFoundException {
        TemplateProcessor.process(ResourceUtils.textOf("templates/install.sh"), mojoContext);
    }

    @Test
    public void testRunResourceTemplate() throws IOException, ClassNotFoundException {
        TemplateProcessor.process(ResourceUtils.textOf("templates/run.sh"), mojoContext);
    }
}
