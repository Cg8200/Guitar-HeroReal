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
        buttons.add(new Button("Iron Maiden - Trooper", screenWidth / 2, screenHeight / 2, 20));
        buttons.add(new Button("BACK", screenWidth / 2, screenHeight / 2 + 70, 20));
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
        if (buttons.get(activeButtonIndex).btntxt.equals("Iron Maiden - Trooper")) {
            PlayScreen.loadSong("src/main/resources/Iron Maiden - Trooper.txt");
            Game.playIsActive = true;
            Game.songSelectIsActive = false;
        }
    }
}
