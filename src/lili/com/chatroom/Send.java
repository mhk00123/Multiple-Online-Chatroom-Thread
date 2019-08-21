package lili.com.chatroom;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

/**
 * *工具類
 * *發送端
 * @author LiLi-PC
 *
 */
public class Send implements Runnable{
	private Socket client;
	private boolean isRunning;
	private BufferedReader console;
	private DataOutputStream dos;
	//Constructor
	public Send(Socket client) {
		this.client = client;
		this.isRunning = true;
		
		console = new BufferedReader(new InputStreamReader(System.in));
		try {
			dos = new DataOutputStream(client.getOutputStream());
		} catch (IOException e) {
			e.printStackTrace();
			release();
			
		}
	}
	
	/**
	 * *從控制台取得msg
	 * @return
	 */
	private String getStringFromConsole() {
		try {
			return console.readLine();
		} catch (IOException e) {
			release();
		}
		return "";
	}
	
	private void release() {
		this.isRunning = false;
		Utils.close(dos, client);
	}
	
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
	
	@Override
	public void run() {
		while(isRunning) {
			String msg = getStringFromConsole();
			if(!msg.equals("")) {
				send(msg);
			}
		}
	}
}
