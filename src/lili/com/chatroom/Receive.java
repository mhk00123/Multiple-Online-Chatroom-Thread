package lili.com.chatroom;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;

/**
 * **�u����
 * *������
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
	
	//1. ��������
	private String receive() {
		String msg = "";
		try {
			msg = dis.readUTF();
		} catch (IOException e) {
			System.out.println("--------�������~--------");
			//�p�G�X���F�������� - ����귽
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
