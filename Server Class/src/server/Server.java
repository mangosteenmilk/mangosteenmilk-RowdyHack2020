package server;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import javax.swing.*;

@SuppressWarnings("serial")
public class Server extends JPanel{
	
	private JFrame frame = new JFrame("Server");
	private JButton sendButton = new JButton();
	private JLabel status = new JLabel();
	private JTextField textField = new JTextField();
	private JTextArea textArea = new JTextArea();
	
	
	
	
	private ObjectOutputStream output;
	private ObjectInputStream input;
	private Socket connection;
	private ServerSocket server;
	private int totalClients = 100;
	private int port = 6789;
	  

	
	
	public static void main(String[] args) {																					//main

		EventQueue.invokeLater(new Runnable() {																					//Causes runnable to have its run method called
		public void run() {
		try {																													//try catch 
		Server window = new Server();																								//creates Main object
		window.frame.setVisible(true);
		} catch (Exception e) {
		e.printStackTrace();}}});
	}

	public Server() {	
		mainPage();


	}
		
	
	private void mainPage() {
		Font font = new Font("Franklin Gothic Medium Cond", Font.BOLD,20);
		setSize(300,400);
		frame = new JFrame();
		frame.setBounds(100, 100, 300, 400);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		
		frame.getContentPane().setBackground(new Color (241,90,34));
		
		
		
		status.setFont(font);
		status.setForeground(Color.WHITE);
		status.setBounds(20, 15, 300, 80);
		frame.getContentPane().add(status);
		
		sendButton.setBorder(BorderFactory.createEmptyBorder(0,0,0,0));
		sendButton.setMargin(new Insets(0, 0, 0, 0));
		sendButton.setText("send");
		sendButton.setBounds(200, 300, 50, 20);
		sendButton.setRolloverEnabled(false);
		sendButton.addActionListener(new ButtonListener());
		frame.getContentPane().add(sendButton);
		
		
		textField.setBounds(40, 300, 150, 20);
		frame.getContentPane().add(textField);
		
		textArea.setBounds(40, 80, 210, 200);
		textArea.setEditable(false);
		textArea.setLineWrap(true);
		textArea.setWrapStyleWord(true);
		frame.getContentPane().add(textArea);
		
	}
	
	public class ButtonListener implements ActionListener												//button listener
	{
		public void actionPerformed (ActionEvent x){
			if (x.getSource() == sendButton){																				//if user clicked on rules button 
				sendMessage(textField.getText());
				textField.setText("");
			}
		}

		

	}
	

	public void startRunning()
    {
        try
        {
            server=new ServerSocket(port, totalClients);
            while(true)
            {
                try
                {
                    status.setText(" Waiting for Someone to Connect...");
                    connection=server.accept();
                    status.setText(" Now Connected to "+connection.getInetAddress().getHostName());


                    output = new ObjectOutputStream(connection.getOutputStream());
                    output.flush();
                    input = new ObjectInputStream(connection.getInputStream());

                    whileChatting();

                }catch(EOFException eofException)
                {
                }
            }
        }
        catch(IOException ioException)
        {
                ioException.printStackTrace();
        }
    }

	
	public void startRunningg() {		
		try {
			server= new ServerSocket(port, totalClients);
			while(true) {
				status.setText("Waiting for connection");
				connection=server.accept();
				status.setText("Connected"+connection.getInetAddress().getHostName());
				
				output = new ObjectOutputStream(connection.getOutputStream());
				output.flush();
				input = new ObjectInputStream(connection.getInputStream());
				
				whileChatting();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	
	private void whileChatting() throws IOException
   {
        String message="";    
        do{
                try
                {
                        message = (String) input.readObject();
                        textArea.append("\n"+message);
                }catch(ClassNotFoundException classNotFoundException)
                {
                        
                }
        }while(!message.equals("Person 2 - END"));
   }
	
	private void sendMessage(String message)
    {
        try
        {
            output.writeObject("Person 1 - " + message);
            output.flush();
            textArea.append("\nPerson 1 - "+ message);
        }
        catch(IOException ioException)
        {
            textArea.append("\n Unable to Send Message");
        }
    }
	
	
	
}
	


