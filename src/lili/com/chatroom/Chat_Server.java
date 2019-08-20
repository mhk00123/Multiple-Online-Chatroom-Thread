package lili.com.chatroom;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * *在線聊天室 : Chat Server 端
 * *v3.0可收發多條信息
 * @author LiLi-PC
 *
 */
public class Chat_Server {
	public static void main(String[] args) throws IOException {
		System.out.println("--------Server--------");
		
		//1. 指定Server端口 - 使用ServerSocket創建服務器
		ServerSocket server = new ServerSocket(8888); //Throws
		
		//2. 阻塞式等待連接 accept - 創建Socket物件
		Socket client = server.accept();
		System.out.println("一個客戶端建立了連接");
		
		DataInputStream dis = new DataInputStream(client.getInputStream());
		DataOutputStream dos = new DataOutputStream(client.getOutputStream());
		
		boolean isRunning = true;
		while(isRunning){
			//3. 接收消息 - DataInputStream
			String msg = dis.readUTF();
			
			//4. 返回消息 DataOutputStream
			dos.writeUTF(msg);
			dos.flush();
		}
		
		//Close - 先開後放(Server不關)
		dis.close();
		dos.close();
		
	}
}
