package im.youni.script.utils;

import java.io.StringWriter;
import java.util.Map;

import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;

/**
 * @author Wang Yunfei 2016年5月4日 下午4:06:38
 */
public class VelocityUtils {
    
    public static final String TEMPLATES_HOME = "templates";
    
    public static String merge(String templateName, Map<String, Object> map) {
        Velocity.init();
        VelocityContext context = new VelocityContext();
        
        for (String key : map.keySet()) {
            context.put(key, map.get(key));
        }
        
        StringWriter sw = new StringWriter();
        Velocity.mergeTemplate(String.format("%s/%s", TEMPLATES_HOME, templateName), "UTF-8", context, sw);
        return sw.toString();
    }
}
