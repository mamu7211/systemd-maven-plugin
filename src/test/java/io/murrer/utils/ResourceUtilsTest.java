package io.murrer.utils;

import org.junit.Test;

import java.io.FileNotFoundException;
import java.io.IOException;

import static org.junit.Assert.*;

public class ResourceUtilsTest {

    @Test
    public void testTextOf() throws IOException {
        assertNotNull(ResourceUtils.textOf("resource-utils/one-liner.txt"));
        assertEquals("resource-utils-test", ResourceUtils.textOf("resource-utils/one-liner.txt"));
    }

    @Test(expected = FileNotFoundException.class)
    public void testTextOfFails() throws IOException {
        ResourceUtils.textOf("not-present-resource-file");
    }
}