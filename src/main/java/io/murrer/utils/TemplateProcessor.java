package io.murrer.utils;

import groovy.text.GStringTemplateEngine;
import io.murrer.mojo.Unit;
import org.apache.maven.project.MavenProject;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class TemplateProcessor {

    public static String process(String template, MavenProject project, Unit unit) throws IOException, ClassNotFoundException {
        GStringTemplateEngine engine = new GStringTemplateEngine();

        Map binding = new HashMap();
        binding.put("project", project);
        binding.put("unit", unit);

        return engine.createTemplate(template).make(binding).toString();
    }
}
