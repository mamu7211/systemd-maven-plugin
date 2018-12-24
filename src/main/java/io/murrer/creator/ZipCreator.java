package io.murrer.creator;

import io.murrer.tool.AbstractWorker;
import org.apache.maven.plugin.logging.Log;
import org.apache.maven.project.MavenProject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class ZipCreator extends AbstractWorker {


    public ZipCreator(MavenProject project, Log log) {
        super(project, log);
    }

    public void zip(File... files) throws IOException {

        File zip = new File(getProject().getBuild().getDirectory(), getProject().getBuild().getFinalName() + ".zip");

        try (FileOutputStream fos = new FileOutputStream(zip);
             ZipOutputStream zos = new ZipOutputStream(fos)) {
            for (File file : files) {
                zipFile(file, zos);
            }
        }

        getLog().info(String.format("Archived everything in '%s'.", zip.getAbsolutePath()));
    }

    private void zipFile(File file, ZipOutputStream zos) throws IOException {
        getLog().debug("Zipping '" + file.getAbsolutePath() + "'");
        FileInputStream fis = new FileInputStream(file);
        ZipEntry zipEntry = new ZipEntry(file.getName());
        zos.putNextEntry(zipEntry);
        byte[] bytes = new byte[1024];
        int length;
        while ((length = fis.read(bytes)) >= 0) {
            zos.write(bytes, 0, length);
        }
        fis.close();
    }

}
