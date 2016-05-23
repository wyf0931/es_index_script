package im.youni.script.utils;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * @author Wang Yunfei 2016年5月4日 下午2:01:01
 */
public class SimpleHttpUtils {
    
    private static final String METHOD_GET = "GET";
    private static final String METHOD_PUT = "PUT";
    
    public static String get(String surl) throws Exception {
        URL url = new URL(surl);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setReadTimeout(10000);
        conn.setConnectTimeout(10000);
        conn.setRequestMethod(METHOD_GET);
        
        if(conn.getResponseCode() == 200) {
            return StringUtils.getString(conn.getInputStream());
        }
        
        return null;
    }
    
    public static boolean exists(String surl) throws Exception {
        URL url = new URL(surl);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setReadTimeout(10000);
        conn.setConnectTimeout(10000);
        conn.setRequestMethod(METHOD_GET);
        
        if(conn.getResponseCode() == 200) {
            return true;
        }
        
        if(conn.getResponseCode() == 404){
            return false;
        }
        
        return false;
    }
    
    public static String put(String surl, String json) throws Exception {
        return put(surl, new ByteArrayInputStream(json.getBytes()));
    }
    
    public static String put(String surl, InputStream in) throws Exception {
        URL url = new URL(surl);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setReadTimeout(10000);
        conn.setConnectTimeout(10000);
        conn.setRequestMethod(METHOD_PUT);
        conn.setDoOutput(true);
        
        IOUtils.copy(in, conn.getOutputStream());
        
        if(conn.getResponseCode() == 200) {
            return StringUtils.getString(conn.getInputStream());
        }
        
        return null;
    }
}
