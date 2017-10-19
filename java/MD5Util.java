import org.apache.commons.codec.binary.Hex;
import org.apache.log4j.Logger;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigInteger;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5Util {

    private final static Logger log = Logger.getLogger(MD5Util.class);
    static MessageDigest md5 = null;
    private static final Integer BUFFER = 8192;

    private final static String[] hexDigits = {"0", "1", "2", "3", "4", "5", "6", "7",
            "8", "9", "a", "b", "c", "d", "e", "f"};

    /**
     * 转换字节数组为16进制字串
     * @param b 字节数组
     * @return 16进制字串
     */
    public static String byteArrayToHexString(byte[] b) {
        StringBuilder resultSb = new StringBuilder();
        for (byte aB : b) {
            resultSb.append(byteToHexString(aB));
        }
        return resultSb.toString();
    }

    /**
     * 转换byte到16进制
     * @param b 要转换的byte
     * @return 16进制格式
     */
    private static String byteToHexString(byte b) {
        int n = b;
        if (n < 0) {
            n = 256 + n;
        }
        int d1 = n / 16;
        int d2 = n % 16;
        return hexDigits[d1] + hexDigits[d2];
    }


    static {
        try {
            md5 = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            log.error("初始化MD5失败: md5", e);
        }
    }

    /**
     * 适用于小文件进行md5加密
     * 文件小于1的使用此方法进行加密
     * @param file
     * @return
     */
    public static String getMd5ByFile(File file) {
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(file);
            byte[] buffer = new byte[BUFFER];
            int length;
            while ((length = fis.read(buffer)) != -1) {
                md5.update(buffer, 0, length);
            }
            fis.close();
            return new String(Hex.encodeHex(md5.digest()));
        } catch (FileNotFoundException e) {
            log.error("加密文件" + file.getAbsolutePath() + " 失败信息:" + e.getMessage());
            return null;
        } catch (IOException e) {
            log.error("加密文件" + file.getAbsolutePath() + " 失败信息:" + e.getMessage());
            return null;
        } finally {
            try {
                if (fis != null){
                    fis.close();
                }
            } catch (IOException e) {
                log.error("关闭输入流失败 : " + e);
            }
        }
    }


    /**
     * 适合大文件加密
     * 大于1的文件需要使用此方法
     * 进行内存映射加密
     * @param file
     * @return
     */
    public static String getMd5ByBigFile(File file) {
        String md5Value = null;
        FileInputStream in = null;
        try {
            in = new FileInputStream(file);
        } catch (FileNotFoundException e1) {
            log.error("获取流文件失败" + e1);
        }
        try {
            MappedByteBuffer byteBuffer = in.getChannel().map(FileChannel.MapMode.READ_ONLY, 0, file.length());
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            md5.update(byteBuffer);
            BigInteger bi = new BigInteger(1, md5.digest());
            md5Value = bi.toString(16);
        } catch (Exception e) {
            log.error("加密文件失败" + e);
        } finally {
            if (null != in) {
                try {
                    in.close();
                } catch (IOException e) {
                    log.error("关闭输入流失败:" + e);
                }
            }
        }
        return md5Value;
    }
    /**
     * MD5编码
     * @param origin 原始字符串
     * @return 经过MD5加密之后的结果
     */
    public static String MD5Encode(String origin) {
        String resultString = null;
        try {
            resultString = origin;
            MessageDigest md = MessageDigest.getInstance("MD5");
            resultString = byteArrayToHexString(md.digest(resultString.getBytes()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resultString;
    }
}
