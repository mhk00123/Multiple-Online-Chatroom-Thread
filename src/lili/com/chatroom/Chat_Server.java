package lili.com.chatroom;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * *�b�u��ѫ� : Chat Server ��
 * *v3.0�i���o�h���H��
 * @author LiLi-PC
 *
 */
public class Chat_Server {
	public static void main(String[] args) throws IOException {
		System.out.println("--------Server--------");
		
		//1. ���wServer�ݤf - �ϥ�ServerSocket�ЫتA�Ⱦ�
		ServerSocket server = new ServerSocket(8888); //Throws
		
		//2. ���릡���ݳs�� accept - �Ы�Socket����
		Socket client = server.accept();
		System.out.println("�@�ӫȤ�ݫإߤF�s��");
		
		DataInputStream dis = new DataInputStream(client.getInputStream());
		DataOutputStream dos = new DataOutputStream(client.getOutputStream());
		
		boolean isRunning = true;
		while(isRunning){
			//3. �������� - DataInputStream
			String msg = dis.readUTF();
			
			//4. ��^���� DataOutputStream
			dos.writeUTF(msg);
			dos.flush();
		}
		
		//Close - ���}���(Server����)
		dis.close();
		dos.close();
		
	}
}
