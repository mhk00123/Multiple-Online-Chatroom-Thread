package lili.com.chatroom;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;

/**
 * **工具類
 * *接收端
 * @author LiLi-PC
 *
 */
public class Receive implements Runnable{
	private Socket client;
	private boolean isRunning;
	private DataInputStream dis;
	
	//Constructor
	public Receive(Socket client) {
		this.client = client;
		try {
			dis = new DataInputStream(client.getInputStream());
			isRunning = true;
		} catch (IOException e) {
			release();
		}
	}
	
	private void release() {
		this.isRunning = false;
		Utils.close(dis, client);
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
	
	public void run() {
		while(isRunning) {
			String msg = receive();
			if(!msg.equals("")) {
				System.out.println(msg);
			}
		}
	}
}
