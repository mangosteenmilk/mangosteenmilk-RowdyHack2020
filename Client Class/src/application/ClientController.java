package application;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

import javafx.scene.control.TextField;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.URL;
import java.rmi.UnknownHostException;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;

import javafx.scene.control.Label;

import javafx.scene.control.TextArea;

public class ClientController implements Initializable {
	@FXML
	private TextArea textArea;
	@FXML
	private TextField textField;
	@FXML
	private Button buttonSend, buttonConnect;
	@FXML
	private Label status;

	private ObjectOutputStream output;
	private ObjectInputStream input;
	private String message = "";
	private String serverIP;
	private Socket connection;
	private int port = 6789;

	@FXML
	public void handleButtonConnect(ActionEvent event) {
		status.setText("Trying to connect...");
		buttonConnect.setVisible(false);
		try {
			connection = new Socket(InetAddress.getByName(serverIP), port);
			status.setText("Connected: " + connection.getInetAddress().getHostName());
			output = new ObjectOutputStream(connection.getOutputStream());
			output.flush();
			input = new ObjectInputStream(connection.getInputStream());
			textField.setDisable(false);
			buttonSend.setDisable(false);
		} catch (UnknownHostException e) {
			status.setText("Unknown Host");
			e.printStackTrace();
		} catch (IOException e) {
			status.setText("Inout Output error");
			e.printStackTrace();
		}

	}

	@FXML
	public void handleButtonSend(ActionEvent event) throws IOException, ClassNotFoundException {
		message = textField.getText();
		textField.setText("");
		sendMessage(message);

	}

	private void sendMessage(String message) {
		try {
			output.writeObject("Person 2:" + message);
			output.flush();

			try {
				whileChatting();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}

			textArea.appendText("\nPerson 2:" + message);
		} catch (IOException e) {
			System.out.println("Error");
			e.printStackTrace();
		}
	}

	private void whileChatting() throws IOException, ClassNotFoundException {
		message = (String) input.readObject();
		textArea.appendText("\n" + message);

	}

	@Override
	public void initialize(URL url, ResourceBundle rb) {
		serverIP = "192.168.0.20";
		textArea.setEditable(false);
		textField.setDisable(true);
		buttonSend.setDisable(true);

		
		 buttonConnect.setVisible(false); buttonSend.setVisible(false);
		 textArea.setVisible(false); textField.setVisible(false);
		 
	}

}
