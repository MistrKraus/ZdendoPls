package sample; /**
 * Created by kraus on 13.01.2017.
 */

import java.io.File;
import java.util.Random;
import java.net.URL;
import java.applet.*;

public class SoundsController {

    private static String path;
    private static URL[] sounds;
    private static Random random = new Random();

    private static int id = 3;

    private static final SoundsController INSTANCE = new SoundsController();

    private SoundsController() {
        this.path = "sounds";

        loadSounds();
    }

    public static SoundsController getSoundsController() {
        return INSTANCE;
    }

    private void loadSounds() {
        File folder = new File(path);
        File[] listOfFiles = folder.listFiles();


        URL[] temp = new URL[listOfFiles.length];

        int j = 0;
        for (int i = 0; i < listOfFiles.length; i++) {
            File file = listOfFiles[i];
            if (file.isFile() && file.getName().endsWith(".wav"))
                try {
                    temp[j++] = new URL("file:" + file.getPath());
                } catch (Exception e) {
                    System.out.println("Chyba pri cteni souboru!");
                }
        }

        sounds = new URL[j];

        for(int i = 0; i < j; i++) {
            sounds[i] = temp[i];
        }
    }

    public void playSound() {
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
    }
}
