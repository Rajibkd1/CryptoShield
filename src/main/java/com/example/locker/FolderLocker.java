package com.example.locker;

import java.io.*;
import java.nio.file.*;
import java.util.zip.*;

/**
 * Utility class for folder locking and unlocking operations
 */
public class FolderLocker {

    /**
     * Locks a folder by zipping it and encrypting the zip file
     *
     * @param folderPath The path to the folder to lock
     * @param password The password to use for encryption
     * @throws Exception If locking fails
     */
    public static void lockFolder(String folderPath, String password) throws Exception {
        System.out.println("Locking folder: " + folderPath);

        File folder = new File(folderPath);
        String zipFileName = folderPath + ".zip";

        // Step 1: Zip the folder
        zipFolder(folder, zipFileName);

        // Step 2: Encrypt the zip file
        CryptoUtils.encryptFile(password, zipFileName);

        // Step 3: Delete the original folder
        deleteFolder(folder);

        System.out.println("Folder has been locked and compressed as: " + zipFileName + ".enc");
    }

    /**
     * Unlocks a folder by decrypting the encrypted zip file
     * (Does not extract the zip file - leaves it as a .zip for the user to extract)
     *
     * @param encryptedZipFilePath The path to the encrypted zip file (.zip.enc)
     * @param password The password to use for decryption
     * @throws Exception If unlocking fails
     */
    public static void unlockFolder(String encryptedZipFilePath, String password) throws Exception {
        System.out.println("Unlocking folder from: " + encryptedZipFilePath);

        // Ensure the file has .enc extension
        if (!encryptedZipFilePath.endsWith(".enc")) {
            encryptedZipFilePath = encryptedZipFilePath + ".enc";
        }

        // Decrypt the encrypted zip file (result will be a .zip file)
        CryptoUtils.decryptFile(password, encryptedZipFilePath);

        // Don't extract - let the user handle the ZIP file themselves
        System.out.println("Folder has been unlocked to ZIP file. You can now extract it manually.");
    }

    /**
     * Zips a folder
     *
     * @param folder The folder to zip
     * @param zipFilePath The path where the zip file will be created
     * @throws IOException If zipping fails
     */
    private static void zipFolder(File folder, String zipFilePath) throws IOException {
        try (FileOutputStream fos = new FileOutputStream(zipFilePath);
             ZipOutputStream zipOut = new ZipOutputStream(fos)) {

            zipDirectory(folder, folder.getName(), zipOut);
        }
    }

    /**
     * Helper method to recursively zip a directory
     *
     * @param fileToZip The file or directory to zip
     * @param fileName The name of the file within the zip
     * @param zipOut The zip output stream
     * @throws IOException If zipping fails
     */
    private static void zipDirectory(File fileToZip, String fileName, ZipOutputStream zipOut) throws IOException {
        if (fileToZip.isHidden()) {
            return;
        }

        if (fileToZip.isDirectory()) {
            File[] children = fileToZip.listFiles();
            if (children != null) {
                for (File childFile : children) {
                    zipDirectory(childFile, fileName + File.separator + childFile.getName(), zipOut);
                }
            }
        } else {
            try (FileInputStream fis = new FileInputStream(fileToZip)) {
                ZipEntry zipEntry = new ZipEntry(fileName);
                zipOut.putNextEntry(zipEntry);

                byte[] buffer = new byte[4096];
                int length;
                while ((length = fis.read(buffer)) > 0) {
                    zipOut.write(buffer, 0, length);
                }
            }
        }
    }

    /**
     * Recursively deletes a folder and its contents
     *
     * @param folder The folder to delete
     */
    private static void deleteFolder(File folder) {
        File[] files = folder.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isDirectory()) {
                    deleteFolder(file);
                } else {
                    file.delete();
                }
            }
        }
        folder.delete();
    }
}