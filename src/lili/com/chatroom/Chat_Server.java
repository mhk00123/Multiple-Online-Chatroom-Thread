package lili.com.chatroom;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * *在線聊天室 : Chat Server 端
 * *v3.0可收發多條信息
 * *v4.0加入多線程 - 多客戶收發
 * *v5.0封裝多線程物件
 * *v6.0加入容器 - 把通道加到容器中
 * *v7.0封裝全體發送方法
 * *v8.0加入用戶名
 * *v8.1加入歡迎信息
 * *v8.2封裝系統消息
 * 
 * @author LiLi-PC
 *
 */
public class Chat_Server {
	//v6.0 - 加入容器
	private static CopyOnWriteArrayList<Channel> all = new CopyOnWriteArrayList<Channel>();
	
	public static void main(String[] args) throws IOException {
		System.out.println("--------Server--------");
		
		//1. 指定Server端口 - 使用ServerSocket創建服務器
		ServerSocket server = new ServerSocket(8888); //Throws
		
		//2. 阻塞式等待連接 accept - 創建Socket物件
		while(true) {
			Socket client = server.accept();
			System.out.println("一個客戶端建立了連接");			
			
			//v6.0 - 加入容器
			Channel c = new Channel(client);
			all.add(c);   //以容器管理所有成員
			//加入多線程(3+4+5)
			new Thread(c).start();
		}
	}

	//*封裝Channel 
	//*1個客戶 = 1個Channel
	static class Channel implements Runnable{
		private DataInputStream dis = null;
		private DataOutputStream dos = null;
		private boolean isRunning;
		private Socket client;
		//*v8.0加入用戶名
		private String name;
		
		//Constructor
		public Channel(Socket client) {
			this.client = client;
			
			try {
				dis = new DataInputStream(client.getInputStream());
				dos = new DataOutputStream(client.getOutputStream());
				//*v8.0加入用戶名
				this.name = receive();
				//*v8.1加入歡迎信息
				this.send("歡迎" + this.name + "進入伺服器");
				//對在線程中的人廣播
				sendOthers(this.name + "進入聊天室", true);
				
				isRunning = true;
				
			} catch (IOException e) {
				System.out.println("--------構建錯誤--------");
				//如果出錯了直接結束 - 釋放資源
				release();
			}
		}
		
		@Override
		public void run() {
			while(isRunning) {
				//1. 接收消息
				String msg = receive();
				//如接收不為空才繼續
				if(!msg.equals("")) {
					//2. 發送消息
					//v7.0 全體發送
					sendOthers(msg, false);
				}
			}
		}
		
		//1. 接收消息
		private String receive() {
			String msg = "";
			try {
				msg = dis.readUTF();
			} catch (IOException e) {
				System.out.println("--------接收錯誤--------");
				//如果出錯了直接結束 - 釋放資源
				release();
			}
			return msg;
		}
		//2. 發送消息
		private void send(String msg) {
			try {
				dos.writeUTF(msg);
				dos.flush();
			} catch (IOException e) {
				System.out.println("--------發送錯誤--------");
				//如果出錯了直接結束 - 釋放資源
				release();
			}
		}
		
		//v7.0 - 全體發送
		private void sendOthers(String msg, boolean isSys) {
			//取得Channel所有成員 - 所有有線程的成員(包含自己)
			for(Channel other:all) {
				//群發 - 不用發給自己 - 此處保留
				if(other == this) {
					continue;
				}
				
				//v8.2封裝系統消息
				if(isSys) {
					//此處的send為其他成員(Channel)里的send()
					other.send(msg);	
				}
				else if(!isSys){
					//此處的send為其他成員(Channel)里的send()
					other.send(this.name +"說:"+ msg);	
				}
			}
		}
		
		//3. 釋放資源
		private void release() {
			//把上面的While停止
			this.isRunning = false;
			//Close - 先開後放(Server不關)
			Utils.close(dos, dis, client);
		}
	}

}
