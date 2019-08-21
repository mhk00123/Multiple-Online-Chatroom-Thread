package lili.com.chatroom;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * *�b�u��ѫ� : Chat Server ��
 * *v3.0�i���o�h���H��
 * *v4.0�[�J�h�u�{ - �h�Ȥ᦬�o
 * *v5.0�ʸ˦h�u�{����
 * *v6.0�[�J�e�� - ��q�D�[��e����
 * *v7.0�ʸ˥���o�e��k
 * *v8.0�[�J�Τ�W
 * *v8.1�[�J�w��H��
 * 
 * @author LiLi-PC
 *
 */
public class Chat_Server {
	//v6.0 - �[�J�e��
	private static CopyOnWriteArrayList<Channel> all = new CopyOnWriteArrayList<Channel>();
	
	public static void main(String[] args) throws IOException {
		System.out.println("--------Server--------");
		
		//1. ���wServer�ݤf - �ϥ�ServerSocket�ЫتA�Ⱦ�
		ServerSocket server = new ServerSocket(8888); //Throws
		
		//2. ���릡���ݳs�� accept - �Ы�Socket����
		while(true) {
			Socket client = server.accept();
			System.out.println("�@�ӫȤ�ݫإߤF�s��");			
			
			//v6.0 - �[�J�e��
			Channel c = new Channel(client);
			all.add(c);   //�H�e���޲z�Ҧ�����
			//�[�J�h�u�{(3+4+5)
			new Thread(c).start();
		}
	}

	//*�ʸ�Channel 
	//*1�ӫȤ� = 1��Channel
	static class Channel implements Runnable{
		private DataInputStream dis = null;
		private DataOutputStream dos = null;
		private boolean isRunning;
		private Socket client;
		//*v8.0�[�J�Τ�W
		private String name;
		
		//Constructor
		public Channel(Socket client) {
			this.client = client;
			
			try {
				dis = new DataInputStream(client.getInputStream());
				dos = new DataOutputStream(client.getOutputStream());
				//*v8.0�[�J�Τ�W
				this.name = receive();
				//*v8.1�[�J�w��H��
				this.send(this.name + "�i�J���A��");
				//��b�u�{�����H�s��
				sendOthers(this.name + "�i�J��ѫ�");
				
				isRunning = true;
				
			} catch (IOException e) {
				System.out.println("--------�c�ؿ��~--------");
				//�p�G�X���F�������� - ����귽
				release();
			}
		}
		
		@Override
		public void run() {
			while(isRunning) {
				//1. ��������
				String msg = receive();
				//�p���������Ť~�~��
				if(!msg.equals("")) {
					//2. �o�e����
					//v7.0 ����o�e
					sendOthers(msg);
				}
			}
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
		//2. �o�e����
		private void send(String msg) {
			try {
				dos.writeUTF(msg);
				dos.flush();
			} catch (IOException e) {
				System.out.println("--------�o�e���~--------");
				//�p�G�X���F�������� - ����귽
				release();
			}
		}
		
		//v7.0 - ����o�e
		private void sendOthers(String msg) {
			//���oChannel�Ҧ����� - �Ҧ����u�{������(�]�t�ۤv)
			for(Channel other:all) {
				//�s�o - ���εo���ۤv - ���B�O�d
				if(other == this) {
					continue;
				}
				//���B��send����L����(Channel)����send()
				other.send(this.name +"��:"+ msg);
			}
		}
		
		//3. ����귽
		private void release() {
			//��W����While����
			this.isRunning = false;
			//Close - ���}���(Server����)
			Utils.close(dos, dis, client);
		}
	}

}
