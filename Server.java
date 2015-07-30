import java.io.*;
import java.net.*;
import java.util.ArrayList;

public class Server {
     
	ArrayList<ClientThread> cl;
	
	public static void main(String[] args) {
		Server serve = new Server();
		serve.start();
	}
	
	public void start() {
		cl = new ArrayList<ClientThread>();
		try {
			ServerSocket serveSock = new ServerSocket(8888);
			
			while(true) {
				System.out.println("Waiting for a connection...");
				Socket sock = serveSock.accept();			//accept incoming clients
				System.out.println("Connection made!");
				ClientThread ct = new ClientThread(sock, this);	//put client on a new thread
				cl.add(ct);		//add client to list
				
				ct.start();		//start the newly created clientThread
				
			}
		} catch(IOException ioe) {
			ioe.printStackTrace();
		}
		
	}
	
	public void showMessage(String s) {
		
		for (int i = 0; i < cl.size(); i++) {
			
			ClientThread ct = cl.get(i);
			
			try {
				ct.oos.writeObject(s);
				ct.oos.flush();
			} catch (IOException ioe) {
				ioe.printStackTrace();
			}
			
		}
		
	}
	
	public void remove(String s) {
		
		for (int i = 0; i < cl.size(); i++) {
			ClientThread ct = cl.get(i);
			if (ct.user.equals(s)) {
				cl.remove(i);
				ct.interrupt();
				showMessage("User: " + s + " Left the chat\n");
				break;
			}
		}
		
	}
}
