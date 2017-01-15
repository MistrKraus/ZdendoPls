package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.stage.Stage;

import java.applet.Applet;
import java.io.File;
import java.net.URL;
import java.util.Random;

public class Main extends Application {

    private static URL[] sounds;
    private static Random random = new Random();
    private static int id = 3;

    private static void loadSounds() {
        File folder = new File("sounds");
        File[] listOfFiles = folder.listFiles();
        assert(listOfFiles != null);
        URL[] temp = new URL[listOfFiles.length];

        int j = 0;
        for (File file : listOfFiles) {
            if (file.isFile() && file.getName().endsWith(".wav"))
                try {
                    temp[j++] = new URL("file:" + file.getPath());
                } catch (Exception e) {
                    System.out.println("Chyba pri cteni souboru!");
                }
        }

        sounds = new URL[j];

        System.arraycopy(temp, 0, sounds, 0, j);
    }

    @Override
    public void start(Stage primaryStage) throws Exception{
        String image = new File("images/zdendoPls4.png").getPath();

        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));

        ImagePattern img = new ImagePattern(new Image(image));
        Scene scene = new Scene(root, 300, 275);
        scene.setFill(img);
        scene.setOnMouseClicked(event -> {
            try {
                int temp;
                do {
                    temp = random.nextInt(sounds.length);
                } while (temp == id);
                id = temp;

                Applet.newAudioClip(sounds[id]).play();
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        });

        primaryStage.setTitle("Zdendo, prosim!");
        primaryStage.setScene(scene);
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
