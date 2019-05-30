package com.lsh.packagelibrary;

import org.json.JSONArray;

import java.io.UnsupportedEncodingException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.interfaces.RSAPrivateKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;

public final class RSAUtils {
    private static String RSA = "RSA";
    private static String RSAC = "RSA/ECB/PKCS1Padding";


    private static char[] base64EncodeChars = new char[]
            {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T',
                    'U', 'V', 'W', 'X', 'Y', 'Z', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm',
                    'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', '0', '1', '2', '3', '4', '5',
                    '6', '7', '8', '9', '+', '/'};
    private static byte[] base64DecodeChars = new byte[]
            {-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
                    -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 62, -1, -1, -1, 63, 52, 53,
                    54, 55, 56, 57, 58, 59, 60, 61, -1, -1, -1, -1, -1, -1, -1, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11,
                    12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, -1, -1, -1, -1, -1, -1, 26, 27, 28, 29,
                    30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51, -1, -1,
                    -1, -1, -1};

    /**
     * @param data
     * @return
     */
    public static String encode(byte[] data) {
        StringBuffer sb = new StringBuffer();
        int len = data.length;
        int i = 0;
        int b1, b2, b3;
        while (i < len) {
            b1 = data[i++] & 0xff;
            if (i == len) {
                sb.append(base64EncodeChars[b1 >>> 2]);
                sb.append(base64EncodeChars[(b1 & 0x3) << 4]);
                sb.append("==");
                break;
            }
            b2 = data[i++] & 0xff;
            if (i == len) {
                sb.append(base64EncodeChars[b1 >>> 2]);
                sb.append(base64EncodeChars[((b1 & 0x03) << 4) | ((b2 & 0xf0) >>> 4)]);
                sb.append(base64EncodeChars[(b2 & 0x0f) << 2]);
                sb.append("=");
                break;
            }
            b3 = data[i++] & 0xff;
            sb.append(base64EncodeChars[b1 >>> 2]);
            sb.append(base64EncodeChars[((b1 & 0x03) << 4) | ((b2 & 0xf0) >>> 4)]);
            sb.append(base64EncodeChars[((b2 & 0x0f) << 2) | ((b3 & 0xc0) >>> 6)]);
            sb.append(base64EncodeChars[b3 & 0x3f]);
        }
        return sb.toString();
    }

