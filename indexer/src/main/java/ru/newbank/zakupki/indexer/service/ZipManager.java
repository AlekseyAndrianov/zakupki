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

    public static List<File> getFilesFromZip(File zipFile, String prefixKey_ns4) {
        List<File> fileList = new ArrayList<>();
        byte[] buffer = new byte[1024];
        try (ZipInputStream zis = new ZipInputStream(new FileInputStream(zipFile))) {
            ZipEntry ze = zis.getNextEntry();
            while (ze != null) {
                String fileName = ze.getName();
                if (isTargetFile(fileName, prefixKey_ns4)) {
                    File newFile = File.createTempFile("temp", fileName);
                    FileOutputStream fos = new FileOutputStream(newFile);
                    int len;
                    while ((len = zis.read(buffer)) > 0) {
                        fos.write(buffer, 0, len);
                    }
                    fos.close();
                    fileList.add(newFile);
                }
                ze = zis.getNextEntry();
            }
            zis.closeEntry();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return fileList;
    }

    private static boolean isTargetFile(String fileName, String prefixKey_ns4) {
        String postMask = ".xml";
        return fileName.contains(prefixKey_ns4) && fileName.contains(postMask);
    }
}
