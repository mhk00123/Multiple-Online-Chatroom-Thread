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
 * *v3.0�i���o�h���H��
 * *v4.0�[�J�h�u�{ - �h�Ȥ᦬�o
 * *v5.0�ʸ˦h�u�{����
 * *v6.0�[�J�e�� - ��q�D�[��e����
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
		new Thread(new Send(client)).start();
		
		//3.  ���o����
		new Thread(new Receive(client)).start();

	}
}
