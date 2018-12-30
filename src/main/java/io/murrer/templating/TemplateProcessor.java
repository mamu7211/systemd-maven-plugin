package io.murrer.templating;

import groovy.text.GStringTemplateEngine;
import io.murrer.exception.CyclicPropertyReferenceException;
import io.murrer.exception.SystemdMojoExecutionException;
import io.murrer.exception.TemplateException;
import io.murrer.utils.FileConstants;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class TemplateProcessor {

    public static String process(String template, MojoContext context)
            throws SystemdMojoExecutionException {

        GStringTemplateEngine engine = new GStringTemplateEngine();

        Map<String, Object> binding = new HashMap<>();
        binding.put("project", context.getProject());
        binding.put("unit", context.getUnit());
        binding.put("install", context.getInstall());
        binding.put("run", context.getRun());
        binding.put("environment", context.getEnvironment());
        binding.put("user", context.getSystem().getUser());

        String lastPass = template.replaceAll(FileConstants.LINE_ENDING_REGEX, FileConstants.LINUX_LINE_ENDING);

        String currentPass;
        int tries = 1;

        do {
            currentPass = lastPass;
            try {
                lastPass = engine.createTemplate(lastPass).make(binding).toString();
            } catch (Exception e) {
                throw new TemplateException(String.format("Failed to process template \n>>>\n%s\n<<<\n", template), e);
            }
            context.getLog().debug("Template result of pass #" + tries);
            context.getLog().debug(">>>");
            context.getLog().debug(currentPass);
            context.getLog().debug("<<<");
        } while (tries++ < 5 && !lastPass.equals(currentPass));

        if (tries >= 4 && !lastPass.equals(currentPass)) {
            String message = "Not all template variables were replaced after five passes processing a template. " +
                    "Check your templates for cyclic usage of variables, run maven using -X to enable debug output.";
            throw new CyclicPropertyReferenceException(message);
        }

        return lastPass.replaceAll("%(\\{|\\()", "\\$$1");
    }
}
