package lili.com.chatroom;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * *在線聊天室 : Client 端
 * @author LiLi-PC
 *
 */
public class Client {
	public static void main(String[] args) throws UnknownHostException, IOException {
		System.out.println("--------Client--------");
		
		//1. 與Server端建立連接 - 使用Socket
		Socket client = new Socket("localhost", 8888); //Throws
	}
}
