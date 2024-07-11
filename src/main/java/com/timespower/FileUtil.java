package com.timespower;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

/**
 * @ClassName: FileUtil
 * @Description: 文件操作工具类
 * @Author: Pumpkinor
 * @Email: pumpkinor@163.com
 * @Date: 2024/7/11
 * @Version: 1.0
 */
public class FileUtil {
    public static void copyDirectory(File sourceDir, File destinationDir) throws IOException {
        if (!destinationDir.exists()) {
            destinationDir.mkdirs();
        }

        File[] files = sourceDir.listFiles();
        if (files != null) {
            for (File file : files) {
                File destinationFile = new File(destinationDir, file.getName());
                if (file.isDirectory()) {
                    copyDirectory(file, destinationFile);
                } else {
                    copyFile(file, destinationFile);
                }
            }
        }
    }

    public static void copyFile(File sourceFile, File destinationFile) throws IOException {
        Files.copy(sourceFile.toPath(), destinationFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
    }

    public static void deleteFolder(File file, boolean deleteThisPath) throws IOException {
        if (file.isDirectory()) {// 处理目录
            File[] files = file.listFiles();
            for (int i = 0; i < files.length; i++) {
                deleteFolder(files[i], true);
            }
        }
        if (deleteThisPath) {
            if (!file.isDirectory()) {// 如果是文件，删除
                File to = new File(file.getAbsolutePath() + System.currentTimeMillis());
                file.renameTo(to);
                to.delete();
                // file.delete();
            } else {// 目录
                if (file.listFiles().length == 0) {// 目录下没有文件或者目录，删除
                    File to = new File(file.getAbsolutePath() + System.currentTimeMillis());
                    file.renameTo(to);
                    to.delete();
                }
            }
        }
    }
}
