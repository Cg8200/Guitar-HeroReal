import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by cg8200 on 10/22/2021.
 */
public class MainMenu extends MenuScreen{


    public MainMenu() {
        super();
        buttons.add( new Button("Quick Play", screenWidth/2, screenHeight/2,20 ));
        buttons.add( new Button("Options", screenWidth/2, screenHeight/2+40,20));
        buttons.add( new Button("Credits", screenWidth/2, screenHeight/2+80,20));
        buttons.add( new Button("Quit", screenWidth/2, screenHeight/2+120,20));
        try {
            image = ImageIO.read(new File("src/main/resources/mainMenuPic.jpg"));
        } catch (IOException e) {
            e.printStackTrace();

        }
        setActiveButton();
    }


    public void Select() {
        if (buttons.get(activeButtonIndex).btntxt.equals("Quick Play")) {
            Game.songSelectIsActive = true;
            Game.mainMenuIsActive = false;
        }
        if (buttons.get(activeButtonIndex).btntxt.equals("Quit")) {
            System.exit(1);
        }
        if (buttons.get(activeButtonIndex).btntxt.equals("Credits")) {
            Game.creditsIsActive = true;
            Game.mainMenuIsActive = false;
        }
    }
}
