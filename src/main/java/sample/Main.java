package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.stage.Stage;

import java.io.File;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        String image = new File("images/zdendoPls4.png").getPath();

        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));

        ImagePattern img = new ImagePattern(new Image("file:"+image));
        Scene scene = new Scene(root, 300, 275);
        scene.setFill(img);
        scene.setOnMouseClicked(event -> SoundsController.getSoundsController().playSound());

        primaryStage.setTitle("Zdendo, prosim!");
        primaryStage.setScene(scene);
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
