package serverChat;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.List;

import javafx.application.Platform;
import javafx.scene.control.ListView;


public class ServerWorker extends Thread{

    private final Socket clientSocket;
    private final ChatServer chatServer;
    private DataOutputStream outputData;

    private ListView usersActive = (ListView) Main.getMainScene().lookup("#usersList");

    public String getClientName() {
        return clientName;
    }

    private String clientName;


    public Socket getClientSocket() {
        return clientSocket;
    }

    ServerWorker(ChatServer chatServer, Socket clientSocket){
        this.clientSocket = clientSocket;
        this.chatServer = chatServer;
    }

    @Override
    public void run(){
        try {
            handleClient();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void handleClient() throws IOException {

        String receivedMessage = "string";
        DataInputStream inputData = new DataInputStream(clientSocket.getInputStream());
        this.clientName = inputData.readUTF();
        System.out.println(clientName);
        this.outputData = new DataOutputStream(clientSocket.getOutputStream());
        outputData.writeUTF(clientName + " you are connected to the server succesfully!");
        Platform.runLater(() -> updateUsersList("add", clientName));

              while(!receivedMessage.equals("")){
                  if(receivedMessage.equals(clientName + ": logout")){
                      Platform.runLater(() -> updateUsersList("del", clientName));
                      break;
                  }
                  else{
                      receivedMessage = inputData.readUTF();
                      handleMessage(receivedMessage);
                  }

        }
        clientSocket.close();
    }

    private void updateUsersList(String token, String clientName) {
        if(token.equals("add")){
            ChatServer.getUsersListModel().add(clientName);
            usersActive.setItems(ChatServer.getUsersListModel());
        }
        else{
            ChatServer.getUsersListModel().remove(clientName);
            usersActive.setItems(ChatServer.getUsersListModel());
        }

    }

    private void handleMessage(String message) throws IOException {
        List<ServerWorker> workersList = ChatServer.getServerWorkers();
        for(ServerWorker worker : workersList){
            worker.send(message);
        }
    }

    private void send(String message) throws IOException {
        outputData.writeUTF(message);
    }
}
