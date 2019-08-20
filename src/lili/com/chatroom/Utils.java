package lili.com.chatroom;

import java.io.Closeable;
import java.io.IOException;

/**
 * **�u����**
 * @author LiLi-PC
 *
 */
public class Utils {
	/**
	 * *����귽*
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
