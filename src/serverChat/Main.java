package serverChat;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
    public static Scene getMainScene() {
        return mainScene;
    }

    private static Scene mainScene;
    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("view/serverGui.fxml"));
        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(root, 600, 400));
        mainScene = primaryStage.getScene();
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
