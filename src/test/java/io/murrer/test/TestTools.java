package io.murrer.test;

import io.murrer.model.User;
import io.murrer.mojo.UnitProperties;
import io.murrer.utils.SystemUtils;
import org.apache.maven.model.Build;
import org.apache.maven.project.MavenProject;
import org.slf4j.Logger;
import org.apache.maven.plugin.logging.Log;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

public class TestTools {

    public static final String PROJECT_VERSION = "PROJECT-VERSION";
    public static final String PROJECT_DESCRIPTION = "PROJECT-DESCRIPTION";
    public static final String PROJECT_GROUP_ID = "PROJECT-GROUP-ID";
    public static final String PROJECT_ARTIFACT_ID = "PROJECT-ARTIFACT-ID";
    public static final String BUILD_OUTPUT_DIRECTORY = "BUILD-OUTPUT-DIRECTORY";
    public static final String UNIT_FILENAME = "UNIT-FILENAME";
    public static final String UNIT_DESCRIPTION = "UNIT-DESCRIPTION";
    public static final String UNIT_AFTER = "UNIT-AFTER";
    public static final String UNIT_USER = "UNIT-USER";
    public static final String UNIT_WANTED_BY = "UNIT-WANTED-BY";
    public static final String UNIT_TYPE = "simple";
    public static final String UNIT_RESTART = "always";
    public static final String GLOBAL_USER_NAME = "GLOBAL-USER-NAME";
    public static final String GLOBAL_USER_HOME = "GLOBAL-USER-HOME";

    public static Log createLogMock(Logger log) {
        Log logMock = mock(Log.class);

        doAnswer(i -> {
            log.info(i.getArgument(0));
            return null;
        }).when(logMock).info(anyString());

        doAnswer(i -> {
            log.debug(i.getArgument(0));
            return null;
        }).when(logMock).info(anyString());

        doAnswer(i -> {
            log.error(i.getArgument(0));
            return null;
        }).when(logMock).error(anyString());

        return logMock;
    }

    public static MavenProject createProjectMock() {
        MavenProject projectMock = mock(MavenProject.class);
        Build buildMock = mock(Build.class);

        when(projectMock.getVersion()).thenReturn(PROJECT_VERSION);
        when(projectMock.getDescription()).thenReturn(PROJECT_DESCRIPTION);
        when(projectMock.getGroupId()).thenReturn(PROJECT_GROUP_ID);
        when(projectMock.getArtifactId()).thenReturn(PROJECT_ARTIFACT_ID);
        when(projectMock.getBuild()).thenReturn(buildMock);

        when(buildMock.getOutputDirectory()).thenReturn(BUILD_OUTPUT_DIRECTORY);

        return projectMock;
    }

    public static SystemUtils createSystemUtilsMock() {
        User user = new User(GLOBAL_USER_NAME, GLOBAL_USER_HOME);
        SystemUtils mock = mock(SystemUtils.class);
        when(mock.getUser()).thenReturn(user);
        return mock;
    }

    public static UnitProperties createValidUnit() {
        UnitProperties unit = new UnitProperties();

        unit.setFileName(UNIT_FILENAME);
        unit.setTemplateFileLocation(null);
        unit.setDescription(UNIT_DESCRIPTION);
        unit.setAfter(UNIT_AFTER);
        unit.setUser(UNIT_USER);
        unit.setWantedBy(UNIT_WANTED_BY);
        unit.setType(UNIT_TYPE);
        unit.setRestart(UNIT_RESTART);

        return unit;
    }

    public static String csvPair(String a, String b) {
        return String.format("%s,%s", a, b);
    }
}
