package io.murrer.utils;

import io.murrer.mojo.UnitProperties;
import lombok.extern.slf4j.Slf4j;
import org.apache.maven.plugin.logging.Log;
import org.apache.maven.plugin.testing.AbstractMojoTestCase;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;

@Slf4j
public class PropertyValidatorTest {

    private Log logMock;

    @Before
    public void setUp() {
        logMock = mock(Log.class);

        doAnswer(i -> {
            log.info(i.getArgument(0));
            return null;
        }).when(logMock).info(anyString());

        doAnswer(i -> {
            log.info(i.getArgument(0));
            return null;
        }).when(logMock).info(anyString());

        doAnswer(i -> {
            log.error(i.getArgument(0));
            return null;
        }).when(logMock).error(anyString());
    }

    @Test
    public void asdf() {
        UnitProperties unit = new UnitProperties();
        unit.setType("not-a-valid-type");
        assertTrue(PropertyValidator.hasErrors("unit", logMock, unit));
    }
}