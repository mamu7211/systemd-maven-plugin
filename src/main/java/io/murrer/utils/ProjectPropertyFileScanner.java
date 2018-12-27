package io.murrer.utils;

import io.murrer.templating.MojoContext;
import io.murrer.worker.AbstractWorker;
import org.apache.commons.io.FileUtils;
import org.apache.maven.model.Resource;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class ProjectPropertyFileScanner extends AbstractWorker {

    private static final String PROPERTY_FILE_REGEX = "^.+\\.properties$";
    private static final FilenameFilter PROPERTY_FILE_NAME_FILTER = (file, s) -> s.matches(PROPERTY_FILE_REGEX);

    public ProjectPropertyFileScanner(MojoContext context) {
        super(context);
    }

    public List<String> getUnresolvedProperties() throws IOException {

        List<File> propertyFiles = getContext().getProject().getResources().stream()
                .map(Resource::getDirectory)
                .distinct()
                .map(File::new)
                .filter(File::exists)
                .flatMap(dir -> Arrays.stream(dir.listFiles(PROPERTY_FILE_NAME_FILTER)))
                .collect(Collectors.toList());

        List<String> properties = new ArrayList<>();

        for (File propertyFile : propertyFiles) {
            properties.addAll(PropertyUtils.getUnresolvedProperties(FileUtils.readLines(propertyFile, StandardCharsets.UTF_8.name())));
        }

        return properties;
    }
}
