package lili.com.chatroom;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * *在線聊天室 : Chat Server 端
 * *v3.0可收發多條信息
 * *v4.0加入多線程 - 多客戶收發
 * @author LiLi-PC
 *
 */
public class Chat_Server {
	public static void main(String[] args) throws IOException {
		System.out.println("--------Server--------");
		
		//1. 指定Server端口 - 使用ServerSocket創建服務器
		ServerSocket server = new ServerSocket(8888); //Throws
		
		//2. 阻塞式等待連接 accept - 創建Socket物件
		while(true) {
			Socket client = server.accept();
			System.out.println("一個客戶端建立了連接");
			
			new Thread(()->{
				DataInputStream dis = null;
				DataOutputStream dos = null;
				try {
					dis = new DataInputStream(client.getInputStream());
					dos = new DataOutputStream(client.getOutputStream());
				} catch (IOException e) {
					e.printStackTrace();
				}
				
				boolean isRunning = true;
				while(isRunning){
					//3. 接收消息 - DataInputStream
					String msg = null;
					try {
						if(dis!=null) {
							msg = dis.readUTF();
						}
						
					} catch (IOException e) {
						e.printStackTrace();
						isRunning = false; //出錯了就要停止線
					}
					
					//4. 返回消息 DataOutputStream
					try {
						dos.writeUTF(msg);
						dos.flush();
					} catch (IOException e) {
						e.printStackTrace();
					}
					
				}
				
				//Close - 先開後放(Server不關)
				try {
					if(dis!=null) {
						dis.close();
					}
					
					if(dos!=null) {
						dos.close();
					}
					
					if(client!=null) {
						client.close();
					}
				} catch (IOException e) {

					e.printStackTrace();
				}

			}).start();
			
		}
	}
}
