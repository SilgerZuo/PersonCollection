/**
 * com.mvcc.util
 */
public class TokenUtil {
    /**
     * 获取token
     * @param personId
     * @return
     */
    public static String generateToken(String personId){
        StringBuffer buffer = new StringBuffer();
        buffer.append(personId);
        String token = MD5Util.MD5Encode(buffer.toString());
        return token;
    }
}
