package im.youni.script.es;

import im.youni.script.es.common.ESExecutor;
import im.youni.script.utils.ResourcesUtils;
import im.youni.script.utils.SimpleHttpUtils;
import im.youni.script.utils.VelocityUtils;

import java.util.HashMap;
import java.util.Map;

import net.sf.json.JSONObject;


/**
 * @author Wang Yunfei 2016年5月4日 下午2:08:48
 */
public class ESExecutors {
    
    public static String settings;
    
    private static final String INDEX_URL = "http://%s/%s";
    
    static {
        try {
            settings = ResourcesUtils.getResourceAsString("settings.vm");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public static final ESExecutor<Boolean> CREATE = new ESExecutor<Boolean>() {
        @Override
        public Boolean execute(String host, Object...objects) {
            String url = String.format(INDEX_URL, host, objects[1]);
            String result = null;
            try {
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("settings", settings);
                
                String json = VelocityUtils.merge((String) objects[0], map);
                result = SimpleHttpUtils.put(url, json);
                
                // {"acknowledged":true}
                if(result != null) {
                    return JSONObject.fromObject(result).getBoolean("acknowledged");
                }
                
            } catch (Exception e) {
                e.printStackTrace();
            }
            
            return false;
        }
    };
    
    public static final ESExecutor<String> GET = new ESExecutor<String>() {
        @Override
        public String execute(String host, Object...objects) {
            String url = String.format(INDEX_URL, host, objects[0]);
            try {
                return SimpleHttpUtils.get(url);
            } catch (Exception e) {
                e.printStackTrace();
            }
            
            return null;
        }
    };
    
    public static final ESExecutor<Boolean> EXISTS = new ESExecutor<Boolean>() {
        @Override
        public Boolean execute(String host, Object...objects) {
            String url = String.format(INDEX_URL, host, objects[0]);
            
            try {
                return SimpleHttpUtils.exists(url);
            } catch (Exception e) {
                e.printStackTrace();
            }
            
            return false;
        }
    };

}
