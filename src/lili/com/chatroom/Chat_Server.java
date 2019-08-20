package lili.com.chatroom;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * *�b�u��ѫ� : Chat Server ��
 * *v3.0�i���o�h���H��
 * *v4.0�[�J�h�u�{ - �h�Ȥ᦬�o
 * @author LiLi-PC
 *
 */
public class Chat_Server {
	public static void main(String[] args) throws IOException {
		System.out.println("--------Server--------");
		
		//1. ���wServer�ݤf - �ϥ�ServerSocket�ЫتA�Ⱦ�
		ServerSocket server = new ServerSocket(8888); //Throws
		
		//2. ���릡���ݳs�� accept - �Ы�Socket����
		while(true) {
			Socket client = server.accept();
			System.out.println("�@�ӫȤ�ݫإߤF�s��");
			
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
					//3. �������� - DataInputStream
					String msg = null;
					try {
						if(dis!=null) {
							msg = dis.readUTF();
						}
						
					} catch (IOException e) {
						e.printStackTrace();
						isRunning = false; //�X���F�N�n����u
					}
					
					//4. ��^���� DataOutputStream
					try {
						dos.writeUTF(msg);
						dos.flush();
					} catch (IOException e) {
						e.printStackTrace();
					}
					
				}
				
				//Close - ���}���(Server����)
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
