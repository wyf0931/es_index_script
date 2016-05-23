package im.youni.script.utils;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * @author Wang Yunfei 2016年4月29日 下午5:56:00
 */
public class StringUtils {
    
    private static final String DEFAULT_CHARSET = "UTF-8";
    
    public static String getString(InputStream is, String charset) throws Exception {
        
        if(is == null || charset == null) {
            return "";
        }
        
        BufferedReader input = new BufferedReader(new InputStreamReader(is, charset));
        
        StringBuilder sb = new StringBuilder();
        String s;
        
        while ((s = input.readLine()) != null) {
            sb.append(s).append("\n");
        }
        
        return sb.toString();
    }
    
    public static String getString(InputStream is) throws Exception {
        return getString(is, DEFAULT_CHARSET);
    }

}
