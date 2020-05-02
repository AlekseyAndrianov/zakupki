package ru.newbank.zakupki.indexer.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class ZipManager {

    public static List<File> getFilesFromZip(File zipFile) {
        List<File> fileList = new ArrayList<>();
        byte[] buffer = new byte[1024];

        try (ZipInputStream zis = new ZipInputStream(new FileInputStream(zipFile))) {
            File folder = File.createTempFile("temp", "");

            ZipEntry ze = zis.getNextEntry();
            while (ze != null) {
                String fileName = ze.getName();
                File newFile = File.createTempFile(folder + File.separator, fileName);
                FileOutputStream fos = new FileOutputStream(newFile);
                int len;
                while ((len = zis.read(buffer)) > 0) {
                    fos.write(buffer, 0, len);
                }
                fos.close();
                ze = zis.getNextEntry();
                newFile.deleteOnExit();
                fileList.add(newFile);
            }
            zis.closeEntry();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return fileList;
    }
}
