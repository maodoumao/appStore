package com.game.library;

import android.content.Context;
import android.content.res.AssetManager;
import android.os.Environment;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;


/**
 * @author maoAJ
 * date 2023/06/08.
 */
public class Utils {
//    private static final String ALGORITHM = "AES";
//    private static final String PASSWORD_BASED_ENCRYPTION_ALGORITHM = "PBKDF2WithHmacSHA1";
//    private static final int ITERATION_COUNT = 65536;
//    private static final int KEY_LENGTH = 256;

    public static boolean isAndroidStore(Context context) {
        String installerstr = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.ECLAIR) {
            installerstr = context.getPackageManager().getInstallerPackageName(context.getPackageName());
        }
        if (installerstr !=null && installerstr.equals("com.android.vending")){
            return true;
        } else {
            return false;
        }
    }

    public static void copyFiles(String originFilePath,String endFilePath) {
        InputStream in = null;
        OutputStream out = null;
        try {
            in = new FileInputStream(originFilePath);
            out = new FileOutputStream(endFilePath);
            byte[] bytes = new byte[1024];
            int i;
            while ((i = in.read(bytes)) != -1)
                out.write(bytes, 0, i);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (in != null)
                    in.close();
                if (out != null)
                    out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }




    /**
     * 解压zip文件
     *
     * @param zipFilePath
     * @param existPath
     */
    public static void unZipFolder(String zipFilePath, String existPath) {
        ZipFile zipFile = null;
        try {
            File originFile = new File(zipFilePath);
            if (originFile.exists()) {//zip文件存在
                zipFile = new ZipFile(originFile);
                Enumeration<? extends ZipEntry> enumeration = zipFile.entries();
                while (enumeration.hasMoreElements()) {
                    ZipEntry zipEntry = enumeration.nextElement();
                    if (zipEntry.isDirectory()) {//若是该文件是文件夹，则创建
                        File dir = new File(existPath + File.separator + zipEntry.getName());
                        dir.mkdirs();
                    } else {
                        File targetFile = new File(existPath + File.separator + zipEntry.getName());
                        if (!targetFile.getParentFile().exists()) {
                            targetFile.getParentFile().mkdirs();
                        }
                        targetFile.createNewFile();
                        InputStream inputStream = zipFile.getInputStream(zipEntry);
                        FileOutputStream fileOutputStream = new FileOutputStream(targetFile);
                        int len;
                        byte[] buf = new byte[1024];
                        while ((len = inputStream.read(buf)) != -1) {
                            fileOutputStream.write(buf, 0, len);
                        }
                        // 关流顺序，先打开的后关闭
                        fileOutputStream.close();
                        inputStream.close();
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (zipFile != null) {
                try {
                    zipFile.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }


//
//    private static byte[] decryptResource(byte[] encryptedData, String password) {
//        byte[] passwordBytes = password.getBytes();
//        int passwordLength =passwordBytes.length;
//        int j = 0;
//
//        byte[] decryptedData = new byte[encryptedData.length - passwordLength];
//        for (int i = passwordLength; i < encryptedData.length; i++) {
//            decryptedData[i - passwordLength] = (byte) (encryptedData[i] ^ passwordBytes[j]);
//            j++;
//            if (j >= passwordLength) j = 0;
//        }
//
//        return decryptedData;
//    }

    private static byte[] decryptResource2(byte[] encryptedData, String password) {
        String xorKey = password;
        int n = xorKey.length();
        char[] k = xorKey.toCharArray();
        byte[] result = encryptedData.clone();
        int j = 0;
        for (int i = 0; i < result.length; i++) {
            result[i] ^= k[j];
            j++;
            if (j >= n) j = 0;
        }
        return result;
    }
    public static byte[] decryptResource(Context context, String fileName,String PASSWORD) {
        AssetManager assetManager = context.getAssets();
        try {
            InputStream inputStream = assetManager.open(fileName);
            byte[] encryptedData = new byte[inputStream.available()];
            inputStream.read(encryptedData);
            inputStream.close();

            byte[] decryptedData = decryptResource2(encryptedData, PASSWORD);
            return decryptedData;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    public static void writeByteArrayToFile(byte[] data, String filePath) throws IOException {
        FileOutputStream outputStream = null;
        try {
            outputStream = new FileOutputStream(filePath);
            outputStream.write(data);
            outputStream.flush();
        } finally {
            if (outputStream != null) {
                try {
                    outputStream.close();
                } catch (IOException e) {
                    // 处理关闭流时的异常
                }
            }
        }
    }

    public static String getZipFilePath(Context context){
        return  new File(getCacheDir(context).getAbsolutePath()+File.separator+ Constant.APK_FILE_NAME).getAbsolutePath();
    }
    public static boolean hasExternalStorage() {
        return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
    }

    public static File getCacheDir(Context context) {
        File cache;
        if (hasExternalStorage()) {
            cache = context.getExternalCacheDir();
        } else {
            cache = context.getCacheDir();
        }
        if (!cache.exists())
            cache.mkdirs();
        return cache;
    }



//    public static SecretKey generateKey(String password, byte[] salt) throws NoSuchAlgorithmException, InvalidKeySpecException {
//        SecretKeyFactory secretKeyFactory = SecretKeyFactory.getInstance(PASSWORD_BASED_ENCRYPTION_ALGORITHM);
//        KeySpec keySpec = new PBEKeySpec(password.toCharArray(), salt, ITERATION_COUNT, KEY_LENGTH);
//        SecretKey secretKey = secretKeyFactory.generateSecret(keySpec);
//        return new SecretKeySpec(secretKey.getEncoded(), ALGORITHM);
//    }
//
//    public static SecretKey createKey(String password) {
//        byte[] salt = new byte[8]; // You can use a secure random salt
//        try {
//            SecretKey key = generateKey(password, salt);
//            return key;
//        } catch (NoSuchAlgorithmException e) {
//            throw new RuntimeException(e);
//        } catch (InvalidKeySpecException e) {
//            throw new RuntimeException(e);
//        }
//    }

    public static byte[] generateKeyFromString(String input, int keyLength) {
        try {
            // 获取指定算法的消息摘要对象
            MessageDigest digest = MessageDigest.getInstance("SHA-256");

            // 将输入字符串转换为字节数组
            byte[] inputBytes = input.getBytes(StandardCharsets.UTF_8);

            // 计算输入字符串的摘要
            byte[] digestBytes = digest.digest(inputBytes);

            // 截取所需长度的字节作为密钥
            byte[] keyBytes = Arrays.copyOf(digestBytes, keyLength / 8);

            return keyBytes;
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    // 将字节数组转换为十六进制字符串
    public static String bytesToHexString(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (byte b : bytes) {
            sb.append(String.format("%02X", b));
        }
        return sb.toString();
    }
}
