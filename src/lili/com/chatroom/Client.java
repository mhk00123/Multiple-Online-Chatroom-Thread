package lili.com.chatroom;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;


/**
 * *在線聊天室 : Client 端
 * *v3.0可收發多條信息
 * *v4.0加入多線程 - 多客戶收發
 * *v5.0封裝多線程物件
 * *v6.0加入容器 - 把通道加到容器中
 * *v8.0加入用戶名
 * @author LiLi-PC
 *
 */
public class Client {
	public static void main(String[] args) throws UnknownHostException, IOException {
		System.out.println("--------Client--------");
		
		//*v8.0加入用戶名
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		System.out.println("請輸入用戶名");
		String name = br.readLine();
		
		//1.  與Server端建立連接 - 使用Socket
		Socket client = new Socket("localhost", 8888); //Throws
		
		//2.  客戶發送信息 - OutputStream
		//2.1  由控制台輸入
		//*v8.0加入用戶名
		new Thread(new Send(client, name)).start();
		
		//3.  取得消息
		new Thread(new Receive(client)).start();

	}
}
