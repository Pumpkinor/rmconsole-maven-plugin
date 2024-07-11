package com.timespower;


import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static com.timespower.FileUtil.copyDirectory;

@Mojo(name = "rmConsoloLog", defaultPhase = LifecyclePhase.PREPARE_PACKAGE)
public class RmConsoloLogMojo extends AbstractMojo {
    @Parameter(defaultValue = "${project.basedir}")
    private File projectDir;
    @Parameter(defaultValue = "${project.build.directory}")
    private String buildDir;
    @Parameter(defaultValue = "${project.artifactId}")
    private String artifactId;
    private static int count;

    public void execute() throws MojoExecutionException {
        try {
            getLog().info("********************************************* start remove console *********************************************");
            getLog().info("copy webapp projectDir:" + projectDir.toPath());
            Path webappsSourcePath = Paths.get(projectDir.toPath() + File.separator + "src" + File.separator + "main" + File.separator + "webapp");
            Path webappsBakPath = Paths.get(projectDir.toPath() + File.separator + "src" + File.separator + "main" + File.separator + "webappbak");
            copyWebappDir(webappsSourcePath, webappsBakPath);
            Path jsPath = Paths.get(buildDir + File.separator + artifactId + File.separator + "js" + File.separator + "views");
            Path jsSourcePath = Paths.get(webappsSourcePath + File.separator + "js" + File.separator + "views");
            getLog().info("jsPath:" + jsPath);
            handleFiles(jsSourcePath.toFile());
            Path jspPath = Paths.get(buildDir + File.separator + artifactId + File.separator + "WEB-INF" + File.separator + "views");
            Path jspSourcePath = Paths.get(webappsSourcePath + File.separator + "WEB-INF" + File.separator + "views");
            getLog().info("jspPath:" + jspPath);
            handleFiles(jspSourcePath.toFile());
            getLog().info("完成移除consolo.log()的操作,本次操作文件数:" + count);
        } catch (IOException e) {
            throw new MojoExecutionException("Error modifying webapp files", e);
        }
    }

    private void copyWebappDir(Path sourcePath, Path distPath) throws IOException {
        copyDirectory(sourcePath.toFile(), distPath.toFile());
        getLog().info("Directory copied successfully!");
    }

    private void handleFiles(File file) throws IOException {
        if (file.isDirectory()) {
            // 获取目录下的所有文件/文件夹
            File[] items = file.listFiles();
            if (items != null) {
                for (File item : items) {
                    // 递归遍历子文件夹
                    handleFiles(item);
                }
            }
        } else if (file.getName().contains(".js")) {
            // 处理文件
            String content = new String(Files.readAllBytes(file.toPath()));
            if (content.contains("console.log")) {
                // 替换文件中的某个字符串
                content = content.replaceAll("console\\.log\\([^)]*\\)", "");
                ;
                Files.write(file.toPath(), content.getBytes());
                getLog().info(file.toPath() + "完成移除consolo.log()");
                count++;
            }
        }
    }
}
