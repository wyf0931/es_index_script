package im.youni.script.es.common;

/**
 * @author Wang Yunfei 2016年5月4日 下午1:45:05
 */
public interface ESExecutor <T> {
    public T execute(String host, Object...objects );
}
