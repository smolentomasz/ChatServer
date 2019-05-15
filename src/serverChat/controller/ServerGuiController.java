package serverChat.controller;

import java.io.IOException;
import java.net.ServerSocket;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import serverChat.ChatServer;

public class ServerGuiController {

    @FXML
    private TextField serverPortLabel;
    @FXML
    private TextField messageSendServer;
    @FXML
    private Button btRunServer;

    @FXML
    public void startServer() throws IOException {
        int enteredPort = Integer.valueOf(serverPortLabel.getText());
        int acctualPort = isPortTaken(enteredPort);

        System.out.println("Server is staring at: " + acctualPort + " port!\n");

        ChatServer.createServer(acctualPort);
        btRunServer.setDisable(true);
        serverPortLabel.setEditable(false);
    }

    private static int isPortTaken(int port) throws IOException {
        int newPort = port;
        boolean isTaken = false;
        ServerSocket checkSocket = null;
        while(!isTaken){
            try{
                checkSocket = new ServerSocket(newPort);
            } catch (IOException e) {
                isTaken = true;
                System.out.println("Choosen port is already not available!\n");
            }finally{
                if(checkSocket != null){
                    checkSocket.close();
                    return newPort;
                }
            }
            newPort++;
        }
        return newPort;
    }
}