    /**
     * @param str
     * @return
     */
    public static byte[] decode(String str) {
        try {
            return decodePrivate(str);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return new byte[]
                {};
    }

    private static byte[] decodePrivate(String str) throws UnsupportedEncodingException {
        StringBuffer sb = new StringBuffer();
        byte[] data = null;
        data = str.getBytes("US-ASCII");
        int len = data.length;
        int i = 0;
        int b1, b2, b3, b4;
        while (i < len) {

            do {
                b1 = base64DecodeChars[data[i++]];
            } while (i < len && b1 == -1);
            if (b1 == -1)
                break;

            do {
                b2 = base64DecodeChars[data[i++]];
            } while (i < len && b2 == -1);
            if (b2 == -1)
                break;
            sb.append((char) ((b1 << 2) | ((b2 & 0x30) >>> 4)));

            do {
                b3 = data[i++];
                if (b3 == 61)
                    return sb.toString().getBytes("iso8859-1");
                b3 = base64DecodeChars[b3];
            } while (i < len && b3 == -1);
            if (b3 == -1)
                break;
            sb.append((char) (((b2 & 0x0f) << 4) | ((b3 & 0x3c) >>> 2)));

            do {
                b4 = data[i++];
                if (b4 == 61)
                    return sb.toString().getBytes("iso8859-1");
                b4 = base64DecodeChars[b4];
            } while (i < len && b4 == -1);
            if (b4 == -1)
                break;
            sb.append((char) (((b3 & 0x03) << 6) | b4));
        }
        return sb.toString().getBytes("iso8859-1");
    }

    private static String PRIVATE_KEY = "MIIEwwIBADANBgkqhkiG9w0BAQEFAASCBK0wggSpAgEAAoIBAQCH4mR/QxeWwb0+\n" +
            "HsTdlX/S8v5XXphEwsFfIaW7pIEXkVrBCeWoQprv9VXmrohmW7Yez2MG4LaIGcSj\n" +
            "dutZ1xYgEfoKbimGSUrLWP7vTfuhHkaDgoS8+c5zNj9X4Vzn1RBs5HSyZoEMCtFZ\n" +
            "8/g8rXwVQZbdza7VY5qfH8mNIS+c5HXDAKwiW2nxeynFn0UWY+iRunrfj842VCSs\n" +
            "9ApavelxkT4P7wvh5KFepWkkCJc63BB25agOC0DCNSt/No+0pt4eN/GIPNK8pJSJ\n" +
            "qiH3H6K+NleTrrqYizmDQr1AtlpV2FUTkl/m7UV3iPyg0T+405/FLCInkSAjDCm/\n" +
            "V3MVA01fAgMBAAECggEAOKMQKEsapeeSrTW98G3DnXVSta/j36UdXD12CsQCWoRn\n" +
            "Q1aQtpUsZx/m8gOFLsTDIAxoxhEbg5bZ8xg5+HRB8JQNmBNak3IxDpjFiZEDdKBd\n" +
            "26qnEO0+M59Ev6hbRPX1pq2CRmmbGB3aLJgXu5LDyUhRTZnRaXfeOxpbNqK3tKDz\n" +
            "QEXMRndrNjfoytyUYaiyMY4V5NZDYOltDLXdyXkTgCx27f3bcgkMnRdEEjVz01Ae\n" +
            "AKMIfZ57G52o+KeQAylL+ZJyosiQGu76qJMPbKaWqeBE/lK4sU0MSe+W0LUz7uT2\n" +
            "x9FdzPxDreAk/oReTn4okV2S8ylOEjDgBhkT3rMUQQKBiQDb2RCUiLQi3ZBA8yJn\n" +
            "X7WJhOK8VVWtiFFvcFp6ExUWU95S0e7m6vhD9KrGCyT7rSgDXhlOuVWI8sVsUhqa\n" +
            "w53SNp0I7e5JQJ2qkwn/XZ2l0uPxcpQEUkBcyYxcl5Z3ES4D2w6YWKu0TNOXHLTa\n" +
            "OSUJCCPB4RVvQjXaSjBEcrjNwJYxxsXcphAXAnkAnjq26qZdE6KVfC1W8NGmPDLN\n" +
            "HdkIBYDRPldJPt6wyvYYBxXdPhsdDNssJ7qUKm1ZQE2zMgT/WV1Ao42NykzJJ3uq\n" +
            "uDXkzPp2M++0E0oHtOwhY2bUVpdQJt/fH+HfD/bNApnUd2lGyiQCJF6Epg7n167H\n" +
            "P2LPaPH5AoGJAKdkg0dVPzM82FVbytpC+YqX77vOjcnvfXIIbht85BV4DlOpHOoe\n" +
            "BqJXbKAWZDZtBYZq44IZg5MepktvyGoGf+hLRQ5De6ey11nFU1tpAFv+UyjIQQN5\n" +
            "TSBZJdJqaVqHbi3pYReGjFZgGMt6RAnUcOqLlA5PP27TTwXCbzXu0991v+ZunsD+\n" +
            "gEkCeFYlf2s30lsbXKm0Aa6xZgFGY1iJzg04+jMn0LATDEwkOia5Z7AlkOZr+ZlT\n" +
            "wtFqoXoaE6L7B48+7y9cpULsFWSStvr+FKjpACv8qJ0L5DBDk1YMKTVPwoq3vkIc\n" +
            "dpa5dxqgaAXvYmQxwJzOfQfv+5f5B2/iOyxW+QKBiF78yBl4yzFibMMIAy3K3f26\n" +
            "ovoVoHbman5qgUMvDsTa9/VJztkNzuO0Id5O8AqU36elEECmNgtEWup6yPDQd73J\n" +
            "bLMFmDCn29upcEEGduJRQ45bo5gnnZGq40PLVaaEpI/bGTNNi/I4R6V+ceVEcmey\n" +
            "VNwf5X1j+Kp8eCC8bXdGl0d1Aw53yeQ=";

    /**
     * 用私钥解密
     *
     * @param encryptedData 经过encryptedData()加密返回的byte数据
     * @param privateKey    私钥
     * @return
     */
    public static byte[] decryptData(byte[] encryptedData, PrivateKey privateKey) {
        try {
            Cipher cipher = Cipher.getInstance(RSAC);
            cipher.init(Cipher.DECRYPT_MODE, privateKey);
            return cipher.doFinal(encryptedData);
        } catch (Exception e) {
            return null;
        }
    }


    /**
     * 从字符串中加载私钥<br>
     * 加载时使用的是PKCS8EncodedKeySpec（PKCS#8编码的Key指令）。
     *
     * @param privateKeyStr
     * @return
     * @throws Exception
     */
    public static PrivateKey loadPrivateKey(String privateKeyStr) throws Exception {
        try {
            byte[] buffer = decode(privateKeyStr);
            PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(buffer);
            KeyFactory keyFactory = KeyFactory.getInstance(RSA);
            return (RSAPrivateKey) keyFactory.generatePrivate(keySpec);
        } catch (NoSuchAlgorithmException e) {
            throw new Exception("无此算法");
        } catch (InvalidKeySpecException e) {
            throw new Exception("私钥非法");
        } catch (NullPointerException e) {
            throw new Exception("私钥数据为空");
        }
    }


    public static String Myjiemi(JSONArray data) throws Exception {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < data.length(); i++) {
            try {
                PrivateKey privateKey = RSAUtils.loadPrivateKey(PRIVATE_KEY);
                byte[] decryptByte = RSAUtils.decryptData(decode(data.getString(i)), privateKey);
                String decryptStr = new String(decryptByte);
                result.append(decryptStr);
            } catch (Exception e) {
                throw new Exception("解析失败");
            }
        }
        return new String(decode(result.toString()));
    }


    private static String key = "20171117";

    public static String DES_Decrypt(String from) throws Exception {
        byte[] decrypted = DES_CBC_Decrypt(hexStringToByte(from.toUpperCase()), key.getBytes());
        return new String(decrypted);
    }

    /**
     * 把16进制字符串转换成字节数组
     *
     * @param hex
     * @return byte[]
     */
    private static byte[] hexStringToByte(String hex) {
        int len = (hex.length() / 2);
        byte[] result = new byte[len];
        char[] achar = hex.toCharArray();
        for (int i = 0; i < len; i++) {
            int pos = i * 2;
            result[i] = (byte) (toByte(achar[pos]) << 4 | toByte(achar[pos + 1]));
        }
        return result;
    }

    private static int toByte(char c) {
        byte b = (byte) "0123456789ABCDEF".indexOf(c);
        return b;
    }

    private static byte[] DES_CBC_Decrypt(byte[] content, byte[] keyBytes) throws Exception {
        try {
            DESKeySpec keySpec = new DESKeySpec(keyBytes);
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
            SecretKey key = keyFactory.generateSecret(keySpec);

            Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, key, new IvParameterSpec(keyBytes));
            return cipher.doFinal(content);
        } catch (Exception e) {
            throw new Exception(e);
        }
    }
}
