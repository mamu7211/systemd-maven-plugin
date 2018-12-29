package io.murrer.utils;

import io.murrer.mojo.UnitProperties;
import io.murrer.test.TestTools;
import lombok.extern.slf4j.Slf4j;
import org.apache.maven.plugin.logging.Log;
import org.apache.maven.project.MavenProject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Slf4j
public class PropertyValidatorTest {

    private Log logMock;
    private MavenProject projectMock;

    @BeforeEach
    public void setUp() {
        logMock = TestTools.createLogMock(log);
        projectMock = TestTools.createProjectMock();
    }

    @Test
    public void validateWithoutErrors() {
        UnitProperties unit = TestTools.createValidUnit();
        assertFalse(PropertyValidator.hasErrors("unit", logMock, unit));
    }

    @Test
    public void validateUnitFileNameNull() {
        UnitProperties unit = TestTools.createValidUnit();
        unit.setFileName(null);
        assertTrue(PropertyValidator.hasErrors("unit", logMock, unit));
    }

    @Test
    public void validateUnitFilenameBlank() {
        UnitProperties unit = TestTools.createValidUnit();
        unit.setFileName("  ");
        assertTrue(PropertyValidator.hasErrors("unit", logMock, unit));
    }

    @Test
    public void validateUnitUserNull() {
        UnitProperties unit = TestTools.createValidUnit();
        unit.setUser(null);
        assertTrue(PropertyValidator.hasErrors("unit", logMock, unit));
    }
}