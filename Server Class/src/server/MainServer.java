package server;

import java.awt.Insets;
import java.io.*;
import java.net.*;
import java.awt.Color;
import java.awt.Graphics;  
import java.awt.Image;  
import java.awt.Toolkit;
import javax.swing.*;


public class MainServer extends JFrame {

    private ObjectOutputStream output;
    private ObjectInputStream input;
    private Socket connection;
    private ServerSocket server;
    private int totalClients = 100;
    private int port = 2020;
  

    private JTextField textField;
    private JTextArea textArea;
    private JButton sendButton;
    private JLabel status;
    private JPanel panel;
    
    private JLabel labelTextField;
    
    
    public MainServer() {
        
        initComponents();
        this.setTitle("Class Chatroom");
        this.setVisible(true);
        setBounds(100, 100, 315, 440);  
        setResizable(false);
       
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
    
   private void whileChatting() throws IOException
   {
        String message="";    
        textField.setEditable(true);
        do{
                try
                {
                        message = (String) input.readObject();
                        textArea.append("\n"+message);
                }catch(ClassNotFoundException classNotFoundException)
                {
                        
                }
        }while(!message.equals("Student 2 - END"));
   }

    @SuppressWarnings("unchecked")
    private void initComponents() {

        textArea = new JTextArea();
        textField = new JTextField();
        sendButton = new JButton();
        status = new JLabel();
        labelTextField = new JLabel();
        
        panel = new JPanel() {  
            public void paintComponent(Graphics g) {  
               Image img = Toolkit.getDefaultToolkit().getImage(  
                   MainServer.class.getResource("/images/bg.jpg"));  
               g.drawImage(img, 0, 0, this.getWidth(), this.getHeight(), this);  
         }  
      }; 
        
        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        panel.setLayout(null);

        textArea.setColumns(20);
        textArea.setRows(5);
		textArea.setBounds(40, 120, 210, 200);
		textArea.setLineWrap(true);
		textArea.setWrapStyleWord(true);
		panel.add(textArea);

        textField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField1ActionPerformed(evt);
            }
        });
        panel.add(textField);
		textField.setBounds(40, 338, 150, 20);

		sendButton.setBorder(BorderFactory.createEmptyBorder(0,0,0,0));
		sendButton.setMargin(new Insets(0, 0, 0, 0));
        sendButton.setText("Send");
        sendButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
		sendButton.setBounds(200, 338, 50, 20);
        panel.add(sendButton);

        status.setText("...");
        status.setForeground(Color.WHITE);
        panel.add(status);
        status.setBounds(38, 90, 300, 40);

        labelTextField.setForeground(new java.awt.Color(255, 255, 255));
        labelTextField.setText("Write your text here");
        panel.add(labelTextField);
        labelTextField.setBounds(45, 338, 150, 20);


        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panel, javax.swing.GroupLayout.PREFERRED_SIZE, 417, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panel, javax.swing.GroupLayout.DEFAULT_SIZE, 403, Short.MAX_VALUE)
        );

        setSize(new java.awt.Dimension(417, 425));
        setLocationRelativeTo(null);
    }

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {
        
        sendMessage(textField.getText());
        textField.setText("");
    }

    private void jTextField1ActionPerformed(java.awt.event.ActionEvent evt) {
        
        sendMessage(textField.getText());
        textField.setText("");
    }

    private void sendMessage(String message)
    {
        try
        {
            output.writeObject("Student 1 - " + message);
            output.flush();
            textArea.append("\nStudent 1 - "+message);
        }
        catch(IOException ioException)
        {
            textArea.append("\n Unable to Send Message");
        }
    }
}
