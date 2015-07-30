import java.io.IOException;
import java.io.ObjectInputStream;

public class listenThread extends Thread {
	
	ObjectInputStream ois;
	Client cli;
	
	public listenThread(ObjectInputStream in, Client c) {
		
		this.ois = in;
		this.cli = c;
		
	}
	
	public void run() {

		while(true) {
			try {
				String message = (String) ois.readObject();
				cli.showMessage(message);
			} catch (IOException ioe) {
				cli.showMessage("Connection ended.");
				break;
			} catch (ClassNotFoundException cnfe) {
				cnfe.printStackTrace();
			} 
		}
	}
		

}
