package sample;

import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.StackPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.ImagePattern;
import javafx.stage.Stage;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class Main extends Application {

    private List<URL> sounds = new ArrayList<>();
    private Random random = new Random();
    private int id = 3;

    private void loadSounds() {
        File tmp = new File(new File("").getAbsolutePath() + "/sounds");
        System.out.println(tmp.getAbsolutePath());
        File[] listOfFiles = new File(tmp.getPath()).listFiles();
        assert(listOfFiles != null);

        Arrays.stream(listOfFiles)
                .filter(file -> file.getName().endsWith(".wav"))
                .forEach(file -> {
                    try {
                        sounds.add(new URL("file:" + file.getPath()));
                    } catch (Exception e) {
                        System.out.println("Chyba pri cteni souboru!");
                    }
                });
    }

    @Override
    public void start(Stage primaryStage) throws Exception{
        loadSounds();
        String image = new File("images/zdendoPls4.png").getPath();
        Parent root = new StackPane();
        ImagePattern img = new ImagePattern(new Image(image));
        Scene scene = new Scene(root, 300, 275);
        scene.setFill(img);
        scene.setOnMouseClicked(event -> {
            try {
                int temp = Main.this.sounds.size();
                do {
                    temp = random.nextInt(temp);
                } while (temp == id);
                id = temp;

                Media media = new Media(Main.this.sounds.get(id).getPath());
                MediaPlayer mediaPlayer = new MediaPlayer(media);
                mediaPlayer.play();
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
