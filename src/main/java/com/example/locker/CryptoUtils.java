package com.example.locker;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.FileOutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.MessageDigest;

/**
 * Utility class for file encryption and decryption operations
 */
public class CryptoUtils {

    /**
     * Encrypts a file using AES encryption
     *
     * @param password The password to use for encryption
     * @param fileName The path to the file to encrypt
     * @throws Exception If encryption fails
     */
    public static void encryptFile(String password, String fileName) throws Exception {
        System.out.println("Encrypting: " + fileName + " to " + fileName + ".enc");

        // Generate key and IV from password
        byte[] key = MessageDigest.getInstance("SHA-256").digest(password.getBytes());
        byte[] iv = new byte[16];
        System.arraycopy(key, 0, iv, 0, 16);

        // Read file data
        byte[] fileData = Files.readAllBytes(Paths.get(fileName));
        byte[] paddedFile = padFile(fileData);

        // Setup encryption
        SecretKey secretKey = new SecretKeySpec(key, "AES");
        IvParameterSpec ivSpec = new IvParameterSpec(iv);

        // Encrypt the data
        Cipher encryptCipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        encryptCipher.init(Cipher.ENCRYPT_MODE, secretKey, ivSpec);
        byte[] encryptedData = encryptCipher.doFinal(paddedFile);

        // Write the encrypted data to a file
        try (FileOutputStream fos = new FileOutputStream(fileName + ".enc")) {
            fos.write(encryptedData);
        }

        // Delete the original file
        Files.deleteIfExists(Paths.get(fileName));

        System.out.println(fileName + " has been encrypted.");
    }

    /**
     * Decrypts a file using AES decryption
     *
     * @param password The password to use for decryption
     * @param fileName The path to the encrypted file (.enc)
     * @throws Exception If decryption fails
     */
    public static void decryptFile(String password, String fileName) throws Exception {
        if (!fileName.endsWith(".enc")) {
            fileName = fileName + ".enc";
        }

        System.out.println("Decrypting: " + fileName + " to " + fileName.substring(0, fileName.length() - 4));

        // Generate key and IV from password
        byte[] key = MessageDigest.getInstance("SHA-256").digest(password.getBytes());
        byte[] iv = new byte[16];
        System.arraycopy(key, 0, iv, 0, 16);

        // Read encrypted data
        byte[] encryptedData = Files.readAllBytes(Paths.get(fileName));

        // Setup decryption
        SecretKey secretKey = new SecretKeySpec(key, "AES");
        IvParameterSpec ivSpec = new IvParameterSpec(iv);

        // Decrypt the data
        Cipher decryptCipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        decryptCipher.init(Cipher.DECRYPT_MODE, secretKey, ivSpec);
        byte[] decryptedData = decryptCipher.doFinal(encryptedData);

        // Write the decrypted data to a file
        String outputFileName = fileName.substring(0, fileName.length() - 4);
        try (FileOutputStream fos = new FileOutputStream(outputFileName)) {
            fos.write(decryptedData);
        }

        // Delete the encrypted file
        Files.deleteIfExists(Paths.get(fileName));

        System.out.println(fileName + " has been decrypted.");
    }

    /**
     * Pads file data to match the block size required by AES
     *
     * @param fileData The file data to pad
     * @return Padded data
     */
    private static byte[] padFile(byte[] fileData) {
        int blockSize = 16;
        int padding = blockSize - (fileData.length % blockSize);
        byte[] paddedData = new byte[fileData.length + padding];
        System.arraycopy(fileData, 0, paddedData, 0, fileData.length);
        return paddedData;
    }
}