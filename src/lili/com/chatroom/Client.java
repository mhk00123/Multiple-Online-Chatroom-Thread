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
 * *�b�u��ѫ� : Client ��
 * @author LiLi-PC
 *
 */
public class Client {
	public static void main(String[] args) throws UnknownHostException, IOException {
		System.out.println("--------Client--------");
		
		//1.     �PServer�ݫإ߳s�� - �ϥ�Socket
		Socket client = new Socket("localhost", 8888); //Throws
		
		//2.     �Ȥ�o�e�H�� - OutputStream
		//2.1  �ѱ���x��J
		BufferedReader console = new BufferedReader(new InputStreamReader(System.in));
		String msg = console.readLine();
		
		DataOutputStream dos = new DataOutputStream(client.getOutputStream());
		dos.writeUTF(msg);
		dos.flush();
		
		//3. ����^��message
		DataInputStream dis = new DataInputStream(client.getInputStream());
		msg = dis.readUTF();
		System.out.println(msg);
		
		//Close - ���}���
		dis.close();
		dos.close();
		client.close();
	}
}
