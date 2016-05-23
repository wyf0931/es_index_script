package im.youni.script.utils;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * @author wangyunfei 2015年6月29日 上午11:11:32
 */
public class IOUtils {
    /**
     * Copy stream from source to target.
     * @param source
     * @param target
     * */
	public static void copy(InputStream source, OutputStream target)throws Exception{
		copy(source, target, false);
	}
	
	/**
     * Copy stream from source to target.
     * @param source
     * @param target
     * @param flush Whether execute flush while per write
     * */
	public static long copy(InputStream source, OutputStream target, boolean flush) throws IOException {
		//buffer size = 8 kb
		byte[] buf = new byte[8 * 1024];
		int c = 0;
		long len = 0;
		while(true) {
			c = source.read(buf);
			if(c <= 0)
				break;
			len += c;
			target.write(buf, 0, c);
			if(flush)
				target.flush();
		}
		if(!flush)
			target.flush();
		return len;
	}
}
