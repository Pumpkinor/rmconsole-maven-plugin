package com.timespower;


import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import static com.timespower.FileUtil.copyDirectory;
import static com.timespower.FileUtil.deleteFolder;

@Mojo(name = "rmConsoloLogAfter", defaultPhase = LifecyclePhase.PREPARE_PACKAGE)
public class RmConsoloLogAfterMojo extends AbstractMojo {
    @Parameter(defaultValue = "${project.basedir}")
    private File projectDir;
    @Parameter(defaultValue = "${project.build.directory}")
    private String buildDir;
    @Parameter(defaultValue = "${project.artifactId}")
    private String artifactId;
    private static int count;

    public void execute() throws MojoExecutionException {
        try {
            getLog().info("restore webapp projectDir:" + projectDir.toPath() + "");
            Path webappsSourcePath = Paths.get(projectDir.toPath() + File.separator + "src" + File.separator + "main" + File.separator + "webapp");
            Path webappsBakPath = Paths.get(projectDir.toPath() + File.separator + "src" + File.separator + "main" + File.separator + "webappbak");
            File webappsSourcePathFile = webappsSourcePath.toFile();
            File webappsBakPathFile = webappsBakPath.toFile();
            deleteFolder(webappsSourcePath.toFile(), true);
            webappsBakPathFile.renameTo(webappsSourcePathFile);
            getLog().info("********************************************* end remove console *********************************************");
        } catch (IOException e) {
            throw new MojoExecutionException("Error modifying webapp files", e);
        }
    }

    private void copyWebappDir(Path sourcePath, Path distPath) throws IOException {
        copyDirectory(sourcePath.toFile(), distPath.toFile());
        getLog().info("Directory copied successfully!");
    }
}
