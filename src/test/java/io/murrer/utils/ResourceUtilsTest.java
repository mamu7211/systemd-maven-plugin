package io.murrer.utils;

import io.murrer.exception.ResourceProcessingException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import static org.junit.jupiter.api.Assertions.*;

public class ResourceUtilsTest {

    @Test
    public void testTextOf() throws ResourceProcessingException {
        assertNotNull(ResourceUtils.textOf("resource-utils/one-liner.txt"));
        assertEquals("resource-utils-test", ResourceUtils.textOf("resource-utils/one-liner.txt"));
    }

    @Test
    public void testTextOfFails() throws ResourceProcessingException {
        Executable e = () -> {
            ResourceUtils.textOf("not-present-resource-file");
        };

        assertThrows(ResourceProcessingException.class, e, "InvalidException");

    }
}