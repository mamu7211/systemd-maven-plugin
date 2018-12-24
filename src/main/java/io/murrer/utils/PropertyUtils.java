package io.murrer.utils;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PropertyUtils {

    private static final String PROPERTY_REGEX = "[0-9a-zA-Z_.\\-]+";
    private static final Pattern PROPERTY_ASSIGNMENT = Pattern.compile("^(?<property>" + PROPERTY_REGEX + ")=(?<value>.*)$");
    private static final String PROPERTY_REFERENCE_REGEX = "^\\$\\{(" + PROPERTY_REGEX + ")}$";


    static List<String> getUnresolvedProperties(List<String> lines) {
        return collectUnresolved(createPropertyMap(lines));
    }

    private static ArrayList<String> collectUnresolved(HashMap<String, String> properties) {
        Set<String> unresolved = new HashSet<>();
        for (String key : properties.keySet()) {

            String value = properties.get(key);
            boolean isReference = value != null && value.trim().matches(PROPERTY_REFERENCE_REGEX);

            if (properties.get(key) == null) {
                unresolved.add(key);
            } else if (isReference) {
                String referenceProperty = value.replaceAll(PROPERTY_REFERENCE_REGEX, "$1");
                if (referenceProperty.equals(key) || !properties.containsKey(referenceProperty)) {
                    unresolved.add(referenceProperty);
                }
            }
        }
        return new ArrayList<>(unresolved);
    }

    private static HashMap<String, String> createPropertyMap(List<String> lines) {
        HashMap<String, String> properties = new HashMap<>();
        if (lines != null) {
            for (String line : lines) {
                Matcher matcher = PROPERTY_ASSIGNMENT.matcher(line);
                if (matcher.matches()) {
                    String property = matcher.group("property");
                    String value = matcher.group("value");
                    properties.put(property, value == null || value.trim().length() == 0 ? null : value);
                }
            }
        }
        return properties;
    }
}
