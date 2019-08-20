package lili.com.chatroom;

import java.io.Closeable;
import java.io.IOException;

/**
 * **工具類**
 * @author LiLi-PC
 *
 */
public class Utils {
	/**
	 * *釋放資源*
	 */
	public static void close(Closeable... targets) {
		for(Closeable t:targets) {
			try {
				if(t!=null) {
					t.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
