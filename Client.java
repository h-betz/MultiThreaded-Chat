import java.io.*;
import java.net.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Client extends JFrame {

	private Socket sock;
	private ObjectOutputStream oos;
	private ObjectInputStream ois;
	private String user;
	private listenThread lt;
	private JTextField userText;
	private JTextArea chatWindow;
	
	public static void main(String[] args) {
		
		String s = (String) JOptionPane.showInputDialog("Enter user name: ", "Customized Dialog");
		Client c = new Client(s);
		c.start();
		
	}
	
	public Client(String s) {
		
		super("Client - Chat");
		this.user = s;
		
		userText = new JTextField();
		userText.setColumns(25);
        userText.setEditable(false);
        userText.addActionListener(
             
            new ActionListener() {
                public void actionPerformed(ActionEvent event) {
                    sendMessage(event.getActionCommand());
                    userText.setText("");
                }
            }
             
        );
        add(userText, BorderLayout.NORTH);
     
        chatWindow = new JTextArea(5, 30);
        JScrollPane scrollPane = new JScrollPane(chatWindow);
        
        add(scrollPane, BorderLayout.SOUTH);
        setSize(450, 200);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
	}
	
	//initialize contact with server and setup streams
	public void start() {
		
		userText.setEditable(true);
		
		try {
			
			sock = new Socket("127.0.0.1", 8888);
			
			oos = new ObjectOutputStream(sock.getOutputStream());
			ois = new ObjectInputStream(sock.getInputStream());
			sendMessage(user);
			lt = new listenThread(ois, this);
			lt.start();
			sendMessage("has connected!\n");
			
		} catch(IOException ioe) {
			ioe.printStackTrace();
		} 
	}
	
	
	//send client's message
	private void sendMessage(String msg) {
		
		if (msg.equals("END")) {
			try {
				oos.writeObject(msg);
			} catch(IOException ioe) {
				ioe.printStackTrace();
			}
			lt.interrupt();
		}
		
		msg = user + ">" + msg + "\n";
		try {
			oos.writeObject(msg);
		} catch(IOException ioe) {
			ioe.printStackTrace();
		}
		
	}
	
	public void showMessage(String msg) {
		
		chatWindow.append(msg);
		
	}

	
}
