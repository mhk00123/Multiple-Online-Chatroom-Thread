package lili.com.chatroom;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * *�b�u��ѫ� : Chat Server ��
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
		
		
	}
}
