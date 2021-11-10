import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;

/**
 * Created by cg8200 on 10/29/2021.
 */
public class Credits extends MenuScreen {
    Button textbtn;
    public Credits() {
        super();
        textbtn = (new Button("MADE BY CADEN GODWIN AND DAVID BAKER", screenWidth / 2 - 150, screenHeight / 2, 20));
        textbtn.isSelected = false;
        buttons.add(new Button("BACK", screenWidth / 2, screenHeight / 2 + 70, 20));
        try {
            image = ImageIO.read(new File("src/main/resources/mainMenuPic.jpg"));
        } catch (IOException e) {
            e.printStackTrace();

        }
        setActiveButton();
    }
    public void draw(Graphics g){
        super.draw(g);
      textbtn.Draw(g);
    }
    public void Select() {
        if (buttons.get(activeButtonIndex).btntxt.equals("BACK")) {
            Game.creditsIsActive = false;
            Game.mainMenuIsActive = true;
        }
    }
}
