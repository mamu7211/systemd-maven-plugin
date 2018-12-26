package io.murrer.utils;

import io.murrer.mojo.Unit;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.apache.maven.plugin.testing.AbstractMojoTestCase;
import org.apache.maven.project.MavenProject;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.time.Instant;

public class TemplateProcessorTest extends AbstractMojoTestCase {

    private MavenProject project;
    private Unit unit;

    @Before
    public void setUp() {
        project = new MavenProject();
        project.setArtifactId("PROJECT-ARTIFACT-ID");
        project.setGroupId("PROJECT-GROUP-ID");
        project.setDescription("PROJECT-DESCRIPTION");
        project.setVersion("PROJECT-VERSION");

        unit = new Unit();
        unit.setName("UNIT-NAME");
        unit.setDescription("UNIT-DESCRIPTION");
        unit.setUser("UNIT-USER");
        unit.setWorkingDirectory("WORKING-DIRECTORY");
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

        String actual = TemplateProcessor.process(template, project, unit);

        assertEquals(expected, actual);
    }

    @Test
    public void testUnitReplacements() throws IOException, ClassNotFoundException {
        String template = "${unit.name}" +
                ":${unit.description}" +
                ":${unit.user}" +
                ":${unit.workingDirectory}";
        String expected = String.format("%s:%s:%s:%s",
                unit.getName(),
                unit.getDescription(),
                unit.getUser(),
                unit.getWorkingDirectory()
        );

        String actual = TemplateProcessor.process(template, project, unit);

        assertEquals(expected, actual);
    }
}
