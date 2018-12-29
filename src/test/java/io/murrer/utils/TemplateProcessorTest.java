package io.murrer.utils;

import io.murrer.exception.CyclicPropertyReferenceException;
import io.murrer.exception.SystemdMojoExecutionException;
import io.murrer.mojo.EnvironmentProperties;
import io.murrer.mojo.InstallProperties;
import io.murrer.mojo.RunProperties;
import io.murrer.mojo.UnitProperties;
import io.murrer.templating.MojoContext;
import io.murrer.templating.TemplateProcessor;
import lombok.extern.slf4j.Slf4j;
import org.apache.maven.project.MavenProject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static io.murrer.test.TestTools.*;
import static io.murrer.utils.FileConstants.EXTENSION_SCRIPT;
import static io.murrer.utils.FileConstants.EXTENSION_UNIT_FILE;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@Slf4j
public class TemplateProcessorTest {

    public static final String DEFAULT_EXECUTION_DIRECTORY = InstallProperties.DEFAULT_INSTALL_BASE_DIRECTORY + "/" + PROJECT_ARTIFACT_ID + "/" + PROJECT_VERSION;
    private MavenProject project;
    private UnitProperties unitProperties;
    private MojoContext mojoContext;
    private RunProperties runProperties;
    private InstallProperties installProperties;
    private EnvironmentProperties environmentProperties;

    @BeforeEach
    public void setUp() {

        unitProperties = new UnitProperties();

        installProperties = new InstallProperties();

        runProperties = new RunProperties();

        environmentProperties = new EnvironmentProperties();

        mojoContext = new MojoContext(createLogMock(log),
                createProjectMock(),
                createSystemUtilsMock(),
                unitProperties,
                runProperties,
                installProperties,
                environmentProperties
        );
    }

    @Test
    public void testCyclicReplacement() throws SystemdMojoExecutionException {

        // let property one reference another, so a cyclic replacement will happen
        mojoContext.getInstall().setDirectory("${unit.user}");
        mojoContext.getUnit().setUser("${install.directory}");

        Executable e = () -> {
            TemplateProcessor.process("${install.directory}", mojoContext);
        };

        assertThrows(CyclicPropertyReferenceException.class, e, "Cyclic reference was not catched.");
    }

    @ParameterizedTest(name = "Templating for Maven Property ''{0}''")
    @CsvSource({
            // Project
            "${project.groupId}," + PROJECT_GROUP_ID,
            "${project.artifactId}," + PROJECT_ARTIFACT_ID,
            "${project.version}," + PROJECT_VERSION,
            "${project.description}," + PROJECT_DESCRIPTION,
            // Build
            "${project.build.outputDirectory}," + BUILD_OUTPUT_DIRECTORY
    })
    public void testProjectProperties(String property, String expected) throws SystemdMojoExecutionException {
        String actual = TemplateProcessor.process(property, mojoContext);
        assertEquals(expected, actual, String.format("Template result '%s' did not match expected '%s'", actual, expected));
    }

    @ParameterizedTest(name = "Templating for Systemd Mojo Property ''{0}''")
    @CsvSource({
            // Unit Replacements
            "${unit.fileName}," + PROJECT_ARTIFACT_ID + EXTENSION_UNIT_FILE,
            "${unit.templateFileLocation},null",
            "${unit.description}," + PROJECT_DESCRIPTION,
            "${unit.after}," + UnitProperties.DEFAULT_AFTER,
            "${unit.user}," + GLOBAL_USER_NAME,
            "${unit.wantedBy}," + UnitProperties.DEFAULT_WANTED_BY,
            "${unit.type}," + UnitProperties.DEFAULT_TYPE,
            "${unit.restart}," + UnitProperties.DEFAULT_RESTART,
            // Install Replacements
            "${install.fileName}," + InstallProperties.DEFAULT_INSTALL_FILE_NAME,
            "${install.directory}," + DEFAULT_EXECUTION_DIRECTORY,
            "${install.overwriteInstalled}," + InstallProperties.DEFAULT_OVERWRITE_INSTALLED,
            "${install.startService}," + InstallProperties.DEFAULT_START_SERVICE,
            // Run Replacements
            "${run.fileName}," + PROJECT_ARTIFACT_ID + EXTENSION_SCRIPT,
            // Environment Filename
            "${environment.fileName}," + EnvironmentProperties.DEFAULT_ENVIRONMENT_FILENAME,
            "${environment.directory}," + DEFAULT_EXECUTION_DIRECTORY,
            "${environment.overwriteInstalled}," + EnvironmentProperties.DEFAULT_OVERWRITE_INSTALLED,
            // System
            "${user.name}," + GLOBAL_USER_NAME,
            "${user.home}," + GLOBAL_USER_HOME
    })
    public void testDefaultReplacements(String property, String expected) throws SystemdMojoExecutionException {
        String actual = TemplateProcessor.process(property, mojoContext);
        assertEquals(expected, actual, String.format("Template result '%s' did not match expected '%s'", actual, expected));
    }

    @Test
    public void testUnitResourceTemplate() throws SystemdMojoExecutionException {
        TemplateProcessor.process(ResourceUtils.textOf("templates/unit.service"), mojoContext);
    }

    @Test
    public void testEnvironmentResourceTemplate() throws SystemdMojoExecutionException {
        TemplateProcessor.process(ResourceUtils.textOf("templates/environment.cfg"), mojoContext);
    }

    @Test
    public void testInstallResourceTemplate() throws SystemdMojoExecutionException {
        TemplateProcessor.process(ResourceUtils.textOf("templates/install.sh"), mojoContext);
    }

    @Test
    public void testRunResourceTemplate() throws SystemdMojoExecutionException {
        TemplateProcessor.process(ResourceUtils.textOf("templates/run.sh"), mojoContext);
    }
}
