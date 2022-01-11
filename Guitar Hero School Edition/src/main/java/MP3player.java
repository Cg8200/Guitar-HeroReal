import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.util.Scanner;
import javazoom.jl.player.Player;

/**
 * Created by cg8200 on 1/7/2022.
 */
public class MP3player {
    private final String mp3FileToPlay;
    private Player jlPlayer;

    public MP3player(String mp3FileToPlay) {
        this.mp3FileToPlay = mp3FileToPlay;
    }

    public void play() {
        try {
            FileInputStream fileInputStream = new FileInputStream(mp3FileToPlay);
            BufferedInputStream bufferedInputStream = new BufferedInputStream(fileInputStream);
            jlPlayer = new Player(bufferedInputStream);
        } catch (Exception e) {
            System.out.println("Problem playing mp3 file " + mp3FileToPlay);
            System.out.println(e.getMessage());
        }

        new Thread() {
            public void run() {
                try {
                    jlPlayer.play();
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            }
        }.start();


    }

    public void close() {
        if (jlPlayer != null) jlPlayer.close();
    }
}


