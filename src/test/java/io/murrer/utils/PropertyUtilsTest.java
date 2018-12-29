package io.murrer.utils;


import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static io.murrer.utils.PropertyUtils.getUnresolvedProperties;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class PropertyUtilsTest {

    @Test
    public void testGetUnresolvedForNull() {
        List<String> unresolved = getUnresolvedProperties(null);
        assertNotNull(unresolved);
        assertEquals(0, unresolved.size());
    }

    @Test
    public void testGetUnresolvedForEmpty() {
        List<String> unresolved = getUnresolvedProperties(new ArrayList<>());
        assertNotNull(unresolved);
        assertEquals(0, unresolved.size());
    }

    @Test
    public void testGetUnresolvedWithNoUnresolvedValue() {
        List<String> unresolved = getUnresolvedProperties(Arrays.asList(
                "key1=value1",
                "key2=value2"
        ));
        assertNotNull(unresolved);
        assertEquals(0, unresolved.size());
    }

    @Test
    public void testGetUnresolvedWithSingleUnresolvedValue() {
        List<String> unresolved = getUnresolvedProperties(Arrays.asList("key="));
        assertNotNull(unresolved);
        assertEquals(1, unresolved.size());
        assertEquals("key", unresolved.get(0));
    }

    @Test
    public void testGetUnresolvedWithSingleUnresolvedValueFromReference() {
        List<String> unresolved = getUnresolvedProperties(Arrays.asList("key=${reference}"));
        assertEquals(1, unresolved.size());
        assertEquals("reference", unresolved.get(0));
    }

    @Test
    public void testGetUnresolvedWithReferenceToPriorProperty() {
        List<String> unresolved = getUnresolvedProperties(Arrays.asList(
                "reference=some-value",
                "key=${reference}"
        ));
        assertEquals(0, unresolved.size());
    }

    @Test
    public void testGetUnresolvedWithMultipleReferencesResolved() {
        List<String> unresolved = getUnresolvedProperties(Arrays.asList(
                "reference1=value",
                "reference2=${reference1}",
                "reference3=${reference2}",
                "reference4=${reference3}"
        ));
        assertEquals(0, unresolved.size());
    }

    @Test
    public void testGetUnresolvedWithMultipleReferenceUnresolved() {
        List<String> unresolved = getUnresolvedProperties(Arrays.asList(
                "reference1=${reference0}",
                "reference2=${reference1}",
                "reference3=${reference2}",
                "reference4=${reference3}"
        ));
        assertEquals(1, unresolved.size());
        assertEquals("reference0", unresolved.get(0));
    }

    @Test
    public void testGetUnresolvedWithSingleUnresolvedReference() {
        List<String> unresolved = getUnresolvedProperties(Arrays.asList(
                "reference1=${reference0}",
                "reference2=${reference0}",
                "reference3=${reference0}",
                "reference4=${reference0}"
        ));
        assertNotNull(unresolved);
        assertEquals(1, unresolved.size());
        assertEquals("reference0", unresolved.get(0));
    }

    @Test
    public void testGetUnresolvedWithSelfReferencing() {
        List<String> unresolved = getUnresolvedProperties(Arrays.asList(
                "reference=${reference}"
        ));
        assertNotNull(unresolved);
        assertEquals(1, unresolved.size());
        assertEquals("reference", unresolved.get(0));
    }
}