package io.murrer.templating;

import groovy.text.GStringTemplateEngine;
import io.murrer.utils.FileConstants;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class TemplateProcessor {

    public static String process(String template, MojoContext context)
            throws IOException, ClassNotFoundException {

        GStringTemplateEngine engine = new GStringTemplateEngine();

        Map<String, Object> binding = new HashMap<>();
        binding.put("project", context.getProject());
        binding.put("unit", context.getUnit());
        binding.put("install", context.getInstall());
        binding.put("run", context.getRun());
        binding.put("environment", context.getRun());

        String linuxLineEndings = template.replaceAll(FileConstants.LINE_ENDING_REGEX, FileConstants.LINUX_LINE_ENDING);
        return engine.createTemplate(linuxLineEndings).make(binding).toString();
    }
}
