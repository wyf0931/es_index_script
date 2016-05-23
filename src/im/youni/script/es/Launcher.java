package im.youni.script.es;

import im.youni.script.es.common.ESExecutor;
import im.youni.script.utils.ResourcesUtils;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Properties;
import java.util.Timer;
import java.util.TimerTask;

import org.apache.log4j.Logger;

/**
 * @author Wang Yunfei 2016年5月4日 上午10:33:52
 */
public class Launcher {
    
    private static SimpleDateFormat SDF = new SimpleDateFormat("YYYY.MM.dd");
    
    private static Logger logger = Logger.getLogger(Launcher.class);
    
    public static void main(String[] args) {
        try {
            
            final String dirRoot = args[0];
            final long period = Long.parseLong(args[1]);
            
            Properties conf = ResourcesUtils.getResourceAsProperties(dirRoot + "/conf.properties");
            if(conf == null || conf.size() <= 0) {
                System.out.println("conf.properties is blank.");
                return ;
            }
            
            final String host = conf.getProperty("host");
            if(host == null || "".equals(host)) {
                System.out.println("host is blank.");
                return ;
            }
            
            final String[] indexs = conf.getProperty("indexs", "").split(",");
            if(indexs == null || indexs.length <= 0) {
                System.out.println("indexs is blank.");
                return ;
            }
            
            // create index
            Timer timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    logger.info("================ Executing week task=================");
                    weekTask(host, indexs, dirRoot);
                    logger.info("================ Waitting for next week task =================");
                }
            }, 5000, period);
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    private static void weekTask(String host, String[] indexs, String dirRoot) {
        Calendar calendar = Calendar.getInstance();
        
        for (int i = 1; i <= 7; i++) {
            calendar.add(Calendar.DAY_OF_YEAR, +1);
            createIndexs(host, indexs, calendar.getTime(), dirRoot);
        }
    }
    
    private static void createIndexs(String host, String[] indexs, Date today, String dirRoot) {
        String vmName = null;
        String indexName = null;
        
        String date = SDF.format(today);
        
        for (String index : indexs) {
            vmName = String.format("%s/templates/%s.vm", dirRoot, index);
            indexName = String.format("%s-%s", index, date);
            
            boolean exists = excuteCommand(ESExecutors.EXISTS, host, indexName);
            
            if(exists == false) {
                boolean rs = excuteCommand(ESExecutors.CREATE, host, vmName, indexName);
                logger.info(String.format("index=[%s] created, status=[%s]", indexName, rs));
            } else {
                logger.info(String.format("index=[%s] exist, has been skip.", indexName));
            }
        }
    }
    
    private static <T> T excuteCommand(ESExecutor<T> executor, String host, Object... args) {
        return executor.execute(host, args);
    }
    
}
