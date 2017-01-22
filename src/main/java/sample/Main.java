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
    private boolean bgIsDefault;
    private int id = 3;

    private static List<String> getJarFileListing(String file, String filter) {
        List<String> files = new ArrayList<>();
        if (file == null) {
            return files; // Empty.
        }

        // Lets stream the jar file
        try (JarInputStream jarInputStream = new JarInputStream(new FileInputStream(file))) {
            JarEntry jarEntry = jarInputStream.getNextJarEntry();
            while(jarEntry != null) {
                String fileName = jarEntry.getName();
                if (filter == null || fileName.matches(filter)) {
                    files.add("/" + fileName);
                }

                jarEntry = jarInputStream.getNextJarEntry();
            }
        } catch (IOException ex) {
            throw new RuntimeException("Unable to get Jar input stream from '" + file + "'", ex);
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

        ImagePattern[] img = new ImagePattern[2];
        BufferedImage bufferedImage = ImageIO.read(getClass().getResourceAsStream("/images/default.png"));
        img[0] = new ImagePattern(SwingFXUtils.toFXImage(bufferedImage, null));
        bufferedImage = ImageIO.read(getClass().getResourceAsStream("/images/selected.png"));
        img[1] = new ImagePattern(SwingFXUtils.toFXImage(bufferedImage, null));

        Parent root = new StackPane();

        Scene scene = new Scene(root, 300, 275);
        scene.setFill(img[0]);
        bgIsDefault = true;

        scene.setOnMouseClicked(event -> {
            if (mouseInRadius(event.getX(), event.getY(), scene))
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

        scene.setOnMouseMoved(event -> {
            if (mouseInRadius(event.getX(), event.getY(), scene)) {
                if (bgIsDefault) {
                    scene.setFill(img[1]);
                    bgIsDefault = false;
                }
            } else {
                if (!bgIsDefault) {
                    scene.setFill(img[0]);
                    bgIsDefault = true;
                }
            }
        });

        primaryStage.setTitle("Zdendo, prosim!");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private boolean mouseInRadius(double x, double y,Scene scene) {
        if ((y > (20 * scene.getHeight()) / 275) && (y < (110 * scene.getHeight()) / 275) &&
                (x > (20 * scene.getWidth()) / 300) && (x < (280 * scene.getWidth()) / 300))
            return true;
        return false;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
