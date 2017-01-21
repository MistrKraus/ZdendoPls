package sample;

import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.jar.JarEntry;
import java.util.jar.JarInputStream;
import javafx.application.Application;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.scene.media.AudioClip;
import javafx.scene.paint.ImagePattern;
import javafx.stage.Stage;
import javax.imageio.ImageIO;

public class Main extends Application {

    private List<String> sounds = new ArrayList<>();
    private Random random = new Random();
    private int id = 3;

    private static List<String> getJarFileListing(String file, String filter) {
        List<String> files = new ArrayList<>();
        if (file == null) {
            return files; // Empty.
        }

        // Lets stream the jar file
        JarInputStream jarInputStream = null;
        try {
            jarInputStream = new JarInputStream(new FileInputStream(file));
            JarEntry jarEntry;

            // Iterate the jar entries within that jar. Then make sure it follows the
            // filter given from the user.
            do {
                jarEntry = jarInputStream.getNextJarEntry();
                if (jarEntry != null) {
                    String fileName = jarEntry.getName();

                    // The filter could be null or has a matching regular expression.
                    if (filter == null || fileName.matches(filter)) {
                        files.add("/" + fileName);
                    }
                }
            }
            while (jarEntry != null);
            jarInputStream.close();
        }
        catch (IOException ioe) {
            throw new RuntimeException("Unable to get Jar input stream from '" + file + "'", ioe);
        }
        return files;
    }

    private void loadSounds() {
        String path = getClass().getProtectionDomain().getCodeSource().getLocation().getPath();
        sounds = getJarFileListing(path, "^sounds/(.*).wav$");
    }

    @Override
    public void start(Stage primaryStage) throws Exception{
        loadSounds();
        BufferedImage bufferedImage = ImageIO.read(getClass().getResourceAsStream("/images/zdendoPls4.png"));
        Parent root = new StackPane();
        ImagePattern img = new ImagePattern(SwingFXUtils.toFXImage(bufferedImage, null));
        Scene scene = new Scene(root, 300, 275);
        scene.setFill(img);
        scene.setOnMouseClicked(event -> {
            try {
                int temp = Main.this.sounds.size();
                do {
                    temp = random.nextInt(temp);
                } while (temp == id);
                id = temp;

                String source = sounds.get(id);
                String url = getClass().getResource(source).toExternalForm();
                AudioClip audioClip = new AudioClip(url);
                audioClip.play();
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
