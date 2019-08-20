package lili.com.chatroom;

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
 * @author LiLi-PC
 *
 */
public class Client {
	public static void main(String[] args) throws UnknownHostException, IOException {
		System.out.println("--------Client--------");
		
		//1.     與Server端建立連接 - 使用Socket
		Socket client = new Socket("localhost", 8888); //Throws
		
		//2.     客戶發送信息 - OutputStream
		//2.1  由控制台輸入
		BufferedReader console = new BufferedReader(new InputStreamReader(System.in));
		DataOutputStream dos = new DataOutputStream(client.getOutputStream());
		DataInputStream dis = new DataInputStream(client.getInputStream());
		
		boolean isRunning = true;
		while(isRunning) {
			String msg = console.readLine();
			dos.writeUTF(msg);
			dos.flush();
			
			//3. 獲取回傳message
			msg = dis.readUTF();
			System.out.println(msg);
		}
		
		//Close - 先開後放
		dis.close();
		dos.close();
		client.close();
	}
}
