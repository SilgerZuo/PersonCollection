
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CookieUtil {
    /**
     * 服务端设置cookie到浏览器
     * @param response
     * @param key
     * @param value
     * @param maxAge
     */
    public static void setCookie(HttpServletResponse response,String key,String value,int maxAge){
        Cookie cookie = new Cookie(key,value);
        cookie.setMaxAge(maxAge);
        cookie.setPath("/");
        response.addCookie(cookie);
    }

    /**
     * 移除cookie
     * @param response
     * @param key
     */
    public static void deleteCookie(HttpServletResponse response,String key){
        if(null == key || "".equalsIgnoreCase(key)){
            return;
        }
        Cookie cookie = new Cookie(key,null);
        cookie.setMaxAge(0);
        response.addCookie(cookie);
    }

    /**
     * 获取指定名称的cookie
     * @param request
     * @param key
     * @return
     */
    public static String getCookie(HttpServletRequest request,String key){
        if(null != key){
            Cookie[] cookies = request.getCookies();
            if(null == cookies){
                return null;
            }
            for(Cookie cookie : cookies) {
                String name = cookie.getName();
                if(key.equalsIgnoreCase(name)){
                    return cookie.getValue();
                }
            }
        }
        return null;
    }
}
