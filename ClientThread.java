import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ClientThread extends Thread {
	
	Socket sock;
	Server serve;
	ObjectOutputStream oos;
	ObjectInputStream ois;
	String message;
	String user;
	
	ClientThread(Socket s, Server server) {
		
		this.sock = s;
		this.serve = server;
		
		try {
			oos = new ObjectOutputStream(sock.getOutputStream());
			ois = new ObjectInputStream(sock.getInputStream());
			user = (String) ois.readObject();
		} catch(IOException ioe) {
			ioe.printStackTrace();
		} catch(ClassNotFoundException cnfe) {
			cnfe.printStackTrace();
		}
		
	}
	
	public void run() {
		while(true) {
			
			try {
				message = (String) ois.readObject();
				if (message.equals("END")) {
					close();
					break;
				}
				showMessage(message);
				sendMessage(message);
			} catch (IOException ioe) {
				ioe.printStackTrace();
			} catch (ClassNotFoundException cnfe) {
				cnfe.printStackTrace();
			}
			
		}
		
	}
	
	public void showMessage(String s) {
		
		System.out.println(message);
		
	}
	
	public void sendMessage(String s) {
		
		serve.showMessage(s);
		
		
	}
	
	private void close() {
		
		System.out.println("Closing connection.");
		
		try {
            
            ois.close();
            oos.close();
            sock.close();
            serve.remove(user);
             
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
	}
	
}
