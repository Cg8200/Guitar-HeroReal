import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;

/**
 * Created by cg8200 on 10/22/2021.
 */
public class SongSelect extends MenuScreen {
    public SongSelect() {

        super();
        buttons.add(new Button("Foghat - Slow Ride", screenWidth / 2, screenHeight / 2, 20));
        buttons.add(new Button("DA-games - build our machine", screenWidth / 2, screenHeight / 2 + 30, 20));
        buttons.add(new Button("BACK", screenWidth / 2, screenHeight / 2 + 100, 20));
        try {
            image = ImageIO.read(new File("src/main/resources/songSelect.jpg"));
        } catch (IOException e) {
            e.printStackTrace();

        }
        setActiveButton();
    }
    public void Select() {
        if (buttons.get(activeButtonIndex).btntxt.equals("BACK")) {
            Game.songSelectIsActive = false;
            Game.mainMenuIsActive = true;
        }
        if (buttons.get(activeButtonIndex).btntxt.equals("Foghat - Slow Ride")) {
            songStitch.loadSong("src/main/resources/Foghat - Slow Ride");
            Game.playIsActive = true;
            Game.songSelectIsActive = false;
        }
        if (buttons.get(activeButtonIndex).btntxt.equals("DA-games - build our machine")) {
            songStitch.loadSong("src/main/resources/DAgames - build our machine");
            Game.playIsActive = true;
            Game.songSelectIsActive = false;
        }
       

    }
}
