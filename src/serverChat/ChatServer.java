package serverChat;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class ChatServer {
    private static ServerSocket newServer;
    private static Socket newClient;
    private static DataInputStream inputData;
    private static DataOutputStream outputData;

    public static void createServer(int serverPort) throws IOException {
        TextArea serverChatWindow = (TextArea) Main.getMainScene().lookup("#serverChatWindow");
        TextField messageSendInput = (TextField) Main.getMainScene().lookup("#messageSendServer");
        Button sendMessage = (Button) Main.getMainScene().lookup("#btSendServer");
        serverChatWindow.setEditable(false);
        newServer = new ServerSocket(serverPort);

        new Thread(() -> {
            while(true){
                System.out.println("Czekam na połaczenie...");
            try {
                String receivedMessage = "";
                newClient = newServer.accept();
                inputData = new DataInputStream(newClient.getInputStream());
                System.out.println("Show me: " + inputData);
                outputData = new DataOutputStream(newClient.getOutputStream());
                while(!receivedMessage.equals("logout")){
                    receivedMessage = inputData.readUTF();
                    serverChatWindow.appendText(receivedMessage + "\n");
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
            }
        }).start();

        sendMessage.setOnAction(event -> {
            try {
                String messageToSend;
                messageToSend = messageSendInput.getText();
                serverChatWindow.appendText("Server: " + messageToSend + "\n");
                outputData.writeUTF("Server: " + messageToSend);
                messageSendInput.setText("");
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

    }

}
