package serverChat;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TextArea;

public class ChatServer {
    private static ServerSocket newServer;
    private static Socket newClient;

    public static void setServerWorkers(ArrayList<ServerWorker> serverWorkers) {
        ChatServer.serverWorkers = serverWorkers;
    }

    private static ArrayList<ServerWorker> serverWorkers = new ArrayList<>();

    public static ObservableList<String> getUsersListModel() {
        return usersListModel;
    }

    private static ObservableList<String> usersListModel = FXCollections.observableArrayList();

    public static ArrayList<ServerWorker> getServerWorkers() {
        return serverWorkers;
    }

    public void createServer(int serverPort) throws IOException {
        TextArea serverChatWindow = (TextArea) Main.getMainScene().lookup("#serverChatWindow");

        serverChatWindow.setEditable(false);
        serverChatWindow.setWrapText(true);

        newServer = new ServerSocket(serverPort);

        new Thread(() -> {
            serverChatWindow.appendText("Server is staring at: " + serverPort + " port!\n");
            while(true){
                try {
                serverChatWindow.appendText("Trying to accept client connection... \n");
                newClient = newServer.accept();
                serverChatWindow.appendText("New client are connected: " + newClient + "\n");
                ServerWorker worker = new ServerWorker(this, newClient);
                serverWorkers.add(worker);
                worker.start();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }

        }).start();

    }

}
