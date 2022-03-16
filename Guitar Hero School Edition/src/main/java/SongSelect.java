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
        buttons.add(new Button("Metallica - One", screenWidth / 2, screenHeight / 2 - 60, 20));
        buttons.add(new Button("Dragonforce - Troopers Of The Stars", screenWidth / 2, screenHeight / 2 - 30, 20));
        buttons.add(new Button("Foghat - Slow Ride", screenWidth / 2, screenHeight / 2, 20));
        buttons.add(new Button("DA-games - build our machine", screenWidth / 2, screenHeight / 2 + 30, 20));
        buttons.add(new Button("Motley Crue - kickstart my heart", screenWidth / 2, screenHeight / 2 + 60, 20));
        buttons.add(new Button("Journey - any way you want it", screenWidth / 2, screenHeight / 2 + 90, 20));
        buttons.add(new Button("BucketHead - Jordan", screenWidth / 2, screenHeight / 2 + 120, 20));
        buttons.add(new Button("BACK", screenWidth / 2, screenHeight / 2 + 160, 20));
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

        if (buttons.get(activeButtonIndex).btntxt.equals("Motley Crue - kickstart my heart")) {
            songStitch.loadSong("src/main/resources/Motley Crue - kickstart my heart");
            Game.playIsActive = true;
            Game.songSelectIsActive = false;
        }
        if (buttons.get(activeButtonIndex).btntxt.equals("Journey - any way you want it")) {
            songStitch.loadSong("src/main/resources/Journey - any way you want it");
            Game.playIsActive = true;
            Game.songSelectIsActive = false;
        }
        if (buttons.get(activeButtonIndex).btntxt.equals("BucketHead - Jordan")) {
            songStitch.loadSong("src/main/resources/BucketHead - Jordan");
            Game.playIsActive = true;
            Game.songSelectIsActive = false;
        }
        if (buttons.get(activeButtonIndex).btntxt.equals("Dragonforce - Troopers Of The Stars")) {
            songStitch.loadSong("src/main/resources/Dragonforce - Troopers Of The Stars");
            Game.playIsActive = true;
            Game.songSelectIsActive = false;
        }
        if (buttons.get(activeButtonIndex).btntxt.equals("Metallica - One")) {
            songStitch.loadSong("src/main/resources/Metallica - One");
            Game.playIsActive = true;
            Game.songSelectIsActive = false;
        }


    }
}
