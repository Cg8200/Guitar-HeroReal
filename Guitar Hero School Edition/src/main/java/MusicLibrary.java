import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;


/**
 * Created by cg8200 on 11/23/2021.
 */
public class MusicLibrary {
    Clip clip;



    public MusicLibrary() {


    }
    public void playSong(String pathname){
        Path currentRelativePath = Paths.get("");
        String path = currentRelativePath.toAbsolutePath().toString();
        try {
            URL file = new URL("file:" + pathname );
            AudioInputStream ais = AudioSystem.getAudioInputStream(file);
            clip = AudioSystem.getClip();
            clip.open(ais);
        } catch (Exception e) {
            e.printStackTrace();
        }

        clip.setFramePosition(0);
        clip.start();

    }


}





