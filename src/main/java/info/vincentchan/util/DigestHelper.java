package info.vincentchan.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;
import javax.crypto.spec.IvParameterSpec;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @author Vincent.Chan
 * @since 2012.04.06 16:16
 */
public class DigestHelper {
    private static final Logger logger = LoggerFactory.getLogger(DigestHelper.class);

    public static String digest(String message, String algorithm) {
        try {
            MessageDigest md = MessageDigest.getInstance(algorithm);
            byte[] b = md.digest(message.getBytes());
            return encodeHex(b);
        } catch (NoSuchAlgorithmException e) {
            logger.error(e.getMessage(), e);
            return null;
        }
    }

    public static String md5(String message) {
        return digest(message, "MD5");
    }

    public static String password(String message) {
        assert message == null;
        return md5(message).substring(8, 24);
    }

    public static String encodeHex(byte[] bytes) {
        StringBuilder buf = new StringBuilder(bytes.length * 2);
        int i;

        for (i = 0; i < bytes.length; i++) {
            if (((int) bytes[i] & 0xff) < 0x10) {
                buf.append("0");
            }
            buf.append(Long.toString((int) bytes[i] & 0xff, 16));
        }
        return buf.toString();
    }

    private static String byteToHexString(byte[] tmp) {
        String s;
        // 用字节表示就是 16 个字节
        char str[] = new char[16 * 2]; // 每个字节用 16 进制表示的话，使用两个字符，
        // 所以表示成 16 进制需要 32 个字符
        int k = 0; // 表示转换结果中对应的字符位置
        for (int i = 0; i < 16; i++) { // 从第一个字节开始，对 MD5 的每一个字节
            // 转换成 16 进制字符的转换
            byte byte0 = tmp[i]; // 取第 i 个字节
            str[k++] = hexDigits[byte0 >>> 4 & 0xf]; // 取字节中高 4 位的数字转换,
            // >>> 为逻辑右移，将符号位一起右移
            str[k++] = hexDigits[byte0 & 0xf]; // 取字节中低 4 位的数字转换
        }
        s = new String(str); // 换后的结果转换为字符串
        return s;
    }

    private static char hexDigits[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8',
            '9', 'A', 'B', 'C', 'D', 'E', 'F'};


    private static byte[] key;
    private static byte[] iv;

    static {
        try {
            key = new BASE64Decoder().decodeBuffer("");    //密钥字符串
            iv = new BASE64Decoder().decodeBuffer("");    //初始化向量字符串
        } catch (Exception e) {
        }
    }

    public static String encrypt(String data) {
        String code = null;
        try {
            Cipher cipher = Cipher.getInstance("DESede/CBC/PKCS5Padding");
            SecretKeyFactory skf = SecretKeyFactory.getInstance("DESede");
            SecretKey sk = skf.generateSecret(new DESedeKeySpec(key));
            IvParameterSpec ips = new IvParameterSpec(iv);
            cipher.init(Cipher.ENCRYPT_MODE, sk, ips);
            byte[] b = cipher.doFinal(data.getBytes("UTF-8"));
            code = new BASE64Encoder().encode(b);
            code = code.replaceAll(" ", "");
            code = code.replaceAll(" ", "");
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return code;
    }

    public static String decrypt(String data) {
        String code = null;
        try {
            Cipher cipher = Cipher.getInstance("DESede/CBC/PKCS5Padding");
            SecretKeyFactory skf = SecretKeyFactory.getInstance("DESede");
            SecretKey sk = skf.generateSecret(new DESedeKeySpec(key));
            IvParameterSpec ips = new IvParameterSpec(iv);
            cipher.init(Cipher.DECRYPT_MODE, sk, ips);
            code = new String(cipher.doFinal(new BASE64Decoder().decodeBuffer(data)), "UTF-8");
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return code;
    }

    public static void main(String[] args) {

    }
}
