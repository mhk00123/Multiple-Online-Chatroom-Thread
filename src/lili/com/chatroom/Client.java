package lili.com.chatroom;

import java.io.IOException;
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
		
		//1. �PServer�ݫإ߳s�� - �ϥ�Socket
		Socket client = new Socket("localhost", 8888); //Throws
	}
}
