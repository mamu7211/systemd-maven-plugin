package io.murrer.worker;

import io.murrer.templating.MojoContext;
import lombok.Getter;
import org.apache.maven.plugin.MojoExecutionException;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;

@Getter
public abstract class AbstractWorker {

    private MojoContext context;

    public AbstractWorker(MojoContext context) {
        this.context = context;
    }

    protected File createFile(String name) throws MojoExecutionException {
        File file = Paths.get(context.getProject().getBuild().getDirectory(), "systemd-bundler", name).toFile();

        if (file.exists()) {
            if (!file.delete()) {
                context.getLog().warn(String.format("Delete of '%s' might not have succeeded, trying to create it anyways.", file.getAbsolutePath()));
            }
        }

        try {
            if (!file.getParentFile().exists()){
                file.getParentFile().mkdirs();
            }
            file.createNewFile();
        } catch (IOException e) {
            throw new MojoExecutionException(String.format("Failed to create file '%s'.", file.getAbsolutePath()));
        }

        return file;
    }
}
